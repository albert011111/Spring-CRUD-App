package com.kruczek.dao;

import com.kruczek.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Patryk on 2017-09-16.
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {


    @Autowired
    SessionFactory sessionFactory;



    @Override
    @Transactional
    public List<Customer> getCustomers() {

        Session currentSession = sessionFactory.getCurrentSession();
        Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName", Customer.class);

        return theQuery.getResultList();
    }

    @Override
    @Transactional
    public void addCustomer(Customer customer) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(customer);
    }

    @Override
    @Transactional
    public Customer getCustomer(long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Customer.class, theId);
    }

    @Override
    @Transactional
    public void deleteCustomer(long theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Customer customer = currentSession.get(Customer.class, theId);
        currentSession.delete(customer);
    }





}
