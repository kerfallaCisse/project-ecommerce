package api.model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customization")
public class CustomizationResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String customization() {
        return "Hello from customization endpoint ";
    }
}
