package dtos;

import java.util.Objects;

public class ResponseMessageDTO {
  private String message;

  public ResponseMessageDTO() {}

  public ResponseMessageDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ResponseMessageDTO)) return false;
    ResponseMessageDTO that = (ResponseMessageDTO) o;
    return Objects.equals(getMessage(), that.getMessage());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getMessage());
  }
}
