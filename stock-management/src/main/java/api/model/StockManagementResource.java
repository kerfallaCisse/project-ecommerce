package api.model;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


@Path("/stock")
public class StockManagementResource {

    private static final String ERROR = "error";
    private static final String MISSING_PARAM_OR_WRONG_TYPE = "missing param or type error";
    private static final String MODEL_TYPE = "modelType";
    private static final String BAG_COLOR = "color_bag_name";
    private static final String POCKET_COLOR = "color_pocket_name";
    private static final String QUANTITY = "quantity";


    // Get json data with all models
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getStock() {
        List<SmallModel> smallModels = SmallModel.listAll();
        List<LargeModel> largeModels = LargeModel.listAll();
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (SmallModel smallModel : smallModels) {
            JsonObject json = Json.createObjectBuilder()
                    .add(MODEL_TYPE, "SmallModel")
                    .add("id", smallModel.id)
                    .add(QUANTITY, smallModel.quantity)
                    .add(POCKET_COLOR, smallModel.color_pocket_name)
                    .add(BAG_COLOR, smallModel.color_bag_name)
                    .build();
            jsonArrayBuilder.add(json);
        }

        for (LargeModel largeModel : largeModels) {
            JsonObject json = Json.createObjectBuilder()
                    .add(MODEL_TYPE, "LargeModel")
                    .add("id", largeModel.id)
                    .add(QUANTITY, largeModel.quantity)
                    .add(POCKET_COLOR, largeModel.color_pocket_name)
                    .add(BAG_COLOR, largeModel.color_bag_name)
                    .build();
            jsonArrayBuilder.add(json);
        }

        return jsonArrayBuilder.build();
    }

    // Send json data to update stock or create a new model
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonArray insertModel(JsonObject model) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        String modelType = null;
        String colorPocket = null;
        String colorBag = null;
        int quantity = 0;

        if (model.containsKey(MODEL_TYPE) && model.get(MODEL_TYPE).getValueType() == JsonValue.ValueType.STRING) {
            modelType = model.getString(MODEL_TYPE);
        } else {
            JsonObject json = Json.createObjectBuilder().add(ERROR, MISSING_PARAM_OR_WRONG_TYPE).build();
            return jsonArrayBuilder.add(json).build();
        }
        if (model.containsKey(POCKET_COLOR) && model.get(POCKET_COLOR).getValueType() == JsonValue.ValueType.STRING) {
            colorPocket = model.getString(POCKET_COLOR);
        } else {
            JsonObject json = Json.createObjectBuilder().add(ERROR, MISSING_PARAM_OR_WRONG_TYPE).build();
            return jsonArrayBuilder.add(json).build();
        }
        if (model.containsKey(BAG_COLOR) && model.get(BAG_COLOR).getValueType() == JsonValue.ValueType.STRING) {
            colorBag = model.getString(BAG_COLOR);
        } else {
            JsonObject json = Json.createObjectBuilder().add(ERROR, MISSING_PARAM_OR_WRONG_TYPE).build();
            return jsonArrayBuilder.add(json).build();
        }
        if (model.containsKey(QUANTITY) && model.get(QUANTITY).getValueType() == JsonValue.ValueType.NUMBER) {
            quantity = model.getInt(QUANTITY);
        } else {
            JsonObject json = Json.createObjectBuilder().add(ERROR, MISSING_PARAM_OR_WRONG_TYPE).build();
            return jsonArrayBuilder.add(json).build();
        }

        if ("small".equals(modelType)) {
            SmallModel smallModel = SmallModel
                    .find("color_pocket_name = ?1 and color_bag_name = ?2", colorPocket, colorBag).firstResult();

            if (smallModel != null) {
                // If the model already exists in the database, update its quantity
                if (smallModel.quantity + quantity >= 0) {
                    smallModel.quantity += quantity;
                    smallModel.persist();
                } else {
                    JsonObject json = Json.createObjectBuilder().add(ERROR, "subtraction error").build();
                    return jsonArrayBuilder.add(json).build();
                }
            } else {
                // Otherwise, create a new model
                if (quantity >= 0) {
                    smallModel = new SmallModel();
                    smallModel.color_pocket_name = colorPocket;
                    smallModel.color_bag_name = colorBag;
                    smallModel.quantity = quantity;
                    smallModel.persist();
                } else {
                    JsonObject json = Json.createObjectBuilder().add(ERROR, "negativ quantity error").build();
                    return jsonArrayBuilder.add(json).build();
                }

            }
        } else if ("large".equals(modelType)) {
            LargeModel largeModel = LargeModel
                    .find("color_pocket_name = ?1 and color_bag_name = ?2", colorPocket, colorBag).firstResult();

            if (largeModel != null) {
                // If the model already exists in the database, update its quantity
                if (largeModel.quantity + quantity >= 0) {
                    largeModel.quantity += quantity;
                    largeModel.persist();
                } else {
                    JsonObject json = Json.createObjectBuilder().add(ERROR, "subtraction error").build();
                    return jsonArrayBuilder.add(json).build();
                }
            } else {
                // Otherwise, create a new model
                if (quantity >= 0) {
                    largeModel = new LargeModel();
                    largeModel.color_pocket_name = colorPocket;
                    largeModel.color_bag_name = colorBag;
                    largeModel.quantity = quantity;
                    largeModel.persist();
                } else {
                    JsonObject json = Json.createObjectBuilder().add(ERROR, "negativ quantity error").build();
                    return jsonArrayBuilder.add(json).build();
                }
            }
        } else {
            // Invalid model type
            JsonObject json = Json.createObjectBuilder().add(ERROR, "invalid model").build();
            return jsonArrayBuilder.add(json).build();
        }

        // Send confirmation
        JsonObject json = Json.createObjectBuilder().add("result", "ok").build();
        return jsonArrayBuilder.add(json).build();
    }

    @Path("/quantity")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getBag(@QueryParam("modelType") String modelType, @QueryParam("bagColor") String bagColor, @QueryParam("pocketColor") String pocketColor) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        if (modelType == null || bagColor == null || pocketColor == null) {
            JsonObject json = Json.createObjectBuilder().add(ERROR, "empty params error").build();
            return jsonArrayBuilder.add(json).build();
        } else {
            if ("smallModel".equals(modelType)) {
                SmallModel smallModel = SmallModel
                        .find("color_pocket_name = ?1 and color_bag_name = ?2", pocketColor, bagColor).firstResult();
                if (smallModel == null) {
                    JsonObject json = Json.createObjectBuilder().add(ERROR, "invalid model").build();
                    return jsonArrayBuilder.add(json).build();
                } else {
                    JsonObject json = Json.createObjectBuilder().add(QUANTITY, smallModel.quantity).build();
                    jsonArrayBuilder.add(json);
                }
            } else if ("largeModel".equals(modelType)) {
                LargeModel largeModel = LargeModel
                        .find("color_pocket_name = ?1 and color_bag_name = ?2", pocketColor, bagColor).firstResult();
                if (largeModel == null) {
                    JsonObject json = Json.createObjectBuilder().add(ERROR, "invalid model").build();
                    return jsonArrayBuilder.add(json).build();
                } else {
                    JsonObject json = Json.createObjectBuilder().add(QUANTITY, largeModel.quantity).build();
                    jsonArrayBuilder.add(json);
                }
            } else {
                JsonObject json = Json.createObjectBuilder().add(ERROR, "invalid model").build();
                return jsonArrayBuilder.add(json).build();
            }

        }

        return jsonArrayBuilder.build();
    }

}
