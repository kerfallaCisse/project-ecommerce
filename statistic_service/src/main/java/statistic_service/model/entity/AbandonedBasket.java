package statistic_service.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class AbandonedBasket extends PanacheEntity {

    @Column(length = 50)
    private String modelType;

    @Column
    private LocalDate created_at;
}
