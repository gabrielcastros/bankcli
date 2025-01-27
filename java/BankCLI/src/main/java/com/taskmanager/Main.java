package com.taskmanager;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (var connection = DB.connectDB()) {
            if (connection == null) {
                System.out.println("Unable to reach DB.");
            } else {
                UserDAO userDAO = new UserDAO(connection);
                CustomerDAO customerDAO = new CustomerDAO(connection);
                BankApp bankApp = new BankApp(userDAO, customerDAO);
                bankApp.showMenu();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
