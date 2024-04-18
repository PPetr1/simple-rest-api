package dtos;

import java.util.Objects;

public class CreatePersonRequestDTO {
  private String name;
  private String gender;
  private String birthday;

  public CreatePersonRequestDTO() {}

  public CreatePersonRequestDTO(String name, String gender, String birthday) {
    this.name = name;
    this.gender = gender;
    this.birthday = birthday;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CreatePersonRequestDTO)) return false;
    CreatePersonRequestDTO that = (CreatePersonRequestDTO) o;
    return Objects.equals(getName(), that.getName()) && Objects.equals(getGender(), that.getGender()) && Objects.equals(getBirthday(), that.getBirthday());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getGender(), getBirthday());
  }
}
