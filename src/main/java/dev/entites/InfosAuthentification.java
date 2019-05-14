package dev.entites;

public class InfosAuthentification {

	private String matriculeCollegue;
	private String motDePasse;
	private String urlPhoto;

	public InfosAuthentification(String matriculeCollegue, String motDePasse, String urlPhoto) {
		super();
		this.matriculeCollegue = matriculeCollegue;
		this.motDePasse = motDePasse;
		this.urlPhoto = urlPhoto;
	}

	public InfosAuthentification(String matriculeCollegue, String motDePasse) {
		super();
		this.matriculeCollegue = matriculeCollegue;
		this.motDePasse = motDePasse;
	}
	
	public InfosAuthentification (){
		super();
	}

	public String getMatriculeCollegue() {
		return matriculeCollegue;
	}

	public void setMatriculeCollegue(String matriculeCollegue) {
		this.matriculeCollegue = matriculeCollegue;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
}
