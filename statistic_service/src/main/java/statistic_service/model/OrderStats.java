package statistic_service.model;

import statistic_service.model.entity.Color;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.util.HashMap;
import jakarta.json.JsonObject;

public class OrderStats extends StatsImpl {
    
    StatsImpl statsImpl  = new StatsImpl();

    public JsonObject stats(List<LocalDate> dates) {
        HashMap<String, Long> colors = new HashMap<>() {
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

        Set<String> colors_name = colors.keySet();
        for (String color_name : colors_name) {
            for (LocalDate date : dates) {
                Long nbrOrders = Color.find("name = ?1 AND created_at = ?2", color_name, date).count();
                statsImpl.updateWeekValue(color_name, nbrOrders, colors);
            }
        }

        return statsImpl.constructResponseObject(colors);
    }
    
}
