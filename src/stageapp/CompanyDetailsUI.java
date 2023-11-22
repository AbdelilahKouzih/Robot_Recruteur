package stageapp;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CompanyDetailsUI {
    private JPanel panel;
    private JTextField nomTextField, activiteTextField, villeTextField, telephoneTextField, emailTextField, siteWebTextField;
    private String emailClient;
    private boolean isEditMode;

    public CompanyDetailsUI(String emailClient) {
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        this.emailClient = emailClient;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Détails de l'entreprise");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        JLabel separatorLabel = new JLabel("-----------------------------");
        separatorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        separatorLabel.setForeground(Color.WHITE);
        panel.add(separatorLabel, gbc);

        gbc.gridy++;
        JLabel nomTitleLabel = new JLabel("Nom :");
        nomTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nomTitleLabel.setForeground(Color.WHITE);
        panel.add(nomTitleLabel, gbc);

        gbc.gridx++;
        nomTextField = new JTextField(20);
        nomTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nomTextField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel activiteTitleLabel = new JLabel("Activité :");
        activiteTitleLabel.setForeground(Color.WHITE);
        activiteTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(activiteTitleLabel, gbc);

        gbc.gridx++;
        activiteTextField = new JTextField(20);
        activiteTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(activiteTextField, gbc);

     // ...
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel villeTitleLabel = new JLabel("Ville :");
        villeTitleLabel.setForeground(Color.WHITE);
        villeTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(villeTitleLabel, gbc);

        gbc.gridx++;
        villeTextField = new JTextField(20);
        villeTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(villeTextField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel telephoneTitleLabel = new JLabel("Téléphone :");
        telephoneTitleLabel.setForeground(Color.WHITE);
        telephoneTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(telephoneTitleLabel, gbc);

        gbc.gridx++;
        telephoneTextField = new JTextField(20);
        telephoneTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(telephoneTextField, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel emailTitleLabel = new JLabel("Email :");
        emailTitleLabel.setForeground(Color.WHITE);
        emailTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(emailTitleLabel, gbc);

        gbc.gridx++;
        emailTextField = new JTextField(20);
        emailTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(emailTextField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel siteWebTitleLabel = new JLabel("Site Web :");
        siteWebTitleLabel.setForeground(Color.WHITE);
        siteWebTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(siteWebTitleLabel, gbc);

        gbc.gridx++;
        siteWebTextField = new JTextField(20);
        siteWebTextField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(siteWebTextField, gbc);

        // ...
        // ...

        // Bouton Modifier
        JButton modifierButton = new JButton("Modifier");
        modifierButton.setBackground(Color.WHITE);
        modifierButton.setForeground(Color.black);
        modifierButton.setBounds(395, 363, 283, 36);
        modifierButton.setFont(new Font("Arial", Font.PLAIN, 18));
        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleEditMode();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(modifierButton, gbc);

        // Bouton Confirmer
        JButton confirmerButton = new JButton("Confirmer");
        confirmerButton.setBackground(Color.WHITE);
        confirmerButton.setForeground(Color.black);
        confirmerButton.setBounds(395, 363, 283, 36);
        confirmerButton.setFont(new Font("Arial", Font.PLAIN, 18));
        confirmerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
                toggleEditMode();
            }
        });
        gbc.gridy++;
        panel.add(confirmerButton, gbc);

        // Bouton Supprimer
        JButton supprimerButton = new JButton("Supprimer");
        supprimerButton.setBackground(Color.WHITE);
        supprimerButton.setForeground(Color.black);
        supprimerButton.setBounds(395, 363, 283, 36);
        supprimerButton.setFont(new Font("Arial", Font.PLAIN, 18));
        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					deleteCompany();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        gbc.gridy++;
        panel.add(supprimerButton, gbc);

        // Initialise le mode d'édition à false
        isEditMode = false;
        setFieldsEditable(false);
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;
        setFieldsEditable(isEditMode);
    }

    private void setFieldsEditable(boolean editable) {
        nomTextField.setEditable(false);
        activiteTextField.setEditable(editable);
        villeTextField.setEditable(editable);
        telephoneTextField.setEditable(editable);
        emailTextField.setEditable(editable);
        siteWebTextField.setEditable(editable);
        // ...
    }
    
    
    
    private void saveChanges() {
        String nom = nomTextField.getText();
        int idEntreprise = getIdEntrepriseByNom(nomTextField.getText());
        int activite = 0;
        try {
            activite = getIdActiviteByNom(activiteTextField.getText());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        int ville = 0;
        try {
            ville = getIdVilleByNom(villeTextField.getText());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        String telephone = telephoneTextField.getText();
        String email = emailTextField.getText();
        String siteWeb = siteWebTextField.getText();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Requête SQL pour la mise à jour de l'entreprise
            String query = "UPDATE entreprise SET raisonsociale = ?, idactivite = ?, idville = ?, telephone = ?, email = ?, siteweb = ? WHERE id = ?";

            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nom);
            stmt.setInt(2, activite);
            stmt.setInt(3, ville);
            stmt.setString(4, telephone);
            stmt.setString(5, email);
            stmt.setString(6, siteWeb);
            stmt.setInt(7, idEntreprise);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(panel, "Modifications enregistrées avec succès !");
            } else {
                JOptionPane.showMessageDialog(panel, "Aucune modification effectuée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Erreur lors de l'enregistrement des modifications.");
        } finally {
            // Fermer la déclaration et la connexion dans l'ordre inverse
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    
    private int getIdActiviteByNom(String nomActivite) throws SQLException {
        int idActivite = -1;
        String query = "SELECT id FROM activite WHERE nom = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomActivite);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idActivite = rs.getInt("id");
            }
        }
        return idActivite;
    }

    private int getIdVilleByNom(String nomVille) throws SQLException {
        int idVille = -1;
        String query = "SELECT id FROM ville WHERE nom = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nomVille);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idVille = rs.getInt("id");
            }
        }
        return idVille;
    }

    
    
    
    
    private void deleteCompany() throws SQLException {
        int choice = JOptionPane.showConfirmDialog(panel,
                "Êtes-vous sûr de vouloir supprimer cette entreprise ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            String nom = nomTextField.getText();

            // Requête SQL pour supprimer l'entreprise
            String query = "DELETE FROM entreprise WHERE raisonsociale = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1,nom);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Suppression réussie
                    JOptionPane.showMessageDialog(panel, "L'entreprise a été supprimée avec succès !");
                } else {
                    // Aucune entreprise supprimée (nom introuvable)
                    JOptionPane.showMessageDialog(panel, "Aucune entreprise supprimée (nom introuvable).");
                }
            }
        }
    }

    public void updateUI(Entreprise entreprise) throws SQLException {
        nomTextField.setText(entreprise.getName());
        activiteTextField.setText(getNomActiviteById(entreprise.getActivity()));
        villeTextField.setText(getNomVilleById(entreprise.getCity()));
        telephoneTextField.setText(entreprise.getPhone());
        emailTextField.setText(entreprise.getEmail());
        siteWebTextField.setText(entreprise.getSite());
    }
    
    
    
    public Entreprise getEntrepriseByNom(String nom) throws SQLException {
        Entreprise entreprise = null;
        String query = "SELECT * FROM entreprise WHERE raisonsociale = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String raisonSociale = rs.getString("raisonsociale");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                String siteWeb = rs.getString("siteweb");
                int idActivite = rs.getInt("idactivite");
                int idVille = rs.getInt("idville");

                // Récupérer les informations supplémentaires de l'activité et de la ville
              //  String activite = getNomActiviteById(idActivite);
                //String ville = getNomVilleById(idVille);

                entreprise = new Entreprise(raisonSociale, idActivite, telephone, email, siteWeb, idVille, id);
            }
        }
        return entreprise;
    }
    
    int getIdEntrepriseByNom(String nom) {
        int idEntreprise = -1;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Requête SQL pour récupérer l'ID de l'entreprise
            String query = "SELECT id FROM entreprise WHERE raisonsociale = ?";
            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nom);
            rs = stmt.executeQuery();

            if (rs.next()) {
                idEntreprise = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer le ResultSet, la déclaration et la connexion dans l'ordre inverse
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return idEntreprise;
    }

    

    
    
    
    
      
    private String getNomActiviteById(int id) throws SQLException {
        String nomActivite = null;
        String query = "SELECT nom FROM activite WHERE id = ?";
        try (Connection conn =DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nomActivite = rs.getString("nom");
            }
        }
        return nomActivite;
    }
    
    private String getNomVilleById(int id) throws SQLException {
        String nomVille = null;
        String query = "SELECT nom FROM ville WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nomVille = rs.getString("nom");
            }
        }
        return nomVille;
    }

    
    public boolean updateEntrepriseInDatabase(Entreprise entreprise) {
        boolean success = false;

       

        // Requête SQL pour mettre à jour les données de l'entreprise
        String sql = "UPDATE entreprise SET nom=?, activite=?, ville=?, telephone=?, adresse=?, email=?, site_web=? WHERE id=?";

        try (Connection connection =DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Définir les paramètres de la requête préparée avec les nouvelles valeurs de l'entreprise
            statement.setString(1, entreprise.getName());
            statement.setInt(2, entreprise.getActivity());
            statement.setInt(3, entreprise.getCity());
            statement.setString(4, entreprise.getPhone());
            statement.setString(5, entreprise.getEmail());
            statement.setString(6, entreprise.getSite());
            statement.setInt(7, entreprise.getId()); // Supposons que l'identifiant de l'entreprise soit disponible

            // Exécuter la requête de mise à jour
            int rowsAffected = statement.executeUpdate();

            // Vérifier si la mise à jour a réussi
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    public JPanel getPanel() {
        return panel;
    }
}