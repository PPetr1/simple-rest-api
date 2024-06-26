package services;

import dtos.*;
import enums.Gender;
import exceptions.ApiBadRequestException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import models.Person;

@Stateless
public class PersonServiceImpl implements PersonService {

  @PersistenceContext(name = "PersonManager")
  private EntityManager entityManager;

  @Override
  public PersonResponseDTO createPerson(CreatePersonRequestDTO requestDTO) {
    validateCreatePersonRequestDTO(requestDTO);
    return savePersonQuery(
        new Person(
            requestDTO.getName(),
            birthdayStringParseToLocalDate(requestDTO.getBirthday()),
            Gender.valueOf(requestDTO.getGender().toUpperCase())));
  }

  @Override
  public PersonResponseDTO savePersonQuery(Person person) {
    entityManager.persist(person);
    entityManager.flush();
    return personToPersonResponseDTO(person);
  }

  @Override
  public void validateCreatePersonRequestDTO(CreatePersonRequestDTO requestDTO) {
    if (requestDTO.getName() == null
        || requestDTO.getGender() == null
        || requestDTO.getBirthday() == null) {
      throwMissingField();
    }
    validateNameFormat(requestDTO.getName());
    validateBirthdayFormat(requestDTO.getBirthday());
    validateGenderFormat(requestDTO.getGender());
  }

  @Override
  public LocalDate birthdayStringParseToLocalDate(String birthday) {
    String[] arr = birthday.split("\\.");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    try {
      return LocalDate.parse(birthday, formatter);
    } catch (DateTimeParseException e) {
      throw new ApiBadRequestException("Birthday needs to be in format:DD.MM.YYYY");
    }
  }

  @Override
  public void validateGenderFormat(String gender) {
    if (gender.isEmpty()) {
      throw new ApiBadRequestException("Gender is empty");
    } else if (!isValidGender(gender)) {
      throw new ApiBadRequestException("Gender can only be MALE,FEMALE or OTHER");
    }
  }

