package dao;

import model.Email;
import utils.DBConnection;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public void saveEmail(Email email) {
    String sql = "INSERT INTO emails (sender, recipient, subject, message, timestamp) VALUES (?, ?, ?, ?, NOW())";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, email.getSender());
        stmt.setString(2, email.getRecipient());
        stmt.setString(3, email.getSubject());
        stmt.setString(4, email.getMessage());

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("✅ Email saved successfully.");
        } else {
            System.out.println("⚠️ Email not saved.");
        }

    } catch (SQLException e) {
        System.err.println("❌ Error saving email: " + e.getMessage());
    }
}


   public List<Email> getAllEmails() {
    List<Email> emailList = new ArrayList<>();
    String sql = "SELECT * FROM emails ORDER BY timestamp DESC";
    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Email email = new Email(
                rs.getString("sender"),
                rs.getString("recipient"),
                rs.getString("subject"),
                rs.getString("message")
            );
            email.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            emailList.add(email);
        }

    } catch (SQLException e) {
        System.err.println("❌ Error fetching emails: " + e.getMessage());
    }
    return emailList;
}
