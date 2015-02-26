package dataAccess;

import java.io.File;
// import java.util.Enumeration;
// import java.util.Vector;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import com.db4o.*;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import configuration.ConfigXML;
import domain.Booking;
import domain.Client;
import domain.Offer;
// import dataModel.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.Users;
import exceptions.OfferCanNotBeBooked;
import exceptions.OverlappingOfferExists;

public class DB4oManager {

	private static ObjectContainer db;
	private static EmbeddedConfiguration configuration;
	private static ClientConfiguration configurationCS;
	private int bookingNumber = 0; // if it is "static" then it is not
									// serialized
	private int offerNumber = 0; // if it is "static" then it is not serialized
	private static DB4oManager theDB4oManager = null;

	private static DB4oManagerAux theDB4oManagerAux;
	private static boolean initialized = false;
	ConfigXML c;

	private DB4oManager() throws Exception {
		theDB4oManagerAux = new DB4oManagerAux(0, 0);
		c = ConfigXML.getInstance();
		System.out.println("Creating DB4oManager instance => isDatabaseLocal: " + c.isDatabaseLocal() + " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		if ((c.getDataBaseOpenMode().equals("initialize")) && (c.isDatabaseLocal()))
			new File(c.getDb4oFilename()).delete();

		if (c.isDatabaseLocal()) {
			configuration = Db4oEmbedded.newConfiguration();
			configuration.common().activationDepth(c.getActivationDepth());
			configuration.common().updateDepth(c.getUpdateDepth());
			db = Db4oEmbedded.openFile(configuration, c.getDb4oFilename());
			System.out.println("DataBase opened");
		} else // c.isDatabaseLocal==false
		{
			openObjectContainer();
		}
		if (c.getDataBaseOpenMode().equals("initialize")) {
			/** ---------------------------------------------------------------------------------------------------- **/
			initializeDB();
			System.out.println("DataBase initialized");
			initialized = true;
		} else // c.getDataBaseOpenMode().equals("open")

		{
			ObjectSet res = db.queryByExample(DB4oManagerAux.class);
			ListIterator listIter = res.listIterator();
			if (listIter.hasNext())
				theDB4oManagerAux = (DB4oManagerAux) res.next();
		}
	}

	public boolean isinitialized() {
		return initialized;
	}

	private void openObjectContainer() {

		configurationCS = Db4oClientServer.newClientConfiguration();
		configurationCS.common().activationDepth(c.getActivationDepth());
		configurationCS.common().updateDepth(c.getUpdateDepth());
		configurationCS.common().objectClass(Owner.class).cascadeOnDelete(true);
		db = Db4oClientServer.openClient(configurationCS, c.getDatabaseNode(), c.getDatabasePort(), c.getUser(), c.getPassword());

	}

	class DB4oManagerAux {
		int bookingNumber;
		int offerNumber;

		DB4oManagerAux(int bookingNumber, int offerNumber) {
			this.bookingNumber = bookingNumber;
			this.offerNumber = offerNumber;
		}
	}

	public static DB4oManager getInstance() throws Exception {
		if (theDB4oManager == null)
			theDB4oManager = new DB4oManager();
		return theDB4oManager;
	}

	public void initializeDB() {
		Users jon = new Owner("Jon", "Jonlog", "passJon", true, true);

		try {
			addUserToDataBase("ivan", "byjoke", "123", false);
			addUserToDataBase("bienvenido", "bienve", "12345", true);
			addUserToDataBase("jose", "ena_795", "123456", true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		Users alfredo = new Owner("Alfredo", "AlfredoLog", "passAlfredo", true, true);
		Users jesus = new Owner("Jes�s", "Jesuslog", "passJesus", true, true);
		Users josean = new Owner("Josean", "JoseanLog", "passJosean", true, true);

		((Owner) jon).addRuralHouse(1, "Ezkioko etxea", "Ezkio");
		((Owner) jon).addRuralHouse(2, "Etxetxikia", "Iru�a");
		((Owner) jesus).addRuralHouse(3, "Udaletxea", "Bilbo");
		((Owner) josean).addRuralHouse(4, "Gaztetxea", "Renteria");

		((Owner) jon).setBankAccount("1234567812785478963");
		((Owner) alfredo).setBankAccount("1234567812785478963");
		((Owner) jesus).setBankAccount("123456781278554154963");
		((Owner) josean).setBankAccount("1234567812785478963");

		db.store(jon);
		db.store(alfredo);
		db.store(jesus);
		db.store(josean);

		db.commit();
	}

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws RemoteException, Exception {

		try {

			// if (c.isDatabaseLocal()==false) openObjectContainer();

			RuralHouse proto = new RuralHouse(ruralHouse.getHouseNumber(), null, null, null);
			ObjectSet result = db.queryByExample(proto);
			RuralHouse rh = (RuralHouse) result.next();
			Offer o = rh.createOffer(theDB4oManagerAux.offerNumber++, firstDay, lastDay, price);
			db.store(theDB4oManagerAux); // To store the new value for
											// offerNumber
			db.store(o);
			db.commit();
			return o;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public void deleteDB() {
		try {
			Users proto = new Users(null, null, null, null, null);
			ObjectSet result = db.queryByExample(proto);
			Vector<Users> owners = new Vector<Users>();
			while (result.hasNext()) {
				Users o = (Owner) result.next();
				System.out.println("Deleted owner: " + o.toString());
				db.delete(o);
			}
			db.commit();
		} finally {
			// db.close();
		}
	}

	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */
	public Booking createBooking(RuralHouse ruralHouse, Date firstDate, Date lastDate, String bookTelephoneNumber) throws OfferCanNotBeBooked {

		try {

			// if (c.isDatabaseLocal()==false) openObjectContainer();

			RuralHouse proto = new RuralHouse(ruralHouse.getHouseNumber(), null, ruralHouse.getDescription(), ruralHouse.getCity());
			ObjectSet result = db.queryByExample(proto);
			RuralHouse rh = (RuralHouse) result.next();

			Offer offer;
			offer = rh.findOffer(firstDate, lastDate);

			if (offer != null) {
				offer.createBooking(theDB4oManagerAux.bookingNumber++, bookTelephoneNumber);
				db.store(theDB4oManagerAux); // To store the new value for
												// bookingNumber
				db.store(offer);
				db.commit();
				return offer.getBooking();
			}
			return null;

		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out.println("Error: com.db4o.ext.ObjectNotStorableException in createBooking " + e.getMessage());
			return null;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	/**
	 * This method existing owners
	 * 
	 */
	public Vector<Owner> getOwners() throws RemoteException, Exception {

		// if (c.isDatabaseLocal()==false) openObjectContainer();
		// Se cogen todos los Users activados
		try {
			Users proto = new Owner(null, null, null, true, null);
			ObjectSet result = db.queryByExample(proto);
			/*
			 * proto = new Owner(null, null, null, false, null); result.addAll(db.queryByExample(proto));
			 */
			Vector<Owner> owners = new Vector<Owner>();
			while (result.hasNext())
				owners.add((Owner) result.next());
			return owners;
		} finally {
			// db.close();
		}
	}

	public Vector<RuralHouse> getAllRuralHouses() throws RemoteException, Exception {

		// if (c.isDatabaseLocal()==false) openObjectContainer();

		try {
			RuralHouse proto = new RuralHouse(0, null, null, null);
			ObjectSet result = db.queryByExample(proto);
			Vector<RuralHouse> ruralHouses = new Vector<RuralHouse>();
			while (result.hasNext())
				ruralHouses.add((RuralHouse) result.next());
			return ruralHouses;
		} finally {
			// db.close();
		}
	}

	public boolean checkUserAvailability(String username) throws RemoteException {
		Users user = new Client(null, username, null, null, null);
		return (db.queryByExample(user).size() == 0);
	}

	public Users checkLogin(String username, String password, boolean isOwner) throws RemoteException {
		Users user;
		if (isOwner) {
			user = new Owner(null, username, password, true, true);
		} else {
			user = new Client(null, username, password, true, false);
		}
		List<Users> data = db.queryByExample(user);
		return (data.size() == 1) ? data.get(0) : null;
	}

	public void activateAccount(String username, boolean isOwner, String bank) throws RemoteException {
		Users act;
		if (isOwner) {
			act = new Owner(null, username, null, null, true);
		} else {
			act = new Client(null, username, null, null, false);
		}
		List<Users> data = db.queryByExample(act);
		if(isOwner){
			((Owner) data.get(0)).setBankAccount(bank);
		}
		data.get(0).setActivated(true);
		db.store(data.get(0));
		db.commit();
	}

	public Users addUserToDataBase(String name, String login, String password, boolean isOwner) throws RemoteException {
		Users client;
		Users owner;
		if (isOwner) {
			client = new Client(name, login, password, false, false);
			owner = new Owner(name, login, password, true, true);
		} else {
			client = new Client(name, login, password, true, false);
			owner = new Owner(name, login, password, false, true);
		}
		db.store(client);
		db.store(owner);
		db.commit();
		return (isOwner) ? owner : client;

	}

	private boolean checkRural(String city, String address, int aumber) {
		return db.queryByExample(new RuralHouse(0, null, null, city, address, aumber)).size() == 0;
	}

	public RuralHouse storeRuralhouse(int houseNumber, Owner owner, String description, String city, String address, int aumber) throws RemoteException {
		RuralHouse rh = new RuralHouse(houseNumber, owner, description, city, address, aumber);
		if (checkRural(city, address, aumber)) {
			db.store(rh);
			db.commit();
			owner.addRuralHouse(houseNumber, description, city, address, aumber);
			return rh;
		} else {
			return null;
		}
	}

	public boolean existsOverlappingOffer(RuralHouse rh, Date firstDay, Date lastDay) throws RemoteException, OverlappingOfferExists {
		try {
			// if (c.isDatabaseLocal()==false) openObjectContainer();

			RuralHouse rhn = (RuralHouse) db.queryByExample(new RuralHouse(rh.getHouseNumber(), null, null, null)).next();
			if (rhn.overlapsWith(firstDay, lastDay) != null)
				throw new OverlappingOfferExists();
			else
				return false;
		} finally {
			// db.close();
		}
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public String toString() {
		return "bookingNumber=" + bookingNumber + " offerNumber=" + offerNumber;
	}
}
