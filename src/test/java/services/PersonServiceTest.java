package services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  @InjectMocks private PersonService personService;

  @Test
  void isValidBirthdayPattern_false() {
    assertFalse(personService.isValidBirthdayPattern("qwerty"));
    assertFalse(personService.isValidBirthdayPattern("13.13.1313"));
    assertFalse(personService.isValidBirthdayPattern("12.13.1313"));
    assertFalse(personService.isValidBirthdayPattern("-1.5.2010"));
    assertFalse(personService.isValidBirthdayPattern("31/12/2000"));
    assertFalse(personService.isValidBirthdayPattern("05-05-1995"));
    assertFalse(personService.isValidBirthdayPattern("2020-01-01"));
    assertFalse(personService.isValidBirthdayPattern("12.122.1999"));
  }

  @Test
  void isValidBirthdayPattern_true() {
    assertTrue(personService.isValidBirthdayPattern("01.01.2012"));
    assertTrue(personService.isValidBirthdayPattern("1.01.2012"));
    assertTrue(personService.isValidBirthdayPattern("01.1.2012"));
    assertTrue(personService.isValidBirthdayPattern("1.1.2012"));
  }

  @Test
  void isValidNamePattern_false() {
    assertFalse(personService.isValidNamePattern("123"));
    assertFalse(personService.isValidNamePattern("qwerty@"));
    assertFalse(personService.isValidNamePattern("John1"));
    assertFalse(personService.isValidNamePattern("@Doe"));
    assertFalse(personService.isValidNamePattern("John.Doe"));
  }

  @Test
  void isValidNamePattern_true() {
    assertTrue(personService.isValidNamePattern("John Doe"));
    assertTrue(personService.isValidNamePattern("Jane Doe"));
    assertTrue(personService.isValidNamePattern("John Smith"));
    assertTrue(personService.isValidNamePattern("Jakub Novák"));
  }
}
