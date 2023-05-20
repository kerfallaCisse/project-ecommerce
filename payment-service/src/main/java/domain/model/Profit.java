package domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;


@Data
@Entity
public class Profit extends PanacheEntity {
    @Column
    public Double amount;

}
