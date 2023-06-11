package statistic_service.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class CustomBag extends PanacheEntity {

    @Column
    private Long user_id;
    
    @Column(length = 50)
    private String modelType;

    @Column(length = 50)
    private String bagColor;

    @Column(length = 50)
    private String pocketColor;

    @Column
    private int logo;

    @Column(length = 255)
    private String image;

    @Column
    private int quantity;


    
}
