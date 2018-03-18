package pl.sda.jpatraining.jpa;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    private String surname;
    private String city;
    @Column(name = "postal_code")
    private String postalCode;
    private String street;

}
