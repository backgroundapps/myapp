package com.manollo.myapp.repository;

import java.util.List;

import com.manollo.myapp.entity.Customer;

import org.springframework.data.repository.CrudRepository;

/**
 * CustomerRepository
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);
}