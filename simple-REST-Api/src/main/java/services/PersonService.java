package services;

import dtos.*;
import enums.Gender;
import exceptions.ApiBadRequestException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import models.Person;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Stateless
public class PersonService {

  @PersistenceContext(name = "PersonManager")
  private EntityManager entityManager;

  public void createPerson(CreatePersonRequestDTO requestDTO) {
    validateCreatePersonRequestDTO(requestDTO);
    savePersonQuery(
        new Person(
            requestDTO.getName(),
            birthdayStringParseToDate(requestDTO.getBirthday()),
            Gender.valueOf(requestDTO.getGender().toUpperCase())));
  }

  @Transactional
  public void savePersonQuery(Person person) {
    entityManager.persist(person);
  }

  void validateCreatePersonRequestDTO(CreatePersonRequestDTO requestDTO) {
    if (requestDTO.getName() == null
        || requestDTO.getGender() == null
        || requestDTO.getBirthday() == null) {
      throwMissingField();
    }
    validateNameFormat(requestDTO.getName());
    validateBirthdayFormat(requestDTO.getBirthday());
    validateGenderFormat(requestDTO.getGender());
  }

  Date birthdayStringParseToDate(String birthday) {
    String[] arr = birthday.split("\\.");
    return java.sql.Date.valueOf(
        LocalDate.of(Integer.parseInt(arr[2]), Integer.parseInt(arr[1]), Integer.parseInt(arr[0])));
  }

  void validateGenderFormat(String gender) {
    if (gender.isEmpty()) {
      throw new ApiBadRequestException("Gender is empty");
    } else if (!isValidGender(gender)) {
      throw new ApiBadRequestException("Gender can only be MALE,FEMALE or OTHER");
    }
  }

  boolean isValidGender(String gender) {
    for (Gender genderEnum : Gender.values()) {
      if (genderEnum.name().equalsIgnoreCase(gender)) {
        return true;
      }
    }
    return false;
  }

  void validateBirthdayFormat(String birthday) {
    if (birthday.isEmpty()) {
      throw new ApiBadRequestException("Birthday cannot be empty");
    } else if (!isValidBirthdayPattern(birthday)) {
      throw new ApiBadRequestException("Birthday needs to be in format:DD.MM.YYYY");
    } else if (Integer.parseInt(birthday.substring(birthday.length() - 4))
        > LocalDate.now().getYear()) {
      throw new ApiBadRequestException("Person is not born yet");
    }
  }

  Boolean isValidBirthdayPattern(String birthday) {
    return Pattern.compile("\\b(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.(\\d{4})\\b")
        .matcher(birthday)
        .matches();
  }

  void validateNameFormat(String name) {
    if (name.isEmpty()) {
      throw new ApiBadRequestException("Name cannot be empty");
    } else if (!isValidNamePattern(name)) {
      throw new ApiBadRequestException("Name cannot contain numbers or symbols");
    } else if (name.length() > 50) {
      throw new ApiBadRequestException("Name can have 50 characters at maximum");
    }
  }

  boolean isValidNamePattern(String name) {
    return Pattern.compile("^[a-zA-Z ěĚšŠčČřŘžŽýÝáÁíÍéÉůŮúÚöÖ]+$").matcher(name).matches();
  }

  public void deletePerson(DeletePersonRequestDTO requestDTO) {
    validateDeletePersonRequestDTO(requestDTO);
    Optional<Person> optionalPerson = findPersonById(requestDTO.getPersonId());
    if (optionalPerson.isPresent()) {
      deletePersonQuery(optionalPerson.get());
    } else {
      throwProvideValidPersonId();
    }
  }

  @Transactional
  void deletePersonQuery(Person person) {
    entityManager.remove(person);
  }

  void validateDeletePersonRequestDTO(DeletePersonRequestDTO requestDTO) {
    if (requestDTO.getPersonId() == 0) {
      throw new ApiBadRequestException("Please provide valid personId");
    }
  }

  @Transactional
  Optional<Person> findPersonById(long personId) {
    return Optional.ofNullable(entityManager.find(Person.class, personId));
  }

  public Object getPersonForName(GetPersonForNameRequestDTO requestDTO) {
    if (requestDTO.getName() == null) {
      throwNameFieldIsMissing();
    }
    validateNameFormat(requestDTO.getName());
    List<Person> personList = getPeopleByNameQuery(requestDTO.getName());
    validateResponseSinglePerson(personList);
    return personToPersonResponseDTO(personList.get(0));
  }

  @Transactional
  List<Person> getPeopleByNameQuery(String name) {
    return entityManager
        .createQuery("SELECT p FROM Person p WHERE p.name LIKE :name", Person.class)
        .setParameter("name", name)
        .getResultList();
  }

  void validateResponseSinglePerson(List<Person> personList) {
    if (personList.isEmpty()) {
      throwNoPersonByTheName();
    } else if (personList.size() > 1) {
      throw new ApiBadRequestException(
          "There are multiple people with the same name, please use /api/getPeopleByName");
    }
  }

  PersonResponseDTO personToPersonResponseDTO(Person person) {
    return new PersonResponseDTO(
        person.getName(),
        person.getGender().toString(),
        birthdayFixResponseFormat(person.getBirthday().toString()));
  }

  String birthdayFixResponseFormat(String birthday) {
    String[] arr = birthday.split("-");
    return arr[2] + "." + arr[1] + "." + arr[0];
  }

  public PersonResponseDTOList getPeopleByName(GetPersonForNameRequestDTO requestDTO) {
    if (requestDTO.getName() == null) {
      throwNameFieldIsMissing();
    }
    validateNameFormat(requestDTO.getName());
    List<Person> personList = getPeopleByNameQuery(requestDTO.getName());
    validateGetPeopleByNameResponse(personList);
    return personListToPersonResponseDTOList(personList);
  }

  private PersonResponseDTOList personListToPersonResponseDTOList(List<Person> personList) {
    return new PersonResponseDTOList(
        personList.stream().map(this::personToPersonResponseDTO).collect(Collectors.toList()));
  }

  void validateGetPeopleByNameResponse(List<Person> personList) {
    if (personList.isEmpty()) {
      throwNoPersonByTheName();
    }
  }

  void throwNoPersonByTheName() {
    throw new ApiBadRequestException("There is no person by the provided name");
  }

  void throwNameFieldIsMissing() {
    throw new ApiBadRequestException("name field is missing");
  }

  void throwMissingField() {
    throw new ApiBadRequestException("Missing field");
  }

  void throwProvideValidPersonId() {
    throw new ApiBadRequestException("Please provide valid personId");
  }

  public PersonResponseDTOList getAllPersons() {
    List<Person> personList = getAllPersonsQuery();
    validateGetAllPersonsResponse(personList);
    return personListToPersonResponseDTOList(personList);
  }

  void validateGetAllPersonsResponse(List<Person> personList) {
    if (personList.isEmpty()) {
      throw new ApiBadRequestException("There are no people entities");
    }
  }

  @Transactional
  List<Person> getAllPersonsQuery() {
    return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
  }

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

  void validateUpdatePersonRequest(UpdatePersonRequestDTO requestDTO) {
    if (requestDTO.getName() == null || requestDTO.getPersonId() == 0) {
      throwMissingField();
    }
    validateNameFormat(requestDTO.getName());
  }
}
