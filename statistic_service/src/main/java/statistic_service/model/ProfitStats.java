package statistic_service.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonObject;
import statistic_service.Date;
import java.util.List;
import java.util.stream.Stream;

import jakarta.persistence.TypedQuery;
import java.util.HashMap;

import java.time.LocalDate;
import jakarta.persistence.EntityManager;
import statistic_service.model.entity.Profit;
import java.util.stream.Collectors;

public class ProfitStats implements Stats {

    Date date = new Date();

    StatsImpl statsImpl = new StatsImpl();

    public <T extends PanacheEntity> JsonObject statsWeek(T entity, EntityManager entityManager) {
        List<LocalDate> dates = date.getDatesLastWeek();
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (LocalDate dte : dates) {
            String day = dte.getDayOfWeek().toString();
            String entityClassName = entity.getClass().getSimpleName();
            String q_string = "FROM " + entityClassName + " WHERE created_at = ?1";
            TypedQuery<Profit> typedQuery = entityManager.createQuery(q_string, Profit.class).setParameter(1, dte);
            Stream<Profit> profits = typedQuery.getResultStream();
            Double pAmount = profits.mapToDouble(p -> p.getAmount()).reduce(0, Double::sum);
            jsonObjectBuilder.add(day, pAmount);
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
            TypedQuery<Profit> typedQuery = entityManager.createQuery(query, Profit.class).setParameter(1, dte);
            Stream<Profit> profits = typedQuery.getResultStream();
            Long pAmount = Double.valueOf(profits.mapToDouble(p -> p.getAmount()).reduce(0, Double::sum)).longValue();
            if (i < 7)
                statsImpl.updateWeekValue("week1", pAmount, weeks);

            else if (i >= 7 && i < 14)
                statsImpl.updateWeekValue("week2", pAmount, weeks);

            else if (i >= 14 && i < 21)
                statsImpl.updateWeekValue("week3", pAmount, weeks);

            else
                statsImpl.updateWeekValue("week4", pAmount, weeks);
        }

        return statsImpl.constructResponseObject(weeks);
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
            TypedQuery<Profit> typedQuery = entityManager.createQuery(query, Profit.class).setParameter(1, dte);
            Stream<Profit> profits = typedQuery.getResultStream();
            Long pAmount = Double.valueOf(profits.mapToDouble(p -> p.getAmount()).reduce(0, Double::sum)).longValue();
            if (i < 7)
                statsImpl.updateWeekValue("week1", pAmount, weeks);

            else if (i >= 7 && i < 14)
                statsImpl.updateWeekValue("week2", pAmount, weeks);

            else if (i >= 14 && i < 21)
                statsImpl.updateWeekValue("week3", pAmount, weeks);

            else if (i >= 21 && i < 28)
                statsImpl.updateWeekValue("week4", pAmount, weeks);

            else if (i >= 28 && i < 35)
                statsImpl.updateWeekValue("week4", pAmount, weeks);

            else if (i >= 35 && i < 42)
                statsImpl.updateWeekValue("week5", pAmount, weeks);

            else if (i >= 42 && i < 49)
                statsImpl.updateWeekValue("week6", pAmount, weeks);

            else if (i >= 49 && i < 56)
                statsImpl.updateWeekValue("week7", pAmount, weeks);

            else if (i >= 56 && i < 63)
                statsImpl.updateWeekValue("week8", pAmount, weeks);

            else if (i >= 63 && i < 70)
                statsImpl.updateWeekValue("week9", pAmount, weeks);

            else if (i >= 70 && i < 77)
                statsImpl.updateWeekValue("week10", pAmount, weeks);

            else if (i >= 77 && i < 84)
                statsImpl.updateWeekValue("week11", pAmount, weeks);

            else
                statsImpl.updateWeekValue("week12", pAmount, weeks);
        }
        return statsImpl.constructResponseObject(weeks);
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

        statsImpl.computeJs(jsonObject1, months, 1);
        statsImpl.computeJs(jsonObject2, months, 4);
        statsImpl.computeJs(jsonObject3, months, 7);
        statsImpl.computeJs(jsonObject4, months, 10);

        return statsImpl.constructResponseObject(months);
    }

}
