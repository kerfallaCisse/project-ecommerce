package statistic_service.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class AbandonedBasket extends PanacheEntity {
    @Column
    private int nbr;
}
