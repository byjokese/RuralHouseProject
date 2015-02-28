package dataAccess;

import java.io.File;
// import java.util.Enumeration;
// import java.util.Vector;

import java.rmi.RemoteException;
import java.util.Date;
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
	private static DB4oManager theDB4oManager = null;

	private static DB4oManagerAux theDB4oManagerAux;
	private static boolean initialized = false;
	private ControlDB4o controlDb4o;

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
			openControl();
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
			ObjectSet<Object> res = db.queryByExample(DB4oManagerAux.class);
			ListIterator<Object> listIter = res.listIterator();
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
		try {
			addUserToDataBase("ivan", "byjoke", "123", false, "1234-5678-12-123456789");
			addUserToDataBase("bienvenido", "bienve", "12345", true, "9876-5432-10-123456789");
			addUserToDataBase("jose", "ena_795", "123456", true, "4567-98763-25-123456789");

			Users jon = addUserToDataBase("Jon", "Jonlog", "passJon", true, "4567-98763-25-122567891");
			addUserToDataBase("Alfredo", "AlfredoLog", "passAlfredo", true, "1234-5678-12-785478963");
			Users jesus = addUserToDataBase("Jesús", "Jesuslog", "passJesus", true, "1534-5588-32-784778963");
			Users josean = addUserToDataBase("Josean", "JoseanLog", "passJosean", true, "1234-5678-12-788589639");

			storeRuralhouse(((Owner) jon), "jon house", "Ezkio", "Ezkioko Kalea", 2);
			storeRuralhouse(((Owner) jon), "Etxetxikia", "Iruña", "berdin Kalea", 27);
			storeRuralhouse(((Owner) jesus), "Udaletxea", "Bilbo", "Udaletxeko kalea", 1);
			storeRuralhouse(((Owner) josean), "Gaztetxea", "Renteria", "Renteriko kalea", 5);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		db.store(controlDb4o);
		db.commit();
	}

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws RemoteException, Exception {
		try {
			// if (c.isDatabaseLocal()==false) openObjectContainer();

			RuralHouse proto = new RuralHouse(ruralHouse.getHouseNumber(), null, null, null);
			ObjectSet<Object> result = db.queryByExample(proto);
			RuralHouse rh = (RuralHouse) result.next();
			Offer o = rh.createOffer(theDB4oManagerAux.offerNumber++, firstDay, lastDay, price);
			db.store(theDB4oManagerAux); // To store the new value for offerNumber
			db.store(o);
			db.commit();
			return o;
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer " + e.getMessage());
			return null;
		}
	}

	public void deleteDB() {
		try {
			Users proto = new Users(null, null, null, null, null);
			ObjectSet<Object> result = db.queryByExample(proto);
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
			ObjectSet<Object> result = db.queryByExample(proto);
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
			ObjectSet<Object> result = db.queryByExample(proto);
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
			ObjectSet<Object> result = db.queryByExample(proto);
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
			user = new Owner(null, username, password, true, true, null);
			// user = new Owner(null, username, password, true, true);
		} else {
			// user = new Client(null, username, password, true, false);
			user = new Client(null, username, password, true, false, null);
		}
		List<Users> data = db.queryByExample(user);
		return (data.size() == 1) ? data.get(0) : null;
	}

	public void activateAccount(String username, boolean isOwner, String bank) throws RemoteException {
		Users act;
		if (isOwner) {
			act = new Owner(null, username, null, null, true, null);
		} else {
			act = new Client(null, username, null, null, false, null);
		}
		List<Users> data = db.queryByExample(act);
		((Owner) data.get(0)).setBankAccount(bank);
		data.get(0).setActivated(true); // POSIBLE ERROR IF NOT CHECKED PREVIUSLY WITH CHECKLOGIN!!
		db.store(data.get(0));
		db.commit();
	}

	public Users addUserToDataBase(String name, String login, String password, boolean isOwner, String bank) throws RemoteException {
		Users client;
		Users owner;
		if (isOwner) {
			client = new Client(name, login, password, false, false);
			owner = new Owner(name, login, password, true, true, bank);
		} else {
			client = new Client(name, login, password, true, false);
			owner = new Owner(name, login, password, false, true, bank);
		}
		db.store(client);
		db.store(owner);
		db.commit();
		return (isOwner) ? owner : client;
	}

	private boolean checkRural(String city, String address, int number) {
		return db.queryByExample(new RuralHouse(0, null, null, city, address, number)).size() == 0;
	}

	public RuralHouse storeRuralhouse(Owner owner, String description, String city, String address, int number) throws RemoteException {
		if (checkRural(city, address, number)) {
			RuralHouse rh = new RuralHouse(controlDb4o.nextHouseNumber(), owner, description, city, address, number);
			owner.addRuralHouse(rh);
			db.store(rh);
			db.commit();
			//saveControl();
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
		saveControl();
		db.close();
		System.out.println("DataBase closed");
	}
	
	private void openControl(){
		//Open control data
		try{
		controlDb4o =  (ControlDB4o) db.queryByExample(new ControlDB4o(0)).get(0);
		}
		catch (Exception e){
			controlDb4o = new ControlDB4o();
		}
	}
	private void saveControl() {
		ControlDB4o lasthouse = (ControlDB4o) db.queryByExample(new ControlDB4o(0)).get(0);
		lasthouse.setLastHouseNumber(controlDb4o.getLastHouseNumber());
		db.store(lasthouse);
		db.commit();
	}

	public String toString() {
		return "bookingNumber=" + controlDb4o.getBookingNumber() + " offerNumber=" + controlDb4o.getOfferNumber();
	}
}
