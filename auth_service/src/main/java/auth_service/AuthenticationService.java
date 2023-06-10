package auth_service;

import java.util.Optional;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import auth_service.entity.User;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.json.Json;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;

import java.util.ArrayList;

@Path("/auth")
public class AuthenticationService {

    @Inject
    JsonWebToken jsonWebToken;
    // //jsonWebToken.getClaim("email")

    @Inject
    SecurityIdentity securityIdentity;

    String api_token = ConfigProvider.getConfig().getValue("auth.api.token", String.class);

    // @GET
    // @Produces(MediaType.TEXT_HTML)
    // public String stock() {
    // return "<html>\n" +
    // " <body>\n" +
    // " <h1>Hello " + securityIdentity.getPrincipal().getName() + "</h1>\n" +
    // "<h1>Admin: " + verifPrivileges() + "</h1>\n" +
    // " </body>\n" +
    // "</html>\n";
    // }

    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject stats() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        if (verifPrivileges())
            return jsonObjectBuilder.add("status", Response.Status.OK.getStatusCode()).build();
        return jsonObjectBuilder.add("status", Response.Status.UNAUTHORIZED.getStatusCode()).build();
    }

    @GET
    @Path("stock")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject stock() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        if (verifPrivileges())
            return jsonObjectBuilder.add("status", Response.Status.OK.getStatusCode()).build();
        return jsonObjectBuilder.add("status", Response.Status.UNAUTHORIZED.getStatusCode()).build();
    }

    public Boolean verifPrivileges() {
        String auth0_user_id = securityIdentity.getPrincipal().getName();
        Optional<User> user = User.find("auth0_user_id", auth0_user_id).firstResultOptional();
        if (user.isPresent()) {
            User correspondingUser = user.get();
            String email = correspondingUser.getEmail();
            // We get user information with auth0 management api
            try {
                HttpResponse<JsonNode> response = Unirest
                        .get("https://dev-xuzmuq3g0kbtxrc4.us.auth0.com/api/v2/users?q=" + email + "&search_engine=v3")
                        .header("authorization", "Bearer " + api_token)
                        .asJson();
                JSONArray jsonArray = response.getBody().getArray();
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONArray groups = jsonObject.getJSONObject("app_metadata").getJSONObject("authorization")
                        .getJSONArray("groups");
                if (groups.length() == 0)
                    return false;
                ArrayList<String> grps = new ArrayList<>();
                for (Object g : groups) {
                    grps.add(g.toString());
                }
                if (grps.contains("admin"))
                    return true;
            } catch (UnirestException e) {
                return false;
                // e.printStackTrace();
            }
        }

        return false;

    }

    /***
     * 
     * THE API MUST BE IN TYPE SERVICE, SO WE CAN USE THE jsonwebToken -> usefull to
     * insert user in the database
     */

    // This endpoint must be called after the user has created an account with auth0
    @POST
    @Path("registration")
    public Response userRegistration() throws UnirestException {
        String auth0_user_id = securityIdentity.getPrincipal().getName();
        Optional<User> user = User.find("auth0_user_id",
                auth0_user_id).firstResultOptional();
        if (user.isEmpty()) {
            User userToInsert = new User();
            HttpResponse<JsonNode> response = Unirest
                    .get("https://dev-xuzmuq3g0kbtxrc4.us.auth0.com/api/v2/users/" + jsonWebToken.getName())
                    .header("authorization", "Bearer " + api_token)
                    .asJson();
            JSONObject userInfo = response.getBody().getObject();
            String email = userInfo.getString("email");
            String nickname = userInfo.getString("nickname");
            String[] account_date = userInfo.getString("created_at").substring(0, 10).split("-");
            int year = Integer.parseInt(account_date[0]);
            int month = Integer.parseInt(account_date[1]);
            int day = Integer.parseInt(account_date[2]);
            LocalDate created_at = LocalDate.of(year, month, day);
            userToInsert.setAuth0_user_id(auth0_user_id);
            userToInsert.setEmail(email);
            userToInsert.setName(nickname);
            userToInsert.setAdmin(0);
            userToInsert.setCreated_at(created_at);
            userToInsert.persist();
            if (userToInsert.isPersistent()) return Response.status(Response.Status.CREATED).build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.CONFLICT).build();

    }

}
