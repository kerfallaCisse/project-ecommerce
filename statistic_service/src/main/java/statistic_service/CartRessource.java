package statistic_service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import statistic_service.model.entity.User;
import statistic_service.mail.GMailer;
import statistic_service.model.entity.CustomBag;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import java.util.List;

@Path("cart")
@Transactional
public class CartRessource  {

    //GMailer gMailer = new GMailer();

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addToCart(JsonObject jsonObject) {
        String email = jsonObject.getString("email");
        String modelType = jsonObject.getString("modelType");
        String bagColor = jsonObject.getString("bagColor");
        String pocketColor = jsonObject.getString("pocketColor");
        String image = jsonObject.getString("image");
        int logo = jsonObject.getInt("logo");
        int quantity = jsonObject.getInt("quantity");

        // Add to cart is only able for user who creates an account
        // But we check if the user exist in the database
        Optional<User> user = User.find("email", email).firstResultOptional();
        if (user.isPresent()) {
            Long user_id = user.get().getId();
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
                return Response.status(Response.Status.CREATED).build();
            else
                return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserCart(@QueryParam("email") String email) {
        if (email == null)
            Response.status(Response.Status.BAD_REQUEST).build();
        // we check if the user exists in the data base
        Optional<User> user = User.find("email", email).firstResultOptional();
        if (user.isPresent()) {
            Long user_id = user.get().getId();
            // We find all the customized bag by the user
            List<CustomBag> custom_bags = CustomBag.find("user_id", user_id).list();
            if (custom_bags.size() == 0)
                return Response.status(Response.Status.NO_CONTENT).build();

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

            return Response.ok(jsonArrayBuilder.build()).build();

        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("confirmation")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getDeliveryInfos(JsonObject jsonObject) {

        String firtsName = jsonObject.getString("firstname");
        String lastName = jsonObject.getString("lastName");
        String address = jsonObject.getString("address");
        String zipCode = jsonObject.getString("zipCode");
        String town =  jsonObject.getString("town");
        String country = jsonObject.getString("country");
        String email = jsonObject.getString("email");
        String phoneNumber = jsonObject.getString("phoneNumber");


        
        return Response.ok().build();
    }

    @GET
    @Path("mail")
    public void testEmail() throws Exception {
        GMailer gMailer = new GMailer();
        gMailer.sendEmail("A new message for command confirmation", "Commande confirmation send from invia");
    }

}
