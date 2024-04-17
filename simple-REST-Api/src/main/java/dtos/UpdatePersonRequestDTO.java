package dtos;

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
}
