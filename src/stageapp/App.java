package stageapp;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {

    private JFrame mainFrame;
    private SearchCompaniesUI searchCompaniesUI;
    private CompanyDetailsUI companyDetailsUI;
    private String emailClient;

    public App(String emailClient) {
        mainFrame = new JFrame("Application de recherche d'entreprises");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        this.emailClient = emailClient;
        searchCompaniesUI = new SearchCompaniesUI(this);
        companyDetailsUI = new CompanyDetailsUI(emailClient);
        companyDetailsUI.getPanel().setPreferredSize(new Dimension(400, 600));
        searchCompaniesUI.getPanel().setPreferredSize(new Dimension(200, 600));
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, searchCompaniesUI.getPanel(), companyDetailsUI.getPanel());
        splitPane.setDividerLocation(200);
        splitPane.setForeground(Color.WHITE);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(splitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton deleteButton = new JButton("Supprimer");
        JButton editButton = new JButton("Modifier");
        JButton confirmButton = new JButton("Confirmer");
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(confirmButton);
        mainFrame.add(rightPanel, BorderLayout.CENTER);
        //mainFrame.add(buttonPanel, BorderLayout.SOUTH); // Ajout du nouveau panneau en bas

        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // Ajoutez des ActionListener pour les boutons si nécessaire
    }

    public void showCompanyDetails(Entreprise entreprise) throws SQLException {
        companyDetailsUI.updateUI(entreprise);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App("");
            }
        });
    }
}