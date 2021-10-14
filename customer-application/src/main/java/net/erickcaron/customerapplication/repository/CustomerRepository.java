package net.erickcaron.customerapplication.repository;

import net.erickcaron.customerapplication.model.Customer;
import net.erickcaron.customerapplication.model.CustomerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByCustomerStatus(CustomerStatus customerStatus);
    List<Customer> findAllByCustomerStatusNot(CustomerStatus customerStatus);

}
