package stats.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import stats.Statistic;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.HashMap;

@Entity
public class Color extends PanacheEntity {

    @Column(length = 100)
    public String name;

    @Column
    public LocalDate created_at;

    private static HashMap<String, Long> colors = new HashMap<>() {
        {
            put("blue", 0L);
            put("white", 0L);
            put("green", 0L);
            put("yellow", 0L);
            put("red", 0L);
            put("grey", 0L);
            put("black", 0L);

        }
    };

    public static JsonObject statsWeek() {
        List<LocalDate> dates = Statistic.getDatesLastWeek();
        Set<String> colors_name = colors.keySet();
        for (String color_name : colors_name) {
            for (LocalDate date : dates) {
                Long nbrOrders = Color.find("name = ?1 AND created_at = ?2", color_name, date).count();
                updateColorOrders(colors, color_name, nbrOrders);
            }
        }

        return Statistic.constructResponseObject(colors);
    }

    

    private static void updateColorOrders(HashMap<String, Long> colors, String color_name, Long nbrOrders) {
        Long initial_value = colors.get(color_name);
        colors.put(color_name, initial_value + nbrOrders);
    }

}
