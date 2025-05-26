package dao;

import model.Email;
import utils.DBConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDAO {
    public void sendEmail(Email email) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO emails (sender, recipient, subject, body) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email.getSender());
            stmt.setString(2, email.getRecipient());
            stmt.setString(3, email.getSubject());
            stmt.setString(4, email.getBody());
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Email inserted successfully.");
            } else {
                System.err.println("Failed to insert email.");
                JOptionPane.showMessageDialog(null, "Failed to send email.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Email> getAllEmails() {
        List<Email> emails = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM emails ORDER BY timestamp DESC";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                Email email = new Email();
                email.setId(rs.getInt("id"));
                email.setSender(rs.getString("sender"));
                email.setRecipient(rs.getString("recipient"));
                email.setSubject(rs.getString("subject"));
                email.setBody(rs.getString("body"));
                email.setTimestamp(rs.getString("timestamp"));
                emails.add(email);
            }
            System.out.println("Retrieved " + emails.size() + " emails from database.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return emails;
    }
}