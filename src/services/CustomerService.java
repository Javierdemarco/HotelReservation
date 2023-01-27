package src.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import src.model.Customer;

public class CustomerService {

    private static CustomerService INSTANCE;
    private static final Map<String, Customer> customers = new HashMap<>();

    public static CustomerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }

    public void addCustomer(String email, String firstName, String lastName) throws Exception {
        // See if email already exist then add
        if (getCustomer(email) != null) {
            throw new Exception("The customer with email " + email + " already exist");
        }
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
