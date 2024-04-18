package dtos;

import java.util.List;
import java.util.Objects;

public class PersonResponseDTOList {
  private List<PersonResponseDTO> people;

  public PersonResponseDTOList() {}

  public PersonResponseDTOList(List<PersonResponseDTO> personResponseDTOList) {
    this.people = personResponseDTOList;
  }

  public List<PersonResponseDTO> getPeople() {
    return people;
  }

  public void setPeople(List<PersonResponseDTO> people) {
    this.people = people;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PersonResponseDTOList)) return false;
    PersonResponseDTOList that = (PersonResponseDTOList) o;
    return Objects.equals(getPeople(), that.getPeople());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPeople());
  }
}
