package stageapp;
import javax.swing.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
public class SearchCompaniesUI extends JFrame {

    private App app;
    private JPanel panel;
    private JPanel searchPanel;
    private JButton searchButton;
    private JList<String> resultsList;
    private DefaultListModel<String> resultsListModel;  
    private JTextField txtactivity;
    private JTextField txtcity;

    private JComboBox<String> comboBox;
    private JComboBox<String> comboBox2;

    String[] ActivitiesArray;
    String[] villesArray ;
  
    public SearchCompaniesUI(App app) {
    	
        this.app = app;
        panel = new JPanel(new BorderLayout());
       // WebScraper web = new WebScraper();
        // Panel de recherche
        searchPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPanel.setBackground(Color.BLACK);

        List<String> activities = null;
		
			try {
				activities = selectActivites();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
     // Convertir la liste en un tableau de chaînes de caractères
        ActivitiesArray = activities.toArray(new String[0]);
        List<String> villes = null;
		try {
			villes = getAllVilles();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

     // Convertir la liste en un tableau de chaînes de caractères
         villesArray = villes.toArray(new String[0]);

     
        searchButton = new JButton("Rechercher");
        searchButton.setBackground(Color.black);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBounds(395, 363, 283, 36);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					rechercherEntreprises();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        //searchPanel.add(new JLabel("Activité "));
       // searchPanel.add(activityComboBox);
       // searchPanel.add(new JLabel("Ville "));
       // searchPanel.add(cityComboBox);
        searchPanel.add(new JLabel());
       // searchPanel.add(searchButton);

       /*=======================================================*/ 
        this.txtactivity = new JTextField();
        txtactivity.setFont(new Font("Tahoma", Font.BOLD, 15));
        txtactivity.setBounds(395, 9, 283, 36);

        txtactivity.setHorizontalAlignment(SwingConstants.CENTER);
        txtactivity.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent arg0) {
        	 txtactivity.setText(null);
         }
        });
        txtactivity.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == 38) {
           int x = comboBox.getSelectedIndex();
           if (x > 0) {
            comboBox.setSelectedIndex(x - 1);
           }
           getContentPane().add(comboBox);
           comboBox.showPopup();
          } else if (e.getKeyCode() == 40) {
           int x = comboBox.getSelectedIndex();
           int y = comboBox.getItemCount();
           if (x + 1 < y)
            comboBox.setSelectedIndex(x + 1);
           getContentPane().add(comboBox);
           comboBox.showPopup();
          }
         }
        });
        txtactivity.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
          try {
           txtactivity.setText(comboBox.getSelectedItem().toString());
           comboBox.removeAllItems();
           comboBox.hidePopup();
           searchPanel.remove(comboBox);
          } catch (Exception e) {
          }
         }
        });
        this.txtactivity.setHorizontalAlignment(SwingConstants.CENTER);
        this.txtactivity.setText("Activité");
        this.txtactivity.addCaretListener(new TextFieldCaretListener());
       // this.searchPanel.add(this.txtactivity);
        this.txtactivity.setColumns(10);

        this.comboBox = new JComboBox<String>();
        this.comboBox.setFocusCycleRoot(true);
        this.comboBox.setFocusTraversalPolicyProvider(true);
        this.comboBox.setAutoscrolls(true);
        this.comboBox.setBounds(15, 11, 199, 17);

        this.comboBox.setBorder(null);
        this.comboBox.setOpaque(false);
        
        
        
        this.txtcity = new JTextField();
        txtcity.setFont(new Font("Tahoma", Font.BOLD, 15));

        txtcity.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent arg0) {
        	 txtcity.setText(null);
         }
        });
        txtcity.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == 38) {
           int x = comboBox2.getSelectedIndex();
           if (x > 0) {
            comboBox2.setSelectedIndex(x - 1);
           }
           getContentPane().add(comboBox2);
           comboBox2.showPopup();
          } else if (e.getKeyCode() == 40) {
           int x = comboBox2.getSelectedIndex();
           int y = comboBox2.getItemCount();
           if (x + 1 < y)
            comboBox2.setSelectedIndex(x + 1);
           getContentPane().add(comboBox2);
           comboBox2.showPopup();
          }
         }
        });
        txtcity.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
          try {
        	  txtcity.setText(comboBox2.getSelectedItem().toString());
           comboBox2.removeAllItems();
           comboBox2.hidePopup();
           searchPanel.remove(comboBox2);
          } catch (Exception e) {
          }
         }
        });
        this.txtcity.setHorizontalAlignment(SwingConstants.CENTER);
        this.txtcity.setText("ville");
        this.txtcity.addCaretListener(new TextFieldCaretListener2());
        txtcity.setHorizontalAlignment(SwingConstants.CENTER);
        this.txtcity.setBounds(14, 9, 200, 19);
       // this.searchPanel.add(this.txtcity);
        this.txtcity.setColumns(10);

        this.comboBox2 = new JComboBox<String>();
        this.comboBox2.setFocusCycleRoot(true);
        this.comboBox2.setFocusTraversalPolicyProvider(true);
        this.comboBox2.setAutoscrolls(true);
        this.comboBox2.setBounds(395, 11, 283, 36);
        this.comboBox2.setBorder(null);
        this.comboBox2.setOpaque(false);
        
        
        JLabel activityLabel = new JLabel("Activité:");
        JLabel cityLabel = new JLabel("Ville:");
        activityLabel.setForeground(Color.WHITE);
        cityLabel.setForeground(Color.WHITE);


        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.LINE_AXIS));
        txtactivity.setPreferredSize(new Dimension(200, 20));
        txtcity.setPreferredSize(new Dimension(200, 20));
        searchPanel.add(activityLabel);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0))); // espace horizontal de 10 pixels
        searchPanel.add(txtactivity);
        searchPanel.add(Box.createRigidArea(new Dimension(20, 0))); // espace horizontal de 20 pixels
        searchPanel.add(cityLabel);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0))); // espace horizontal de 10 pixels
        searchPanel.add(txtcity);
        searchPanel.add(searchButton);
        
        
        
        /*=======================================================*/ 

        panel.add(searchPanel, BorderLayout.NORTH);

       // Liste des résultats
        resultsListModel = new DefaultListModel<>();
        resultsList = new JList<>(resultsListModel);
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(resultsList);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter un écouteur pour sélectionner une entreprise
        resultsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = resultsList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String nomEntreprise = resultsListModel.getElementAt(selectedIndex);
                    Entreprise entreprise;
					try {
						entreprise = getEntrepriseByNom(nomEntreprise);
						app.showCompanyDetails(entreprise);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                }
            }
        });
        
        
        
        
        
        
       
        
        
    }

    
    
    private List<String> selectActivites() {
        List<String> activities = new ArrayList<>();

        // Requête SQL pour récupérer les activités à partir de la table "activite"
        String sql = "SELECT nom FROM activite";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            // Parcourir le ResultSet et ajouter les activités à la liste
            while (resultSet.next()) {
                String activite = resultSet.getString("nom");
                activities.add(activite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activities;
    }
    
    
    public List <String> getAllVilles() throws SQLException {
    	
    	List <String> villes = new ArrayList<>();

        // Se connecter à la base de données
        Connection connection =DatabaseConnection.getInstance().getConnection();

        // Exécuter la requête SELECT pour récupérer toutes les villes
        String query = "SELECT nom FROM ville";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        // Parcourir les résultats et ajouter les villes à la liste
        while (resultSet.next()) {
            String ville = resultSet.getString("nom");
            villes.add(ville);
        }

        // Fermer les ressources
        resultSet.close();
        statement.close();
        connection.close();

        return villes;
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
    
      
    private String getNomActiviteById(int id) throws SQLException {
        String nomActivite = null;
        String query = "SELECT nom FROM activite WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
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
 
	public static Connection getConnection() throws SQLException {
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/robot_recruteur";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
          //  System.out.println("success");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
        }

        return conn;
    }


    private ArrayList<Entreprise> rechercheEntreprisesParActiviteEtVille(int activite, int ville) {
        ArrayList<Entreprise> entreprises = new ArrayList<>();
        
        try {
            Connection conn =DatabaseConnection.getInstance().getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM entreprise WHERE idactivite='" + activite + "' AND idville='" + ville + "'");
            
            while (rs.next()) {
                Entreprise entreprise = new Entreprise();
                entreprise.setName(rs.getString("raisonsociale"));
                entreprise.setActivity(rs.getInt("idactivite"));
                entreprise.setCity(rs.getInt("idville"));
                entreprise.setPhone(rs.getString("telephone"));
                entreprise.setEmail(rs.getString("email"));
                entreprise.setSite(rs.getString("siteweb"));
                entreprises.add(entreprise);
            }
            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return entreprises;
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

    
    
     
    private void rechercherEntreprises() throws SQLException {
        // Récupérer l'activité et la ville sélectionnées
        String activite = (String) txtactivity.getText();
        String ville = (String) txtcity.getText();
        

        // Rechercher les entreprises correspondantes dans la base de données
        ArrayList<Entreprise> entreprises = rechercheEntreprisesParActiviteEtVille(getIdActiviteByNom(activite), getIdVilleByNom(ville));
        
        // Mettre à jour la liste des résultats
        resultsListModel.clear();
        for (Entreprise entreprise : entreprises) {
            resultsListModel.addElement(entreprise.getName());
        }
    }

    public JPanel getPanel() {
        return panel;
    }
    
    
    
    
    private class TextFieldCaretListener implements CaretListener {
    	  public void caretUpdate(CaretEvent e) {

    	   try {
    	    comboBox.removeAllItems();
    	    comboBox.hidePopup();
    	    panel.remove(comboBox);
    	    if (e.getMark() > 0) {
    	     for (String string : ActivitiesArray) {
    	      if (string.toLowerCase().startsWith(txtactivity.getText().toLowerCase())) {
    	       panel.add(comboBox);
    	       comboBox.addItem(string);
    	       comboBox.showPopup();
    	      }
    	     }
    	    }
    	   } catch (Exception e1) {
    	   }
    	   if (e.getMark() < 2) {
    	    panel.remove(comboBox);
    	   }
    	  }
    	 }
  
    private class TextFieldCaretListener2 implements CaretListener {
  	  public void caretUpdate(CaretEvent e) {

  	   try {
  	    comboBox2.removeAllItems();
  	    comboBox2.hidePopup();
  	    panel.remove(comboBox2);
  	    if (e.getMark() > 0) {
  	     for (String string : villesArray) {
  	      if (string.toLowerCase().startsWith(txtcity.getText().toLowerCase())) {
  	       panel.add(comboBox2);
  	       comboBox2.addItem(string);
  	       comboBox2.showPopup();
  	      }
  	     }
  	    }
  	   } catch (Exception e1) {
  	   }
  	   if (e.getMark() < 2) {
  	    panel.remove(comboBox2);
  	   }
  	  }
  	 }
   
}