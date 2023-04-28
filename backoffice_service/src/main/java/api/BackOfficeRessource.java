package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import api.model.Color;
import api.model.ColorRepository;
import api.model.Statistic;
import api.model.StatisticRepository;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 * 
 * @author kerfalla
 */

@Path("stats")
@RolesAllowed("admin")
public class BackOfficeRessource {

    StatisticRepository statisticRepository = new StatisticRepository(); // <- for test create the object to avoid null
                                                                         // pointer exception

    ColorRepository colorRepository = new ColorRepository(); // same for color repository

    // @Inject
    // StatisticRepository statisticRepository;

    // @Inject
    // ColorRepository colorRepository;

    @GET
    @Path("connected")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getNumberOfUserConnected", summary = "Get number of users connected", description = "get the number of users who have clicked on the website")
    @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response getNumberOfUserConnected() {

        List<Statistic> stats = statisticRepository.list("SELECT number_of_user_connected FROM Statistic");
        if(stats.size() == 0) return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
        return Response.ok(stats.get(0)).build();
    }

    @GET
    @Path("user_account")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getNumbOfAccountsCreated", summary = "retrieves the accounts created", description = "get the number of users who have created an account")
    @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response getNumbOfAccountsCreated() {
        List<Statistic> stats = statisticRepository.list("SELECT number_of_user_compte FROM Statistic");
        if(stats.size() == 0) return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
        return Response.ok(stats.get(0)).build();
    }

    @GET
    @Path("user_commands")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getNumberOfUserWhoMadeAnOrder", summary = "orders placed", description = "get the number of users who have placed orders")
    @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response getNumberOfUserWhoMadeAnOrder() {
        List<Statistic> stats = statisticRepository.list("SELECT number_of_user_commands FROM Statistic");
        if(stats.size() == 0) return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
        return Response.ok(stats.get(0)).build();
    }

    @GET
    @Path("abandoned_orders")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getNumberOfAbandonedOrders", summary = "abandoned orders", description = "get the number of abandoned orders")
    @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response getNumberOfAbandonedOrders() {
        List<Statistic> stats = statisticRepository.list("SELECT number_of_abandoned_bag FROM Statistic");
        if(stats.size() == 0) return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
        return Response.ok(stats.get(0)).build();
    }

    @GET
    @Path("colors_stats")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getColorsStats", summary = "colors stats", description = "retrieve color statistics")
    @APIResponse(responseCode = "200", description = "Operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response getColorsStats() {
        List<Color> colors = colorRepository.list("SELECT color_name, number_of_commands FROM Color");
        if(colors.size() == 0) return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
        return Response.ok(colors).build();
    }

}
