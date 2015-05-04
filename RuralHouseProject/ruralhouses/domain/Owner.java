package domain;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class Owner extends Users implements Serializable {

	private String bankAccount = "";
	private Vector<RuralHouse> ruralHouses;
	private Vector<ExtraActivity> extraActivities;
	private int mark;
	private int numVotes;

	public Owner(String name, String username, String password, Boolean activated, Boolean isOwner) {
		super(name, username, password, activated, isOwner);
		this.ruralHouses = new Vector<RuralHouse>();
		this.extraActivities = new Vector<ExtraActivity>();
		this.setMark(3);
		this.numVotes = 1;
	}

	public void setExtraActivities(Vector<ExtraActivity> extraActivities) {
		this.extraActivities = extraActivities;
	}

	public void setRuralHouses(Vector<RuralHouse> ruralHouses) {
		this.ruralHouses = ruralHouses;
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
		this.setMark(3);
		this.numVotes = 1;
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

	public RuralHouse updateRuralHouse(RuralHouse rh) {
		int pos = searchHouse(rh);
		if (pos == -1) {
			return null;
		} else {
			ruralHouses.remove(pos);
			ruralHouses.add(pos, rh);
		}
		return rh;
	}

	public int searchHouse(RuralHouse rh) {
		int i = 0;
		for (RuralHouse rural : ruralHouses) {
			if (rural.getNumber() == rh.getNumber()) {
				return i;
			}
			i++;
		}
		return -1;
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

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = ((this.mark*numVotes)+mark)/(numVotes+1);
	}

}