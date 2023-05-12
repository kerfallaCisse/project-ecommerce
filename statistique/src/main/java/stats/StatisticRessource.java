package stats;

import java.time.LocalDate;
import jakarta.ws.rs.GET;
import java.util.List;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import stats.model.Color;
import stats.model.User;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/statistics")
public class StatisticRessource {

    private List<LocalDate> datesWeek = Statistic.getDatesLastWeek();
    private List<LocalDate> datesLastMonth = Statistic.getDatesLastMonth();
    private List<LocalDate> datesLastThreeMonth = Statistic.getDatesLastThreeMonths();
    private List<LocalDate> datesLastYear = Statistic.getDatesLastYear();
    
    @GET
    @Path("users/last_week")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersLastWeek() {
        return Response.ok(User.statsWeek()).build();
    }

    @GET
    @Path("users/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersLastMonth() {
        return Response.ok(User.statsMonth()).build();
    }

    @GET
    @Path("users/last_three_months")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersLastThreeMonth() {
        
        return Response.ok(User.statsLastThreeMonth(datesLastThreeMonth)).build();
        
    }

    @GET
    @Path("users/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersLastYear() {
        return Response.ok(User.statsLastYear()).build();
    }

    @GET
    @Path("colors/last_week")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastWeek() {
        return Response.ok(Color.stats(datesWeek)).build();
    }

    @GET
    @Path("colors/last_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastMonth() {
        return Response.ok(Color.stats(datesLastMonth)).build();
    }

    @GET
    @Path("colors/last_three_months")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastThreeMonths() {
        return Response.ok(Color.stats(datesLastThreeMonth)).build();
    }

    @GET
    @Path("colors/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColorsLastYear() {
        return Response.ok(Color.stats(datesLastYear)).build();
    }





    
}
