package services;

import dtos.*;
import jakarta.ejb.Local;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import models.Person;

@Local
public interface PersonService {
  PersonResponseDTO createPerson(CreatePersonRequestDTO requestDTO);

  @Transactional
  PersonResponseDTO savePersonQuery(Person person);

  void validateCreatePersonRequestDTO(CreatePersonRequestDTO requestDTO);

  LocalDate birthdayStringParseToLocalDate(String birthday);

  void validateGenderFormat(String gender);

  boolean isValidGender(String gender);

  void validateBirthdayFormat(String birthday);

  Boolean isValidBirthdayPattern(String birthday);

  void validateNameFormat(String name);

  boolean isValidNamePattern(String name);

  void deletePerson(DeletePersonRequestDTO requestDTO);

  @Transactional
  void deletePersonQuery(Person person);

  void validateDeletePersonRequestDTO(long personId);

  @Transactional
  Optional<Person> findPersonById(long personId);

  PersonResponseDTO getPersonByName(GetPersonByNameRequestDTO requestDTO);

  @Transactional
  List<Person> getPeopleByNameQuery(String name);

  void validateResponseSinglePerson(List<Person> personList);

  PersonResponseDTO personToPersonResponseDTO(Person person);

  String birthdayFixResponseFormat(String birthday);

  PersonResponseDTOList getPeopleByName(GetPersonByNameRequestDTO requestDTO);

  PersonResponseDTOList personListToPersonResponseDTOList(List<Person> personList);

  void validateGetPeopleByNameResponse(List<Person> personList);

  void throwNoPersonByTheName();

  void throwNameFieldIsMissing();

  void throwMissingField();

  void throwProvideValidPersonId();

  PersonResponseDTOList getAllPeople();

  void validateGetAllPeopleResponse(List<Person> personList);

  @Transactional
  List<Person> getAllPeopleQuery();

  void updatePerson(UpdatePersonRequestDTO requestDTO);

  void validateUpdatePersonRequest(UpdatePersonRequestDTO requestDTO);
}
