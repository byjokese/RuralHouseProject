package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

@SuppressWarnings("serial")
public class Offer implements Serializable {

	private int offerNumber;
	private Date firstDay; // Dates are stored as java.util.Date objects instead of java.sql.Date objects
	private Date lastDay; // because, they are not well stored in db4o as java.util.Date objects
	private float price; // This is coherent because objects of java.sql.Date are objects of java.util.Date
	private Booking booking; // That is: java.sql.Date is a subclass (or extends) java.util.Date
	private RuralHouse ruralHouse;
	private Vector<ExtraActivity> extraActivities;
	private Vector<ExtraActivity> reservedActivities;

	private boolean isReserved;

	public Offer(int offerNumber, RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) {
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.isReserved = false;
		this.price = price;
		this.ruralHouse = ruralHouse;
		this.offerNumber = offerNumber;
		extraActivities = new Vector<ExtraActivity>();
	}

	public Offer(int offerNumber, RuralHouse ruralHouse, Date firstDay, Date lastDay, float price, ArrayList<ExtraActivity> ExtraActi) {
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.price = price;
		this.isReserved = false;
		this.ruralHouse = ruralHouse;
		this.offerNumber = offerNumber;
		extraActivities = new Vector<ExtraActivity>();
		for (ExtraActivity act : ExtraActi) {
			extraActivities.add(act);
		}
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean choosed) {
		this.isReserved = choosed;
	}

	public Vector<ExtraActivity> getExtraActivities() {
		return extraActivities;
	}

	public void setExtraActivities(Vector<ExtraActivity> extraActivities) {
		this.extraActivities = extraActivities;
	}

	/**
	 * Get the house number of the offer
	 * 
	 * @return the house number
	 */
	public RuralHouse getRuralHouse() {
		return this.ruralHouse;
	}

	/**
	 * Set the house number to a offer
	 * 
	 * @param house
	 *            number
	 */
	public void setRuralHouse(RuralHouse ruralHouse) {
		this.ruralHouse = ruralHouse;
	}

	/**
	 * Get the offer number
	 * 
	 * @return offer number
	 */
	public int getOfferNumber() {
		return this.offerNumber;
	}

	/**
	 * Get the first day of the offer
	 * 
	 * @return the first day
	 */
	public Date getFirstDay() {
		return this.firstDay;
	}

	/**
	 * Set the first day of the offer
	 * 
	 * @param firstDay
	 *            The first day
	 */
	public void setFirstDay(Date firstDay) {
		this.firstDay = firstDay;
	}

	/**
	 * Get the last day of the offer
	 * 
	 * @return the last day
	 */
	public Date getLastDay() {
		return this.lastDay;
	}

	/**
	 * asign offers numbers
	 * 
	 * @param offerNumber
	 */
	public void setOfferNumber(int offerNumber) {
		this.offerNumber = offerNumber;
	}

	/**
	 * Set the last day of the offer
	 * 
	 * @param lastDay
	 *            The last day
	 */
	public void setLastDay(Date lastDay) {
		this.lastDay = lastDay;
	}

	/**
	 * Get the price
	 * 
	 * @return price
	 */
	public float getPrice() {
		return this.price;
	}

	/**
	 * Set the price
	 * 
	 * @param price
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * Get the book number
	 * 
	 * @return book object
	 */
	public Booking getBooking() {
		return this.booking;
	}

	/**
	 * Set the book object
	 * 
	 * @param book
	 *            Book object
	 * @return None
	 */
	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */
	public Booking createBooking(int numBooking, String bookTelephoneNumber, String email) {
		Date date = new Date(System.currentTimeMillis());
		return booking = new Booking(numBooking, bookTelephoneNumber, this, date, email);

	}

	public String toString() {
		return offerNumber + " " + firstDay.toString().substring(0, 10) + " -> " + lastDay.toString().substring(0, 10) + " " + price;
	}

	public Vector<ExtraActivity> getReservedActivities() {
		return reservedActivities;
	}

	public void setReservedActivities(Vector<ExtraActivity> reservedActivities) {
		this.reservedActivities = reservedActivities;
	}
	
	public boolean cancelBooking(){
		this.booking = null;
		this.isReserved = false;
		return true;
	}
	
}