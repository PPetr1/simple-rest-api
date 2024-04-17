package services;

import dtos.*;
import jakarta.ejb.Local;
import jakarta.transaction.Transactional;
import models.Person;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Local
public interface PersonService {
  PersonResponseDTO createPerson(CreatePersonRequestDTO requestDTO);

  @Transactional
  PersonResponseDTO savePersonQuery(Person person);

  void validateCreatePersonRequestDTO(CreatePersonRequestDTO requestDTO);

  Date birthdayStringParseToDate(String birthday);

  void validateGenderFormat(String gender);

  boolean isValidGender(String gender);

  void validateBirthdayFormat(String birthday);

  Boolean isValidBirthdayPattern(String birthday);

  void validateNameFormat(String name);

  boolean isValidNamePattern(String name);

  void deletePerson(DeletePersonRequestDTO requestDTO);

  @Transactional
  void deletePersonQuery(Person person);

  void validateDeletePersonRequestDTO(DeletePersonRequestDTO requestDTO);

  @Transactional
  Optional<Person> findPersonById(long personId);

  Object getPersonForName(GetPersonForNameRequestDTO requestDTO);

  @Transactional
  List<Person> getPeopleByNameQuery(String name);

  void validateResponseSinglePerson(List<Person> personList);

  PersonResponseDTO personToPersonResponseDTO(Person person);

  String birthdayFixResponseFormat(String birthday);

  PersonResponseDTOList getPeopleByName(GetPersonForNameRequestDTO requestDTO);

  PersonResponseDTOList personListToPersonResponseDTOList(List<Person> personList);

  void validateGetPeopleByNameResponse(List<Person> personList);

  void throwNoPersonByTheName();

  void throwNameFieldIsMissing();

  void throwMissingField();

  void throwProvideValidPersonId();

  PersonResponseDTOList getAllPersons();

  void validateGetAllPersonsResponse(List<Person> personList);

  @Transactional
  List<Person> getAllPersonsQuery();

  void updatePerson(UpdatePersonRequestDTO requestDTO);

  void validateUpdatePersonRequest(UpdatePersonRequestDTO requestDTO);
}
