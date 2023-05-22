package com.example.projectmanagementtool.Repository;

import com.example.projectmanagementtool.Model.User;
import com.example.projectmanagementtool.Repository.Util.ConnectionManager;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("UserRepository")
public class UserRepository {


    public User getUserFromID(int id) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Users WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                String role = rs.getString("Role");
                return new User(id, name, email, password, role);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
        return null;
    }

    public List<User> getAllUsers() {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Users";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            List<User> userList = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                String role = rs.getString("Role");
                userList.add(new User(id, name, email, password, role));
            }
            return userList;

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);

        }
    }

    public void createUser(User user) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "Insert into Users (Name, Email, Password, Role) values (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public User getUser(String email, String password) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String SQL = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            User user = null;

            while (rs.next()) {
                user = new User(rs.getInt("ID"), rs.getString("Name"), rs.getString("Email"), rs.getString("Password"), rs.getString("Role"));
            }
            return user;

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
