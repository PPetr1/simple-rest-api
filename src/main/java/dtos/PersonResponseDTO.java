package dtos;


import java.util.Objects;

public class PersonResponseDTO {
  private long id;
  private String name;
  private String gender;
  private String birthday;

  public PersonResponseDTO() {}

  public PersonResponseDTO(long id, String name, String gender, String birthday) {
    this.id = id;
    this.name = name;
    this.gender = gender;
    this.birthday = birthday;
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
    if (!(o instanceof PersonResponseDTO)) return false;
    PersonResponseDTO that = (PersonResponseDTO) o;
    return getId() == that.getId() && Objects.equals(getName(), that.getName()) && Objects.equals(getGender(), that.getGender()) && Objects.equals(getBirthday(), that.getBirthday());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getGender(), getBirthday());
  }
}
