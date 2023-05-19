package api.model;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
    public String getBag(@QueryParam("modelType") String modelType, @QueryParam("bagColor") String bagColor,
            @QueryParam("pocketColor") String pocketColor) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        if (modelType == null) {
            modelType = "largeModel";
        }
        if (bagColor == null) {
            bagColor = "Black";
        }
        if (pocketColor == null) {
            pocketColor = "Black";
        }
        if ("largeModel".equals(modelType)) {
            LargeModel largeModel = LargeModel.find("bag_name", bagColor + pocketColor).firstResult();
            if (largeModel == null) {
                jsonArrayBuilder.add(Json.createObjectBuilder()
                        .add("Error", "Bag not found"));
            }

            jsonArrayBuilder.add(Json.createObjectBuilder()
                    .add("id", largeModel.id)
                    .add("cloudinary_url", largeModel.cloudinary_url));

        }

        else if ("smallModel".equals(modelType)) {
            jsonArrayBuilder.add(Json.createObjectBuilder()
                    .add("Error", "Databse empty"));
        }

        return jsonArrayBuilder.build().toString();

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