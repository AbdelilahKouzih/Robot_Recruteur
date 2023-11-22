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

public class email_admin extends JFrame {

	private JPanel contentPane;

	private String mail;
	private int id;
	private JTextField nom_f  = new JTextField();
	private JTextField email_f= new JTextField();
	private JTextField prenom_f= new JTextField();
	private JTextField password_f= new JTextField();
	private JTextField genre_f= new JTextField();
	private static final String PLACEHOLDER_FORMAT = "Format: YYYY-MM-DD";
	private JTextField date_f= new JTextField(PLACEHOLDER_FORMAT);
	private int id_compte = 0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	email_admin frame = new email_admin();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public email_admin(int id_compte) {
		
		
		email_admin.this.id_compte = id_compte;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 715, 324);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		 setResizable(false);
		
		 FlatIntelliJLaf.install();

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setIcon(new ImageIcon(email_admin.class.getResource("/stageapp/images/supprimer.png")));
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
		btnDelete.setBounds(91, 226, 123, 36);
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
			String sql = "SELECT mail FROM email where idcompte = '"+id_compte+"'  ORDER BY mail";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = statement.executeQuery()) {
					// Parcourir le ResultSet et ajouter les clients au JComboBox compteComboBox
					while (resultSet.next()) {
						mail = resultSet.getString("mail");
						compteComboBox.addItem(mail );
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("Une erreur s'est produite lors de la récupération des informations des clients : "
					+ e.getMessage());
			e.printStackTrace();
		}

		JLabel lblNewLabel = new JLabel("Email :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(91, 100, 50, 14);
		contentPane.add(lblNewLabel);

		JLabel lblPrenom = new JLabel("Password :");
		lblPrenom.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrenom.setBounds(59, 144, 82, 14);
		contentPane.add(lblPrenom);
		
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
		                String mail = email_f.getText();
		                String password = password_f.getText();

		                // Call a method to create the user with the provided information
		                
		                
		                try {
		                    Connection connection = DatabaseConnection.getInstance().getConnection();
		                    String sql = "INSERT INTO email (mail, passwd, idcompte) VALUES (?, ?, ?)";
		                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
		                        statement.setString(1, mail);
		                        statement.setString(2, password);
		                        statement.setInt(3, email_admin.this.id_compte);
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
				ImageIcon(email_admin.class.getResource("/stageapp/images/conception-graphique.png")));
		btnCreate.setBounds(260, 226, 123, 36);
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
		                String email = email_f.getText();
		                String password = password_f.getText();

		                // Call a method to update the user with the provided information
		               
		                try {
		                    Connection connection = DatabaseConnection.getInstance().getConnection();
		                    String sql = "UPDATE email SET mail = ?, passwd = ? WHERE id = ?";
		                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
		                        statement.setString(1, email);
		                        statement.setString(2, password);
		                        statement.setInt(3, id);
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
		btnUpdate.setIcon(new ImageIcon(email_admin.class.getResource("/stageapp/images/mettre-a-jour-les-fleches.png")));
		btnUpdate.setBounds(431, 226, 123, 36);
		contentPane.add(btnUpdate);
		
		
	/*	Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(59, 89, 152), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );*/
		
		email_f = new JTextField();
		email_f.setBounds(182, 95, 285, 29);
		contentPane.add(email_f);
		
	/*	nom_f.setBorder(border);
		nom_f.setBackground(new Color(240, 248, 255));
		nom_f.setForeground(Color.DARK_GRAY);
		nom_f.setFont(new Font("Arial", Font.PLAIN, 14));*/
        
		email_f.setColumns(10);

		password_f = new JTextField();
		password_f.setColumns(10);
		password_f.setBounds(182, 139, 285, 29);
		
	/*	prenom_f.setBorder(border);
		prenom_f.setBackground(new Color(240, 248, 255));
		prenom_f.setForeground(Color.DARK_GRAY);
		prenom_f.setFont(new Font("Arial", Font.PLAIN, 14)); */
		contentPane.add(password_f);

		
		 
		
	        

		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(email_admin.class.getResource("/stageapp/images/e-mail (1).png")));
		lblNewLabel_1.setBounds(524, 81, 128, 134);
		contentPane.add(lblNewLabel_1);
	}

	public void afficher_informations_de_compte(String selectedCompte) {

		

			try {
				Connection connection = DatabaseConnection.getInstance().getConnection();
				String sql = "SELECT mail , passwd , id FROM email WHERE mail = ? and idcompte = ?";
				try (PreparedStatement statement = connection.prepareStatement(sql)) {
					statement.setString(1, selectedCompte);
					statement.setInt(2, email_admin.this.id_compte);

					try (ResultSet resultSet = statement.executeQuery()) {
						// Parcourir le ResultSet et ajouter les emails au JComboBox emailComboBox
						while (resultSet.next()) {
							email_f.setText(resultSet.getString("mail"));
							password_f.setText(resultSet.getString("passwd"));

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
	

	static void delete_compte(int id) {

		Statement stmt = null;
		try {
			Connection connection = DatabaseConnection.getInstance().getConnection();
			 connection.setAutoCommit(false);
			stmt = connection.createStatement();
			
			String sql = "DELETE From email where  id = '" + id + "'";
			stmt.executeUpdate(sql);
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
	                    email_admin.this.dispose(); 
	                  new email_admin(email_admin.this.id_compte).setVisible(true); 
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	    }
}
