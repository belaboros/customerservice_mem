package com.bb.cs.controller;

import com.bb.cs.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Customer REST controller:
 * Features:
 * <ul>
 *     <li>CRUD functionality for customers</li>
 *     <li>in memory repository</li>
 * </ul>
 *
 * TODO:
 * <ul>
 *     <li>make customer repository thread safe</li>
 * </ul>
 */
@RestController
@RequestMapping(path = "/api", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
@ConfigurationProperties(prefix="customercontroller")
public class CustomerController {


    @Autowired
    private CrudRepository<Customer, Integer> r;



    /**
     * http://localhost:9090/api/customers
     * @return
     */
    @GetMapping(value = "/customers")
    public List<Customer> getCustomers() {
        List<Customer> copy = new ArrayList<>();
        r.findAll().forEach(c -> copy.add(c));
        return copy;
    }

    /**
     * http://localhost:9090/api/customers/{id}
     * @return
     */
    @GetMapping(path = "/customers/{id}")
    public Customer getCustomer(@PathVariable Integer id) {
        return r.findById(id).get();
    }

    /**
     * http://localhost:9090/api/customers?title=XXX&name=YYY
     * @return
     */
    @PostMapping(value = "/customers")
    public Customer addCustomer(@RequestParam(value="title") String title, @RequestParam(value="name") String name) {
        Customer c = r.save(new Customer(title, name));
        return c;
    }

    /**
     * http://localhost:9090/api/customers/samples
     * @return
     */
    @PostMapping(value = "/customers/samples")
    public String addCustomerSamples() {
        r.save(new Customer("Mr", "John Doe"));
        r.save(new Customer("Ms", "Jane Doe"));
        r.save(new Customer("Miss", "Janie Doe"));

        return "";
    }

    /**
     * http://localhost:9090/api/customers/{id}
     * @return
     */
    @PutMapping(path = "/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable Integer id) {
        Optional<Customer> studentOptional = r.findById(id);

        if (!studentOptional.isPresent())
            return ResponseEntity.notFound().build();

        customer.setId(id);

        r.save(customer);

        return ResponseEntity.noContent().build();
    }

    /**
     * http://localhost:9090/api/customers?id=XYZ
     * @return
     */
    @DeleteMapping(value = "/customers/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        r.deleteById(id);
        return "";
    }

    /**
     * http://localhost:9090/api/customers
     * @return
     */
    @DeleteMapping(value = "/customers/all")
    public String deleteAllCustomer() {
        r.deleteAll();
        return "";
    }
}

