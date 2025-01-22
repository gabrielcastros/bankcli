package com.taskmanager;

public class Customer extends User{
    private int cardNumber;
    private double balance;

    public Customer(int id, String email, String name, int pin, String role, int cardNumber, double balance) {
        super(id, email, name, pin, role);
        this.cardNumber = cardNumber;
        this.balance = balance;
    }
}
