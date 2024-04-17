package resources;

import dtos.CreatePersonRequestDTO;
import dtos.DeletePersonRequestDTO;
import dtos.GetPersonForNameRequestDTO;
import dtos.ResponseMessageDTO;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import services.PersonService;

@Path("")
public class PersonResource {

  @EJB private PersonService personService;

  @POST
  @Path("/createPerson")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createPerson(CreatePersonRequestDTO requestDTO) {
    personService.createPerson(requestDTO);
    return Response.status(200)
        .entity(new ResponseMessageDTO("Person was successfully saved"))
        .build();
  }

  @DELETE
  @Path("/deletePerson")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePerson(DeletePersonRequestDTO requestDTO) {
    personService.deletePerson(requestDTO);
    return Response.status(200).entity(new ResponseMessageDTO("Person was successfully deleted"))
            .build();
  }

  @GET
  @Path("/getPersonForName")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPersonForName(GetPersonForNameRequestDTO requestDTO) {
    return Response.status(200).entity(personService.getPersonForName(requestDTO)).build();
  }

  @GET
  @Path("/getPeopleByName")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPeopleByName(GetPersonForNameRequestDTO requestDTO) {
    return Response.status(200).entity(personService.getPeopleByName(requestDTO)).build();
  }

  @GET
  @Path("/getAllPersons")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllPersons() {
    return Response.status(200).entity(personService.getAllPersons()).build();
  }
}
