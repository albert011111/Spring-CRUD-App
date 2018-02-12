package com.kruczek.dao;

import com.kruczek.entity.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getCustomers();
    void addCustomer(Customer customer);
    Customer getCustomer(long customerId);
    void deleteCustomer(long customerId);
}
