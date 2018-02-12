package com.kruczek.service;

import com.kruczek.entity.Customer;

import java.util.List;


public interface CustomerService {
    List<Customer> getCustomers();
    void addCustomer(Customer customer);
    Customer getCustomer(long customerId);
    void deleteCustomer(long customerId);
}
