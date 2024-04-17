package resources;

import dtos.CreatePersonRequestDTO;
import dtos.DeletePersonRequestDTO;
import dtos.ResponseMessageDTO;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.PersonService;

@Path("/person")
public class PersonResource {

  @EJB private PersonService personService;

  @POST
  @Path("/create")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createPerson(CreatePersonRequestDTO requestDTO) {
    personService.createPerson(requestDTO);
    return Response.status(200)
        .entity(new ResponseMessageDTO("Person was successfully saved"))
        .build();
  }

  @DELETE
  @Path("/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePerson(DeletePersonRequestDTO requestDTO) {
    personService.deletePerson(requestDTO);
    return Response.status(200).entity(new ResponseMessageDTO("Person was successfully deleted"))
            .build();
  }
}
