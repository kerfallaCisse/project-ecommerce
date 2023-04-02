package api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

import api.model.User;
import io.quarkus.oidc.UserInfo;
//import io.vertx.mutiny.mysqlclient.MySQLPool;
//import io.vertx.mutiny.sqlclient.Tuple;
//import javax.annotation.security.RolesAllowed;

/**
 * 
 * @author kerfalla
 */

@Path("stats")
public class BackOfficeRessource {

    @Inject
    UserInfo userInfo;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Path("verif_user")
    @Produces(MediaType.APPLICATION_JSON)
    //@RolesAllowed("user")
    public Response verifyUserRole() {
        String user_email = userInfo.get("email").toString();
        user_email = user_email.substring(0, user_email.length() - 1);
        user_email = user_email.substring(1);

        return User.find("email", user_email)
                .singleResultOptional()
                .map(user -> Response.status(Response.Status.OK).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());

    }

    @GET
    @Path("connected")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUserConnected() {

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("user_account")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumbOfAccountsCreated() {

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("user_commands")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUserWhoMadeAnOrder() {

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("abandoned_orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfAbandonedOrders() {

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("colors_stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsStats() {

        return Response.status(Response.Status.OK).build();
    }

}
