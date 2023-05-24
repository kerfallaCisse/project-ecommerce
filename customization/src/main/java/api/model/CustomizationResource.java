package api.model;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

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

    // Upload logo and put on a bag, upload image on cloudinary
    /*@Path("/add_logo")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public JsonObject addLogo(@MultipartForm CustomizationFormData formData) throws IOException {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        BufferedImage bagImage = CustomizationFormData.bagImage;
        BufferedImage newLogo = CustomizationFormData.newLogo;

        Graphics2D g = (Graphics2D) bagImage.createGraphics();
        g.drawImage(newLogo, (bagImage.getWidth() - newLogo.getWidth()) / 2, (bagImage.getHeight() - newLogo.getHeight()) / 2, null);
        // Replace by db upload 
        ImageIO.write(bagImage, "jpg", new File("bagWithLogo.jpg"));

        return jsonObjectBuilder.add("bag", "cloudinary url").build();
    }*/

    @Path("/add_logo")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String mergeImages(CustomizationFormData imageData) throws IOException {
        // Read the bagImage from the input stream
        BufferedImage bagImage = ImageIO.read(imageData.getBagImage());

        // Read the newLogo from the input stream
        BufferedImage newLogo = ImageIO.read(imageData.getNewLogo());

        // Create a Graphics object to perform the overlay
        Graphics2D g2d = bagImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the newLogo onto the bagImage
        g2d.drawImage(newLogo, 0, 0, null);

        // Cleanup
        g2d.dispose();

        // Save the merged image to a file (optional)
        String mergedImagePath = "/customization";
        ImageIO.write(bagImage, "png", new File(mergedImagePath));

        return mergedImagePath;
    }
    
}