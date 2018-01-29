package com.kruczek.service;

import com.kruczek.entity.Customer;

import java.util.List;

/**
 * Created by Patryk on 21.09.2017.
 */
public interface CustomerService {
    List<Customer> getCustomers();
    void addCustomer(Customer customer);

    Customer getCustomer(long theId);

    void deleteCustomer(long theId);
}
