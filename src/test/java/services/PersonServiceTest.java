package services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

  @InjectMocks private PersonServiceImpl personServiceImpl;

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
}
