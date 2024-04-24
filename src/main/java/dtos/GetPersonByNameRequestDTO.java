package dtos;

import java.util.Objects;

public class GetPersonByNameRequestDTO {
  private String name;

  public GetPersonByNameRequestDTO() {}

  public GetPersonByNameRequestDTO(String name) {
    this.name = name;
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
    if (!(o instanceof GetPersonByNameRequestDTO)) return false;
    GetPersonByNameRequestDTO that = (GetPersonByNameRequestDTO) o;
    return Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }
}
