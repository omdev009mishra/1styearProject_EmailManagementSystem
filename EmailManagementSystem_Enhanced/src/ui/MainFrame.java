package ui;

import model.Email;
import dao.EmailDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JTextField senderField, recipientField, subjectField;
    private JTextArea bodyArea;
    private JButton sendButton, viewButton;

    public MainFrame() {
        setTitle("Email Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Sender:"));
        senderField = new JTextField();
        inputPanel.add(senderField);
        inputPanel.add(new JLabel("Recipient:"));
        recipientField = new JTextField();
        inputPanel.add(recipientField);
        inputPanel.add(new JLabel("Subject:"));
        subjectField = new JTextField();
        inputPanel.add(subjectField);
        inputPanel.add(new JLabel("Body:"));
        bodyArea = new JTextArea(5, 30);
        inputPanel.add(new JScrollPane(bodyArea));
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        sendButton = new JButton("Send Email");
        viewButton = new JButton("View Sent Emails");
        buttonPanel.add(sendButton);
        buttonPanel.add(viewButton);
        add(buttonPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendEmail());
        viewButton.addActionListener(e -> viewEmails());

        setVisible(true);
    }

    private void sendEmail() {
        String sender = senderField.getText().trim();
        String recipient = recipientField.getText().trim();
        String subject = subjectField.getText().trim();
        String body = bodyArea.getText().trim();

        if (sender.isEmpty() || recipient.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Email email = new Email(sender, recipient, subject, body);
        boolean success = EmailDAO.saveEmail(email);

        if (success) {
            JOptionPane.showMessageDialog(this, "Email sent successfully.");
            senderField.setText("");
            recipientField.setText("");
            subjectField.setText("");
            bodyArea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to send email. Check DB connection.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewEmails() {
        List<Email> emails = EmailDAO.getAllEmails();
        StringBuilder sb = new StringBuilder("Sent Emails: ");
        for (Email email : emails) {
            sb.append(email).append(" ");
        }
        JTextArea emailList = new JTextArea(sb.toString());
        emailList.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(emailList), "Email History", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}