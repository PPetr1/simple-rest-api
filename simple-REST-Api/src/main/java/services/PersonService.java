package services;

import dtos.CreatePersonRequestDTO;
import dtos.DeletePersonRequestDTO;
import dtos.GetPersonForNameRequestDTO;
import dtos.PersonResponseDTO;
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

@Stateless
public class PersonService {

  @PersistenceContext(name = "PersonManager")
  private EntityManager entityManager;

  @Transactional
  public void createPerson(CreatePersonRequestDTO requestDTO) {
    validateCreatePersonRequestDTO(requestDTO);
    entityManager.persist(
        new Person(
            requestDTO.getName(),
            birthdayStringParseToDate(requestDTO.getBirthday()),
            Gender.valueOf(requestDTO.getGender().toUpperCase())));
  }

  void validateCreatePersonRequestDTO(CreatePersonRequestDTO requestDTO) {
    if (requestDTO.getName() == null
        || requestDTO.getGender() == null
        || requestDTO.getBirthday() == null) {
      throw new ApiBadRequestException("Missing field");
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

  @Transactional
  public void deletePerson(DeletePersonRequestDTO requestDTO) {
    Optional<Person> optionalPerson =
        Optional.ofNullable(entityManager.find(Person.class, requestDTO.getPersonId()));
    if (optionalPerson.isPresent()) {
      entityManager.remove(optionalPerson.get());
    } else {
      throw new ApiBadRequestException(
          "Provided personId: " + requestDTO.getPersonId() + " doesn't exist");
    }
  }

  @Transactional
  public Object getPersonForName(GetPersonForNameRequestDTO requestDTO) {
    List<Person> personList =
        entityManager
            .createQuery("SELECT p FROM Person p WHERE p.name LIKE :name", Person.class)
            .setParameter("name", requestDTO.getName())
            .getResultList();
    validateResponseSinglePerson(personList);
    return personToPersonResponseDTO(personList.get(0));
  }

  void validateResponseSinglePerson(List<Person> personList) {
    if (personList.isEmpty()) {
      throw new ApiBadRequestException("There is no person by the provided name");
    } else if (personList.size() > 1) {
      throw new ApiBadRequestException("There are multiple people with the same name, please use different endpoint");
    }
  }

  PersonResponseDTO personToPersonResponseDTO(Person person) {
    return new PersonResponseDTO(person.getName(), person.getGender().toString(), birthdayFixResponseFormat(person.getBirthday().toString()));
  }

  String birthdayFixResponseFormat(String birthday) {
    String[] arr = birthday.split("-");
    return arr[2] + "." + arr[1] + "." + arr[0];
  }

}
