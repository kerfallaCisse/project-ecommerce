package statistic_service;

import java.util.Optional;
import jakarta.json.JsonObject;
import statistic_service.model.entity.User;
import statistic_service.mail.GMailer;
import statistic_service.model.entity.CustomBag;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import java.util.List;
import java.util.ArrayList;
import org.eclipse.microprofile.config.ConfigProvider;

public class CartRessource {

    public Boolean addToCart(JsonObject jsonObject) {
        try {
            String email = jsonObject.getString("email");
            String modelType = jsonObject.getString("modelType");
            String bagColor = jsonObject.getString("bagColor");
            String pocketColor = jsonObject.getString("pocketColor");
            String image = jsonObject.getString("image");
            Integer logo = (Integer) jsonObject.getInt("logo");
            Integer quantity = (Integer) jsonObject.getInt("quantity");

            if (pocketColor == null || bagColor == null || quantity == null || logo == null || image == null
                    || modelType == null || email == null) {
                return false;
            }

            String[] models = { "largeModel", "smallModel" };
            List<String> all_models = new ArrayList<>();
            for (String mdl : models) {
                all_models.add(mdl);
            }

            if (!all_models.contains(modelType))
                return false;

            String[] colors_name = { "red", "blue", "green", "yellow", "white", "grey",
                    "black" };
            List<String> all_colors = new ArrayList<>();
            for (String clr : colors_name) {
                all_colors.add(clr);
            }

            if (!all_colors.contains(pocketColor) || !all_colors.contains(bagColor)) {
                return false;
            }

            // Add to cart is only able for user who creates an account
            // But we check if the user exist in the database
            Optional<User> user = User.find("email", email).firstResultOptional();
            if (user.isPresent()) {
                Long user_id = user.get().getId();

                Optional<CustomBag> csbag = CustomBag
                        .find("bagColor = ?1 AND pocketColor = ?2 AND user_id = ?3", bagColor, pocketColor, user_id)
                        .firstResultOptional();
                // We update the user custom bag
                if (csbag.isPresent()) {
                    // We only update the quantity
                    CustomBag bagToUpdate = csbag.get();
                    int initial_quantity = bagToUpdate.getQuantity();
                    bagToUpdate.update("quantity = ?1", initial_quantity + quantity);
                    return true;
                }

                // We create a new instance in the data base
                CustomBag customBag = new CustomBag();
                customBag.setBagColor(bagColor);
                customBag.setImage(image);
                customBag.setLogo(logo);
                customBag.setPocketColor(pocketColor);
                customBag.setModelType(modelType);
                customBag.setUser_id(user_id);
                customBag.setQuantity(quantity);
                customBag.persist();
                if (customBag.isPersistent())
                    return true;
                else
                    return false;
            }
        } catch (NullPointerException e) {
            // System.err.println(e.getMessage());
            // System.err.println(e.getStackTrace());
            return false;
        }

        return false;
    }

    public JsonArray getUserCart(String email) {
        if (email == null)
            return null;
        // we check if the user exists in the data base
        Optional<User> user = User.find("email", email).firstResultOptional();
        if (user.isPresent()) {
            Long user_id = user.get().getId();
            // We find all the customized bag by the user
            List<CustomBag> custom_bags = CustomBag.find("user_id", user_id).list();
            if (custom_bags.size() == 0)
                return null;

            // We create the json to return
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            for (CustomBag cb : custom_bags) {
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                jsonObjectBuilder.add("modelType", cb.getModelType());
                jsonObjectBuilder.add("bagColor", cb.getBagColor());
                jsonObjectBuilder.add("pocketColor", cb.getPocketColor());
                jsonObjectBuilder.add("logo", cb.getLogo());
                jsonObjectBuilder.add("image", cb.getImage());
                jsonObjectBuilder.add("quantity", cb.getQuantity());
                jsonArrayBuilder.add(jsonObjectBuilder);
            }

            return jsonArrayBuilder.build();

        }

        return null;
    }

    public Boolean getDeliveryInfos(JsonObject jsonObject) {

        String firtsName;
        String lastName;
        String address;
        String zipCode;
        String town;
        String country;
        String email;
        String phoneNumber;

        try {
            firtsName = jsonObject.getString("firstName");
            lastName = jsonObject.getString("lastName");
            address = jsonObject.getString("address");
            zipCode = jsonObject.getString("zipCode");
            town = jsonObject.getString("town");
            country = jsonObject.getString("country");
            email = jsonObject.getString("email");
            phoneNumber = jsonObject.getString("phoneNumber");
        } catch (NullPointerException e) {
            // System.err.println(e.getMessage());
            // System.err.println(e.getStackTrace());
            return false;
        }

        String MESSAGE_TO_INVIA = "New order to be delivered." + "\nCountry: " + country + "\nAddress: " + address
                + "\nZip code: " + zipCode + "\nTown: " + town + "\nEmail: " + email + "\nPhone number: " + phoneNumber
                + "\nFirst name: " + firtsName + "\nLast name: " + lastName;
        String INVIA_SUBJECT = "new command";
        String CUSTOMER_SUBJECT = "order confirmation";
        String MESSAGE_TO_CUSTOMER = "We have received your order. Thank you for your purchase. \nYou will receive the bag at the following address: "
                + "\nCountry: " + country + "\nAddress: " + address
                + "\nZip code: " + zipCode + "\nTown: " + town;

        try {

            GMailer gMailer = new GMailer();
            String INVIA_EMAIL = ConfigProvider.getConfig().getValue("invia.email", String.class);
            // We send the email to invia inbox
            gMailer.sendEmail(INVIA_SUBJECT, MESSAGE_TO_INVIA, INVIA_EMAIL);

            // We send the email to the customer
            gMailer.sendEmail(CUSTOMER_SUBJECT, MESSAGE_TO_CUSTOMER, email);

        } catch (Exception e) {
            // System.err.println(e.getMessage());
            // System.err.println(e.getStackTrace());
            return false;
        }

        return true;
    }

    public Boolean deleteBasket(String image) {
        // All the images generated by cloudinary are unic
        Optional<CustomBag> basketToDelete = CustomBag.find("image", image).firstResultOptional();
        if (basketToDelete.isPresent()) {
            CustomBag basket = basketToDelete.get();
            basket.delete();
            return true;
        }
        return false;
    }

    public Boolean updateBasketQty(String image) {
        // We get the quantity of the bag in the db
        Optional<CustomBag> basketToUpdate = CustomBag.find("image", image).firstResultOptional();
        if (basketToUpdate.isPresent()) {
            CustomBag basket = basketToUpdate.get();
            int initial_quantity = basket.getQuantity();
            if (initial_quantity <= 0) {
                return false;
            }
            // We update the quantity and the stats for abandonned basket
            basket.update("image = ?1 AND quantity = ?2", image, initial_quantity - 1);
        }

        return true;
    }

    // Delete user basket after payment
    public Boolean deletUserBaskets(String user_email) {
        Optional<User> user = User.find("email", user_email).firstResultOptional();
        if (user.isPresent()) {
            Long user_id = user.get().getId();
            // We delete all the basket of this user
            CustomBag.delete("user_id", user_id);
            return true;
        }
        return false;
    }
}
