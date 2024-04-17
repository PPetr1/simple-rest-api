package exceptions;

import dtos.ErrorResponseDTO;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiBadRequestExceptionMapper implements ExceptionMapper<ApiBadRequestException> {
  @Override
  public Response toResponse(ApiBadRequestException e) {
    return Response.status(400)
        .entity(new ErrorResponseDTO(e.getMessage()))
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
