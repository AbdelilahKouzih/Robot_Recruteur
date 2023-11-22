package stageapp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Button;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
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
import java.awt.event.ActionEvent;
import com.formdev.flatlaf.FlatIntelliJLaf;



public class LoginDesign extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private Button button;
	private Statement stmt;
	private Connection conn;
	private Button buttonSignUP;
	int xx, xy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginDesign frame = new LoginDesign();
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
	 */
	public LoginDesign() {
		
		
		 FlatIntelliJLaf.install();

		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 476);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 0, 346, 490);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
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
				LoginDesign.this.setLocation(x - xx, y - xy);
			}
		});
		label.setBounds(-38, 0, 420, 275);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setIcon(new ImageIcon(LoginDesign.class.getResource("images/bg8.jpg")));
		panel.add(label);

		JLabel lblWeGotYou = new JLabel("");
		lblWeGotYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeGotYou.setForeground(new Color(240, 248, 255));
		lblWeGotYou.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWeGotYou.setBounds(111, 343, 141, 27);
		panel.add(lblWeGotYou);

		button = new Button("Login");
		button.setForeground(Color.WHITE);
		button.setBackground(Color.black);
		button.setBounds(395, 313, 283, 36);
		contentPane.add(button);

		buttonSignUP = new Button("SignUp");
		buttonSignUP.setForeground(Color.WHITE);
		buttonSignUP.setBackground(Color.black);
		buttonSignUP.setBounds(395, 363, 283, 36);
		contentPane.add(buttonSignUP);

		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(395, 132, 54, 14);
		contentPane.add(lblEmail);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(395, 157, 283, 36);
		contentPane.add(textField_1);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(395, 204, 96, 14);
		contentPane.add(lblPassword);

		passwordField = new JPasswordField();
		passwordField.setBounds(395, 229, 283, 36);
		contentPane.add(passwordField);

		/*
		 * JLabel lbl_close = new JLabel("X"); lbl_close.addMouseListener(new
		 * MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent arg0) {
		 * 
		 * System.exit(0); } });
		 * lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		 * lbl_close.setForeground(new Color(241, 57, 83)); lbl_close.setFont(new
		 * Font("Tahoma", Font.PLAIN, 18)); lbl_close.setBounds(691, 0, 37, 27);
		 * contentPane.add(lbl_close);
		 */
	

		button.addActionListener(this);
		buttonSignUP.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == button) {
	        String email = textField_1.getText();
	        System.out.println(email);

	        String password = new String(passwordField.getPassword());
	        
	        
	        try {
	            // Établir une connexion à la base de données
	            Connection connection = DatabaseConnection.getInstance().getConnection();

	            // Créer la requête SQL
	            String query = "SELECT COUNT(*) FROM admin WHERE mail = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, email);

	            // Exécuter la requête
	            ResultSet resultSet = statement.executeQuery();
	            resultSet.next();
	            int count = resultSet.getInt(1);

	            // Vérifier si l'e-mail appartient à un administrateur
	            if (count > 0) {
	            	dispose();
	            	Admin_interface admin = new Admin_interface();
	            	admin.setVisible(true);
	            } 

	            // Fermer les ressources
	            resultSet.close();
	            statement.close();
	            connection.close();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	        
	        

	        // Test for empty email and password fields
	        if (email.trim().isEmpty() || password.trim().isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Please fill in both email and password fields.");
	            return;
	        }

	        try {
	            // Get the database connection
	            Connection connection = DatabaseConnection.getInstance().getConnection();

	            // Test for invalid database connection
	            if (connection == null || connection.isClosed()) {
	                JOptionPane.showMessageDialog(this, "Could not connect to the database.");
	                return;
	            }

	            // Test for SQL injection attacks using parameterized queries
	            String adminQuery = "SELECT * FROM compte c JOIN email e ON c.id = e.idcompte WHERE e.mail = ? AND e.passwd = ?";
	            PreparedStatement adminStmt = connection.prepareStatement(adminQuery);
	            adminStmt.setString(1, email);
	            adminStmt.setString(2, password);
	            ResultSet adminRs = adminStmt.executeQuery();
	            if (adminRs.next()) {
	                JOptionPane.showMessageDialog(this, "Login successful");
	                dispose();

	              //  PostulationInterface frame = new PostulationInterface(email);
	              //  frame.setVisible(true);
	                
	                post frame = new post(email);
	                frame.setVisible(true);

	                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	                    // Code to execute on application shutdown
	                    System.out.println("The application is closing. Executing shutdown code...");

	                   // if (!frame.emailsAEnvoyerTextField.getText().equals(frame.entreprisesTrouveesTextField.getText())) {
						//    frame.enregistrerInformations();
					//	}
	                }));

				} else {

					JOptionPane.showMessageDialog(this, "Invalid email or password. Please try again.");
				}
			} catch (SQLException ex) {
				System.out.println("Error: " + ex.getMessage());
			}
		} else if (e.getSource() == buttonSignUP) {
			dispose();
			SignUpDesign form = null;
			try {
				form = new SignUpDesign();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			form.setVisible(true);
		}
	}


}