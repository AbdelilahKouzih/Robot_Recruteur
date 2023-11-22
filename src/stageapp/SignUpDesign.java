package stageapp;
import java.awt.EventQueue;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import stageapp.DatabaseConnection;
import com.formdev.flatlaf.FlatIntelliJLaf;


public class SignUpDesign extends JFrame implements ActionListener {

	private static final String PLACEHOLDER_FORMAT = "Format: YYYY-MM-DD";
	private JPanel contentPane;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private  JTextField textFieldFirstName;
	private  JTextField textFieldLastName ;
	private Button button;
	private Statement stmt;
    private Connection conn;
    private JLabel dateLabel;
    private JTextField dateTextField;
    private JLabel genreLabel;
    private JComboBox<String> genreComboBox;
    private DatabaseConnection databaseConnection;

	
	int xx,xy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpDesign frame = new SignUpDesign();
					frame.setUndecorated(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	// going to borrow code from a gist to move frame.
	

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public SignUpDesign() throws SQLException {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 520);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 FlatIntelliJLaf.install();

		
		conn = DatabaseConnection.getInstance().getConnection();

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 346, 520);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("KeepToo");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(139, 305, 84, 27);
		panel.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				 xx = e.getX();
			     xy = e.getY();
			}
		});
		label.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				
				int x = arg0.getXOnScreen();
	            int y = arg0.getYOnScreen();
	            SignUpDesign.this.setLocation(x - xx, y - xy);  
			}
		});
		label.setBounds(-38, 0, 420, 275);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(SignUpDesign.class.getResource("images/bg8.jpg")));
		panel.add(label);
		
		JLabel lblWeGotYou = new JLabel("");
		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeGotYou.setForeground(new Color(240, 248, 255));
		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWeGotYou.setBounds(111, 343, 141, 27);
		panel.add(lblWeGotYou);
		
		button = new Button("SignUp");
		button.setForeground(Color.WHITE);
		button.setBackground(Color.black);
		button.setBounds(395, 430, 283, 36);
		contentPane.add(button);
		
		
		
		// Add first name label
	    JLabel lblFirstName = new JLabel("First Name");
	    lblFirstName.setBounds(395, 5, 100, 14);
	    contentPane.add(lblFirstName);

	    // Add first name text field
	    textFieldFirstName = new JTextField();
	    textFieldFirstName.setBounds(395, 26, 283, 36);
	    contentPane.add(textFieldFirstName);
	    textFieldFirstName.setColumns(10);

	    // Add last name label
	    JLabel lblLastName = new JLabel("Last Name");
	    lblLastName.setBounds(395, 67, 100, 14);
	    contentPane.add(lblLastName);

	    // Add last name text field
	    textFieldLastName = new JTextField();
	    textFieldLastName.setBounds(395, 88, 283, 36);
	    contentPane.add(textFieldLastName);
	    textFieldLastName.setColumns(10);
		
	    genreLabel = new JLabel("Genre");
        genreComboBox = new JComboBox<>(new String[]{"Masculin", "Féminin"});
        genreLabel.setBounds(395, 119, 283, 36);
        genreComboBox.setBounds(395, 150, 283, 26);
        genreComboBox.setBackground(Color.WHITE);
	    
	    dateLabel = new JLabel("Date de Naissance");
        dateTextField = new JTextField(PLACEHOLDER_FORMAT);
        dateTextField.setForeground(Color.GRAY);
        dateLabel.setBounds(395, 172, 283, 36);
        dateTextField.setBounds(395, 202, 283, 36);
        
        dateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dateTextField.getText().equals(PLACEHOLDER_FORMAT)) {
                    dateTextField.setText(""); // Effacer le placeholder lorsqu'il obtient le focus
                    dateTextField.setForeground(Color.BLACK); // Rétablir la couleur du texte
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dateTextField.getText().isEmpty()) {
                    dateTextField.setText(PLACEHOLDER_FORMAT); // Réafficher le placeholder si le champ est vide
                    dateTextField.setForeground(Color.GRAY); // Utiliser la couleur grise pour le placeholder
                }
            }
        });
        
        
        
        contentPane.add(dateLabel);
        contentPane.add(dateTextField);
        contentPane.add(genreLabel);
        contentPane.add(genreComboBox);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(395, 240, 54, 14);
		contentPane.add(lblEmail);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(395, 261, 283, 36);
		contentPane.add(textField_1);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(395, 302, 96, 14);
		contentPane.add(lblPassword);
		
		JLabel lblRepeatPassword = new JLabel("Repeat Password");
		lblRepeatPassword.setBounds(395, 364, 133, 14);
		contentPane.add(lblRepeatPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(395, 323, 283, 36);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(395, 385, 283, 36);
		contentPane.add(passwordField_1);
		
		
	            stmt = conn.createStatement();
			    button.addActionListener(this);

		
	}
	
	
	
	
	public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            String prenom = new String(textFieldFirstName.getText());
            String nom = new String(textFieldLastName.getText());
            String email = textField_1.getText();
            String datedenaissance = dateTextField.getText();
            String genre = genreComboBox.getSelectedItem().toString();
            
            
            if (!datedenaissance.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                JOptionPane.showMessageDialog(null, "Le format de la date de naissance doit être YYYY-MM-DD.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!email.matches("^[\\w\\.]+@[\\w]+\\.[\\w]{2,4}$")) {
                JOptionPane.showMessageDialog(this, "Invalid email address. Please try again.");
                return;
            }
            
            String password = new String(passwordField.getPassword());
            if (password.length() < 8) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long. Please try again.");
                return;
            }
            if (!password.matches(".*[!@#$%^&*()].*")) {
                JOptionPane.showMessageDialog(this, "Password must contain at least one special character. Please try again.");
                return;
            }

            String confirmPassword = new String(passwordField_1.getPassword());
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.");
                return;
            }
            
         // Test for empty email and password fields
	        if (email.trim().isEmpty() || password.trim().isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please fill in both email and password fields.");
	            return;
	        }
	        
	            String query = "SELECT COUNT(*) FROM compte WHERE nom = ? AND prenom = ?";
				PreparedStatement statement;
				try {
					statement = conn.prepareStatement(query);
					statement.setString(1, nom);
					statement.setString(2, prenom);

					ResultSet resultSet = statement.executeQuery();
					resultSet.next();
					int count = resultSet.getInt(1);
					if (count > 0) {
						JOptionPane.showMessageDialog(null, "Le nom et prénom existent.", "Erreur",JOptionPane.ERROR_MESSAGE);
						return;
					} 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
	        
           
	        
	            // Insérer les données dans la table "compte"
	            String insertCompteQuery = "INSERT INTO compte (nom, prenom, genre, datenaissance) VALUES (?, ?, ?, ?)";
	            try (PreparedStatement compteStmt = conn.prepareStatement(insertCompteQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
	                compteStmt.setString(1, nom);
	                compteStmt.setString(2, prenom);
	                compteStmt.setString(3, genre);
	                compteStmt.setString(4,datedenaissance);
	                compteStmt.executeUpdate();

	                // Récupérer l'ID auto-incrémenté généré pour le compte
	                ResultSet generatedKeys = compteStmt.getGeneratedKeys();
	                int compteId = -1;
	                if (generatedKeys.next()) {
	                    compteId = generatedKeys.getInt(1);
	                } else {
	                    // Gérer l'erreur de récupération de l'ID généré pour le compte
	                    JOptionPane.showMessageDialog(null, "Erreur lors de la récupération de l'ID généré pour le compte.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                // Insérer les données dans la table "email"
	                String insertEmailQuery = "INSERT INTO email (mail, passwd, idcompte) VALUES (?, ?, ?)";
	                try (PreparedStatement emailStmt = conn.prepareStatement(insertEmailQuery)) {
	                    emailStmt.setString(1, email);
	                    emailStmt.setString(2, password);
	                    emailStmt.setInt(3, compteId);
	                    emailStmt.executeUpdate();

	                    // Afficher un message de succès ou effectuer d'autres opérations
	                    JOptionPane.showMessageDialog(null, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
	                    dispose();
                        LoginDesign  log= new LoginDesign();
                        log.setVisible(true);
	                    // Effacer les champs de saisie après l'inscription réussie
	                    textFieldFirstName.setText("");
	                    textFieldLastName.setText("");
	                    dateTextField.setText("");
	                    genreComboBox.setSelectedIndex(0);
	                    textField_1.setText("");
	                    passwordField.setText("");
	                    passwordField_1.setText("");
	                } catch (SQLException ex) {
	                    JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion des données dans la table 'email'.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (SQLException ex) {
	                JOptionPane.showMessageDialog(null, "Erreur lors de l'insertion des données dans la table 'compte'.", "Erreur", JOptionPane.ERROR_MESSAGE);
	            }
	        

	        
	        
	        
	        
	        
        }
    }
	
	

}