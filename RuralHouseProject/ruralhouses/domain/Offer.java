package domain;

import java.io.*;
import java.util.Date;	


@SuppressWarnings("serial")
public class Offer implements Serializable {
	

	private int offerNumber;
	private Date firstDay; // Dates are stored as java.util.Date objects instead of java.sql.Date objects
	private Date lastDay;  // because, they are not well stored in db4o as java.util.Date objects
	private float price;   // This is coherent because objects of java.sql.Date are objects of java.util.Date 
	private Booking booking;  // That is: java.sql.Date is a subclass (or extends) java.util.Date
	private RuralHouse ruralHouse;

	
	public Offer(int offerNumber,RuralHouse ruralHouse, Date firstDay, Date lastDay, float price){
		  this.firstDay=firstDay;
		  this.lastDay=lastDay;
		  this.price=price;
		  this.ruralHouse=ruralHouse;
		  this.offerNumber=offerNumber;
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
	 * @param house number
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
	 * @param First day, last day, house number and telephone
	 * @return a book
	 */
	public Booking createBooking(int numBooking,String bookTelephoneNumber) {
		return booking=new Booking(numBooking,bookTelephoneNumber,this);
			
	}
	
	public String toString(){
		return offerNumber+";"+firstDay.toString()+";"+lastDay.toString()+";"+price+";"+ruralHouse;
	}
}