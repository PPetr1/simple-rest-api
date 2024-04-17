package dtos;

import java.util.List;

public class PersonResponseDTOList {
  private List<PersonResponseDTO> personResponseDTOList;

  public PersonResponseDTOList() {}

  public PersonResponseDTOList(List<PersonResponseDTO> personResponseDTOList) {
    this.personResponseDTOList = personResponseDTOList;
  }

  public List<PersonResponseDTO> getPersonResponseDTOList() {
    return personResponseDTOList;
  }

  public void setPersonResponseDTOList(List<PersonResponseDTO> personResponseDTOList) {
    this.personResponseDTOList = personResponseDTOList;
  }
}
