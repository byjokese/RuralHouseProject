package dataAccess;

public class ControlDB4o {
	private int lastHouseNumber;
	private int bookingNumber; // if it is "static" then it is not serialized
	private int offerNumber; // if it is "static" then it is not serialized

	public ControlDB4o() {
		lastHouseNumber = 0;
		bookingNumber = 0;
		offerNumber = 0;
	}

	public ControlDB4o(int lastHouseNumber) {
		super();
		this.lastHouseNumber = lastHouseNumber;
	}

	public int getLastHouseNumber() {
		return lastHouseNumber;
	}

	public void setLastHouseNumber(int lastHouseNumber) {
		this.lastHouseNumber = lastHouseNumber;
	}

	public int nextHouseNumber() {		
		return lastHouseNumber += 1;
	}

	public int getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(int bookingNumber) {
		this.bookingNumber = bookingNumber;
	}

	public int getOfferNumber() {
		return offerNumber;
	}

	public void setOfferNumber(int offerNumber) {
		this.offerNumber = offerNumber;
	}

}
