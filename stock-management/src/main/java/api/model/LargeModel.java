package api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "LargeModel")
public class LargeModel extends PanacheEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "quantity")
    public int quantity;

    @Column(name = "color_pocket_name")
    public String color_pocket_name;

    @Column(name = "color_bag_name")
    public String color_bag_name;
}