package dtos;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ErrorResponseDTO)) return false;
    ErrorResponseDTO that = (ErrorResponseDTO) o;
    return Objects.equals(getErrorMessage(), that.getErrorMessage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getErrorMessage());
  }
}
