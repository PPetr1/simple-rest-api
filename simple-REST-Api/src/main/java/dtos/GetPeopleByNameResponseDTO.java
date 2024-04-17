package dtos;

import java.util.List;

public class GetPeopleByNameResponseDTO {
  private List<PersonResponseDTO> personResponseDTOList;

  public GetPeopleByNameResponseDTO() {}

  public GetPeopleByNameResponseDTO(List<PersonResponseDTO> personResponseDTOList) {
    this.personResponseDTOList = personResponseDTOList;
  }

  public List<PersonResponseDTO> getPersonResponseDTOList() {
    return personResponseDTOList;
  }

  public void setPersonResponseDTOList(List<PersonResponseDTO> personResponseDTOList) {
    this.personResponseDTOList = personResponseDTOList;
  }
}
