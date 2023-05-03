package api.model;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/stock")
public class StockManagementResource {

    // get json data with all models
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getStock() {
        List<SmallModel> smallModels = SmallModel.listAll();
        List<LargeModel> largeModels = LargeModel.listAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (SmallModel smallModel : smallModels) {
            JsonObject json = Json.createObjectBuilder()
                .add("modelType", "SmallModel")
                .add("id", smallModel.id)
                .add("quantity", smallModel.quantity)
                .add("color_pocket_name", smallModel.color_pocket_name)
                .add("color_bag_name", smallModel.color_bag_name)
                .build();
            jsonArrayBuilder.add(json);
        }

        for (LargeModel largeModel : largeModels) {
            JsonObject json = Json.createObjectBuilder()
                .add("modelType", "LargeModel")
                .add("id", largeModel.id)
                .add("quantity", largeModel.quantity)
                .add("color_pocket_name", largeModel.color_pocket_name)
                .add("color_bag_name", largeModel.color_bag_name)
                .build();
            jsonArrayBuilder.add(json);
        }

        return jsonArrayBuilder.build();
    }

    // Send json data to update stock
    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonArray insertModel(JsonObject model) {
        String modelType = model.getString("modelType");
        String colorPocket = model.getString("color_pocket_name");
        String colorBag = model.getString("color_bag_name");
        int quantity = model.getInt("quantity");

        if ("small".equals(modelType)) {
            SmallModel smallModel = SmallModel.find("color_pocket_name = ?1 and color_bag_name = ?2", colorPocket, colorBag).firstResult();

            if (smallModel != null) {
                // If the model already exists in the database, update its quantity
                smallModel.quantity += quantity;
            } else {
                // Otherwise, create a new model
                smallModel = new SmallModel();
                smallModel.color_pocket_name = colorPocket;
                smallModel.color_bag_name = colorBag;
                smallModel.quantity = quantity;
            }

            smallModel.persist();

        } else if ("large".equals(modelType)) {
            LargeModel largeModel = LargeModel.find("color_pocket_name", colorPocket).firstResult();

            if (largeModel != null) {
                // If the model already exists in the database, update its quantity
                largeModel.quantity += quantity;
            } else {
                // Otherwise, create a new model
                largeModel = new LargeModel();
                largeModel.color_pocket_name = colorPocket;
                largeModel.color_bag_name = colorBag;
                largeModel.quantity = quantity;
            }

            largeModel.persist();

        } else {
            // Invalid model type
            throw new IllegalArgumentException("Invalid model type: " + modelType);
        }
        
        // Send confirmation
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        JsonObject json = Json.createObjectBuilder().add("result", "ok").build();
        jsonArrayBuilder.add(json);
        return jsonArrayBuilder.build();
    }

}
