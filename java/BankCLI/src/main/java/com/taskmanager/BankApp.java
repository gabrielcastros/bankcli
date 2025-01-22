package com.taskmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class BankApp {
    private UserDAO userDAO;
    private CustomerDAO customerDAO;

    public BankApp(UserDAO userDAO, CustomerDAO customerDAO) {
        this.userDAO = userDAO;
        this.customerDAO = customerDAO;
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Welcome to BankApp ===");
            System.out.println("1 - Login");
            System.out.println("2 - Register");
            System.out.println("3 - Exit\n");
            System.out.print("Choose an option: ");

            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    handleLogin(sc);
                    break;
                case 2:
                    handleRegister(sc);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void showAuthMenu(int id) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Welcome to BankApp ===");
            System.out.println("1 - Check balance");
            System.out.println("2 - Deposit");
            System.out.println("3 - Withdraw");
            System.out.print("Choose an option: ");

            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    double balance = customerDAO.checkBalance(id);
                    System.out.println("\nBalance: " + balance);
                    break;
                case 2:
                    handleDeposit(sc, id);
                    break;
                case 3:
                    handleWithdraw(sc, id);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void handleLogin(Scanner sc) {
        System.out.println("\n=== Login ===");
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter your pin: ");
        int pin = 0;
        try {
            pin = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid pin.");
        }

        try {
            User user = userDAO.login(email, pin);
            if (user != null) {
                System.out.println("Welcome, " + user.getName() + "!");
                showAuthMenu(user.getId());
            } else {
                System.out.println("Invalid email or pin. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login. Please try again later.");
            e.printStackTrace();
        }
    }

    private void handleRegister(Scanner sc) {
        System.out.println("\n=== Register ===");
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        System.out.print("Enter your pin: ");
        int pin = Integer.parseInt(sc.nextLine());

        try {
            int id = userDAO.register(email, name, pin, "C");
            System.out.println("\nRegistration successful! User ID: " + id);
        } catch (Exception e) {
            System.out.println("\nAn error occurred during registration. Please try again later.");
            e.printStackTrace();
        }
    }

    public void handleDeposit(Scanner sc, int id) {
        System.out.println("\n=== Deposit ===");
        System.out.print("Enter amount (xx.xx): ");
        double amount = Double.parseDouble(sc.nextLine());
        double balance = customerDAO.deposit(id, amount);
        System.out.println("Success! New balance: " + balance);
    }

    public void handleWithdraw(Scanner sc, int id) {
        System.out.println("\n=== Withdraw ===");
        System.out.print("Enter amount (xx.xx): ");
        double amount = Double.parseDouble(sc.nextLine());
        double balance = customerDAO.withdraw(id, amount);
        System.out.println("Success! New balance: " + balance);
    }

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb", "username", "password")) {
            UserDAO userDAO = new UserDAO(connection);
            CustomerDAO customerDAO = new CustomerDAO(connection);
            BankApp app = new BankApp(userDAO, customerDAO);
            app.showMenu();
        } catch (Exception e) {
            System.out.println("Failed to connect to the database. Exiting...");
            e.printStackTrace();
        }
    }
}