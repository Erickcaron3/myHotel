package net.erickcaron.customerapplication.service.impl;

import net.erickcaron.customerapplication.exception.CustomerError;
import net.erickcaron.customerapplication.exception.CustomerException;
import net.erickcaron.customerapplication.model.Customer;
import net.erickcaron.customerapplication.model.CustomerStatus;
import net.erickcaron.customerapplication.repository.CustomerRepository;
import net.erickcaron.customerapplication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll(CustomerStatus customerStatus) {
        if(customerStatus != null){
            customerRepository.findAllByCustomerStatus(customerStatus);
        }
        return customerRepository.findAllByCustomerStatusNot(CustomerStatus.DELETED);
    }

    @Override
    public Customer findById(Long id) {
        Customer customerById = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerException(CustomerError.CUSTOMER_NOT_EXISTING));

        checkIfCustomerStatusIsDeleted(customerById.getCustomerStatus());
        return customerById;
    }

    @Override
    public Long create(Customer customer) {
        checkIfCustomerInputIsCorrect(customer);
        return customerRepository.save(customer).getId();
    }

    @Override
    public void update(Long id, Customer customer) {
        Customer customerFromDB = findById(id);
        checkIfCustomerInputIsCorrect(customer);
        Customer customerToDB = completeUpdate(customerFromDB, customer);
        customerRepository.save(customerToDB);

    }

    private Customer completeUpdate(Customer customerFromDB, Customer newCustomer) {
        newCustomer.setCustomerStatus(customerFromDB.getCustomerStatus());
        newCustomer.setId(customerFromDB.getId());
        return newCustomer;
    }
    private void checkIfCustomerStatusIsDeleted(CustomerStatus customerStatus) {
        if(customerStatus.equals(CustomerStatus.DELETED)){
            throw new CustomerException(CustomerError.CUSTOMER_DATAS_DELETED);
        }
    }

    @Override
    public void deactivate(Long id) {
        Customer customerToDelete = findById(id);
        changeStatusToDeleted(customerToDelete);
        customerRepository.save(customerToDelete);
    }


    private void checkIfCustomerInputIsCorrect(Customer customer) {
        checkIfIdentityInputCorrect(customer.getLastName(), customer.getFirstName());
    }


    private void checkIfIdentityInputCorrect(String firstName, String lastName) {
        if(firstName.trim().length() == 0 || lastName.trim().length() == 0){
            throw new CustomerException(CustomerError.INCORRECT_IDENTITY_INPUT);
        }
    }

    private Customer changeStatusToDeleted(Customer customer){
        customer.setCustomerStatus(CustomerStatus.DELETED);
        return customer;
    }

}
