package stageapp;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.swing.*;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;
import com.formdev.flatlaf.FlatIntelliJLaf;

public class PostulationInterface extends JFrame {
	private static JComboBox<String> compteComboBox;
	private static JComboBox<String> emailComboBox;
	private static JComboBox<String> villeComboBox;
	private static JComboBox<String> secteurComboBox;
	private static JComboBox<String> mailComboBox;
	private static JTextField intervalleTextField1;
	private static JTextField intervalleTextField2;
	public static JTextField entreprisesTrouveesTextField;
	public static JTextField emailsAEnvoyerTextField;
	private String emailCompte;
	private String prenom;
	private String nom;
	private List<String> villes = new ArrayList<String>();
	private List<String> activities = new ArrayList<String>();
	private boolean selectedActivite;
	private boolean selectedVille;
	private JCheckBox tousVillesCheckBox;
	private JCheckBox tousActivitesCheckBox;
	private List<String> emailsForPostulation = new ArrayList<String>();
	private List<MailC> mailsList;
	private int idCompte;
	private String selectedObject;
	private int arr = 0;
	JButton arreterButton = new JButton("arreter");

	private String selectedCompte;

	public PostulationInterface(String emailCompte) {
		setSize(650, 400);

		setTitle("Interface de Postulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null); // Utilisation d'une disposition personnalisée (null layout)

		this.emailCompte = emailCompte;
		
		 FlatIntelliJLaf.install();


		// Création des composants
		JLabel compteLabel = new JLabel("Compte :");
		compteComboBox = new JComboBox<>();

		try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
			// Requête SQL pour récupérer le nom et le prénom depuis la table "compte"
			// où l'email est égal à l'email connecte
			String sql = "SELECT c.nom, c.prenom FROM compte c JOIN email e ON c.id = e.idcompte WHERE e.mail = ?";
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, emailCompte);
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

		JLabel emailLabel = new JLabel("Email :");
		emailComboBox = new JComboBox<>();
		JLabel villeLabel = new JLabel("Ville :");
		villeComboBox = new JComboBox<>();
		JLabel secteurLabel = new JLabel("Secteur d'activité :");
		secteurComboBox = new JComboBox<>();
		JLabel mailLabel = new JLabel("Mail :");
		mailComboBox = new JComboBox<>();
		JLabel intervalleLabel = new JLabel("Intervalle (en minutes) :");
		intervalleTextField1 = new JTextField(10);
		intervalleTextField2 = new JTextField(10);

		JLabel entreprisesTrouveesLabel = new JLabel("Entreprises trouvées :");
		entreprisesTrouveesTextField = new JTextField(10);
		JLabel emailsAEnvoyerLabel = new JLabel("Emails envoyés :");
		emailsAEnvoyerTextField = new JTextField(10);
		JButton demarrerButton = new JButton("Démarrer");
		JButton annulerButton = new JButton("Annuler");
		JButton showButton = new JButton("Show");
		JButton emailUpdate = new JButton("U");
		JButton compteUpdate = new JButton("U");

		/*
		 * BufferedImage originalImage = null; try { originalImage = ImageIO.read(new
		 * File("\\images\\operation.png")); // Remplacez "update.png" par le chemin de
		 * votre icône } catch (IOException e) { e.printStackTrace(); }
		 */

		int iconWidth = 20;
		int iconHeight = 20;
		/*
		 * Image resizedImage = originalImage.getScaledInstance(iconWidth, iconHeight,
		 * Image.SCALE_SMOOTH);
		 * 
		 * // Créer une icône à partir de l'image redimensionnée ImageIcon updateIcon =
		 * new ImageIcon(resizedImage);
		 * 
		 * emailUpdate.setIcon(updateIcon);
		 */

		
		arreterButton.setEnabled(false);
		
		// Positionner le bouton et définir sa taille
		emailUpdate.setBounds(590, 40, iconWidth, iconHeight);
		compteUpdate.setBounds(590, 10, iconWidth, iconHeight); // Définir les positions et les tailles des composants
		// Définir les positions et les tailles des composants
		compteLabel.setBounds(10, 10, 100, 20);
		compteComboBox.setBounds(140, 10, 432, 20);
		emailLabel.setBounds(10, 40, 100, 20);
		emailComboBox.setBounds(140, 40, 432, 20);

		villeLabel.setBounds(10, 70, 100, 20);
		villeComboBox.setBounds(140, 70, 280, 20);
		tousVillesCheckBox = new JCheckBox("Toutes les villes");
		tousVillesCheckBox.setBounds(430, 70, 150, 20);

		secteurLabel.setBounds(10, 100, 120, 20);
		secteurComboBox.setBounds(140, 100, 280, 20);
		tousActivitesCheckBox = new JCheckBox("Tous les activités");
		tousActivitesCheckBox.setBounds(430, 100, 150, 20);

		tousVillesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedVille = tousVillesCheckBox.isSelected();
				villeComboBox.setEnabled(!selectedVille);
			}
		});

		tousActivitesCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedActivite = tousActivitesCheckBox.isSelected();
				secteurComboBox.setEnabled(!selectedActivite);
			}
		});

		mailsList = GetMails();
		mailComboBox = new JComboBox<>();

		for (MailC mail : mailsList) {

			mailComboBox.addItem(mail.getObjet());

		}

		mailLabel.setBounds(10, 130, 100, 20);
		mailComboBox.setBounds(140, 130, 280, 20);

		intervalleLabel.setBounds(10, 160, 150, 20);
		intervalleTextField1.setBounds(170, 160, 80, 20);
		intervalleTextField2.setBounds(280, 160, 80, 20);
		entreprisesTrouveesLabel.setBounds(10, 190, 150, 20);
		entreprisesTrouveesTextField.setBounds(280, 190, 80, 20);

		emailsAEnvoyerLabel.setBounds(10, 220, 150, 20);
		emailsAEnvoyerTextField.setBounds(170, 220, 80, 20);
		showButton.setBounds(170, 190, 80, 20);

		// Centrer les boutons
		int buttonWidth = 100;
		int buttonHeight = 30;
		int xMargin = (getSize().width - buttonWidth * 2) / 3;
		int yMargin = 30;
		int buttonY = getSize().height - buttonHeight - yMargin;

		demarrerButton.setBounds(xMargin, 280, buttonWidth, buttonHeight);
		annulerButton.setBounds(427, 280, buttonWidth, buttonHeight);

		// Ajout des composants à la fenêtre
		getContentPane().add(showButton);
		getContentPane().add(compteLabel);
		getContentPane().add(compteComboBox);
		getContentPane().add(emailLabel);
		getContentPane().add(emailComboBox);
		getContentPane().add(villeLabel);
		getContentPane().add(villeComboBox);
		getContentPane().add(secteurLabel);
		getContentPane().add(secteurComboBox);
		getContentPane().add(mailLabel);
		getContentPane().add(mailComboBox);
		getContentPane().add(intervalleLabel);
		getContentPane().add(intervalleTextField1);
		getContentPane().add(intervalleTextField2);
		getContentPane().add(entreprisesTrouveesLabel);
		getContentPane().add(entreprisesTrouveesTextField);
		getContentPane().add(emailsAEnvoyerLabel);
		getContentPane().add(emailsAEnvoyerTextField);
		getContentPane().add(demarrerButton);
		getContentPane().add(annulerButton);
		getContentPane().add(tousActivitesCheckBox);
		getContentPane().add(tousVillesCheckBox);
		getContentPane().add(emailUpdate);
		getContentPane().add(compteUpdate);

		compteComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// emailComboBox.removeAll();
				emailComboBox.removeAllItems();
				// Récupérer le compte sélectionné
				selectedCompte = (String) compteComboBox.getSelectedItem();

				// Mettre à jour les emails du combobox emailComboBox en fonction du compte
				// sélectionné
				updateEmails(selectedCompte);

				idCompte = select_id_compte(selectedCompte);

			}
		});

		emailComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// emailComboBox.removeAllItems();
				// updateEmails(selectedCompte);
				// System.out.println("salam");
			}
		});

		selectVilles();
		selectActivites();

		selectedObject = (String) mailComboBox.getSelectedItem();
		MailC mail = getMailByName(selectedObject);
		// ActionListener pour le bouton Démarrer
		// ActionListener pour le bouton Démarrer
		demarrerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				emailsForPostulation = getEmailsEntreprisesSelectionnees();
				int nombreEmails = emailsForPostulation.size();
				entreprisesTrouveesTextField.setText(nombreEmails + "");

				String emailText = emailsAEnvoyerTextField.getText();

				if (mailComboBox.getItemCount() == 0) {
					NewMailInterface n = new NewMailInterface("id");
					n.setVisible(true);
				} else if (emailText.isEmpty()) {
					Thread thread = new Thread(new Runnable() {
						public void run() {
							
							
							arreterButton.setEnabled(true);
							
							Mail m = new Mail();
							m.setupServerProperties();
							int i = 0;
							int duree = ((Integer.parseInt(intervalleTextField1.getText())
									+ Integer.parseInt(intervalleTextField2.getText())) / 2) * 60000;
							for (String element : emailsForPostulation) {
								
								
								try {
									Thread.sleep(duree);
									demarrerButton.setEnabled(false);
									m.draftEmail2(mail.getFichier(), mail.getObjet(), mail.getMessage(), element);
									m.sendEmail(emailCompte, mail.getCode());
									i++;
									emailsAEnvoyerTextField.setText(Integer.toString(i));
								} catch (InterruptedException | MessagingException | IOException ex) {
									ex.printStackTrace();
								}

								if (arr != 0) {

									enregistrerInformations();
									arr = 0;
									arreterButton.setEnabled(false);

									break;

								}

							}

							demarrerButton.setEnabled(true);
						}
					});
					thread.start();
				} else if (emailText != null) {
					try {
						int emailNumber = Integer.parseInt(emailText);
						if (emailNumber < emailsForPostulation.size()) {
							int startIndex = emailNumber + 1;
							Thread thread = new Thread(new Runnable() {
								public void run() {
									
									arreterButton.setEnabled(true);
									
									Mail m = new Mail();
									m.setupServerProperties();
									int i = startIndex;
									int duree = ((Integer.parseInt(intervalleTextField1.getText())
											+ Integer.parseInt(intervalleTextField2.getText())) / 2) * 60000;
									for (int j = i; j < emailsForPostulation.size(); j++) {

										

										String element = emailsForPostulation.get(j);
										try {
											Thread.sleep(duree);
											demarrerButton.setEnabled(false);
											m.draftEmail2(mail.getFichier(), mail.getObjet(), mail.getMessage(),
													element);
											m.sendEmail(emailCompte, mail.getCode());
											i++;
											emailsAEnvoyerTextField.setText(Integer.toString(i));
											if (emailNumber == i) {
												break;
											}
										} catch (InterruptedException | MessagingException | IOException ex) {
											ex.printStackTrace();
										}

										if (arr != 0) {

											enregistrerInformations();
											arr = 0;
											arreterButton.setEnabled(false);
											break;

										}

									}
									demarrerButton.setEnabled(true);
								}
							});
							thread.start();

						} else {
							// L'index spécifié dépasse la taille de la liste d'e-mails
							return;
							// Gérer cette situation selon vos besoins
						}
					} catch (NumberFormatException e1) {
						// Le contenu du champ de texte n'est pas un nombre valide
						// Gérer cette situation selon vos besoins
					}
				}

			}
		});
		// ActionListener pour le bouton Annuler
		annulerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				viderChampsInterface();
			}
		});

		emailUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// dispose();
				openEmailManagementApp();
			}
		});

		showButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				emailsForPostulation = getEmailsEntreprisesSelectionnees();
				int nombreEmails = emailsForPostulation.size();
				entreprisesTrouveesTextField.setText(nombreEmails + "");
				comparerInfosEmailsEnvoyes();
			}
		});

		JButton newMailButton = new JButton("New Mail");
		newMailButton.setBounds(430, 130, 142, 20);
		newMailButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get the selected nom et prenom from compteComboBox
				String selectedNomPrenom = (String) compteComboBox.getSelectedItem();
				String[] parts = selectedNomPrenom.split(" ");
				String selectedNom = parts[0];
				String selectedPrenom = parts[1];

				try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
					// Query to retrieve the ID from the compte table based on the selected nom et
					// prenom
					String sql = "SELECT id FROM compte WHERE nom = ? AND prenom = ?";
					try (PreparedStatement statement = connection.prepareStatement(sql)) {
						// Set the nom and prenom as the parameters
						statement.setString(1, selectedNom);
						statement.setString(2, selectedPrenom);
						try (ResultSet resultSet = statement.executeQuery()) {
							if (resultSet.next()) {
								// Retrieve the ID value
								String id = resultSet.getString("id");

								// Pass the ID to the NewMailInterface
								NewMailInterface newMailInterface = new NewMailInterface(id);
								newMailInterface.setVisible(true);
								new PostulationInterface(emailCompte).setVisible(false);
								dispose();
							} else {
								System.out.println("No matching ID found for the selected nom et prenom.");
							}
						}
					}
				} catch (SQLException ex) {
					System.err.println("An error occurred while retrieving the ID: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		});

		
		newMailButton.setBounds(430, 130, 142, 20);
		arreterButton.setBounds(280, 280, 110, 30);

		arreterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				arr++;
				arreterButton.setEnabled(false);
			}
		});

		// Définit la taille de la fenêtre
		getContentPane().add(newMailButton);
		getContentPane().add(arreterButton);

		setVisible(true);
	}

	public void viderChampsInterface() {

		tousActivitesCheckBox.setSelected(false);
		tousVillesCheckBox.setSelected(false);
		villeComboBox.setEnabled(true);
		secteurComboBox.setEnabled(true);
		intervalleTextField1.setText("");
		intervalleTextField2.setText("");
		entreprisesTrouveesTextField.setText("");
		emailsAEnvoyerTextField.setText("");

	}

	private void openEmailManagementApp() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new EmailManagementApp().createAndShowGUI(idCompte);
			}
		});
	}

	public static void enregistrerInformations() {

		boolean isVilleComboBoxEnabled = villeComboBox.isEnabled();
		boolean isSecteurComboBoxEnabled = secteurComboBox.isEnabled();
		try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
			String sql = "INSERT INTO information_derniere_postulation (compte, email, ville, secteur, mail, intervalle1, intervalle2, entreprises_trouvees, emails_envoyer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, compteComboBox.getSelectedItem().toString());
			statement.setString(2, emailComboBox.getSelectedItem().toString());

			if (!isVilleComboBoxEnabled) {
				statement.setString(3, "tous les villes");
			} else {
				statement.setString(3, villeComboBox.getSelectedItem().toString());
			}

			if (!isSecteurComboBoxEnabled) {
				statement.setString(4, "tous les activitées");
			} else {
				statement.setString(4, secteurComboBox.getSelectedItem().toString());
			}

			statement.setString(5, mailComboBox.getSelectedItem().toString());
			statement.setInt(6, Integer.parseInt(intervalleTextField1.getText()));
			statement.setInt(7, Integer.parseInt(intervalleTextField2.getText()));
			statement.setInt(8, Integer.parseInt(entreprisesTrouveesTextField.getText()));
			statement.setInt(9, Integer.parseInt(emailsAEnvoyerTextField.getText()));

			// Exécutez la requête INSERT
			statement.executeUpdate();

			System.out.println("Les informations ont été enregistrées avec succès dans la base de données.");
		} catch (SQLException e) {
			System.err.println(
					"Une erreur s'est produite lors de l'enregistrement des informations dans la base de données : "
							+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void testerExistenceCompteEmail() {
		String compte = compteComboBox.getSelectedItem().toString();
		String email = emailComboBox.getSelectedItem().toString();

		try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
			String sql = "SELECT * FROM information_derniere_postulation WHERE compte = ? AND email = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, compte);
			statement.setString(2, email);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String ville = resultSet.getString("ville");
				String secteur = resultSet.getString("secteur");
				String mail = resultSet.getString("mail");
				int intervalle1 = resultSet.getInt("intervalle1");
				int intervalle2 = resultSet.getInt("intervalle2");
				int entreprisesTrouvees = resultSet.getInt("entreprises_trouvees");
				int emailsAEnvoyer = resultSet.getInt("emails_envoyer");
				boolean isVilleBoolean = resultSet.getBoolean("ville");
				boolean isActiviteBoolean = resultSet.getBoolean("secteur");

				if (isVilleBoolean && isActiviteBoolean) {
					villeComboBox.setEnabled(false);
					secteurComboBox.setEnabled(false);

					tousVillesCheckBox.setSelected(true);
					tousActivitesCheckBox.setSelected(true);
				} else {
					villeComboBox.setEnabled(true);
					secteurComboBox.setEnabled(true);

					tousVillesCheckBox.setSelected(false);
					tousActivitesCheckBox.setSelected(false);

					villeComboBox.setSelectedItem(ville);
					secteurComboBox.setSelectedItem(secteur);
				}

				mailComboBox.setSelectedItem(mail);
				intervalleTextField1.setText(Integer.toString(intervalle1));
				intervalleTextField2.setText(Integer.toString(intervalle2));
				entreprisesTrouveesTextField.setText(Integer.toString(entreprisesTrouvees));
				emailsAEnvoyerTextField.setText(Integer.toString(emailsAEnvoyer));
			} else {
				villeComboBox.setEnabled(true);
				secteurComboBox.setEnabled(true);
				tousVillesCheckBox.setSelected(false);
				tousActivitesCheckBox.setSelected(false);
				villeComboBox.setSelectedIndex(0);
				secteurComboBox.setSelectedIndex(0);
				mailComboBox.setSelectedIndex(0);
				intervalleTextField1.setText("");
				intervalleTextField2.setText("");
				entreprisesTrouveesTextField.setText("");
				emailsAEnvoyerTextField.setText("");
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.err.println(
					"Une erreur s'est produite lors de la récupération des informations depuis la base de données : "
							+ e.getMessage());
			e.printStackTrace();
		}

	}

	public List<MailC> GetMails() {

		List<MailC> mailsList = new ArrayList<>();
		String userEmail = emailCompte;

		String sql = "SELECT m.id, m.objet, m.message, m.date, m.idcompte ,m.fichiers,m.code  FROM mail m JOIN compte c ON m.idcompte = c.id JOIN email e ON e.idcompte = c.id  WHERE e.mail = ?";

		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, userEmail);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String objet = resultSet.getString("objet");
					String message = resultSet.getString("message");
					String date = resultSet.getString("date");
					idCompte = resultSet.getInt("idcompte");
					String fichiers = resultSet.getString("fichiers");
					String code = resultSet.getString("code");
					MailC mail = new MailC(id, objet, message, date, idCompte, fichiers, code);
					mailsList.add(mail);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mailsList;

	}

	public MailC getMailByName(String object) {

		MailC selectedMail = null;
		for (MailC mail : mailsList) {
			if (mail.getObjet().equals(object)) { // Comparer le nom sélectionné avec l'objet courant de la liste
				selectedMail = mail;
				break; // Sortir de la boucle si l'objet correspondant est trouvé
			}
		}

		return selectedMail;
	}

	public List<String> getEmailsEntreprisesSelectionnees() {
		List<String> emailsSelectionnes = new ArrayList<>();

		// Vérifier si l'option "Tous les villes" est sélectionnée
		boolean tousVillesSelectionne = tousVillesCheckBox.isSelected();

		// Vérifier si l'option "Tous les activités" est sélectionnée
		boolean tousActivitesSelectionne = tousActivitesCheckBox.isSelected();

		// Exécuter la requête en fonction des options sélectionnées
		try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
			String sql;
			if (tousVillesSelectionne && tousActivitesSelectionne) {
				// Requête pour récupérer tous les e-mails des entreprises de toutes les villes
				// et toutes les activités
				sql = "SELECT email FROM entreprise";
			} else if (tousVillesSelectionne) {
				// Récupérer l'activité sélectionnée dans le JComboBox activiteComboBox
				String activiteSelectionnee = (String) secteurComboBox.getSelectedItem();
				if (activiteSelectionnee != null) {
					// Requête pour récupérer les e-mails des entreprises de toutes les villes et
					// l'activité sélectionnée
					sql = "SELECT e.email FROM entreprise e JOIN activite a ON e.idactivite = a.id WHERE a.nom = ?";
				} else {
					// Aucune activité sélectionnée, ne rien faire
					return emailsSelectionnes;
				}
			} else if (tousActivitesSelectionne) {
				// Récupérer la ville sélectionnée dans le JComboBox villeComboBox
				String villeSelectionnee = (String) villeComboBox.getSelectedItem();
				if (villeSelectionnee != null) {
					// Requête pour récupérer les e-mails des entreprises de la ville sélectionnée
					// et toutes les activités
					sql = "SELECT e.email FROM entreprise e JOIN ville v ON e.idville = v.id WHERE v.nom = ?";
				} else {
					// Aucune ville sélectionnée, ne rien faire
					return emailsSelectionnes;
				}
			} else {
				// Récupérer la ville sélectionnée dans le JComboBox villeComboBox
				String villeSelectionnee = (String) villeComboBox.getSelectedItem();
				// Récupérer l'activité sélectionnée dans le JComboBox activiteComboBox
				String activiteSelectionnee = (String) secteurComboBox.getSelectedItem();
				if (villeSelectionnee != null && activiteSelectionnee != null) {
					// Requête pour récupérer les e-mails des entreprises de la ville sélectionnée
					// et l'activité sélectionnée
					sql = "SELECT e.email FROM entreprise e,activite a,ville v WHERE e.idville = v.id and e.idactivite = a.id and v.nom = ? AND a.nom = ?";
				} else {
					// Aucune ville ou activité sélectionnée, ne rien faire
					return emailsSelectionnes;
				}
			}

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				if (!tousVillesSelectionne && !tousActivitesSelectionne) {
					// Récupérer la ville sélectionnée dans le JComboBox villeComboBox
					String villeSelectionnee = (String) villeComboBox.getSelectedItem();
					statement.setString(1, villeSelectionnee);
					// Récupérer l'activité sélectionnée dans le JComboBox activiteComboBox
					String activiteSelectionnee = (String) secteurComboBox.getSelectedItem();

					statement.setString(2, activiteSelectionnee);
				} else if (!tousVillesSelectionne) {
					// Récupérer la ville sélectionnée dans le JComboBox villeComboBox
					String villeSelectionnee = (String) villeComboBox.getSelectedItem();
					statement.setString(1, villeSelectionnee);
				} else if (!tousActivitesSelectionne) {
					// Récupérer l'activité sélectionnée dans le JComboBox activiteComboBox
					String activiteSelectionnee = (String) secteurComboBox.getSelectedItem();
					statement.setString(1, activiteSelectionnee);
				}

				try (ResultSet resultSet = statement.executeQuery()) {
					// Parcourir le ResultSet et ajouter les e-mails à la liste emailsSelectionnes
					while (resultSet.next()) {
						String email = resultSet.getString("email");
						emailsSelectionnes.add(email);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * for(String email:emailsSelectionnes) {
		 * 
		 * System.out.println(email); }
		 */

		return emailsSelectionnes;
	}

	public void comparerInfosEmailsEnvoyes() {
		String compte = compteComboBox.getSelectedItem().toString();
		String email = emailComboBox.getSelectedItem().toString();

		try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
			String sql = "SELECT * FROM information_derniere_postulation WHERE compte = ? AND email = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, compte);
			statement.setString(2, email);

			ResultSet resultSet = statement.executeQuery();

			boolean correspondanceTrouvee = false; // Indicateur de correspondance trouvée

			while (resultSet.next()) {
				int emailsEnvoyes = resultSet.getInt("emails_envoyer");
				System.out.println(emailsEnvoyes);

				// Comparer les informations
				String ville = "";
				String secteur = "";
				String mail = mailComboBox.getSelectedItem().toString();
				int intervalle1 = Integer.parseInt(intervalleTextField1.getText());
				int intervalle2 = Integer.parseInt(intervalleTextField2.getText());
				int entreprisesTrouvees = Integer.parseInt(entreprisesTrouveesTextField.getText());

				// Tester si les combobox sont désactivées
				if (!villeComboBox.isEnabled()) {
					ville = resultSet.getString("ville");
				} else if (tousVillesCheckBox.isSelected()) {
					ville = "tous les villes";
				} else {
					ville = villeComboBox.getSelectedItem().toString();
				}

				if (!secteurComboBox.isEnabled()) {
					secteur = resultSet.getString("secteur");
				} else if (tousActivitesCheckBox.isSelected()) {
					secteur = "tous les activités";
				} else {
					secteur = secteurComboBox.getSelectedItem().toString();
				}

				// Comparer les valeurs
				if (ville.equals(resultSet.getString("ville")) && secteur.equals(resultSet.getString("secteur"))
						&& mail.equals(resultSet.getString("mail")) && intervalle1 == resultSet.getInt("intervalle1")
						&& intervalle2 == resultSet.getInt("intervalle2")
						&& entreprisesTrouvees == resultSet.getInt("entreprises_trouvees")) {
					// Les informations correspondent
					correspondanceTrouvee = true;

					// Afficher le nombre d'e-mails envoyés depuis la base de données
					emailsAEnvoyerTextField.setText(Integer.toString(emailsEnvoyes));

					Statement stmt = null;
					try {

						stmt = connection.createStatement();
						String query = "DELETE FROM information_derniere_postulation WHERE compte = '" + compte
								+ "'  and email = '" + email + "' and ville = '" + resultSet.getString("ville")
								+ "' and secteur = '" + resultSet.getString("secteur") + "' and mail = '"
								+ resultSet.getString("mail") + "' and intervalle1 = '"
								+ resultSet.getInt("intervalle1") + "' and intervalle2 = '"
								+ resultSet.getInt("intervalle2") + "' and entreprises_trouvees = '"
								+ resultSet.getInt("entreprises_trouvees") + "' and emails_envoyer = '" + emailsEnvoyes
								+ "' ";

						stmt.executeUpdate(query);
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break; // Sortir de la boucle, car une correspondance a été trouvée
				} else {

					emailsAEnvoyerTextField.setText("");

				}
			}

			if (!correspondanceTrouvee) {
				System.out.println("Aucune correspondance trouvée");
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.err.println(
					"Une erreur s'est produite lors de la récupération des informations depuis la base de données : "
							+ e.getMessage());
			e.printStackTrace();
		} catch (NumberFormatException e) {
			System.err.println("Erreur de format de nombre : " + e.getMessage());
			e.printStackTrace();
			// Traitez l'erreur de conversion ici
		}
	}

	private void selectVilles() {
		// Requête SQL pour récupérer les villes à partir de la table "ville"
		String sql = "SELECT nom FROM ville";
		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			// Effacer les anciennes valeurs du JComboBox
			// villeComboBox.removeAllItems();

			// Parcourir le ResultSet et ajouter les villes au JComboBox
			while (resultSet.next()) {
				String ville = resultSet.getString("nom");
				villeComboBox.addItem(ville);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void selectVilles2() {
		// Requête SQL pour récupérer les villes à partir de la table "ville"
		String sql = "SELECT nom FROM ville";
		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			// Effacer les anciennes valeurs du JComboBox
			// villeComboBox.removeAllItems();

			// Parcourir le ResultSet et ajouter les villes au JComboBox
			while (resultSet.next()) {
				String ville = resultSet.getString("nom");
				villes.add(ville);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void selectActivites() {
		// Requête SQL pour récupérer les activités à partir de la table "activite"
		String sql = "SELECT nom FROM activite";
		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			// Effacer les anciennes valeurs du JComboBox
			// secteurComboBox.removeAllItems();

			// Parcourir le ResultSet et ajouter les activités au JComboBox
			while (resultSet.next()) {
				String activite = resultSet.getString("nom");
				secteurComboBox.addItem(activite);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void selectActivites2() {
		// Requête SQL pour récupérer les activités à partir de la table "activite"
		String sql = "SELECT nom FROM activite";
		try (Connection connection = DatabaseConnection.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			// Effacer les anciennes valeurs du JComboBox
			// secteurComboBox.removeAllItems();

			// Parcourir le ResultSet et ajouter les activités au JComboBox
			while (resultSet.next()) {
				String activite = resultSet.getString("nom");
				activities.add(activite);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateEmails(String selectedCompte) {
		// Effacer les anciens emails
		emailComboBox.removeAllItems();
		// Séparer la chaîne selectedCompte en nom et prénom
		String[] parts = selectedCompte.split("\\s+"); // Supposons que les parties sont séparées par un espace

		if (parts.length >= 2) {
			String nom = parts[0];
			String prenom = parts[1];

			// Récupérer les emails correspondant au nom et prénom à partir de la base de
			// données
			try {
				// Get the database connection
				Connection connection = DatabaseConnection.getInstance().getConnection();
				String sql = "SELECT e.mail FROM compte c JOIN email e ON c.id = e.idcompte WHERE c.nom = ? and c.prenom = ?";
				try (PreparedStatement statement = connection.prepareStatement(sql)) {
					statement.setString(1, nom);
					statement.setString(2, prenom);

					try (ResultSet resultSet = statement.executeQuery()) {
						// Parcourir le ResultSet et ajouter les emails au JComboBox emailComboBox
						while (resultSet.next()) {
							String email = resultSet.getString("mail");
							emailComboBox.addItem(email);
						}
					}
				}
			} catch (SQLException e) {
				System.err.println("Une erreur s'est produite lors de la récupération des emails : " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public int select_id_compte(String selectedCompte) {

		String[] parts = selectedCompte.split("\\s+"); // Supposons que les parties sont séparées par un espace

		String nom = null;
		String prenom = null;
		if (parts.length >= 2) {
			nom = parts[0];
			prenom = parts[1];
		}

		int id = 0;
		String query = "SELECT id FROM compte WHERE nom = ? AND prenom = ?";

		try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, nom);
			statement.setString(2, prenom);

			ResultSet resultSet = statement.executeQuery();
			resultSet.next();

			id = resultSet.getInt(1);

			System.out.println("id =" + id);

		} catch (SQLException e) {
			System.out.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
		}

		return id;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PostulationInterface("");

			}
		});
	}
}