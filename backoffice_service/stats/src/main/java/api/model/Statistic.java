package api.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Statistic extends PanacheEntity {

    @Column
    public Integer number_of_user_connected;

    @Column
    public Integer number_of_user_compte;

    @Column
    public Integer number_of_user_commands;

    @Column
    public Integer number_of_abandoned_bag;
    
}
