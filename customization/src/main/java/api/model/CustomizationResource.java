package api.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;
import java.util.HashMap;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Path("/customization")
public class CustomizationResource {

    @ConfigProperty(name = "cloudinary.cloud_name")
    String cloud_name;

    @ConfigProperty(name = "cloudinary.api_key")
    String api_key;

    @ConfigProperty(name = "cloudinary.api_secret")
    String api_secret;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getBag(@MultipartForm CustomizationFormData bagModel) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
    
        String modelType = getValueAsString(bagModel.modelType, jsonObjectBuilder);
        if (modelType == null) {
            return jsonObjectBuilder.build();
        }
        
        String pocketColor = getValueAsString(bagModel.pocketColor, jsonObjectBuilder);
        if (pocketColor == null) {
            return jsonObjectBuilder.build();
        }
        
        String bagColor = getValueAsString(bagModel.bagColor, jsonObjectBuilder);
        if (bagColor == null) {
            return jsonObjectBuilder.build();
        }
        
        String email = getValueAsString(bagModel.email, jsonObjectBuilder);
        if (email == null) {
            return jsonObjectBuilder.build();
        }
        
        int quantity = getValueAsInteger(bagModel.quantity, jsonObjectBuilder);
        if (quantity < 0) {
            return jsonObjectBuilder.build();
        }

        if ("largeModel".equals(bagModel.modelType) || "smallModel".equals(bagModel.modelType)) {
            LargeModel largeModel = LargeModel.find("bag_name", bagModel.bagColor + bagModel.pocketColor).firstResult();
            if (largeModel == null) {
                return jsonObjectBuilder.add("error", "bag not found").build();
            } else {
                String bagImage = null; // Set to null before testing
                try {
                    if (ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(bagModel.file.toPath()))) != null) {
                        File bagWithLogo = mergeImages(largeModel.cloudinary_url, bagModel.file);
                        bagImage = upload_image(bagWithLogo);
                    } else {
                        bagImage = largeModel.cloudinary_url;
                    }

                    jsonObjectBuilder
                            .add("email", email)
                            .add("modelType", modelType)
                            .add("bagColor", bagColor)
                            .add("pocketColor", pocketColor)
                            .add("quantity", quantity)
                            .add("cloudinary_url", bagImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Change it in case when real images are available
        /*
         * else if ("smallModel".equals(modelType)) {
         * SmallModel smallModel = SmallModel.find("bag_name", bagColor +
         * pocketColor).firstResult();
         * if (smallModel == null) {
         * return jsonObjectBuilder.add("error", "bag not found").build();
         * } else {
         * jsonObjectBuilder
         * .add("id", largeModel.id)
         * .add("cloudinary_url", largeModel.cloudinary_url);
         * }
         * }
         */

        else {
            return jsonObjectBuilder.add("error", "bag not found").build();
        }

        return jsonObjectBuilder.build();

    }

    public String upload_image(File bagWithLogo) {
        Map<String, String> config = new HashMap<String, String>();
        config.put("cloud_name", cloud_name);
        config.put("api_key", api_key);
        config.put("api_secret", api_secret);
        Cloudinary cloudinary = new Cloudinary(config);

        try {
            // Upload
            Map<String, Object> uploadResult = cloudinary.uploader().upload(bagWithLogo, ObjectUtils.emptyMap());
            // Get the public URL
            return cloudinary.url().generate(uploadResult.get("public_id").toString());
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return "Error: " + exception.getMessage();
        }
    }

    public File mergeImages(String imageUrl, File logoFile) throws IOException {
        byte[] formData = Files.readAllBytes(logoFile.toPath());
        ByteArrayInputStream bInputStream = new ByteArrayInputStream(formData);
        BufferedImage logo = ImageIO.read(bInputStream); // Uploaded logo 150x150 px 

        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();
        File file = File.createTempFile("temp", null);
        FileOutputStream outputStream = new FileOutputStream(file);

        byte[] buffer = new byte[8192];
        int bytesRead;
        try {
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    } finally {
        inputStream.close();
        outputStream.close();
    }

        File cloudinaryData = file;
        BufferedImage bagImage = ImageIO.read(cloudinaryData);

        // Create a Graphics object to perform the overlay
        Graphics2D g2d = bagImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the newLogo onto the bagImage
        g2d.drawImage(logo, (bagImage.getWidth() - logo.getWidth()) / 2, (bagImage.getHeight() / 2), null);

        // Cleanup
        g2d.dispose();

        // Save the merged image to a file (optional)
        File tempFile = File.createTempFile("logo", ".png");
        ImageIO.write(bagImage, "png", tempFile);

        return tempFile;
    }

    private String getValueAsString(Object value, JsonObjectBuilder jsonObjectBuilder) {
    if (value instanceof String) {
        return (String) value;
    } else {
        jsonObjectBuilder.add("error", "type error");
        return null;
    }
}

    private int getValueAsInteger(Object value, JsonObjectBuilder jsonObjectBuilder) {
    if (value instanceof String) {
        try {
            return Integer.parseInt((String) value);
        } catch (NumberFormatException e) {
            jsonObjectBuilder.add("error", "type error");
            return -1;
        }
    } else {
        jsonObjectBuilder.add("error", "type error");
        return -1;
    }

}}
