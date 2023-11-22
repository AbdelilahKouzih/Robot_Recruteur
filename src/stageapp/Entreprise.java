package stageapp;

class Entreprise {
    private String name;
    private int idactivity;
    private String phone;
    private String email;
    private String site;
    private int idcity;
    private int id;
    
   
    
    public Entreprise(String name, int idactivity, String phone, String email, String site, int idcity, int id) {
		super();
		this.name = name;
		this.idactivity = idactivity;
		this.phone = phone;
		this.email = email;
		this.site = site;
		this.idcity = idcity;
		this.id = id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Entreprise() {
		// TODO Auto-generated constructor stub
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
        return name;
    }
    
   
    
    public String getPhone() {
        return phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    
    /* public static void main(String[] args) throws Exception {
	    // Charger la page web
	    String url = "https://www.marocannuaire.org/";
	    Document doc = Jsoup.connect(url).get();

	    // Sélectionner le menu Annuaire et ses liens de niveau supérieur
	    Elements menuAnnuaire = doc.select("li.menu-item-has-children > a");

	    // Afficher le texte des liens pères de Annuaire
	    for (Element menu : menuAnnuaire) {
	        System.out.println(menu.text());
	    }
	}*/
    
    public int getActivity() {
		return idactivity;
	}

	public void setActivity(int activity) {
		this.idactivity = activity;
	}


	

	public int getCity() {
		return idcity;
	}

	
	public void setCity(int city) {
		this.idcity = city;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
    public String toString() {
        return "Company Name: " + name + "\n" +
               "Phone: " + phone + "\n" +
               "Email: " + email + "\n";
    }

	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}
}