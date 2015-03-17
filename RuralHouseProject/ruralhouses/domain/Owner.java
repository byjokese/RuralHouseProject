package domain;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class Owner extends Users implements Serializable {

	private String bankAccount = "";
	private Vector<RuralHouse> ruralHouses;
	private Vector<ExtraActivity> ExtraActivities;
	public Owner(String name, String username, String password, Boolean activated, Boolean isOwner) {
		super(name, username, password, activated, isOwner);
		ruralHouses = new Vector<RuralHouse>();
		ExtraActivities = new Vector<ExtraActivity>();
	}
	
	public Vector<ExtraActivity> getExtraActivities() {
		return ExtraActivities;
	}

	public void setExtraActivities(Vector<ExtraActivity> extraActivities) {
		ExtraActivities = extraActivities;
	}

	public Owner(String name, String username, String password, Boolean activated, Boolean isOwner, String bankAccount) {
		super(name, username, password, activated, isOwner);
		this.bankAccount = bankAccount;
		this.ruralHouses = new Vector<RuralHouse>();
		this.ExtraActivities = new Vector<ExtraActivity>();
	}

	public Vector<RuralHouse> getRuralHouses() {
		return ruralHouses;
	}

	public RuralHouse addRuralHouse(int houseNumber, String description, String city, String address, int number) {
		RuralHouse rh = new RuralHouse(houseNumber, this, description, city, address, number);
		ruralHouses.add(rh);
		return rh;
	}

	public RuralHouse addRuralHouse(RuralHouse rh) {
		ruralHouses.add(rh);
		return rh;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

}