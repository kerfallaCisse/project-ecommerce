package statistic_service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Color extends PanacheEntity {

    // This entity zs also use for orders

    @Column(length = 100)
    private String name;

    @Column
    private LocalDate created_at;


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
