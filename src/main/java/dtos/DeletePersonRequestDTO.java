package dtos;

import java.util.Objects;

public class DeletePersonRequestDTO {
  private long personId;

  public DeletePersonRequestDTO() {}

  public DeletePersonRequestDTO(long id) {
    this.personId = id;
  }

  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DeletePersonRequestDTO)) return false;
    DeletePersonRequestDTO that = (DeletePersonRequestDTO) o;
    return getPersonId() == that.getPersonId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPersonId());
  }
}
