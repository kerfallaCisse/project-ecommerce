package statistic_service.model.entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Entity
@Data
public class User extends PanacheEntity {

    @Column
    Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String auth0_user_id;

    @Column
    private LocalDate created_at;

    public JsonObject usersTotal() {
        int total = User.listAll().size();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("total", total);
        
        return jsonObjectBuilder.build();
    }



}
