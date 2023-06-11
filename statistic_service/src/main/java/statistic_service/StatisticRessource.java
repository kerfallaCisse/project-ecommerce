package statistic_service;

import jakarta.persistence.EntityManager;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import statistic_service.model.OrderStats;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;
import jakarta.ws.rs.POST;
import statistic_service.model.ProfitStats;
import statistic_service.model.StatsImpl;
import statistic_service.model.entity.AbandonedBasket;
import statistic_service.model.entity.Color;
import statistic_service.model.entity.Profit;
import statistic_service.model.entity.User;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.PUT;

import java.util.ArrayList;
import java.util.List;
import jakarta.ws.rs.core.Response;

@Path("/statistics")
@Transactional
public class StatisticRessource {

    Date date = new Date();

    ProfitStats profitStats = new ProfitStats();

    StatsImpl statsImpl = new StatsImpl();

    OrderStats orderStats = new OrderStats();

    Profit profit = new Profit();
    EntityManager pEntityManager = Profit.getEntityManager();

    User user = new User();
    EntityManager userEntityManager = User.getEntityManager();

    AbandonedBasket abandonedBasket = new AbandonedBasket();
    EntityManager abEntityManager = AbandonedBasket.getEntityManager();

    Color color = new Color();
    EntityManager colorEntityManager = Color.getEntityManager();

    List<LocalDate> datesLastThreeMonths = date.getDatesLastThreeMonths();

    CartRessource cartRessource = new CartRessource();

    @GET
    @Path("users/last_week")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersStatsWeek() {
        return Response.ok(statsImpl.statsWeek(user, userEntityManager)).build();
    }

    @GET
    @Path("users/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersStatLastMonth() {
        return Response.ok(statsImpl.statsMonth(user, userEntityManager)).build();
    }

    @GET
    @Path("users/last_three_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersStatsLastThreeMonth() {
        return Response.ok(statsImpl.statsLastThreeMonths(user, userEntityManager, datesLastThreeMonths)).build();
    }

    @GET
    @Path("users/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersStatsLastYear() {
        return Response.ok(statsImpl.statsLastYear(user, userEntityManager)).build();
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
        List<LocalDate> dates = date.getDatesLastWeek();
        return Response.ok(orderStats.stats(dates)).build();
    }

    @GET
    @Path("colors/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastMonth() {
        List<LocalDate> dates = date.getDatesLastMonth();
        return Response.ok(orderStats.stats(dates)).build();
    }

    @GET
    @Path("colors/last_three_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastThreeMonths() {
        List<LocalDate> dates = date.getDatesLastThreeMonths();
        return Response.ok(orderStats.stats(dates)).build();
    }

    @GET
    @Path("colors/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastYear() {
        List<LocalDate> dates = date.getDatesLastYear();
        return Response.ok(orderStats.stats(dates)).build();
    }

    @GET
    @Path("orders/last_week")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersLastWeek() {
        return Response.ok(orderStats.statsWeek(color, colorEntityManager)).build();
    }

    @GET
    @Path("orders/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersLastMonth() {
        return Response.ok(orderStats.statsMonth(color, colorEntityManager)).build();
    }

    @GET
    @Path("orders/last_three_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersLastThreeMonth() {
        return Response.ok(orderStats.statsLastThreeMonths(color, colorEntityManager, datesLastThreeMonths)).build();
    }

    @GET
    @Path("orders/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersLastYear() {
        return Response.ok(orderStats.statsLastYear(color, colorEntityManager)).build();
    }

    // When a user make an order
    @POST
    @Path("colors/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addOrders(JsonObject jsonObject) {
        String pocketColor;
        String bagColor;
        Integer quantity;
        try {
            pocketColor = jsonObject.getString("pocket");
            bagColor = jsonObject.getString("bag");
            quantity = (Integer) jsonObject.getInt("quantity");
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (pocketColor == null || bagColor == null || quantity == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String[] colors_name = { "red", "blue", "green", "yellow", "white", "grey",
                "black" };
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
                if (!color.save(pocketColor, currentDate)) {
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
            }
        } else {
            for (String color_name : colors) {
                for (int i = 0; i < quantity; i++) {
                    if (!color.save(color_name, currentDate)) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                }
            }
        }

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("profit/last_week")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfitLastWeek() {
        return Response.ok(profitStats.statsWeek(profit, pEntityManager)).build();
    }

    @GET
    @Path("profit/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfitLastMonth() {
        return Response.ok(profitStats.statsMonth(profit, pEntityManager)).build();
    }

    @GET
    @Path("profit/last_three_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfitLastThreeMonth() {
        return Response.ok(profitStats.statsLastThreeMonths(profit, pEntityManager, datesLastThreeMonths)).build();
    }

    @GET
    @Path("profit/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfitLastYear() {
        return Response.ok(profitStats.statsLastYear(profit, pEntityManager)).build();
    }

    @GET
    @Path("abandoned_basket/last_week")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAbandonedBasketLastWeek() {
        return Response.ok(statsImpl.statsWeek(abandonedBasket, abEntityManager)).build();
    }

    @GET
    @Path("abandoned_basket/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAbandonedBasketLastMonth() {
        return Response.ok(statsImpl.statsMonth(abandonedBasket, abEntityManager)).build();
    }

    @GET
    @Path("abandoned_basket/last_three_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAbandonedBasketLastThreeMonth() {
        return Response.ok(statsImpl.statsLastThreeMonths(abandonedBasket, abEntityManager, datesLastThreeMonths))
                .build();
    }

    @GET
    @Path("abandoned_basket/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAbandonedBasketLastYear() {
        return Response.ok(statsImpl.statsLastYear(abandonedBasket, abEntityManager)).build();
    }

    @POST
    @Path("abandoned_basket")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAbandonedBasket(JsonObject jsonObject) {
        String modelType;
        try {
            modelType = jsonObject.getString("modelType");
            if (modelType.equals("smallModel") || modelType.equals("largeModel")) {
                LocalDate currentDate = date.now;
                AbandonedBasket aBasket = new AbandonedBasket();
                aBasket.setModelType(modelType);
                aBasket.setCreated_at(currentDate);
                aBasket.persist();
                if (aBasket.isPersistent())
                    return Response.status(Response.Status.CREATED).build();
                else
                    return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
  
        return Response.status(Response.Status.BAD_REQUEST).build();

    }

    @Path("cart/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserCart(JsonObject jsonObject) {
        if(cartRessource.addToCart(jsonObject)) {
            return Response.status(Response.Status.OK).build();
        }

         return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("cart")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserCart(@QueryParam("email") String email) {
        JsonArray jsonObject = cartRessource.getUserCart(email);
        if (jsonObject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(jsonObject).build();
    }


    @Path("cart/confirmation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getDeliveryInfosSendMail(JsonObject jsonObject) {
        if(cartRessource.getDeliveryInfos(jsonObject)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Path("cart")
    @DELETE
    public Response deleteUserBasket(@QueryParam("image") String image) {
        if(cartRessource.deleteBasket(image)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("cart")
    @PUT
    public Response UpdateUserBasketQuantity(@QueryParam("image") String image) {
        if(cartRessource.updateBasketQty(image)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }   

}