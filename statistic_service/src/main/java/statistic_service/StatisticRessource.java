package statistic_service;

import statistic_service.model.Color;
import statistic_service.model.User;
import statistic_service.model.Profit;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import jakarta.ws.rs.POST;

import java.util.ArrayList;
import java.util.List;
import jakarta.ws.rs.core.Response;

@Path("/statistics")
public class StatisticRessource {

    Date date = new Date();

    User user = new User();

    Color color = new Color();

    List<LocalDate> datesWeek = date.getDatesLastWeek();
    List<LocalDate> datesMonth = date.getDatesLastMonth();
    List<LocalDate> datesLastThreeMonth = date.getDatesLastThreeMonths();
    List<LocalDate> datesLastYear = date.getDatesLastYear();

    @GET
    @Path("users/last_week")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersStatsWeek() {
        return Response.ok(user.statsWeek(datesWeek)).build();
    }

    @GET
    @Path("users/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersStatLastMonth() {
        return Response.ok(user.statsMonth(datesMonth)).build();
    }

    @GET
    @Path("users/last_three_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersStatsLastThreeMonth() {
        return Response.ok(user.statsLastThreeMonth(datesLastThreeMonth)).build();
    }

    @GET
    @Path("users/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersStatsLastYear() {
        return Response.ok(user.statsLastYear()).build();
    }

    @GET
    @Path("users/total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersTotal() {
        return Response.ok(user.usersTotal()).build();
    }

    @GET
    @Path("colors/last_week")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsStatsWeek() {
        return Response.ok(color.stats(datesWeek)).build();
    }

    @GET
    @Path("colors/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastMonth() {
        return Response.ok(color.stats(datesMonth)).build();
    }

    @GET
    @Path("colors/last_three_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastThreeMonths() {
        return Response.ok(color.stats(datesLastThreeMonth)).build();
    }

    @GET
    @Path("colors/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastYear() {
        return Response.ok(color.stats(datesLastYear)).build();
    }

    @GET
    @Path("orders/last_week")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersLastWeek() {
        return Response.ok(color.statsWeek(datesWeek)).build();
    }

    @GET
    @Path("orders/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersLastMonth() {
        return Response.ok(color.statsMonth(datesMonth)).build();
    }

    @GET
    @Path("orders/last_three_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersLastThreeMonth() {
        return Response.ok(color.statsLastThreeMonth(datesLastThreeMonth)).build();
    }

    @GET
    @Path("orders/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersLastYear() {
        return Response.ok(color.statsLastYear()).build();
    }

    @POST
    @Path("colors/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addOrders(JsonObject jsonObject) {
        String pocketColor = jsonObject.getString("pocket");
        String bagColor = jsonObject.getString("bag");
        Integer quantity = (Integer) jsonObject.getInt("quantity");

        if (pocketColor == null || bagColor == null || quantity == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String[] colors_name = { "red", "blue", "green", "yellow", "white", "grey", "black" };
        List<String> all_colors = new ArrayList<>();
        for (String clr : colors_name) {
            all_colors.add(clr);
        }

        if (!all_colors.contains(pocketColor) || !all_colors.contains(bagColor)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // After all checkings

        LocalDate currentDate = LocalDate.now();
        String[] colors = { bagColor, pocketColor };

        if (pocketColor.equals(bagColor)) {
            for (int i = 0; i < quantity; i++) {
                if(!color.save(pocketColor, currentDate)) {
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
            }
        } else {
            for (String color_name : colors) {
                for (int i = 0; i < quantity; i++) {
                    if(!color.save(color_name, currentDate)) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                }
            }
        }

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("profit")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfit() {
        Profit profit = Profit.findById(1);
        Double amount = profit.getAmount(); 
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        return Response.ok(jsonObjectBuilder.add("profit",amount).build()).build();
    }

}
