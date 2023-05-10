package stats;

import java.time.LocalDate;
import jakarta.ws.rs.GET;
import java.util.List;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import stats.model.User;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/statistics")
public class StatisticRessource {
    
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
    @Path("users/last_three_month")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersLastThreeMonth() {
        List<LocalDate> dates = Statistic.getDatesLastThreeMonths();
        return Response.ok(User.statsLastThreeMonth(dates)).build();
        
    }

    @GET
    @Path("users/last_year")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewUsersLastYear() {
        // LocalDate now = LocalDate.now();
        // LocalDate _threeMonThDate1 = now.minusMonths(3);
        // LocalDate _threeMonThDate2 = _threeMonThDate1.minusMonths(3);
        // LocalDate _threeMonThDate3 = _threeMonThDate2.minusMonths(3);
        // LocalDate _threeMonThDate4 = _threeMonThDate3.minusMonths(3);

        // System.out.println(_threeMonThDate4);
        // System.out.println(_threeMonThDate3);
        // System.out.println(_threeMonThDate2);
        // System.out.println(_threeMonThDate1);
        // System.out.println(now);

        

        return Response.ok(User.statsLastYear()).build();
    }



    
}
