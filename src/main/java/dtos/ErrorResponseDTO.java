package dtos;

public class ErrorResponseDTO {
  private String errorMessage;

  public ErrorResponseDTO() {}

  public ErrorResponseDTO(String error) {
    this.errorMessage = error;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
