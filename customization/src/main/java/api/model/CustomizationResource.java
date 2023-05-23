package api.model;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Path("/customization")
public class CustomizationResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getBag(@QueryParam("modelType") String modelType, @QueryParam("bagColor") String bagColor,
            @QueryParam("pocketColor") String pocketColor) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        if (modelType == null) {
            modelType = "largeModel";
        }
        if (bagColor == null) {
            bagColor = "Black";
        }
        if (pocketColor == null) {
            pocketColor = "Black";
        }
        if ("largeModel".equals(modelType) || "smallModel".equals(modelType)) {
            LargeModel largeModel = LargeModel.find("bag_name", bagColor + pocketColor).firstResult();
            if (largeModel == null) {
                return jsonObjectBuilder.add("error", "bag not found").build();
            } else {
                jsonObjectBuilder
                    .add("id", largeModel.id)
                    .add("cloudinary_url", largeModel.cloudinary_url);
            }
        }

        // Change it in case when real images are available
        /*else if ("smallModel".equals(modelType)) {
            SmallModel smallModel = SmallModel.find("bag_name", bagColor + pocketColor).firstResult();
            if (smallModel == null) {
                return jsonObjectBuilder.add("error", "bag not found").build();
            } else {
                jsonObjectBuilder
                    .add("id", largeModel.id)
                    .add("cloudinary_url", largeModel.cloudinary_url);
            }
        }
        */

        else {
            return jsonObjectBuilder.add("error", "bag not found").build();
        }

        return jsonObjectBuilder.build();

    }

    // IMPORTANT change manually image path and model.bag_name
    @Path("/upload")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public String upload_image() {
        Map<String, String> config = new HashMap<String, String>();
        // Ask Denis for credits
        config.put("cloud_name", "");
        config.put("api_key", "");
        config.put("api_secret", "");
        Cloudinary cloudinary = new Cloudinary(config);

        try {
            // Upload
            Map<String, Object> uploadResult = cloudinary.uploader().upload("BlackBlack.jpeg", ObjectUtils.emptyMap()); // Change
                                                                                                                        // URL

            // Get the public URL
            String publicUrl = cloudinary.url().generate(uploadResult.get("public_id").toString());
            System.out.println(publicUrl);

            // Add URL to database
            LargeModel model = new LargeModel();
            model.bag_name = ""; // Set the bag name here. Ex BlackBlack
            model.cloudinary_url = publicUrl;
            model.persist(); // Save the entity to the database

            return publicUrl;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return "Error: " + exception.getMessage();
        }
    }

}