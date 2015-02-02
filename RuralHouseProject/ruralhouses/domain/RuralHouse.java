package domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Iterator;
import java.util.Vector;

public class RuralHouse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int houseNumber;
	private String description;
	private Owner owner;
	private String city; 
	public Vector<Offer> offers;
	



	public RuralHouse() {
		super();
	}

	public RuralHouse(int houseNumber, Owner owner, String description, String city) {
		this.houseNumber = houseNumber;
		this.description = description;
		this.owner = owner;
		this.city = city;
		offers=new Vector<Offer>();
	}

	public int getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(int houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner=owner;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city=city;
	}

	
	public String toString() {
		return this.houseNumber + ": " + this.city;
	}
	
	/**
	 * This method creates an offer with a house number, first day, last day and price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */
	public Offer createOffer(int offerNumber,Date firstDay, Date lastDay, float price)  {
        Offer off=new Offer(offerNumber,this,firstDay,lastDay,price);
        offers.add(off);
        return off;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + houseNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuralHouse other = (RuralHouse) obj;
		if (houseNumber != other.houseNumber)
			return false;
		return true;
	}
	
	
	/**
	 * This method obtains the account number of the owner of the  house number
	 * 
	 * @param houseNumber
	 *            Number of the house
	 * @return Owner account number of the house
	 */
	public String getAccountNumber(int houseNumber) {
		/*try {
			dbMngr=DBManager.getInstance();
			return dbMngr.getOwner(houseNumber).getBankAccount();

		} catch (Exception e) {
			System.out.println("Error, accessing to DB Manager: "
					+ e.toString());
			return null;
		}*/ return null;
	}
	
	/**
	 * This method obtains available offers for a concrete house in a certain period 
	 * 
	 * @param houseNumber, the house number where the offers must be obtained 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return a vector of offers(Offer class)  available  in this period
	 */
	public Vector<Offer> getOffers( Date firstDay,  Date lastDay) {
		
		Vector<Offer> availableOffers=new Vector<Offer>();
		Iterator<Offer> e=offers.iterator();
		Offer offer;
		while (e.hasNext()){
			offer=e.next();
			if ( (offer.getFirstDay().compareTo(firstDay)>=0) && (offer.getLastDay().compareTo(lastDay)<=0) && (offer.getBooking()==null) )
				availableOffers.add(offer);
		}
		return availableOffers;
		
	}
	
	/**
	 * This method obtains the offer that match exactly with a given dates that has not been booked
	 * 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return the  offer(Offer class)  available  for a this period
	 */
	public Offer findOffer( Date firstDay,  Date lastDay) {
		
		Iterator<Offer> e=offers.iterator();
		Offer offer=null;
		while (e.hasNext()){
			offer=e.next();
			if ( (offer.getFirstDay().compareTo(firstDay)==0) && (offer.getLastDay().compareTo(lastDay)==0) && (offer.getBooking()==null) )
				return offer;
		}
		return null;
		
	}

	/**
	 * This method obtains the first offer that overlaps with the provided dates
	 * 
	 * @param firstDay, first day in a period range 
	 * @param lastDay, last day in a period range
	 * @return the first offer that overlaps with those dates, or null if there is no overlapping offer
	 */

	public Offer overlapsWith( Date firstDay,  Date lastDay) {
		
		Iterator<Offer> e=offers.iterator();
		Offer offer=null;
		while (e.hasNext()){
			offer=e.next();
			if ( (offer.getFirstDay().compareTo(lastDay)<0) && (offer.getLastDay().compareTo(firstDay)>0))
				return offer;
		}
		return null;
		
	}

}
