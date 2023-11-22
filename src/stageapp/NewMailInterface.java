package stageapp;
	
	import javax.swing.*;

	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.io.File;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.time.LocalDateTime;
	import java.time.format.DateTimeFormatter;
	import java.util.ArrayList;
	import java.util.List;
	import stageapp.DatabaseConnection;
	import com.formdev.flatlaf.FlatIntelliJLaf;

	
	
	public class NewMailInterface extends JFrame {
	    private JTextField objetField;
	    private JTextArea messageField;
	    private JTextField dateField;
	    private JTextField codeArea;
	    private List<File> uploadedFiles;
	    private JList<String> fileList;
	    private JComboBox<String> mailComboBox;
	    private static String id;
	    private String selectedMailId; // Variable to store the selected mail ID
	    JButton importButton = new JButton("Import");
	    JButton createButton = new JButton("Create");
	    JButton updateButton = new JButton("Update");
	    JButton deleteButton = new JButton("Delete");
	    JButton cancelButton = new JButton("Cancel");
	    JButton confirmButton = new JButton("Confirm");

	    public NewMailInterface(String id) {
	        this.id = id;
	        uploadedFiles = new ArrayList<>();
	        
			 FlatIntelliJLaf.install();


	        // Initialize Swing components
	        objetField = new JTextField(20);
	        messageField = new JTextArea(10, 20);
	        messageField.setLineWrap(true);
	        messageField.setWrapStyleWord(true);

	        dateField = new JTextField(20);
	        codeArea = new JTextField(20);
	        fileList = new JList<>();
	        JScrollPane fileListScrollPane = new JScrollPane(fileList);

	        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	        
	        // Create a panel for each group of components
	        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        comboBoxPanel.add(new JLabel("     Mails :  "));
	        mailComboBox = new JComboBox<>();
	        comboBoxPanel.add(mailComboBox);
	        
	        int verticalSpaceHeight = 100; // Adjust the height as needed
	        Component verticalSpace = Box.createRigidArea(new Dimension(0, verticalSpaceHeight));
	        comboBoxPanel.add(verticalSpace);
	        
	        JPanel objetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        objetPanel.add(new JLabel("     Objet :  "));
	        objetPanel.add(objetField);

	        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        messagePanel.add(new JLabel("Message :"));
	        messagePanel.add(new JScrollPane(messageField));

	        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        datePanel.add(new JLabel("      Date  : "));
	        datePanel.add(dateField);

	        JPanel importPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        importPanel.add(new JLabel("     Fichier :   "));
	        importPanel.add(importButton);

	        JPanel fileListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        fileListPanel.add(new JLabel("                   "));
	        fileListPanel.add(fileListScrollPane);

	        JPanel codePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        codePanel.add(new JLabel("        Code:"));
	        codePanel.add(codeArea);

	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        buttonPanel.add(createButton);
	        buttonPanel.add(updateButton);
	        buttonPanel.add(deleteButton);

	        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	        controlPanel.add(cancelButton);
	        controlPanel.add(confirmButton);

	      

	        // Add the panels to the frame
	        add(comboBoxPanel);
	        add(objetPanel);
	        add(messagePanel);
	        add(datePanel);
	        add(importPanel);
	        add(fileListPanel);
	        add(codePanel);
	        add(buttonPanel);
	        add(controlPanel);
	      

	        // Adjust component sizes
	        objetField.setPreferredSize(new Dimension(200, 30));
	        messageField.setPreferredSize(new Dimension(300, 00));
	        dateField.setPreferredSize(new Dimension(200, 30));
	        fileListScrollPane.setPreferredSize(new Dimension(200, 100));
	        codeArea.setPreferredSize(new Dimension(100, 30));
	        mailComboBox.setPreferredSize(new Dimension(200, 30));
	        messageField.setCaretPosition(0);


	        // Set field styles
	        objetField.setFont(new Font("Arial", Font.PLAIN, 12));
	        objetField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	        messageField.setFont(new Font("Arial", Font.PLAIN, 12));
	        messageField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	        dateField.setFont(new Font("Arial", Font.PLAIN, 12));
	        dateField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	        codeArea.setFont(new Font("Arial", Font.PLAIN, 12));
	        codeArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	        
	        // Set colors and fonts
	        getContentPane().setBackground(new Color(240, 240, 240));
	        objetPanel.setBackground(new Color(240, 240, 240));
	        messagePanel.setBackground(new Color(240, 240, 240));
	        datePanel.setBackground(new Color(240, 240, 240));
	        importPanel.setBackground(new Color(240, 240, 240));
	        fileListPanel.setBackground(new Color(240, 240, 240));
	        codePanel.setBackground(new Color(240, 240, 240));
	        buttonPanel.setBackground(new Color(240, 240, 240));
	        controlPanel.setBackground(new Color(240, 240, 240));
	        comboBoxPanel.setBackground(new Color(240, 240, 240));

	        createButton.setBackground(new Color(51, 153, 255));
	        createButton.setForeground(Color.WHITE);
	        updateButton.setBackground(new Color(51, 153, 255));
	        updateButton.setForeground(Color.WHITE);
	        deleteButton.setBackground(new Color(51, 153, 255));
	        deleteButton.setForeground(Color.WHITE);
	        cancelButton.setBackground(new Color(204, 0, 0));
	        cancelButton.setForeground(Color.WHITE);
	        confirmButton.setBackground(new Color(0, 153, 51));
	        confirmButton.setForeground(Color.WHITE);

	        pack();
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        
	        dateField.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                // Set the current date and time to the dateField
	                LocalDateTime now = LocalDateTime.now();
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	                dateField.setText(now.format(formatter));
	            }
	        });
	
	        // Register event listeners
	        importButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                JFileChooser fileChooser = new JFileChooser();
	                fileChooser.setMultiSelectionEnabled(true);
	                int result = fileChooser.showOpenDialog(NewMailInterface.this);
	                if (result == JFileChooser.APPROVE_OPTION) {
	                    File[] selectedFiles = fileChooser.getSelectedFiles();
	                    for (File file : selectedFiles) {
	                        uploadedFiles.add(file);
	                    }
	                    updateFileList();
	                }
	            }
	        });
	
	        createButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	 createMail();
	                 refreshInterface();
	            }
	        });
	
	        updateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                updateMail();
	                refreshInterface();
	            }
	        });
	
	        deleteButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                deleteMail();
	                refreshInterface();
	            }
	        });
	        cancelButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Close the current NewMailInterface window
	                dispose();

	                try {
	                    Connection connection = DatabaseConnection.getInstance().getConnection();

	                    // Perform select query to retrieve the email
	                    String query = "SELECT mail FROM email WHERE idcompte = ?";
	                    PreparedStatement statement = connection.prepareStatement(query);
	                    statement.setString(1, id);
	                    ResultSet resultSet = statement.executeQuery();

	                    String email = null;
	                    if (resultSet.next()) {
	                        email = resultSet.getString("mail");
	                    }

	                    // Close the database connection and statement
	                    resultSet.close();
	                    statement.close();
	                    connection.close();

	                    final String finalEmail = email; // Create a final variable for use inside the Runnable

	                    // Create and display the PostulationInterface window, passing the retrieved email
	                    SwingUtilities.invokeLater(new Runnable() {
	                        @Override
	                        public void run() {
	                            try {
	                                dispose();
	                               // new PostulationInterface(finalEmail).setVisible(true);
	                                new post(finalEmail).setVisible(true);

	                            } catch (Exception e) {
	                                e.printStackTrace();
	                            }
	                        }
	                    });
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	        });

	        confirmButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Close the current NewMailInterface window
	                dispose();
	                clearFields();
	                disableCRUDButtons();

	                try {
	                    Connection connection = DatabaseConnection.getInstance().getConnection();

	                    // Perform select query to retrieve the email
	                    String query = "SELECT mail FROM email WHERE idcompte = ?";
	                    PreparedStatement statement = connection.prepareStatement(query);
	                    statement.setString(1, id);
	                    ResultSet resultSet = statement.executeQuery();

	                    String email = null;
	                    if (resultSet.next()) {
	                        email = resultSet.getString("mail");
	                    }

	                    // Close the database connection and statement
	                    resultSet.close();
	                    statement.close();
	                    connection.close();

	                    final String finalEmail = email; // Create a final variable for use inside the Runnable

	                    // Create and display the PostulationInterface window, passing the retrieved email
	                    SwingUtilities.invokeLater(new Runnable() {
	                        @Override
	                        public void run() {
	                            try {
	                           //     new PostulationInterface(finalEmail).setVisible(true);
	                                new post(finalEmail).setVisible(true);

	                            } catch (Exception e) {
	                                e.printStackTrace();
	                            }
	                        }
	                    });
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	        });
	        // Populate mailComboBox
	        populateMailComboBox();
	
	        pack();
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	    }
	
	    private void updateFileList() {
	        DefaultListModel<String> listModel = new DefaultListModel<>();
	        for (File file : uploadedFiles) {
	            String path = file.getAbsolutePath();
	            listModel.addElement(path);
	        }
	        fileList.setModel(listModel);
	    }
	  
	    private void refreshInterface() {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    NewMailInterface.this.dispose(); 
	                    new NewMailInterface(id).setVisible(true); 
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	    
	    
	
	    private void createMail() {
	        String objet = objetField.getText();
	        String message = messageField.getText();
	        String date = dateField.getText();
	        String code = codeArea.getText();
	        StringBuilder stringBuilder = new StringBuilder();
	
	        for (File file : uploadedFiles) {
	            String path = file.getAbsolutePath();
	            stringBuilder.append(path);
	            stringBuilder.append(";");
	        }
	
	        String fichiers = stringBuilder.toString();
	
	        try {
	            Connection connection = DatabaseConnection.getInstance().getConnection();
	
	            // Insert values into the "mail" table in the MySQL database
	            String query = "INSERT INTO mail (objet, message, date, idcompte, fichiers, code) VALUES (?, ?, ?, ?, ?, ?)";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, objet);
	            statement.setString(2, message);
	            statement.setString(3, date);
	            statement.setString(4, id);
	            statement.setString(5, fichiers);
	            statement.setString(6, code);
	            statement.executeUpdate();
	
	            // Close the database connection and statement
	            statement.close();
	            connection.close();
	
	            // Display a message or perform any other necessary actions after successful insertion
	            JOptionPane.showMessageDialog(this, "mail inserted successful");
	
	            clearFields();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    private void enableCRUDButtons() {
	        updateButton.setEnabled(true);
	        deleteButton.setEnabled(true);
	        cancelButton.setEnabled(true);
	        confirmButton.setEnabled(false);
	    }
	
	    private void disableCRUDButtons() {
	        updateButton.setEnabled(false);
	        deleteButton.setEnabled(false);
	        cancelButton.setEnabled(true);
	        confirmButton.setEnabled(false);
	    }
	
	    private void updateMail() {
	        // Check if a mail is selected
	        if (selectedMailId == null) {
	            return;
	        }
	
	        String objet = objetField.getText();
	        String message = messageField.getText();
	        String date = dateField.getText();
	        String code = codeArea.getText();
	        StringBuilder stringBuilder = new StringBuilder();
	
	        for (File file : uploadedFiles) {
	            String path = file.getAbsolutePath();
	            stringBuilder.append(path);
	            stringBuilder.append(";");
	        }
	
	        String fichiers = stringBuilder.toString();
	
	        try {
	            Connection connection = DatabaseConnection.getInstance().getConnection();
	
	            // Update the mail in the database
	            String query = "UPDATE mail SET objet = ?, message = ?, date = ?, fichiers = ?, code = ? WHERE id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, objet);
	            statement.setString(2, message);
	            statement.setString(3, date);
	            statement.setString(4, fichiers);
	            statement.setString(5, code);
	            statement.setString(6, selectedMailId);
	            statement.executeUpdate();
	
	            // Close the database connection and statement
	            statement.close();
	            connection.close();
	
	            // Display a message or perform any other necessary actions after successful update
	       
	            JOptionPane.showMessageDialog(this, "mail updated successful");
	
	            clearFields();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	    private void deleteMail() {
	        // Check if a mail is selected
	        if (selectedMailId == null) {
	            return;
	        }
	
	        try {
	            Connection connection = DatabaseConnection.getInstance().getConnection();
	
	            // Delete the mail from the database
	            String query = "DELETE FROM mail WHERE id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, selectedMailId);
	            statement.executeUpdate();
	
	            // Close the database connection and statement
	            statement.close();
	            connection.close();
	
	            // Display a message or perform any other necessary actions after successful deletion
	            JOptionPane.showMessageDialog(this, "mail updated successfully");
	
	            clearFields();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	
	
	    private void fillFieldsWithSelectedData(String mailId, String fileName) {
	    	selectedMailId = mailId; // Store the selected mail ID
	    	try {
	            Connection connection = DatabaseConnection.getInstance().getConnection();
	
	            // Fetch the mail object from the database
	            String query = "SELECT * FROM mail WHERE id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, mailId);
	            ResultSet resultSet = statement.executeQuery();
	
	            if (resultSet.next()) {
	                // Retrieve the data from the result set
	                String objet = resultSet.getString("objet");
	                String message = resultSet.getString("message");
	                String date = resultSet.getString("date");
	                String code = resultSet.getString("code");
	                String fichiers = resultSet.getString("fichiers");
	
	                // Set the retrieved data to the corresponding fields
	                objetField.setText(objet);
	                messageField.setText(message);
	                dateField.setText(date);
	                codeArea.setText(code);
	
	                // Populate the fileList with the file paths
	                DefaultListModel<String> listModel = new DefaultListModel<>();
	                String[] filePaths = fichiers.split(";");
	                for (String filePath : filePaths) {
	                    listModel.addElement(filePath);
	                }
	                fileList.setModel(listModel);
	            }
	
	            // Close the database connection
	            resultSet.close();
	            statement.close();
	            connection.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	
	
	
	    private void clearFields() {
	        objetField.setText("");
	        messageField.setText("");
	        dateField.setText("");
	        codeArea.setText("");
	        fileList.clearSelection();
	    }
	
	    private void populateMailComboBox() {
	        // Establish a database connection
	    	try {
	            Connection connection = DatabaseConnection.getInstance().getConnection();
	
	            // Fetch the mail objects from the database
	            String query = "SELECT id, objet FROM mail WHERE idcompte = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, id);
	            ResultSet resultSet = statement.executeQuery();
	
	            // Populate the mailComboBox with the mail objects
	            while (resultSet.next()) {
	                String mailId = resultSet.getString("id");
	                String mailObjet = resultSet.getString("objet");
	                mailComboBox.addItem(mailId + ": " + mailObjet);
	            }
	
	            // Close the database connection
	            resultSet.close();
	            statement.close();
	            connection.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
	        mailComboBox.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Get the selected item from the mailComboBox
	                String selectedMail = (String) mailComboBox.getSelectedItem();
	                if (selectedMail != null) {
	                    // Extract the mailId and fileName from the selected item
	                    String[] parts = selectedMail.split(":");
	                    String mailId = parts[0].trim();
	                    String fileName = parts[1].trim();
	
	                    // Call the fillFieldsWithSelectedData method
	                    fillFieldsWithSelectedData(mailId, fileName);
	                }
	            }
	        });
	    }
	
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    new NewMailInterface(id).setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
	}