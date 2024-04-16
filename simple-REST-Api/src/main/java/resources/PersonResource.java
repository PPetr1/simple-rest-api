package resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import services.PersonService;

public class PersonResource {

    @EJB
    private PersonService personService;
    
}
