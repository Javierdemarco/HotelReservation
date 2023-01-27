package src.test;

import src.model.Customer;

public class Tester {

    public static void main(String[] args) {

        // Test Correct Customer
        Customer customer = new Customer("first", "second", "j@domain.com");
        System.out.println(customer);

        // Test Incorrect Email
        Customer customerEmail = new Customer("first", "second", "email");
    }

}
