package com.taskmanager;

public class User {
    private int id;
    private String name;
    private String email;
    private int pin;
    private final String role;

    public User(int id, String email, String name, int pin, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pin = pin;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPin() {
        return pin;
    }

    public String getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changePin(int newPin) {
        this.pin = newPin;
    }
}
