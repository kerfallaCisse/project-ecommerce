package statistic_service.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Profit extends PanacheEntity {
    @Column
    private Double amount;

    @Column
    private LocalDate created_at;

}
