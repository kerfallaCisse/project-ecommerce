package statistic_service.model;

import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;


public interface Stats {
    public <T extends PanacheEntity> JsonObject statsWeek(T entity, EntityManager entityManager);
    public <T extends PanacheEntity> JsonObject statsMonth(T entity, EntityManager entityManager);
    public <T extends PanacheEntity> JsonObject statsLastThreeMonths(T entity, EntityManager entityManager, List<LocalDate> dates);
    public <T extends PanacheEntity> JsonObject statsLastYear(T entity, EntityManager entityManager);
}
