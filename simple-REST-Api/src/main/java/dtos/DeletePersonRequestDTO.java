package dtos;

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
}
