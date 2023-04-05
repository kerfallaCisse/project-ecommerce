package api.model;

import java.util.List;

import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/stock")
public class StockManagementResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        List<SmallModel> smallModel = SmallModel.list("SELECT quantity FROM SmallModel");
        return Response.ok(smallModel).build(); 
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void insertModel(JsonObject model) {
        String modelType = model.getString("modelType");

        if ("small".equals(modelType)) {
            SmallModel smallModel = new SmallModel();
            smallModel.quantity = model.getInt("quantity");
            smallModel.color_pocket_name = model.getString("color_pocket_name");
            smallModel.color_bag_name = model.getString("color_bag_name");
            smallModel.persist();
        } else if ("large".equals(modelType)) {
            LargeModel largeModel = new LargeModel();
            largeModel.quantity = model.getInt("quantity");
            largeModel.color_pocket_name= model.getString("color_pocket_name");
            largeModel.color_bag_name = model.getString("color_bag_name");
            largeModel.persist();
        } else {
            // invalid model type
            throw new IllegalArgumentException("Invalid model type: " + modelType);
        }
    }
}
