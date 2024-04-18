package dtos;

import java.util.Objects;

public class UpdatePersonRequestDTO {
  private long personId;
  private String name;

  public UpdatePersonRequestDTO() {}

  public UpdatePersonRequestDTO(long personId, String name) {
    this.personId = personId;
    this.name = name;
  }

  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UpdatePersonRequestDTO)) return false;
    UpdatePersonRequestDTO that = (UpdatePersonRequestDTO) o;
    return getPersonId() == that.getPersonId() && Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPersonId(), getName());
  }
}
