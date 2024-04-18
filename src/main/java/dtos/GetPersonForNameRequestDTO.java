package dtos;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GetPersonForNameRequestDTO)) return false;
    GetPersonForNameRequestDTO that = (GetPersonForNameRequestDTO) o;
    return Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }
}
