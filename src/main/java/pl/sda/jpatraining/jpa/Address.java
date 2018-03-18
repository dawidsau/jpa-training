package pl.sda.jpatraining.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Address {
    private String city;
    @Column(name = "postal_code")
    private String postalCode;
    private String street;
}
