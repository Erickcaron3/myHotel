package net.erickcaron.customerapplication.rest;

import net.erickcaron.customerapplication.model.Customer;
import net.erickcaron.customerapplication.model.CustomerStatus;
import net.erickcaron.customerapplication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/customers")
public class CustomerController {

    //TODO PATCH!

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> findAll(@RequestParam(name = "status", required = false) CustomerStatus customerStatus){
        return customerService.findAll(customerStatus);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Long id){
        return customerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Customer customer){
        customerService.update(id, customer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateById(@PathVariable("id") Long id){
        customerService.deactivate(id);
    }
}
