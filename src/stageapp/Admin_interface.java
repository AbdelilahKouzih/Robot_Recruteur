package stageapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.formdev.flatlaf.FlatIntelliJLaf;


public class Admin_interface extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin_interface frame = new Admin_interface();
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
	public Admin_interface() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 687, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		 FlatIntelliJLaf.install();

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				user_admin frame = new user_admin();
				frame.setVisible(true);

			}
		});
		btnNewButton.setIcon(new ImageIcon(Admin_interface.class.getResource("/stageapp/images/utilisateur (1).png")));
		btnNewButton.setBounds(37, 78, 141, 129);
		contentPane.add(btnNewButton);
		btnNewButton.setBackground(null); // Set the background to null
		btnNewButton.setOpaque(false); // Set opaque to false
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false); // Remove the border
		btnNewButton.setFocusPainted(false);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1
				.setIcon(new ImageIcon(Admin_interface.class.getResource("/stageapp/images/base-de-donnees (1).png")));
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setFocusPainted(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setBackground((Color) null);
		btnNewButton_1.setBounds(260, 78, 141, 129);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					new AdminInterface();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new App("");
					}
				});
			}

		});
		btnNewButton_2.setIcon(new ImageIcon(Admin_interface.class.getResource("/stageapp/images/entreprise.png")));
		btnNewButton_2.setOpaque(false);
		btnNewButton_2.setFocusPainted(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setBackground((Color) null);
		btnNewButton_2.setBounds(490, 78, 141, 129);
		contentPane.add(btnNewButton_2);

		JLabel lblNewLabel = new JLabel("Users");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(80, 53, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblAdmins = new JLabel("Admins");
		lblAdmins.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAdmins.setBounds(305, 53, 57, 14);
		contentPane.add(lblAdmins);

		JLabel lblEntreprise = new JLabel("Entreprises");
		lblEntreprise.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEntreprise.setBounds(515, 53, 78, 14);
		contentPane.add(lblEntreprise);

		JLabel nb_users = new JLabel("");
		nb_users.setFont(new Font("Tahoma", Font.BOLD, 16));
		nb_users.setBounds(90, 239, 46, 14);
		contentPane.add(nb_users);

		JLabel nb_Admin = new JLabel("");
		nb_Admin.setFont(new Font("Tahoma", Font.BOLD, 16));
		nb_Admin.setBounds(316, 239, 46, 14);
		contentPane.add(nb_Admin);

		JLabel nb_entreprise = new JLabel("");
		nb_entreprise.setFont(new Font("Tahoma", Font.BOLD, 16));
		nb_entreprise.setBounds(547, 239, 46, 14);
		contentPane.add(nb_entreprise);

		try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
			String query1 = "SELECT COUNT(*) AS count1 FROM compte";
			String query2 = "SELECT COUNT(*) AS count2 FROM admin";
			String query3 = "SELECT COUNT(*) AS count3 FROM entreprise";

			try (Statement statement = connection.createStatement()) {
				ResultSet resultSet1 = statement.executeQuery(query1);
				if (resultSet1.next()) {
					int count1 = resultSet1.getInt("count1");

					nb_users.setText(Integer.toString(count1));
				}

				ResultSet resultSet2 = statement.executeQuery(query2);
				if (resultSet2.next()) {
					int count2 = resultSet2.getInt("count2");

					nb_Admin.setText(Integer.toString(count2));
				}

				ResultSet resultSet3 = statement.executeQuery(query3);
				if (resultSet3.next()) {
					int count3 = resultSet3.getInt("count3");

					nb_entreprise.setText(Integer.toString(count3));
				}
			}
		} catch (SQLException e) {
			System.err.println("Une erreur s'est produite lors de l'exécution des requêtes : " + e.getMessage());
			e.printStackTrace();
		}

	}
}
