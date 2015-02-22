package domain;

import java.io.Serializable;

public class Users implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String username = "";
	private String name = "";
	private String password = "";
	private Boolean activated = false;
	private Boolean isOwner;

	public Users(String name, String username, String password,
			Boolean activated, Boolean isOwner) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.activated = activated;
		this.isOwner = isOwner;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}

	public Boolean isOwner() {
		return isOwner;
	}

	public void setOwner() {
		this.isOwner = true;
	}

	public void setClient() {
		this.isOwner = false;
	}

}