package com.kruczek.service;

import com.kruczek.dao.CustomerDao;
import com.kruczek.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Patryk on 21.09.2017.
 */

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerDao customerDao;


    @Override
    public List<Customer> getCustomers() {
        return customerDao.getCustomers();
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDao.addCustomer(customer);
    }

    @Override
    public Customer getCustomer(long theId) {
        return customerDao.getCustomer(theId);
    }

    @Override
    public void deleteCustomer(long theId) {
        customerDao.deleteCustomer(theId);
    }
}
