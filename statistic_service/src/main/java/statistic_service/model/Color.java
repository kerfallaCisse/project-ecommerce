package statistic_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.List;
import jakarta.json.JsonObject;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

@Entity
public class Color extends PanacheEntity {

    // This entity is also use for orders

    @Column(length = 100)
    public String name;

    @Column
    public LocalDate created_at;

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
                User.updateWeekValue(color_name, nbrOrders, colors);
            }
        }

        return User.constructResponseObject(colors);
    }

    public JsonObject statsWeek(List<LocalDate> dates) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (LocalDate date : dates) {
            String day = date.getDayOfWeek().toString();
            Long nbrOrders = Color.find("created_at", date).count();
            jsonObjectBuilder.add(day, nbrOrders);
        }

        return jsonObjectBuilder.build();
    }

    public JsonObject statsMonth(List<LocalDate> dates) {
        HashMap<String, Long> weeks = new HashMap<>() {
            {
                put("week1", 0L);
                put("week2", 0L);
                put("week3", 0L);
                put("week4", 0L);

            }
        };

        for (int i = 0; i < dates.size(); i++) {

            LocalDate date = dates.get(i);
            long nbrOrder = Color.find("created_at", date).count();

            if (i < 7)
                User.updateWeekValue("week1", nbrOrder, weeks);

            else if (i >= 7 && i < 14)
                User.updateWeekValue("week2", nbrOrder, weeks);

            else if (i >= 14 && i < 21)
                User.updateWeekValue("week3", nbrOrder, weeks);

            else
                User.updateWeekValue("week4", nbrOrder, weeks);

        }

        return User.constructResponseObject(weeks);

    }

    public JsonObject statsLastThreeMonth(List<LocalDate> dates) {
        HashMap<String, Long> weeks = new HashMap<>() {
            {
                put("week1", 0L);
                put("week2", 0L);
                put("week3", 0L);
                put("week4", 0L);
                put("week5", 0L);
                put("week6", 0L);
                put("week7", 0L);
                put("week8", 0L);
                put("week9", 0L);
                put("week10", 0L);
                put("week11", 0L);
                put("week12", 0L);

            }
        };

        for (int i = 0; i < dates.size(); i++) {

            LocalDate date = dates.get(i);
            long nbrOrder = Color.find("created_at", date).count();

            if (i < 7)
                User.updateWeekValue("week1", nbrOrder, weeks);

            else if (i >= 7 && i < 14)
                User.updateWeekValue("week2", nbrOrder, weeks);

            else if (i >= 14 && i < 21)
                User.updateWeekValue("week3", nbrOrder, weeks);

            else if (i >= 21 && i < 28)
                User.updateWeekValue("week4", nbrOrder, weeks);

            else if (i >= 28 && i < 35)
                User.updateWeekValue("week4", nbrOrder, weeks);

            else if (i >= 35 && i < 42)
                User.updateWeekValue("week5", nbrOrder, weeks);

            else if (i >= 42 && i < 49)
                User.updateWeekValue("week6", nbrOrder, weeks);

            else if (i >= 49 && i < 56)
                User.updateWeekValue("week7", nbrOrder, weeks);

            else if (i >= 56 && i < 63)
                User.updateWeekValue("week8", nbrOrder, weeks);

            else if (i >= 63 && i < 70)
                User.updateWeekValue("week9", nbrOrder, weeks);

            else if (i >= 70 && i < 77)
                User.updateWeekValue("week10", nbrOrder, weeks);

            else if (i >= 77 && i < 84)
                User.updateWeekValue("week11", nbrOrder, weeks);

            else
                User.updateWeekValue("week12", nbrOrder, weeks);

        }

        return User.constructResponseObject(weeks);
    }

    public JsonObject statsLastYear() {

        HashMap<String, Long> months = new HashMap<>() {
            {
                put("month1", 0L);
                put("month2", 0L);
                put("month3", 0L);
                put("month4", 0L);
                put("month5", 0L);
                put("month6", 0L);
                put("month7", 0L);
                put("month8", 0L);
                put("month9", 0L);
                put("month10", 0L);
                put("month11", 0L);
                put("month12", 0L);
            }
        };

        LocalDate now = LocalDate.now();
        LocalDate _threeMonThDate1 = now.minusMonths(3);
        LocalDate _threeMonThDate2 = _threeMonThDate1.minusMonths(3);
        LocalDate _threeMonThDate3 = _threeMonThDate2.minusMonths(3);
        LocalDate _threeMonThDate4 = _threeMonThDate3.minusMonths(3);

        // We generate the interval dates for each dates
        List<LocalDate> _threeMonThDate4_list = _threeMonThDate4.datesUntil(_threeMonThDate3)
                .collect(Collectors.toList());
        List<LocalDate> _threeMonThDate3_list = _threeMonThDate3.datesUntil(_threeMonThDate2)
                .collect(Collectors.toList());
        List<LocalDate> _threeMonThDate2_list = _threeMonThDate2.datesUntil(_threeMonThDate1)
                .collect(Collectors.toList());
        List<LocalDate> _threeMonThDate1_list = _threeMonThDate1.datesUntil(now)
                .collect(Collectors.toList());

        JsonObject jsonObject1 = statsLastThreeMonth(_threeMonThDate4_list);
        JsonObject jsonObject2 = statsLastThreeMonth(_threeMonThDate3_list);
        JsonObject jsonObject3 = statsLastThreeMonth(_threeMonThDate2_list);
        JsonObject jsonObject4 = statsLastThreeMonth(_threeMonThDate1_list);

        User.computeJs(jsonObject1, months, 1);
        User.computeJs(jsonObject2, months, 4);
        User.computeJs(jsonObject3, months, 7);
        User.computeJs(jsonObject4, months, 10);

        return User.constructResponseObject(months);

    }

    public Boolean save(String color, LocalDate currentDate) {
        Color colorObject = new Color();
        colorObject.name = color;
        colorObject.created_at = currentDate;
        colorObject.persist();
        if(!colorObject.isPersistent()) {
            return false;
        } else {
            return true;
        }
    }

}
