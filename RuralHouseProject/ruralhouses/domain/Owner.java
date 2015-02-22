package domain;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class Owner extends Users implements Serializable {

	private String bankAccount = "";
	private Vector<RuralHouse> ruralHouses;

	public Owner(String name, String username, String password,
			Boolean activated, Boolean isOwner) {
		super(name, username, password, activated, isOwner);
		ruralHouses = new Vector<RuralHouse>();
	}

	public Owner(String name, String username, String password,	Boolean activated, Boolean isOwner, String bankAccount,
			Vector<RuralHouse> ruralHouses) {
		super(name, username, password, activated, isOwner);
		this.bankAccount = bankAccount;
		this.ruralHouses = ruralHouses;
	}

	/**
	 * This method obtains an owner's(userId) rural houses
	 * 
	 * @param userId
	 *            user key
	 * @return a vector of Rural Houses
	 */

	public Vector<RuralHouse> getRuralHouses() {
		return ruralHouses;
	}

	public RuralHouse addRuralHouse(int houseNumber, String description,
			String city) {
		RuralHouse rh = new RuralHouse(houseNumber, this, description, city);
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