package services;

import dtos.CreatePersonRequestDTO;
import enums.Gender;
import exceptions.ApiBadRequestException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import models.Person;

import java.time.LocalDate;
import java.util.Date;
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
            birthdayParse(requestDTO.getBirthday()),
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

  Date birthdayParse(String birthday) {
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
    return Pattern.compile("^[a-zA-Z ]+$").matcher(name).matches();
  }
}
