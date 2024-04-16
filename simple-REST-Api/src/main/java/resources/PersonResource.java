package resources;

import dtos.CreatePersonRequestDTO;
import dtos.ResponseMessageDTO;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.PersonService;

public class PersonResource {

  @EJB private PersonService personService;

  @POST
  @Path("/createPerson")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createPerson(CreatePersonRequestDTO requestDTO) {
    personService.createPerson(requestDTO);
    return Response.status(201)
        .entity(new ResponseMessageDTO("Person was successfully saved"))
        .build();
  }
}
