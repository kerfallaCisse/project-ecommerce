package org.acme;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;



@Path("/hello")
public class GreetingResource {

    @Inject
    GreetingService service;

    @Inject
    @Default
    DataSource dataSource;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting")
    public String greeting(@QueryParam("name") String name) {
        return service.greeting(name);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String hello() throws SQLException {
        String result = "";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM total");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = "Actual quantity is : " + resultSet.getInt(1);
            }
        }
        catch(Exception e) {
            System.out.println("Error");
          }
        String formHtml = "<form method=\"post\" action=\"/hello\">" +
                          "<label for=\"quantity\">New quantity:</label>" +
                          "<input type=\"number\" id=\"quantity\" name=\"quantity\">" +
                          "<button type=\"submit\">Update</button>" +
                          "</form>";

        return "<h1>" + result + "</h1>" + formHtml;
    }

    @POST
    @Path("/")
    public Response setNewStock(@FormParam("quantity") int quantity) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE total SET quantity = ?");
            statement.setInt(1, quantity);
            statement.executeUpdate();
        }

        // Redirect to hello endpoint
        return Response.seeOther(UriBuilder.fromPath("/hello").build()).build();
    }
}