package statistic_service.model;

import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.time.LocalDate;

@ApplicationScoped
public class Stats {

    public <T extends PanacheEntity> void statsWeek(T entity, EntityManager entityManager) {
        String query = "SELECT id FROM " + entity.getClass().getSimpleName() + " WHERE created_at = ?1";
        LocalDate now = LocalDate.now();
        List<?> elements = entityManager.createQuery(query).setParameter(1, now).getResultList();
        System.out.println(elements.size());
    }

    public <T extends PanacheEntity> void profit(T entity, EntityManager entityManager) {
        String query = "SELECT id FROM " + entity.getClass().getSimpleName() + " WHERE created_at = ?1";
        LocalDate now = LocalDate.now();
        List<?> elements = entityManager.createQuery(query).setParameter(1, now).getResultList();
        // Utilisation de reduce pour les profils

    }
}
