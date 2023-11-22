package stageapp;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import stageapp.DatabaseConnection;

public class EmailManagementApp {
    private DefaultTableModel emailTableModel;
    private JTable emailTable;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private  int idCompte;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmailManagementApp().createAndShowGUI(1);
            }
        });
    }

    public void createAndShowGUI(int idCompte) {
        JFrame frame = new JFrame("Gestion des Emails");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         this.idCompte=idCompte;
        emailTableModel = new DefaultTableModel();
        emailTableModel.addColumn("Email");
        emailTableModel.addColumn("Mot de passe");
        emailTable = new JTable(emailTableModel);
        JScrollPane tableScrollPane = new JScrollPane(emailTable);

        emailTextField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ajouterEmail();
            }
        });

        JButton updateButton = new JButton("Modifier");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifierEmail(emailTextField.getText(),new String(passwordField.getPassword()));
            }
        });

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                supprimerEmail();
            }
        });

        JButton searchButton = new JButton("Confirmer");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
            	//new PostulationInterface(null);
            }
        });
        
        JButton cancelButton = new JButton("annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current NewMailInterface window

                try {
                    Connection connection = DatabaseConnection.getInstance().getConnection();

                    // Perform select query to retrieve the email
                    String query = "SELECT mail FROM email WHERE idcompte = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setLong(1, idCompte);
                    ResultSet resultSet = statement.executeQuery();

                    String email = null;
                    if (resultSet.next()) {
                        email = resultSet.getString("mail");
                    }

                    // Close the database connection and statement
                    resultSet.close();
                    statement.close();
                    connection.close();

                    final String finalEmail = email; 
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                              //  new PostulationInterface(finalEmail).setVisible(true);
                                new post(finalEmail).setVisible(true);

                                EmailManagementApp.this.dispose();
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

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailTextField);
        inputPanel.add(new JLabel("Mot de passe:"));
        inputPanel.add(passwordField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(searchButton);
        inputPanel.add(cancelButton);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(tableScrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void dispose() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(emailTable);
        frame.dispose();

        // Perform any necessary cleanup or additional logic
        // ...
    }

	private void ajouterEmail() {
        String email = emailTextField.getText();
        String password = new String(passwordField.getPassword());

        Vector<String> rowData = new Vector<>();
        rowData.add(email);
        rowData.add(password);

        emailTableModel.addRow(rowData);

        emailTextField.setText("");
        passwordField.setText("");

        // Ajouter les données à la base de données
        try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String sql = "INSERT INTO email (mail, passwd , idcompte) VALUES (?, ? ,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setInt(3, idCompte);


            statement.executeUpdate();

            System.out.println("Email ajouté avec succès à la base de données.");
        } catch (SQLException e) {
            System.err.println("Une erreur s'est produite lors de l'ajout de l'email à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
    }


 // Méthode pour modifier un e-mail
    private void modifierEmail(String email, String password) {
    	
    	int rowIndex1 = emailTable.getSelectedRow();
        if (rowIndex1 != -1) {
            emailTableModel.setValueAt(email, rowIndex1, 0);
            emailTableModel.setValueAt(password, rowIndex1, 1);

            try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String sql = "UPDATE email SET mail = ?, passwd = ? WHERE mail = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, emailTableModel.getValueAt(rowIndex1, 0).toString());

            statement.executeUpdate();

            System.out.println("E-mail modifié avec succès dans la base de données.");
        } catch (SQLException e) {
            System.err.println("Une erreur s'est produite lors de la modification de l'e-mail dans la base de données : " + e.getMessage());
            e.printStackTrace();
        }
        
        }else {
            System.out.println("Aucune ligne sélectionnée dans la table.");
        }
    }

    // Méthode pour chercher un e-mail dans la base de données
    private void chercherEmail(String email) {
    	try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String sql = "SELECT * FROM email WHERE mail = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String password = resultSet.getString("passwd");

                // Afficher les résultats dans les champs de texte ou faire autre chose avec les données récupérées
               // emailTextField.setText(email);
               // passwordField.setText(password);
                

                Vector<String> rowData = new Vector<>();
                rowData.add(email);
                rowData.add(password);

                emailTableModel.addRow(rowData);
            } else {
                // Aucun e-mail correspondant trouvé
                System.out.println("Aucun e-mail correspondant trouvé dans la base de données.");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Une erreur s'est produite lors de la recherche de l'e-mail dans la base de données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    
    
    // Méthode pour supprimer un e-mail de la base de données
    private void supprimerEmail() {
    	
    	int rowIndex = emailTable.getSelectedRow();
        if (rowIndex != -1) {
            String email = emailTableModel.getValueAt(rowIndex, 0).toString();
            emailTableModel.removeRow(rowIndex);

            try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
            String sql = "DELETE FROM email WHERE mail = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            statement.executeUpdate();

            System.out.println("E-mail supprimé avec succès de la base de données.");
        } catch (SQLException e) {
            System.err.println("Une erreur s'est produite lors de la suppression de l'e-mail de la base de données : " + e.getMessage());
            e.printStackTrace();
        }}else {
            System.out.println("Aucune ligne sélectionnée dans la table.");
        }
    }

   
   
     

 

    
    
    
    
}