package pl.sda.jpatraining.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("sdajpa");

    public static void main(String[] args) {

        EntityManager entityManager =
                ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setFirstName("Andrzej");
        customer.setSurname("Nowak");
        Address address = new Address();//
        address.setCity("Warszawa");
        address.setPostalCode("92-001");
        address.setStreet("Kopcińskiego");
        customer.setAddress(address);
        entityManager.persist(customer);

        transaction.commit();



    }
}
