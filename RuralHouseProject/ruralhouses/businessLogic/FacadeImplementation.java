package businessLogic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.sql.SQLException;
import java.util.Vector;

import dataAccess.DB4oManager;
import domain.Booking;
import domain.Client;
import domain.ExtraActivity;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.Users;
import exceptions.BadDates;
import exceptions.DB4oManagerCreationException;
import exceptions.OfferCanNotBeBooked;
import exceptions.OverlappingOfferExists;

public class FacadeImplementation extends UnicastRemoteObject implements ApplicationFacadeInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Vector<Owner> owners;
	Vector<Client> clients;
	Vector<RuralHouse> ruralHouses;
	DB4oManager dB4oManager;

	public FacadeImplementation() throws RemoteException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			DB4oManagerCreationException {
		owners = null;
		ruralHouses = null;
		try {
			dB4oManager = DB4oManager.getInstance();
		} catch (com.db4o.ext.DatabaseFileLockedException e) {
			System.out.println("Error in FacadeImplementation: " + e.toString());
			throw e;
		} catch (Exception e) {
			System.out.println("Error in FacadeImplementation: " + e.toString());
			throw new DB4oManagerCreationException();
		}
	}

	/**
	 * This method creates an offer with a house number, first day, last day and price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return the created offer, or null, or an exception
	 */
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws OverlappingOfferExists, BadDates, RemoteException,
			Exception {
		if (firstDay.compareTo(lastDay) >= 0)
			throw new BadDates();
		ruralHouses = null;
		owners = null;
		boolean b = dB4oManager.existsOverlappingOffer(ruralHouse, firstDay, lastDay); // The ruralHouse object in the client may not be updated
		if (!b)
			return dB4oManager.createOffer(ruralHouse, firstDay, lastDay, price);
		return null;
	}

	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */
	public Booking createBooking(RuralHouse ruralHouse, Date firstDate, Date lastDate, String bookTelephoneNumber) throws OfferCanNotBeBooked {
		ruralHouses = null;
		owners = null;
		return dB4oManager.createBooking(ruralHouse, firstDate, lastDate, bookTelephoneNumber);
	}

	/**
	 * This method existing owners
	 * 
	 */
	public Vector<Owner> getOwners() throws RemoteException, Exception {

		if (owners != null) {
			System.out.println("Owners obtained directly from business logic layer");
			return owners;
		} else
			return owners = dB4oManager.getOwners();

	}

	public Vector<RuralHouse> getAllRuralHouses() throws RemoteException, Exception {
		if (ruralHouses != null) {
			System.out.println("RuralHouses obtained directly from business logic layer");
			return ruralHouses;
		} else
			return ruralHouses = dB4oManager.getAllRuralHouses();
	}

	public Users checkLogin(String username, String password, boolean isOwner) throws RemoteException {
		return dB4oManager.checkLogin(username, password, isOwner);
	}



	public Users addUserToDataBase(String name, String login, String password, boolean isOwner, String BankAccount) throws RemoteException {
		return dB4oManager.addUserToDataBase(name, login, password, isOwner, BankAccount);
	}

	public void close() throws RemoteException {
		dB4oManager.close();

	}

	@Override
	public boolean checkUserAvailability(String username) throws RemoteException {
		return dB4oManager.checkUserAvailability(username);

	}

	public RuralHouse storeRuralhouse(int houseNumber, Owner owner, String description, String city, String address, int aumber) throws RemoteException {
		try {
			return dB4oManager.storeRuralhouse(houseNumber, owner, description, city, address, aumber);
		} catch (Exception e) {
			System.out.println("Error at storeRuralhouse raised at Facadeimplementation: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		
	}

	public void activateAccount(String username, boolean isOwner, String bank) throws RemoteException {
		dB4oManager.activateAccount(username, isOwner, bank);
	}

	public ExtraActivity storeExtraActivity(Owner owner, String nombre, String lugar, Date fecha, String description) throws RemoteException {
		return dB4oManager.storeExtraActivity(owner, nombre, lugar, fecha, description);
	}

	@SuppressWarnings({ "null", "unused" })
	public List<List<Offer>> searchAvailableOffers(String city, String numberOfNights, Date date, int minPrice, int maxPrice) throws RemoteException {
		/**Offers comes with an previous applied filter of starting date. will never show a offer previous to the given date of start.**/
		List<Offer> offers = dB4oManager.searchEngine(city, date);
		List<List<Offer>> allAvailableOffers = null;
		List<Offer> requestedOffers = null;
		List<Offer> possibleOffers = null;
		for (Offer offer : offers) {
			if (offer.getRuralHouse().getCity().equalsIgnoreCase(city)) {	//City Filter
				if (offer.getPrice() > minPrice && offer.getPrice() < maxPrice) {	//Price Filter
					requestedOffers.add(offer);
				} else if (offer.getPrice() < minPrice) {
					possibleOffers.add(offer);
				}
			}
		}
		if (requestedOffers != null)
			allAvailableOffers.add(requestedOffers);
		if (possibleOffers != null)
			allAvailableOffers.add(possibleOffers);
		return allAvailableOffers;
	}


	

	public Offer StoreOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price,ArrayList<ExtraActivity> ExtraActi) throws RemoteException{
		
		return dB4oManager.StoreOffer(ruralHouse, firstDay, lastDay, price, ExtraActi);
	}

}
