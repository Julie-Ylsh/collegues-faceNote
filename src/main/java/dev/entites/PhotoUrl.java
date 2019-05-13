package dev.entites;

public class PhotoUrl {
	public PhotoUrl() {
	}
	
	public PhotoUrl(String photoUrl) {
		super();
		this.photoUrl = photoUrl;
	}

	String photoUrl;

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
