package domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;
import java.time.LocalDate;


@Data
@Entity
public class Profit extends PanacheEntity {
    @Column
    private Double amount;

    @Column
    private LocalDate created_at;
}
