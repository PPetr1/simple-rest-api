package dtos;

public class GetPersonForNameRequestDTO {
  private String name;

  public GetPersonForNameRequestDTO() {}

  public GetPersonForNameRequestDTO(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
