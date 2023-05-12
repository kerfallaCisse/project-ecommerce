package stats;

import java.time.LocalDate;
import stats.Statistic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Statistic {
    
    private static LocalDate now = LocalDate.now();

    /**
     * 
     * @return the list of dates for the last seven days
     */

    public static List<LocalDate> getDatesLastWeek() {
        LocalDate startDate = now.minusDays(7);
        return startDate.datesUntil(now).collect(Collectors.toList());
    }

    /**
     * 
     * @return the list of dates for the last month, last thirty days from now
     */

    public static List<LocalDate> getDatesLastMonth() {
        LocalDate startDate = now.minusMonths(1);
        return startDate.datesUntil(now).collect(Collectors.toList());
    }

    /**
     * 
     * @return the list of dates for the last ninety days from now
     */

    public static List<LocalDate> getDatesLastThreeMonths() {
        LocalDate startDate = now.minusMonths(3);
        return startDate.datesUntil(now).collect(Collectors.toList());
    }

    /**
     * 
     * @return the list of dates for the last three hundred and sixty five days
     */
    public static List<LocalDate> getDatesLastYear() {
        LocalDate startDate = now.minusYears(1);
        return startDate.datesUntil(now).collect(Collectors.toList());
    }

    public static JsonObject constructResponseObject(HashMap<String, Long> map) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Set<Map.Entry<String, Long>> paires = map.entrySet();
        Iterator<Map.Entry<String, Long>> iter = paires.iterator();

        while (iter.hasNext()) {
            Map.Entry<String, Long> paire = iter.next();
            jsonObjectBuilder.add(paire.getKey(), paire.getValue());
        }

        return jsonObjectBuilder.build();
    }

}
