package stageapp;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class user_admin extends JFrame {

	private JPanel contentPane;

	private String prenom;
	private String nom;
	private int id;
	private JTextField nom_f  = new JTextField();
	private JTextField prenom_f= new JTextField();
	private JTextField genre_f= new JTextField();
	private static final String PLACEHOLDER_FORMAT = "Format: YYYY-MM-DD";
	private JTextField date_f= new JTextField(PLACEHOLDER_FORMAT);
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					user_admin frame = new user_admin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public user_admin() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 715, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		 setResizable(false);
		
		 FlatIntelliJLaf.install();

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setIcon(new ImageIcon(user_admin.class.getResource("/stageapp/images/supprimer.png")));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int choix = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer ce client ?",
						"Confirmation", JOptionPane.YES_NO_OPTION);
				if (choix == JOptionPane.YES_OPTION) {
					delete_compte(id);
					refreshInterface();
				}
			}
		});
		btnDelete.setBounds(99, 288, 123, 36);
		contentPane.add(btnDelete);

		JComboBox<String> compteComboBox = new JComboBox<>();
        compteComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCompte = (String) compteComboBox.getSelectedItem();
                afficher_informations_de_compte(selectedCompte);
            }
        });
        compteComboBox.setBounds(182, 41, 285, 29);
        getContentPane().add(compteComboBox);

		try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
			// Requête SQL pour récupérer le nom et le prénom depuis la table "compte"
			// où l'email est égal à l'email connecte
			String sql = "SELECT nom, prenom FROM compte  ORDER BY nom, prenom";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = statement.executeQuery()) {
					// Parcourir le ResultSet et ajouter les clients au JComboBox compteComboBox
					while (resultSet.next()) {
						nom = resultSet.getString("nom");
						prenom = resultSet.getString("prenom");
						compteComboBox.addItem(nom + " " + prenom);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Une erreur s'est produite lors de la récupération des informations des clients : "
					+ e.getMessage());
			e.printStackTrace();
		}

		JLabel lblNewLabel = new JLabel("Nom :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(83, 103, 64, 14);
		contentPane.add(lblNewLabel);

		JLabel lblPrenom = new JLabel("Prenom :");
		lblPrenom.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrenom.setBounds(59, 146, 88, 14);
		contentPane.add(lblPrenom);

		JLabel lblGenre = new JLabel("Genre :");
		lblGenre.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGenre.setBounds(72, 190, 75, 14);
		contentPane.add(lblGenre);

		JLabel lblDate = new JLabel("Date naissance :");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDate.setBounds(10, 227, 137, 14);
		contentPane.add(lblDate);
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				 int choix = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir cree un compte ?",
		                    "Confirmation", JOptionPane.YES_NO_OPTION);
		            if (choix == JOptionPane.YES_OPTION) {
		                // Perform the operation you want to confirm here
		                // For example, you can call a method to create or update the user
		                // based on the input fields

		                // Example: Creating a new user
		                String nom = nom_f.getText();
		                String prenom = prenom_f.getText();
		                String genre = genre_f.getText();
		                String dateNaissance = date_f.getText();

		                // Call a method to create the user with the provided information
		                
		                
		                try {
		                    Connection connection = DatabaseConnection.getInstance().getConnection();
		                    String sql = "INSERT INTO compte (nom, prenom, genre, datenaissance) VALUES (?, ?, ?, ?)";
		                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
		                        statement.setString(1, nom);
		                        statement.setString(2, prenom);
		                        statement.setString(3, genre);
		                        statement.setString(4, dateNaissance);
		                        statement.executeUpdate();
		                    }
		                } catch (SQLException e1) {
		                    System.err.println("Une erreur s'est produite lors de la création de l'utilisateur : " + e1.getMessage());
		                    e1.printStackTrace();
		                }
		                
		                

		                // Refresh the interface
		                refreshInterface();
		            }
				
			
			}
		});
		btnCreate.setIcon(new
				ImageIcon(user_admin.class.getResource("/stageapp/images/conception-graphique.png")));
		btnCreate.setBounds(268, 288, 123, 36);
		contentPane.add(btnCreate);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				 int choix = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir mettre à jour cet utilisateur ?",
		                    "Confirmation", JOptionPane.YES_NO_OPTION);
		            if (choix == JOptionPane.YES_OPTION) {
		                // Perform the update operation here

		                // Get the updated information from the input fields
		                String nom = nom_f.getText();
		                String prenom = prenom_f.getText();
		                String genre = genre_f.getText();
		                String dateNaissance = date_f.getText();

		                // Call a method to update the user with the provided information
		               
		                try {
		                    Connection connection = DatabaseConnection.getInstance().getConnection();
		                    String sql = "UPDATE compte SET nom = ?, prenom = ?, genre = ?, datenaissance = ? WHERE id = ?";
		                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
		                        statement.setString(1, nom);
		                        statement.setString(2, prenom);
		                        statement.setString(3, genre);
		                        statement.setString(4, dateNaissance);
		                        statement.setInt(5, id);
		                        statement.executeUpdate();
		                    }
		                } catch (SQLException e1) {
		                    System.err.println("Une erreur s'est produite lors de la mise à jour de l'utilisateur : " + e1.getMessage());
		                    e1.printStackTrace();
		                }

		                // Refresh the interface
		                refreshInterface();
		            }
				
				
			}
		});
		btnUpdate.setIcon(new ImageIcon(user_admin.class.getResource("/stageapp/images/mettre-a-jour-les-fleches.png")));
		btnUpdate.setBounds(439, 288, 123, 36);
		contentPane.add(btnUpdate);
		
		
	/*	Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(59, 89, 152), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );*/
		
		nom_f = new JTextField();
		nom_f.setBounds(182, 97, 285, 29);
		contentPane.add(nom_f);
		
	/*	nom_f.setBorder(border);
		nom_f.setBackground(new Color(240, 248, 255));
		nom_f.setForeground(Color.DARK_GRAY);
		nom_f.setFont(new Font("Arial", Font.PLAIN, 14));*/
        
		nom_f.setColumns(10);

		prenom_f = new JTextField();
		prenom_f.setColumns(10);
		prenom_f.setBounds(182, 141, 285, 29);
		
	/*	prenom_f.setBorder(border);
		prenom_f.setBackground(new Color(240, 248, 255));
		prenom_f.setForeground(Color.DARK_GRAY);
		prenom_f.setFont(new Font("Arial", Font.PLAIN, 14)); */
		contentPane.add(prenom_f);

		genre_f = new JTextField();
		genre_f.setColumns(10);
		genre_f.setBounds(182, 185, 285, 29);
	/*	genre_f.setBorder(border);
		genre_f.setBackground(new Color(240, 248, 255));
		genre_f.setForeground(Color.DARK_GRAY);
		genre_f.setFont(new Font("Arial", Font.PLAIN, 14)); */
		contentPane.add(genre_f);

		date_f = new JTextField();
		date_f.setColumns(10);
		date_f.setBounds(182, 222, 285, 29);
	/*	date_f.setBorder(border);
		date_f.setBackground(new Color(240, 248, 255));
		date_f.setForeground(Color.DARK_GRAY);
		date_f.setFont(new Font("Arial", Font.PLAIN, 14)); */
		contentPane.add(date_f);
		
		
		date_f.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (date_f.getText().equals(PLACEHOLDER_FORMAT)) {
                	date_f.setText(""); // Effacer le placeholder lorsqu'il obtient le focus
                	date_f.setForeground(Color.BLACK); // Rétablir la couleur du texte
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (date_f.getText().isEmpty()) {
                	date_f.setText(PLACEHOLDER_FORMAT); // Réafficher le placeholder si le champ est vide
                	date_f.setForeground(Color.GRAY); // Utiliser la couleur grise pour le placeholder
                }
            }
        });

		
		 
		
	        

		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(user_admin.class.getResource("/stageapp/images/bouton-de-compte-rond-avec-lutilisateur-a-linterieur (2).png")));
		lblNewLabel_1.setBounds(524, 108, 128, 134);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(null); // Set the background to null
		btnNewButton.setOpaque(false); // Set opaque to false
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false); // Remove the border
		btnNewButton.setFocusPainted(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				email_admin frame = new email_admin(id);
				frame.setVisible(true);
			}
		});
		btnNewButton.setIcon(new ImageIcon(user_admin.class.getResource("/stageapp/images/email (1).png")));
		btnNewButton.setBounds(472, 41, 24, 26);
		contentPane.add(btnNewButton);
	}

	public void afficher_informations_de_compte(String selectedCompte) {

		String[] parts = selectedCompte.split("\\s+"); // Supposons que les parties sont séparées par un espace

		if (parts.length >= 2) {
			String nom = parts[0];
			String prenom = parts[1];

			try {
				Connection connection = DatabaseConnection.getInstance().getConnection();
				String sql = "SELECT id , nom , prenom , genre , datenaissance FROM compte WHERE nom = ? and prenom = ?";
				try (PreparedStatement statement = connection.prepareStatement(sql)) {
					statement.setString(1, nom);
					statement.setString(2, prenom);

					try (ResultSet resultSet = statement.executeQuery()) {
						// Parcourir le ResultSet et ajouter les emails au JComboBox emailComboBox
						while (resultSet.next()) {
							nom_f.setText(resultSet.getString("nom"));
							prenom_f.setText(resultSet.getString("prenom"));
							genre_f.setText(resultSet.getString("genre"));

							Date date = resultSet.getDate("datenaissance"); // Assuming "datenaissance" is the column
																			// name
							String dateString = null;
							if (date != null) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Specify the desired
																									// date format
								dateString = dateFormat.format(date); // Convert Date to String
							}
							date_f.setText(dateString);
							id = resultSet.getInt("id");
							System.out.println(id);
						}
					}
				}
			} catch (SQLException e) {
				System.err.println("Une erreur s'est produite lors de la récupération des emails : " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	static void delete_compte(int id) {

		Statement stmt = null;
		try {
			Connection connection = DatabaseConnection.getInstance().getConnection();
			 connection.setAutoCommit(false);
			stmt = connection.createStatement();
			
			String sql = "DELETE From compte where  id = '" + id + "'";
			String sql1 = "DELETE From email where  idcompte = '" + id + "'";
			String sql2 = "DELETE From mail where  idcompte = '" + id + "'";
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
			  connection.setAutoCommit(true);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	 private void refreshInterface() {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    user_admin.this.dispose(); 
	                    new user_admin().setVisible(true); 
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
}
