package api.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Color extends PanacheEntity {
    
    @Column(length = 50)
    public String color_name;

    @Column
    public Integer number_of_commands;

    
}
