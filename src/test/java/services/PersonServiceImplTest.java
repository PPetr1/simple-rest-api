package services;

import dtos.*;
import enums.Gender;
import exceptions.ApiBadRequestException;
import jakarta.persistence.EntityManager;
import models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

  @Mock
  private EntityManager entityManager;

  @InjectMocks private PersonServiceImpl personServiceImpl;

  private PersonServiceImpl personServiceImplSpy;

  @BeforeEach
  void setUp() {
    personServiceImplSpy = spy(personServiceImpl);
  }

  @Test
  void isValidBirthdayPattern_false() {
    assertFalse(personServiceImpl.isValidBirthdayPattern("qwerty"));
    assertFalse(personServiceImpl.isValidBirthdayPattern("13.13.1313"));
    assertFalse(personServiceImpl.isValidBirthdayPattern("12.13.1313"));
    assertFalse(personServiceImpl.isValidBirthdayPattern("-1.5.2010"));
    assertFalse(personServiceImpl.isValidBirthdayPattern("31/12/2000"));
    assertFalse(personServiceImpl.isValidBirthdayPattern("05-05-1995"));
    assertFalse(personServiceImpl.isValidBirthdayPattern("2020-01-01"));
    assertFalse(personServiceImpl.isValidBirthdayPattern("12.122.1999"));
  }

  @Test
  void isValidBirthdayPattern_true() {
    assertTrue(personServiceImpl.isValidBirthdayPattern("01.01.2012"));
    assertTrue(personServiceImpl.isValidBirthdayPattern("1.01.2012"));
    assertTrue(personServiceImpl.isValidBirthdayPattern("01.1.2012"));
    assertTrue(personServiceImpl.isValidBirthdayPattern("1.1.2012"));
  }

  @Test
  void isValidNamePattern_false() {
    assertFalse(personServiceImpl.isValidNamePattern("123"));
    assertFalse(personServiceImpl.isValidNamePattern("qwerty@"));
    assertFalse(personServiceImpl.isValidNamePattern("John1"));
    assertFalse(personServiceImpl.isValidNamePattern("@Doe"));
    assertFalse(personServiceImpl.isValidNamePattern("John.Doe"));
  }

  @Test
  void isValidNamePattern_true() {
    assertTrue(personServiceImpl.isValidNamePattern("John Doe"));
    assertTrue(personServiceImpl.isValidNamePattern("Jane Doe"));
    assertTrue(personServiceImpl.isValidNamePattern("John Smith"));
    assertTrue(personServiceImpl.isValidNamePattern("Jakub Novák"));
  }

  @Test
  void validateCreatePersonRequestDTO_throws() {
    CreatePersonRequestDTO requestDTO = mock(CreatePersonRequestDTO.class);

    assertThrows(ApiBadRequestException.class,() -> personServiceImpl.validateCreatePersonRequestDTO(requestDTO));
  }

  @Test
  void validateCreatePersonRequestDTO_doesNotThrows() {
    CreatePersonRequestDTO requestDTO = mock(CreatePersonRequestDTO.class);


    when(requestDTO.getBirthday()).thenReturn("birthday");
    when(requestDTO.getName()).thenReturn("name");
    when(requestDTO.getGender()).thenReturn("gender");

    doNothing().when(personServiceImplSpy).validateBirthdayFormat(anyString());
    doNothing().when(personServiceImplSpy).validateNameFormat(anyString());
    doNothing().when(personServiceImplSpy).validateGenderFormat(anyString());

    assertDoesNotThrow(() -> personServiceImplSpy.validateCreatePersonRequestDTO(requestDTO));
  }

  @Test
  void validateNameFormat_isEmptyThrows() {
    String name = "";

    assertThrows(ApiBadRequestException.class,() -> personServiceImpl.validateGenderFormat(name));
  }

  @Test
  void validateNameFormat_maxFiftyCharactersThrows() {
    String name = "A".repeat(51);

    assertThrows(ApiBadRequestException.class,() -> personServiceImplSpy.validateGenderFormat(name));
  }

  @Test
  void validateGenderFormat_genderIsEmptyThrows() {
    String gender = "";

    assertThrows(ApiBadRequestException.class, () -> personServiceImpl.validateGenderFormat(gender));
  }

  @Test
  void validateBirthdayFormat_birthdayIsEmptyThrows() {
    String birthday = "";

    assertThrows(ApiBadRequestException.class, () -> personServiceImpl.validateBirthdayFormat(birthday));
  }

  @Test
  void deletePerson_validPersonIdThrows() {
    DeletePersonRequestDTO requestDTO = mock(DeletePersonRequestDTO.class);

    doNothing().when(personServiceImplSpy).validateDeletePersonRequestDTO(anyLong());
    when(personServiceImplSpy.findPersonById(anyLong())).thenReturn(Optional.empty());

    assertThrows(ApiBadRequestException.class, () -> personServiceImplSpy.deletePerson(requestDTO));
  }

  @Test
  void validateDeletePersonRequestDTO_validPersonIdThrows() {
    long personId = 0;

    assertThrows(ApiBadRequestException.class, () -> personServiceImplSpy.validateDeletePersonRequestDTO(personId));
  }

  @Test
  void validateResponseSinglePerson_doesNotThrow() {
    List<Person> personList = List.of(new Person());

    assertDoesNotThrow(() -> personServiceImpl.validateResponseSinglePerson(personList));
  }

  @Test
  void getPersonForName_missingFieldThrows() {
    GetPersonForNameRequestDTO requestDTO = mock(GetPersonForNameRequestDTO.class);

    assertThrows(ApiBadRequestException.class, () -> personServiceImpl.getPersonForName(requestDTO));
  }

  @Test
  void birthdayFixResponseFormat_returnsCorrectFormat() {
    String dateString = "2017-10-15";

    assertEquals("15.10.2017", personServiceImpl.birthdayFixResponseFormat(dateString));
  }

  @Test
  void personToPersonResponseDTO_returnsCorrectPersonResponseDTO() {
    Person person = mock(Person.class);
    when(person.getId()).thenReturn(1L);
    when(person.getGender()).thenReturn(Gender.MALE);
    when(person.getName()).thenReturn("name");
    when(person.getBirthday()).thenReturn(LocalDate.of(2017,10,15));
    when(personServiceImplSpy.birthdayFixResponseFormat(LocalDate.of(2017,10,15).toString())).thenReturn("15.10.2017");

    PersonResponseDTO expected = new PersonResponseDTO(1L, "name", "MALE", "15.10.2017");

    PersonResponseDTO actual = personServiceImplSpy.personToPersonResponseDTO(person);

    assertEquals(expected.getId(), actual.getId());
    assertEquals(expected.getName(), actual.getName());
    assertEquals(expected.getGender(), actual.getGender());
    assertEquals(expected.getBirthday(), actual.getBirthday());
  }

  @Test
  void validateGetPeopleByNameResponse_isEmptyThrows() {
    List<Person> personList = new ArrayList<>();

    assertThrows(ApiBadRequestException.class, () -> personServiceImpl.validateGetPeopleByNameResponse(personList));
  }

  @Test
  void getPeopleByName_nameIsMissingThrows() {
    GetPersonForNameRequestDTO requestDTO = mock(GetPersonForNameRequestDTO.class);

    assertThrows(ApiBadRequestException.class, () -> personServiceImpl.getPeopleByName(requestDTO));
  }

  @Test
  void validateGetAllPersonsResponse_isEmptyThrows() {
    List<Person> personList = new ArrayList<>();

    assertThrows(ApiBadRequestException.class, () -> personServiceImpl.validateGetAllPersonsResponse(personList));
  }

  @Test
  void validateUpdatePersonRequest_missingFieldThrows_null() {
    UpdatePersonRequestDTO requestDTO = mock(UpdatePersonRequestDTO.class);

    when(requestDTO.getName()).thenReturn(null);

    assertThrows(ApiBadRequestException.class, () -> personServiceImpl.validateUpdatePersonRequest(requestDTO));
  }

  @Test
  void validateUpdatePersonRequest_missingFieldThrows_zero() {
    UpdatePersonRequestDTO requestDTO = mock(UpdatePersonRequestDTO.class);

    when(requestDTO.getName()).thenReturn("name");
    when(requestDTO.getPersonId()).thenReturn(0L);

    assertThrows(ApiBadRequestException.class, () -> personServiceImpl.validateUpdatePersonRequest(requestDTO));
  }

  @Test
  void updatePerson_provideValidPersonIdThrowsAfterQuery() {
    UpdatePersonRequestDTO requestDTO = mock(UpdatePersonRequestDTO.class);

    doNothing().when(personServiceImplSpy).validateUpdatePersonRequest(requestDTO);
    when(personServiceImplSpy.findPersonById(anyLong())).thenReturn(Optional.empty());


    assertThrows(ApiBadRequestException.class, () -> personServiceImplSpy.updatePerson(requestDTO));
  }
}
