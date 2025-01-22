package com.taskmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserDAO {
    private static Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }


    public static User login(String email, int pin) throws Exception {
        String sql = "SELECT * FROM Users WHERE email = ? and pin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setInt(2, pin);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id_user"), rs.getString("email"), rs.getString("name"), rs.getInt("pin"), rs.getString("role"));
                } else {
                    return null;
                }
            }
        }
    }

    public static int register(String email, String name, int pin, String role) throws SQLException {
        String sql = "INSERT INTO public.users "
                   + "VALUES(NEXTVAL('users_id_user_seq'), ?, ?, ?, 0, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, email);
            stmt.setInt(2, pin);
            stmt.setString(3, role);
            stmt.setString(4, name);

            int inserted = stmt.executeUpdate();
            if (inserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
