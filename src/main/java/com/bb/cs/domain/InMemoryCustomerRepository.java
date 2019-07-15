package com.bb.cs.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * in memory repository.
 * TODO:
 * <ul>
 *      <li>current implementation is not thread safe</li>
 * </ul>
 */
@Repository
public class InMemoryCustomerRepository implements CrudRepository<Customer, Integer> {

    private Set<Customer> customers = new TreeSet<>();
    private AtomicInteger nextId = new AtomicInteger(0);


    public InMemoryCustomerRepository() {
    }

    @Override
    public long count() {
        return customers.size();
    }

    @Override
    public boolean existsById(Integer id) {
        return customers.stream().anyMatch(c -> c.getId().equals(id));
    }

    public Customer save(Customer s) {
        Customer customerWithId;

        // if new (un-persisted) entity
        if (s.getId() < 0) {
            customerWithId = new Customer(nextId.getAndIncrement(), s.getTitle(), s.getName());
            customers.add(customerWithId);
        } else {
            customerWithId = s;
            // remove the old one
            deleteById(s.getId());

            // add the new one
            customers.add(customerWithId);
        }

        return customerWithId;
    }

    @Override
    public Iterable<Customer> findAll() {
        return Collections.unmodifiableSet(customers);
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customers.stream().filter(c-> ObjectUtils.nullSafeEquals(c.getId(), id)).findAny();
    }

    @Override
    public void deleteById(Integer id) {
        customers.removeIf(c -> ObjectUtils.nullSafeEquals(c.getId(), id));
    }

    @Override
    public void delete(Customer customer) {
        customers.removeIf(c->ObjectUtils.nullSafeEquals(c.getId(), customer.getId()));
    }

    @Override
    public void deleteAll() {
        customers.clear();
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<Customer> findAllById(Iterable<Integer> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException();
    }
}
