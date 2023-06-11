package statistic_service.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import statistic_service.Date;
import java.util.List;
import java.time.LocalDate;
import jakarta.json.Json;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class StatsImpl implements Stats {

    Date date = new Date();

    public <T extends PanacheEntity> JsonObject statsWeek(T entity, EntityManager entityManager) {
        List<LocalDate> dates = date.getDatesLastWeek();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (LocalDate dte : dates) {
            String day = dte.getDayOfWeek().toString();
            String query = "FROM " + entity.getClass().getSimpleName() + " WHERE created_at = ?1";
            Long nbrUser = Long.valueOf(entityManager.createQuery(query).setParameter(1, dte).getResultList().size());
            jsonObjectBuilder.add(day, nbrUser);
        }
        return jsonObjectBuilder.build();
    }

    public <T extends PanacheEntity> JsonObject statsMonth(T entity, EntityManager entityManager) {
        List<LocalDate> dates = date.getDatesLastMonth();
        HashMap<String, Long> weeks = new HashMap<>() {
            {
                put("week1", 0L);
                put("week2", 0L);
                put("week3", 0L);
                put("week4", 0L);

            }
        };

        for (int i = 0; i < dates.size(); i++) {
            LocalDate dte = dates.get(i);
            String query = "FROM " + entity.getClass().getSimpleName() + " WHERE created_at = ?1";
            Long nbrUser = Long.valueOf(entityManager.createQuery(query).setParameter(1, dte).getResultList().size());

            if (i < 7)
                updateWeekValue("week1", nbrUser, weeks);

            else if (i >= 7 && i < 14)
                updateWeekValue("week2", nbrUser, weeks);

            else if (i >= 14 && i < 21)
                updateWeekValue("week3", nbrUser, weeks);

            else
                updateWeekValue("week4", nbrUser, weeks);
        }

        return constructResponseObject(weeks);

    }

    public <T extends PanacheEntity> JsonObject statsLastThreeMonths(T entity, EntityManager entityManager,
            List<LocalDate> dates) {
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
            LocalDate dte = dates.get(i);
            String query = "FROM " + entity.getClass().getSimpleName() + " WHERE created_at = ?1";
            Long nbrUser = Long.valueOf(entityManager.createQuery(query).setParameter(1, dte).getResultList().size());
            if (i < 7)
                updateWeekValue("week1", nbrUser, weeks);

            else if (i >= 7 && i < 14)
                updateWeekValue("week2", nbrUser, weeks);

            else if (i >= 14 && i < 21)
                updateWeekValue("week3", nbrUser, weeks);

            else if (i >= 21 && i < 28)
                updateWeekValue("week4", nbrUser, weeks);

            else if (i >= 28 && i < 35)
                updateWeekValue("week4", nbrUser, weeks);

            else if (i >= 35 && i < 42)
                updateWeekValue("week5", nbrUser, weeks);

            else if (i >= 42 && i < 49)
                updateWeekValue("week6", nbrUser, weeks);

            else if (i >= 49 && i < 56)
                updateWeekValue("week7", nbrUser, weeks);

            else if (i >= 56 && i < 63)
                updateWeekValue("week8", nbrUser, weeks);

            else if (i >= 63 && i < 70)
                updateWeekValue("week9", nbrUser, weeks);

            else if (i >= 70 && i < 77)
                updateWeekValue("week10", nbrUser, weeks);

            else if (i >= 77 && i < 84)
                updateWeekValue("week11", nbrUser, weeks);

            else
                updateWeekValue("week12", nbrUser, weeks);
        }
        return constructResponseObject(weeks);
    }

    public <T extends PanacheEntity> JsonObject statsLastYear(T entity, EntityManager entityManager) {
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

        JsonObject jsonObject1 = statsLastThreeMonths(entity, entityManager, _threeMonThDate4_list);
        JsonObject jsonObject2 = statsLastThreeMonths(entity, entityManager, _threeMonThDate3_list);
        JsonObject jsonObject3 = statsLastThreeMonths(entity, entityManager, _threeMonThDate2_list);
        JsonObject jsonObject4 = statsLastThreeMonths(entity, entityManager, _threeMonThDate1_list);

        computeJs(jsonObject1, months, 1);
        computeJs(jsonObject2, months, 4);
        computeJs(jsonObject3, months, 7);
        computeJs(jsonObject4, months, 10);

        return constructResponseObject(months);
    }

    protected void updateWeekValue(String key, Long nbrUser, HashMap<String, Long> weeks) {
        Long nbrOfUser = weeks.get(key);
        weeks.put(key, nbrOfUser + nbrUser);
    }

    protected JsonObject constructResponseObject(HashMap<String, Long> map) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        Set<Map.Entry<String, Long>> paires = map.entrySet();
        Iterator<Map.Entry<String, Long>> iter = paires.iterator();

        while (iter.hasNext()) {
            Map.Entry<String, Long> paire = iter.next();
            jsonObjectBuilder.add(paire.getKey(), paire.getValue());
        }

        return jsonObjectBuilder.build();
    }

    protected void computeJs(JsonObject jsonObject, HashMap<String, Long> months, int start_month) {
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