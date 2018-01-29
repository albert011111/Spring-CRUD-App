package com.kruczek.dao;

import com.kruczek.entity.Customer;

import java.util.List;

/**
 * Created by Patryk on 14.09.2017.
 */
public interface CustomerDao {
     List<Customer> getCustomers();

    void addCustomer(Customer customer);

    Customer getCustomer(long theId);

    void deleteCustomer(long theId);
}
