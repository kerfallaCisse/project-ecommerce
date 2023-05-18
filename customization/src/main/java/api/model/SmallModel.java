package api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "SmallModel")
public class SmallModel extends PanacheEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "bag_name")
    public String bag_name;

    @Column(name = "cloudinary_url")
    public String cloudinary_url;
}