package com.kruczek.dao;

import com.kruczek.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);
        return theQuery.getResultList();
    }

    @Override
    public void addCustomer(Customer customer) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(customer);
    }

    @Override
    public Customer getCustomer(long customerId) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Customer.class, customerId);
    }

    @Override
    public void deleteCustomer(long customerId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Customer customer = currentSession.get(Customer.class, customerId);
        currentSession.delete(customer);
    }
}
