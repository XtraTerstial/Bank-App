package repository;

import domain.Customer;

import java.util.*;

public class CustomerRepository {
    private final Map<String, Customer> customersById = new HashMap<>();

    public List<Customer> findAll() {
        return new ArrayList<>(customersById.values());
    }
}
