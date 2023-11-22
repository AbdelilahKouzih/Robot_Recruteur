package stageapp;


import java.util.Date;

public class MailC {
    private int id;
    private String objet;
    private String message;
    private String date;
    private int idCompte;
    private String fichier;
    private String code;
    
    // Constructeur
    public MailC(int id, String objet, String message, String date, int idCompte ,String fichier,String code) {
        this.id = id;
        this.objet = objet;
        this.message = message;
        this.date = date;
        this.idCompte = idCompte;
        this.fichier=fichier;
        this.code=code;
    }
    
    // Getters et setters
    
    public int getId() {
        return id;
    }

    public String getFichier() {
		return fichier;
	}

	public void setFichier(String fichier) {
		this.fichier = fichier;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(int id) {
        this.id = id;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }
    
    // Autres méthodes si nécessaire
}