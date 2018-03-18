package pl.sda.jpatraining.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Orders")
public class Order {

    @Id
    private Integer id;
}

