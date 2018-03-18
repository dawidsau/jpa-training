package pl.sda.jpatraining.jpa;

import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

public class JpaMain {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("sdajpa");

    public static void main(String[] args) {

        EntityManager entityManager =
                ENTITY_MANAGER_FACTORY.createEntityManager();

        findCustomerExample(entityManager);
        createCustomer();
        createOrder();

        System.exit(0);
    }

    private static void createOrder() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Order order = new Order();
        order.setOrderStatus(OrderStatus.NEW);
        order.setTotalCost(BigDecimal.valueOf(100));
        entityManager.persist(order);
        transaction.commit();

    }

    private static void createCustomer() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
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
        System.out.println();
        transaction.commit();
    }

    private static void findCustomerExample(EntityManager entityManager) {
        Query query = entityManager.createQuery(
                "select c from Customer c " +
                        "where c.firstName = :firstName and c.surname = :surname",Customer.class);
        query.setParameter("firstName", "Andrzej");
        query.setParameter("surname", "Nowak");

        List<Customer> resultList = query.getResultList();

        QCustomer qcustomer = QCustomer.customer; //ciekawostka
        Customer andrzej = new JPAQuery<>(entityManager).select(qcustomer).from(qcustomer)
                .where(qcustomer.firstName.eq("Andrzej")).fetchFirst();
    }
}
