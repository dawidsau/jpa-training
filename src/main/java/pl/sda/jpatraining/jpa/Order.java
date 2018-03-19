package pl.sda.jpatraining.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @ManyToOne
    private Customer customer;

}

