package domain;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class Owner extends Users implements Serializable {

	private String bankAccount = "";
	private Vector<RuralHouse> ruralHouses;
	private Vector<ExtraActivity> extraActivities;

	public Owner(String name, String username, String password, Boolean activated, Boolean isOwner) {
		super(name, username, password, activated, isOwner);
		ruralHouses = new Vector<RuralHouse>();
		extraActivities = new Vector<ExtraActivity>();
	}

	public Vector<ExtraActivity> getExtraActivities() {
		return extraActivities;
	}

	public void addExtraActivities(ExtraActivity extraActivity) {
		extraActivities.addElement(extraActivity);
	}

	public Owner(String name, String username, String password, Boolean activated, Boolean isOwner, String bankAccount) {
		super(name, username, password, activated, isOwner);
		this.bankAccount = bankAccount;
		this.ruralHouses = new Vector<RuralHouse>();
		this.extraActivities = new Vector<ExtraActivity>();
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

	public RuralHouse updateRuralHouse(RuralHouse rh, int index) {
		ruralHouses.remove(index);
		ruralHouses.add(index, rh);
		return rh;
	}

	public boolean deleteRuralHouse(int index) {
		ruralHouses.remove(index);
		return true;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public ExtraActivity updateExtraActivity(ExtraActivity ex, int index){
		extraActivities.remove(index);
		extraActivities.add(index, ex);
		return ex;
	}
	
	public boolean deleteExtraActivity(int index){
		extraActivities.remove(index);
		return true;
	}

}