  @Override
  public boolean isValidGender(String gender) {
    for (Gender genderEnum : Gender.values()) {
      if (genderEnum.name().equalsIgnoreCase(gender)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void validateBirthdayFormat(String birthday) {
    if (birthday.isEmpty()) {
      throw new ApiBadRequestException("Birthday cannot be empty");
    } else if (!isValidBirthdayPattern(birthday)) {
      throw new ApiBadRequestException("Birthday needs to be in format:DD.MM.YYYY");
    } else if (LocalDate.now().isBefore(birthdayStringParseToLocalDate(birthday))) {
      throw new ApiBadRequestException("Person is not born yet");
    }
  }

  @Override
  public Boolean isValidBirthdayPattern(String birthday) {
    return Pattern.compile("\\b(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.(\\d{4})\\b")
        .matcher(birthday)
        .matches();
  }

  @Override
  public void validateNameFormat(String name) {
    if (name.isEmpty()) {
      throw new ApiBadRequestException("Name cannot be empty");
    } else if (!isValidNamePattern(name)) {
      throw new ApiBadRequestException("Name cannot contain numbers or symbols");
    } else if (name.length() > 50) {
      throw new ApiBadRequestException("Name can have 50 characters at maximum");
    }
  }

  @Override
  public boolean isValidNamePattern(String name) {
    return Pattern.compile("^[a-zA-Z ěĚšŠčČřŘžŽýÝáÁíÍéÉůŮúÚöÖ]+$").matcher(name).matches();
  }

  @Override
  public void deletePerson(DeletePersonRequestDTO requestDTO) {
    validateDeletePersonRequestDTO(requestDTO.getPersonId());
    Optional<Person> optionalPerson = findPersonById(requestDTO.getPersonId());
    if (optionalPerson.isPresent()) {
      deletePersonQuery(optionalPerson.get());
    } else {
      throwProvideValidPersonId();
    }
  }

  @Override
  public void deletePersonQuery(Person person) {
    entityManager.remove(person);
  }

  @Override
  public void validateDeletePersonRequestDTO(long personId) {
    if (personId == 0) {
      throwProvideValidPersonId();
    }
  }

  @Override
  public Optional<Person> findPersonById(long personId) {
    return Optional.ofNullable(entityManager.find(Person.class, personId));
  }

  @Override
  public PersonResponseDTO getPersonByName(GetPersonByNameRequestDTO requestDTO) {
    if (requestDTO.getName() == null) {
      throwNameFieldIsMissing();
    }
    validateNameFormat(requestDTO.getName());
    List<Person> personList = getPeopleByNameQuery(requestDTO.getName());
    validateResponseSinglePerson(personList);
    return personToPersonResponseDTO(personList.get(0));
  }

  @Override
  public List<Person> getPeopleByNameQuery(String name) {
    return entityManager
        .createQuery("SELECT p FROM Person p WHERE p.name LIKE :name", Person.class)
        .setParameter("name", name)
        .getResultList();
  }

  @Override
  public void validateResponseSinglePerson(List<Person> personList) {
    if (personList.isEmpty()) {
      throwNoPersonByTheName();
    } else if (personList.size() > 1) {
      throw new ApiBadRequestException(
          "There are multiple people with the same name, please use /api/getPeopleByName");
    }
  }

  @Override
  public PersonResponseDTO personToPersonResponseDTO(Person person) {
    return new PersonResponseDTO(
        person.getId(),
        person.getName(),
        person.getGender().toString(),
        parseLocalDateToStringBirthday(person.getBirthday()));
  }

  @Override
  public String parseLocalDateToStringBirthday(LocalDate birthday) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    return birthday.format(formatter);
  }

  @Override
  public PersonResponseDTOList getPeopleByName(GetPersonByNameRequestDTO requestDTO) {
    if (requestDTO.getName() == null) {
      throwNameFieldIsMissing();
    }
    validateNameFormat(requestDTO.getName());
    List<Person> personList = getPeopleByNameQuery(requestDTO.getName());
    validateGetPeopleByNameResponse(personList);
    return personListToPersonResponseDTOList(personList);
  }

  @Override
  public PersonResponseDTOList personListToPersonResponseDTOList(List<Person> personList) {
    return new PersonResponseDTOList(
        personList.stream().map(this::personToPersonResponseDTO).collect(Collectors.toList()));
  }

  @Override
  public void validateGetPeopleByNameResponse(List<Person> personList) {
    if (personList.isEmpty()) {
      throwNoPersonByTheName();
    }
  }

  @Override
  public void throwNoPersonByTheName() {
    throw new ApiBadRequestException("There is no person by the provided name");
  }

  @Override
  public void throwNameFieldIsMissing() {
    throw new ApiBadRequestException("name field is missing");
  }

  @Override
  public void throwMissingField() {
    throw new ApiBadRequestException("Missing field");
  }

  @Override
  public void throwProvideValidPersonId() {
    throw new ApiBadRequestException("Please provide valid personId");
  }

  @Override
  public PersonResponseDTOList getAllPeople() {
    List<Person> personList = getAllPeopleQuery();
    validateGetAllPeopleResponse(personList);
    return personListToPersonResponseDTOList(personList);
  }

  @Override
  public void validateGetAllPeopleResponse(List<Person> personList) {
    if (personList.isEmpty()) {
      throw new ApiBadRequestException("There are no person entities");
    }
  }

  @Override
  public List<Person> getAllPeopleQuery() {
    return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
  }

  @Override
  public void updatePerson(UpdatePersonRequestDTO requestDTO) {
    validateUpdatePersonRequest(requestDTO);
    Optional<Person> optionalPerson = findPersonById(requestDTO.getPersonId());
    if (optionalPerson.isPresent()) {
      Person person = optionalPerson.get();
      person.setName(requestDTO.getName());
      savePersonQuery(person);
    } else {
      throwProvideValidPersonId();
    }
  }

  @Override
  public void validateUpdatePersonRequest(UpdatePersonRequestDTO requestDTO) {
    if (requestDTO.getName() == null || requestDTO.getPersonId() == 0) {
      throwMissingField();
    }
    validateNameFormat(requestDTO.getName());
  }
}
