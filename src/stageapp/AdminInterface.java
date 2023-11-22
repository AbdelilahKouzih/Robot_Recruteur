package stageapp;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.formdev.flatlaf.FlatIntelliJLaf;


public class AdminInterface extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> emailComboBox;

    private Connection connection;
    private Statement statement;

    public AdminInterface() throws SQLException {
    	
		 

        // Set up the frame
        setTitle("Admin Interface");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Apply a different look and feel (optional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("ComboBox.disabledForeground", new ColorUIResource(Color.BLACK));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Create the email field
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        // Create the password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordField.setEchoChar((char) 0);

        // Create the email combo box
        JLabel comboBoxLabel = new JLabel("Select Email:");
        emailComboBox = new JComboBox<>();
        emailComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateFields();
            }
        });
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        comboBoxPanel.add(comboBoxLabel);
        comboBoxPanel.add(emailComboBox);

        // Create the CRUD buttons
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        

        // Create the confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	   dispose();
            }
        });
        JPanel confirmButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        confirmButtonPanel.add(confirmButton);
        confirmButton.setBackground(new Color(0, 153, 51));
        addButton.setBackground(new Color(51, 153, 255));
        updateButton.setBackground(new Color(51, 153, 255));
        deleteButton.setBackground(new Color(51, 153, 255));

        // Set up the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240)); // Set the background color here
        mainPanel.add(emailPanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(comboBoxPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(confirmButtonPanel);
        add(mainPanel);

        try {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Populate the email combo box
        populateComboBox();

        // Activate the delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAdmin();
            }
        });

        // Activate the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAdmin();
            }
        });

        // Activate the update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAdmin();
            }
        });

        // Show the frame
        setVisible(true);
        
        FlatIntelliJLaf.install();
    }

    private void deleteAdmin() {
        String selectedEmail = (String) emailComboBox.getSelectedItem();
        if (selectedEmail != null) {
            try {
                Connection connection = DatabaseConnection.getInstance().getConnection();
                String query = "DELETE FROM admin WHERE mail = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, selectedEmail);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                JOptionPane.showMessageDialog(null, "Admin deleted successfully.");

                // Update the combo box and clear the fields
                populateComboBox();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to delete admin.");
            }
        }
    }

    private void addAdmin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                Connection connection = DatabaseConnection.getInstance().getConnection();
                String query = "INSERT INTO admin (mail, passwd) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                JOptionPane.showMessageDialog(null, "Admin added successfully.");

                // Update the combo box and clear the fields
                populateComboBox();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to add admin.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter both email and password.");
        }
    }

    private void updateAdmin() {
        String selectedEmail = (String) emailComboBox.getSelectedItem();
        String newEmail = emailField.getText();
        String newPassword = new String(passwordField.getPassword());

        if (selectedEmail != null && !newEmail.isEmpty() && !newPassword.isEmpty()) {
            try {
                Connection connection = DatabaseConnection.getInstance().getConnection();
                String query = "UPDATE admin SET mail = ?, passwd = ? WHERE mail = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, newEmail);
                preparedStatement.setString(2, newPassword);
                preparedStatement.setString(3, selectedEmail);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                JOptionPane.showMessageDialog(null, "Admin updated successfully.");

                // Update the combo box
                populateComboBox();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to update admin.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please enter both email and password.");
        }
    }

    private void populateComboBox() {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            ResultSet resultSet = statement.executeQuery("SELECT mail  FROM admin");
            List<String> emails = new ArrayList<>();
            while (resultSet.next()) {
                String email = resultSet.getString("mail");
                emails.add(email);
            }
            resultSet.close();

            emailComboBox.setModel(new DefaultComboBoxModel<>(emails.toArray(new String[0])));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        passwordField.setText("");
        emailField.setText("");
    }

    private void populateFields() {
        String selectedEmail = (String) emailComboBox.getSelectedItem();
        if (selectedEmail != null) {
            try {
                Connection connection = DatabaseConnection.getInstance().getConnection();
                ResultSet resultSet = statement.executeQuery("SELECT passwd FROM admin WHERE mail  = '" + selectedEmail + "'");
                if (resultSet.next()) {
                    String password = resultSet.getString("passwd");
                    emailField.setText(selectedEmail);
                    passwordField.setText(password);
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AdminInterface();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}