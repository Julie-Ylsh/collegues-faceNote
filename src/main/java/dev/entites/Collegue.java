package dev.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COLLEGUE")
public class Collegue {

	// Avec mot de passe sans matricule
	public Collegue(String motDePasse, String nom, String prenoms, String photoUrl, List<String> roles) {
		super();
		this.nom = nom;
		this.prenoms = prenoms;

		this.photoUrl = photoUrl;
		this.roles = roles;
		this.motDePasse = motDePasse;
	}

	// Sans mot de passe avec matricule
	public Collegue(Integer matricule, String nom, String prenoms, String photoUrl, List<String> roles) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.prenoms = prenoms;

		this.photoUrl = photoUrl;
		this.roles = roles;

	}

	// Pour la BDD, avec mot de passe avec matricule
	public Collegue(Integer matricule, String motDePasse, String nom, String prenoms, String photoUrl,
			List<String> roles) {
		super();
		this.matricule = matricule;
		this.nom = nom;
		this.prenoms = prenoms;
		this.motDePasse = motDePasse;
		this.photoUrl = photoUrl;
		this.roles = roles;

	}

	// Pourl'inscription, avec matricule, mot de passe et photo
	public Collegue(String motDePasse, Integer matricule, String photoUrl) {
		super();
		this.matricule = matricule;
		this.photoUrl = photoUrl;
		this.motDePasse = motDePasse;
	}

	// Pour les votes, avec vote
	public Collegue(Integer matricule, Integer nbVotes) {
		super();
		this.matricule = matricule;
		this.nbVotes = nbVotes;
	}

	public Collegue(String nom) {
		super();
		this.nom = nom;
	}

	public Collegue() {
		super();
	}

	@Id // obligatoire
	private Integer matricule;

	@Column(name = "MOT_DE_PASSE")
	private String motDePasse;

	@Column(name = "NOM")
	private String nom;

	@Column(name = "PRENOMS")
	private String prenoms;

	@Column(name = "PHOTOURL")
	private String photoUrl;
	
	@Column(name="NOMBRE_VOTES")
	private Integer nbVotes;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

	public Integer getMatricule() {
		return matricule;
	}

	public void setMatricule(Integer matricule) {
		this.matricule = matricule;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenoms() {
		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Integer getNbVotes() {
		return nbVotes;
	}

	public void setNbVotes(Integer nbVotes) {
		this.nbVotes = nbVotes;
	}
}
