package domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;


@Data
@Entity
public class Profit extends PanacheEntity {
    @Column
    private Double amount;

    @Column
    private LocalDate created_at;
}
