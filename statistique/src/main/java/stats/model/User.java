package stats.model;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import stats.Statistic;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        HashMap<String, Long> weeks = new HashMap<>() {
            {
                put("week1", 0L);
                put("week2", 0L);
                put("week3", 0L);
                put("week4", 0L);

            }
        };

        List<LocalDate> dates = Statistic.getDatesLastMonth();

        for (int i = 0; i < dates.size(); i++) {

            LocalDate date = dates.get(i);
            long nbrUser = User.find("created_at", date).count();

            if (i < 7)
                updateNbrOfUsersWeek("week1", nbrUser, weeks);

            else if (i >= 7 && i < 14)
                updateNbrOfUsersWeek("week2", nbrUser, weeks);

            else if (i >= 14 && i < 21)
                updateNbrOfUsersWeek("week3", nbrUser, weeks);

            else
                updateNbrOfUsersWeek("week4", nbrUser, weeks);

        }

        return Statistic.constructResponseObject(weeks);

    }

    public static JsonObject statsLastThreeMonth(List<LocalDate> dates) {
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
            long nbrUser = User.find("created_at", date).count();

            if (i < 7)
                updateNbrOfUsersWeek("week1", nbrUser, weeks);

            else if (i >= 7 && i < 14)
                updateNbrOfUsersWeek("week2", nbrUser, weeks);

            else if (i >= 14 && i < 21)
                updateNbrOfUsersWeek("week3", nbrUser, weeks);

            else if (i >= 21 && i < 28)
                updateNbrOfUsersWeek("week4", nbrUser, weeks);

            else if (i >= 28 && i < 35)
                updateNbrOfUsersWeek("week4", nbrUser, weeks);

            else if (i >= 35 && i < 42)
                updateNbrOfUsersWeek("week5", nbrUser, weeks);

            else if (i >= 42 && i < 49)
                updateNbrOfUsersWeek("week6", nbrUser, weeks);

            else if (i >= 49 && i < 56)
                updateNbrOfUsersWeek("week7", nbrUser, weeks);

            else if (i >= 56 && i < 63)
                updateNbrOfUsersWeek("week8", nbrUser, weeks);

            else if (i >= 63 && i < 70)
                updateNbrOfUsersWeek("week9", nbrUser, weeks);

            else if (i >= 70 && i < 77)
                updateNbrOfUsersWeek("week10", nbrUser, weeks);

            else if (i >= 77 && i < 84)
                updateNbrOfUsersWeek("week11", nbrUser, weeks);

            else
                updateNbrOfUsersWeek("week12", nbrUser, weeks);

        }

        return Statistic.constructResponseObject(weeks);
    }

    public static JsonObject statsLastYear() {

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

        computeJs(jsonObject1, months, 1);
        computeJs(jsonObject2, months, 4);
        computeJs(jsonObject3, months, 7);
        computeJs(jsonObject4, months, 10);

        return Statistic.constructResponseObject(months);

    }

    private static void updateNbrOfUsersWeek(String key, Long nbrUser, HashMap<String, Long> weeks) {
        Long nbrOfUser = weeks.get(key);
        weeks.put(key, nbrOfUser + nbrUser);
    }



    private static void computeJs(JsonObject jsonObject, HashMap<String, Long> months, int start_month) {
        Long _monthValue = 0L;
        String initialMonth = "month" + Long.toString(Long.valueOf(start_month));
        String startMonthPlus1 = "month" + Long.toString(Long.valueOf(start_month + 1));
        String startMonthPlus2 = "month" + Long.toString(Long.valueOf(start_month + 2));
        _monthValue += Long.parseLong(jsonObject.getJsonNumber("week1").toString())
                + Long.parseLong(jsonObject.getJsonNumber("week2").toString())
                + Long.parseLong(jsonObject.getJsonNumber("week3").toString())
                + Long.parseLong(jsonObject.getJsonNumber("week4").toString());
        months.put(initialMonth, _monthValue);
        _monthValue = 0L;
        _monthValue += Long.parseLong(jsonObject.getJsonNumber("week5").toString())
                + Long.parseLong(jsonObject.getJsonNumber("week6").toString())
                + Long.parseLong(jsonObject.getJsonNumber("week7").toString())
                + Long.parseLong(jsonObject.getJsonNumber("week8").toString());
        months.put(startMonthPlus1, _monthValue);
        _monthValue = 0L;
        _monthValue += Long.parseLong(jsonObject.getJsonNumber("week9").toString())
                + Long.parseLong(jsonObject.getJsonNumber("week10").toString())
                + Long.parseLong(jsonObject.getJsonNumber("week11").toString())
                + Long.parseLong(jsonObject.getJsonNumber("week12").toString());
        months.put(startMonthPlus2, _monthValue);
    }

}
