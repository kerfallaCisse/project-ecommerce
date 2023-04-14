package ch.unige.projet_info.stats_jwt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class User extends PanacheEntity {

    @Column
    public Integer admin;

    @Column(length = 100)
    public String auth0_user_id;

    @Column(length = 50)
    public String email;

}
