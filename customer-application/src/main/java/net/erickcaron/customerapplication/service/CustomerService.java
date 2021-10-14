package net.erickcaron.customerapplication.service;

import net.erickcaron.customerapplication.model.Customer;
import net.erickcaron.customerapplication.model.CustomerStatus;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll(CustomerStatus customerStatus);

    Customer findById(Long id);

    Long create(Customer customer);

    void update(Long id, Customer customer);

    void deactivate(Long id);
}
