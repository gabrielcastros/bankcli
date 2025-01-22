package com.taskmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    private static Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public double checkBalance(int id) {
        String sql = "SELECT balance FROM public.users WHERE id_user = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public double deposit(int id, double amount) {
        double balance = checkBalance(id);
        double newBalance = balance + amount;
        String sql = "UPDATE public.users SET balance = ? WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
        return checkBalance(id);
    }

    public double withdraw(int id, double amount) {
        double balance = checkBalance(id);
        double newBalance = balance - amount;
        String sql = "UPDATE public.users SET balance = ? WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
        return checkBalance(id);
    }

}
