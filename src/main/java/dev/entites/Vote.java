package dev.entites;

public class Vote {

	public Vote(Integer matriculeVotant, Integer matriculeCollegue, ChoixVote choixVote) {
		super();
		this.matriculeVotant = matriculeVotant;
		this.matriculeCollegue = matriculeCollegue;
		this.choixVote = choixVote;
	}

	public Vote() {
		super();
	}

	private Integer matriculeVotant;
	private Integer matriculeCollegue;
	private ChoixVote choixVote;

	public enum ChoixVote {
		YES, NO
	};

	public Integer getMatriculeVotant() {
		return matriculeVotant;
	}

	public void setMatriculeVotant(Integer matriculeVotant) {
		this.matriculeVotant = matriculeVotant;
	}

	public Integer getMatriculeCollegue() {
		return matriculeCollegue;
	}

	public void setMatriculeCollegue(Integer matriculeCollegue) {
		this.matriculeCollegue = matriculeCollegue;
	}

	public ChoixVote getChoixVote() {
		return choixVote;
	}

	public void setChoixVote(ChoixVote choixVote) {
		this.choixVote = choixVote;
	}

}
