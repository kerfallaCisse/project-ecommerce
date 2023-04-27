package ch.unige.projet_info.stats_jwt;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.jwt.JsonWebToken;

import ch.unige.projet_info.stats_jwt.model.User;
import ch.unige.projet_info.stats_jwt.model.UserRepository;

/**
 * @author kerfalla
 */

@Path("jwt")
public class StatsJwtRessource {

    @Inject
    StatsJwtService statsJwtService;

    @Inject
    JsonWebToken jsonWebToken;

    @Inject
    UserRepository userRepository;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJwt() {

        if (verifUser() == true) {
            String jwt = statsJwtService.generateJwt();
            return Response.ok(jwt).build();
        }

        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }


    
    public boolean verifUser() {
        var auth0_user_id = jsonWebToken.getSubject();
        Optional<User> user = userRepository.find("auth0_user_id", auth0_user_id)
                .singleResultOptional();

        if (user.isPresent()) {
            var u = user.get();
            if (u.admin == 1)
                return true;
        }

        return false;
    }

}
