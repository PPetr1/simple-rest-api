package models;

import enums.Gender;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  @Temporal(TemporalType.DATE)
  private Date birthday;
  @Enumerated(EnumType.STRING)
  private Gender gender;

  public Person() {}

  public Person(String name, Date birthday, Gender gender) {
    this.name = name;
    this.birthday = birthday;
    this.gender = gender;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    Person person = (Person) o;
    return getId() == person.getId()
        && Objects.equals(getName(), person.getName())
        && Objects.equals(getBirthday(), person.getBirthday())
        && getGender() == person.getGender();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getBirthday(), getGender());
  }
}
