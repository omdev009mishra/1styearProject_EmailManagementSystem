package dao;

import model.Email;
import utils.DBConnection;
import java.sql.*;
import java.util.*;

public class EmailDAO {
    public static boolean saveEmail(Email email) {
        String query = "INSERT INTO emails (sender, recipient, subject, body) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email.getSender());
            ps.setString(2, email.getRecipient());
            ps.setString(3, email.getSubject());
            ps.setString(4, email.getBody());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("DB Error: " + e.getMessage());
            return false;
        }
    }

    public static List<Email> getAllEmails() {
        List<Email> emails = new ArrayList<>();
        String query = "SELECT * FROM emails";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                emails.add(new Email(
                    rs.getString("sender"),
                    rs.getString("recipient"),
                    rs.getString("subject"),
                    rs.getString("body")));
            }
        } catch (SQLException e) {
            System.err.println("DB Retrieval Error: " + e.getMessage());
        }
        return emails;
    }
}