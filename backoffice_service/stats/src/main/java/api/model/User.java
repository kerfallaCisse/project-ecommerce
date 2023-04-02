package api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "user")
public class User extends PanacheEntity {

    @Column(length = 50)
    public String email;
    @Column
    public int admin;

}
