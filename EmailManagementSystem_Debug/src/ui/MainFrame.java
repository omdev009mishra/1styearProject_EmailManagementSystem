package ui;

import dao.EmailDAO;
import model.Email;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainFrame extends JFrame {
    JTextField senderField, recipientField, subjectField;
    JTextArea bodyArea;
    JButton sendButton, viewButton;

    public MainFrame() {
        setTitle("Email Management System");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        senderField = new JTextField();
        recipientField = new JTextField();
        subjectField = new JTextField();
        bodyArea = new JTextArea(10, 30);
        sendButton = new JButton("Send Email");
        viewButton = new JButton("View Sent Emails");

        panel.add(new JLabel("Sender:"));
        panel.add(senderField);
        panel.add(new JLabel("Recipient:"));
        panel.add(recipientField);
        panel.add(new JLabel("Subject:"));
        panel.add(subjectField);
        panel.add(new JLabel("Body:"));
        panel.add(new JScrollPane(bodyArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        buttonPanel.add(viewButton);
        panel.add(buttonPanel);

        add(panel);

        sendButton.addActionListener((ActionEvent e) -> {
            if (senderField.getText().isEmpty() || recipientField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sender and Recipient fields cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Email email = new Email();
            email.setSender(senderField.getText());
            email.setRecipient(recipientField.getText());
            email.setSubject(subjectField.getText());
            email.setBody(bodyArea.getText());

            new EmailDAO().sendEmail(email);
            JOptionPane.showMessageDialog(this, "Email Sent!");

            // Clear fields after sending
            senderField.setText("");
            recipientField.setText("");
            subjectField.setText("");
            bodyArea.setText("");
        });

        viewButton.addActionListener(e -> {
            JFrame viewFrame = new JFrame("Sent Emails");
            viewFrame.setSize(700, 500);
            JTextArea emailList = new JTextArea();
            emailList.setEditable(false);

            List<Email> emails = new EmailDAO().getAllEmails();
            if (emails.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No emails found in the database.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            StringBuilder sb = new StringBuilder();
            for (Email email : emails) {
                sb.append("From: ").append(email.getSender()).append("\n");
                sb.append("To: ").append(email.getRecipient()).append("\n");
                sb.append("Subject: ").append(email.getSubject()).append("\n");
                sb.append("Body:\n").append(email.getBody()).append("\n");
                sb.append("Time: ").append(email.getTimestamp()).append("\n");
                sb.append("------------------------------------------------\n\n");
            }

            emailList.setText(sb.toString());
            viewFrame.add(new JScrollPane(emailList));
            viewFrame.setLocationRelativeTo(null);
            viewFrame.setVisible(true);
        });

        setVisible(true);
    }
}