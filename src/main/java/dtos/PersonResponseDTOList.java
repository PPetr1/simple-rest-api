package dtos;

import java.util.List;

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
}
