package auth_service.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDate;


@Entity
@Data
public class User extends PanacheEntity {

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String auth0_user_id;

    @Column
    private LocalDate created_at;

    @Column
    private int admin;
}
