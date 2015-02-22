package domain;

import java.io.Serializable;
import java.util.Vector;

public class Client extends Users implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Vector<Booking> offers;

	public Client(String name, String username, String password,
			Boolean activated, Boolean isOwner, Vector<Booking> offers) {
		super(name, username, password, activated, isOwner);
		this.offers = offers;
	}


	public Client(String name, String username, String password,
			Boolean activated, Boolean isOwner) {
		super(name, username, password, activated, isOwner);
		offers = new Vector<Booking>();
	}


	public Vector<Booking> getOffers() {
		return this.offers;
	}

	public void addBook(int bookingNumber, String telephone, Offer offer) {
		Booking book = new Booking(bookingNumber, telephone, offer);
		offers.add(book);
	}

}
