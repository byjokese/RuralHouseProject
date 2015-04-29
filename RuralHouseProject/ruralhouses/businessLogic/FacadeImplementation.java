package businessLogic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import exceptions.OverlappingOfferExists;

public class FacadeImplementation extends UnicastRemoteObject implements ApplicationFacadeInterface {

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

	public boolean existsOverlappingOffer(RuralHouse rh, Date firstDay, Date lastDay) throws RemoteException, OverlappingOfferExists {
		return dB4oManager.existsOverlappingOffer(rh, firstDay, lastDay);
	}

	public Booking bookOffer(Users user, String telephone, Offer offer) throws RemoteException {
		return dB4oManager.bookOffer(user, telephone, offer);
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

	public RuralHouse storeRuralhouse(Owner owner, String description, String city, String address, int aumber) throws RemoteException {
		try {
			return dB4oManager.storeRuralhouse(owner, description, city, address, aumber);
		} catch (Exception e) {
			System.out.println("Error at storeRuralhouse raised at Facadeimplementation: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	public RuralHouse updateRuralHouse(RuralHouse rh, Owner owner, String description, int mark, List<String[]> comments) throws RemoteException {
		return dB4oManager.updateRuralHouse(rh, owner, description, mark, comments);
	}

	public boolean deleteRuralHouse(RuralHouse rh, Owner owner, int index) throws RemoteException {
		return dB4oManager.deleteRuralHouse(rh, owner, index);
	}

	public void activateAccount(String username, boolean isOwner, String bank) throws RemoteException {
		dB4oManager.activateAccount(username, isOwner, bank);
	}

	public ExtraActivity storeExtraActivity(Owner owner, String nombre, String lugar, Date fecha, String description) throws RemoteException {
		return dB4oManager.storeExtraActivity(owner, nombre, lugar, fecha, description);
	}

	public Offer updateOffer(Offer o, RuralHouse rh, float price, Date firstDay, Date lastDay, Vector<ExtraActivity> vectorlistSeleccion)
			throws RemoteException {
		return dB4oManager.updateOffer(o, rh, price, firstDay, lastDay, vectorlistSeleccion);
	}

	public boolean deleteOffer(Offer o) throws RemoteException {
		return dB4oManager.deleteOffer(o);
	}

	public List<List<Offer>> searchAvailableOffers(String city, String numberOfNights, Date date, int minPrice, int maxPrice) throws RemoteException {
		/** Offers comes with an previous applied filter of starting date. will never show a offer previous to the given date of start. **/
		// Calculation of the last date of the search
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // date of begin
		calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt(numberOfNights)); // we operate the add begin + numberOfNights
		Date lastDate = calendar.getTime(); // Get back of the time
		// Search and filters
		List<Offer> offers = dB4oManager.searchEngine(date);
		List<List<Offer>> allAvailableOffers = new Vector<>();
		List<Offer> requestedOffers = new Vector<Offer>();
		List<Offer> possibleOffers = new Vector<Offer>();
		for (Offer offer : offers) {
			if (offer.getRuralHouse().getCity().equalsIgnoreCase(city)) { // City Filter
				if (offer.getPrice() >= minPrice && offer.getPrice() <= maxPrice && lastDate.compareTo(offer.getLastDay()) == 0) { // Price & last date filter
					requestedOffers.add(offer);
				} else if (offer.getPrice() < minPrice || lastDate.compareTo(offer.getLastDay()) != 0 || lastDate.compareTo(offer.getLastDay()) == 0) {
					possibleOffers.add(offer);
				}
			}
		}
		allAvailableOffers.add(requestedOffers);
		allAvailableOffers.add(possibleOffers);
		return allAvailableOffers;
	}

	public Owner updateOwner(Owner owner, String bankAccount, Vector<RuralHouse> ruralHouses, Vector<ExtraActivity> extraActivities, int mark)
			throws RemoteException {
		return dB4oManager.updateOwner(owner, bankAccount, ruralHouses, extraActivities, mark);
	}

	public Offer storeOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price, ArrayList<ExtraActivity> ExtraActi) throws RemoteException {
		return dB4oManager.storeOffer(ruralHouse, firstDay, lastDay, price, ExtraActi);
	}

	public boolean qualify(int ownerMark, int houseMark, boolean isAnonmous, String comment, String name, Booking book) throws RemoteException {
		book.getOffer().getRuralHouse().addComments(comment, (isAnonmous) ? "Anonimous" : name);
		dB4oManager.updateRuralHouse(book.getOffer().getRuralHouse(), book.getOffer().getRuralHouse().getOwner(), book.getOffer().getRuralHouse()
				.getDescription(), houseMark, book.getOffer().getRuralHouse().getComments());
		dB4oManager.updateOwner(book.getOffer().getRuralHouse().getOwner(), book.getOffer().getRuralHouse().getOwner().getBankAccount(), book.getOffer()
				.getRuralHouse().getOwner().getRuralHouses(), book.getOffer().getRuralHouse().getOwner().getExtraActivities(), ownerMark);
		return true;
	}
}
