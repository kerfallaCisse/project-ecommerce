package stats.model;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import stats.Statistic;

import java.util.HashMap;
import java.util.List;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Entity
public class User extends PanacheEntity {

    @Column(length = 100)
    public String name;

    @Column(length = 100)
    public String email;

    @Column(length = 100)
    public String auth0_user_id;

    @Column
    public LocalDate created_at;

    private static HashMap<String, Long> weeks = new HashMap<>() {{
        put("sem1", 0L);
        put("sem2", 0L);
        put("sem3", 0L);
        put("sem4", 0L);

    }};

    public static JsonObject statsWeek() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        List<LocalDate> dates = Statistic.getDatesLastWeek();
        for (LocalDate date : dates) {
            String day = date.getDayOfWeek().toString();
            Long nbrUser = User.find("created_at", date).count();
            jsonObjectBuilder.add(day, nbrUser);
        }
        return jsonObjectBuilder.build();
    }

    public static JsonObject statsMonth() {

        List<LocalDate> dates = Statistic.getDatesLastMonth();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        for (int i = 0; i < dates.size(); i++) {

            LocalDate date = dates.get(i);
            long nbrUser = User.find("created_at", date).count();

            if (i < 7)
                updateNbrOfUsersWeek("sem1", nbrUser);

            else if (i >= 7 && i < 14)
                updateNbrOfUsersWeek("sem2", nbrUser);

            else if (i >= 14 && i < 21)
                updateNbrOfUsersWeek("sem3", nbrUser);

            else
                updateNbrOfUsersWeek("sem4", nbrUser);

        }

        jsonObjectBuilder.add("sem1", weeks.get("sem1"));
        jsonObjectBuilder.add("sem2", weeks.get("sem2"));
        jsonObjectBuilder.add("sem3", weeks.get("sem3"));
        jsonObjectBuilder.add("sem4", weeks.get("sem4"));

        return jsonObjectBuilder.build();

    }

    private static void updateNbrOfUsersWeek(String key, long nbrUser) {
        Long nbrOfUser = weeks.get(key);
        weeks.put(key, nbrOfUser + nbrUser);
    }

}
