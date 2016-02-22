package dataAccess;

import java.io.File;
// import java.util.Enumeration;
// import java.util.Vector;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

import configuration.ConfigXML;
import domain.Booking;
import domain.Client;
import domain.ExtraActivity;
import domain.Offer;
// import dataModel.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.Users;
import exceptions.OverlappingOfferExists;

public class DB4oManager {

	private static ObjectContainer db;
	private static EmbeddedConfiguration configuration;
	private static ClientConfiguration configurationCS;
	private static DB4oManager theDB4oManager = null;

	private static ControlDB4o theDB4oManagerAux;
	private static boolean initialized = false;
	ConfigXML c;

	private DB4oManager() throws Exception {
		theDB4oManagerAux = new ControlDB4o();

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
			ObjectSet<Object> res = db.queryByExample(DB4oManagerAux.class);
			ListIterator<Object> listIter = res.listIterator();
			if (listIter.hasNext())
				theDB4oManagerAux = (ControlDB4o) res.next();
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

	@SuppressWarnings({ "deprecation" })
	public void initializeDB() {
		RuralHouse rhB1 = null;
		RuralHouse rjB2 = null;
		RuralHouse rhJ = null;
		RuralHouse rhJe = null;
		try {
			addUserToDataBase("ivan", "byjoke", "123", false, "1234-5678-12-123456789");
			Users bienve = addUserToDataBase("bienvenido", "bienve", "12345", true, "9876-5432-10-123456789");
			addUserToDataBase("jose", "ena_795", "123456", true, "4567-98763-25-123456789");

			Users jon = addUserToDataBase("Jon", "Jonlog", "passJon", true, "4567-98763-25-122567891");
			addUserToDataBase("Alfredo", "AlfredoLog", "passAlfredo", true, "1234-5678-12-785478963");
			Users jesus = addUserToDataBase("Jesus", "Jesuslog", "passJesus", true, "1534-5588-32-784778963");
			Users josean = addUserToDataBase("Josean", "JoseanLog", "passJosean", true, "1234-5678-12-788589639");

			try {
				rhJ = storeRuralhouse(((Owner) jon), "jon house", "Tolosa", "Tolosa2 Kalea", 2);
				storeRuralhouse(((Owner) jon), "Etxetxikia", "Iruña", "berdin Kalea", 21);
				rhJe = storeRuralhouse(((Owner) jesus), "Udaletxea", "Tolosa", "Udaletxeko kalea", 1);
				storeRuralhouse(((Owner) josean), "Gaztetxea", "Renteria", "Renteriko kalea", 5);
				rhB1 = storeRuralhouse(((Owner) bienve), "CasaBienve", "Tolosa", "Tolosa Kalea", 27);
				rjB2 = storeRuralhouse((Owner) bienve, "Another house", "tolosa", "7th street", 7);
			} catch (Exception e) {
				System.out.println("Error at initialize DataBase on: ./storeRuralHouses " + e.getMessage());
			}
			ArrayList<ExtraActivity> activities = new ArrayList<ExtraActivity>();
			activities.add(storeExtraActivity((Owner) bienve, "Montar a caballo", "Tolosa2 Kalea", new Date(2015, 7, 31), "Actividad para montar a caballo"));
			activities.add(storeExtraActivity((Owner) bienve, "Montar a bici", "kale", new Date(2015, 7, 3), "Actividad para montar en bicicleta"));
			activities.add(storeExtraActivity((Owner) bienve, "Fiesta", "fiestakalea", new Date(2015, 7, 4), "fiesta de tolosa"));
			ArrayList<ExtraActivity> activitiesA = new ArrayList<ExtraActivity>();
			activitiesA.add(storeExtraActivity((Owner) jon, "Montar a caballo", "Tolosa Kalea", new Date(2015, 7, 7), "Actividad para montar a caballo"));
			activitiesA.add(storeExtraActivity((Owner) jon, "Fiesta", "fiestakalea", new Date(2015, 7, 8), "fiesta de tolosa"));
			ArrayList<ExtraActivity> activitiesB = new ArrayList<ExtraActivity>();
			activitiesB.add(storeExtraActivity((Owner) bienve, "Run", "mountain", new Date(2015, 6, 31), "Runing in the mountain"));
			activitiesB.add(storeExtraActivity((Owner) bienve, "Fiesta", "fiestakalea", new Date(2015, 7, 1), "fiesta de tolosa"));
			try {
				storeOffer(rhJ, new Date(2015 - 1900, 6, 30), new Date(2015 - 1900, 7, 4), 750, activitiesA); // Julio --> 6
				storeOffer(rhB1, new Date(2015 - 1900, 6, 30), new Date(2015 - 1900, 7, 4), 1200, activities);
				storeOffer(rjB2, new Date(2015 - 1900, 6, 30), new Date(2015 - 1900, 7, 30), 999, activitiesB);
				storeOffer(rhJe, new Date(2015 - 1900, 6, 30), new Date(2015 - 1900, 7, 8), 1100, activitiesB);
			} catch (Exception e1) {
				System.out.println("Error at initialize DataBase on: ./storeOffer " + e1.getMessage());
				e1.printStackTrace();
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws RemoteException, Exception {
		try {
			// if (c.isDatabaseLocal()==false) openObjectContainer();
			RuralHouse proto = new RuralHouse(ruralHouse.getHouseNumber(), null, null, null, null, 0);
			ObjectSet<RuralHouse> result = db.queryByExample(proto);
			if (result.size() != 0) {
				RuralHouse rh = (RuralHouse) result.get(0);
				Offer o = rh.createOffer(theDB4oManagerAux.nextOffersNumber(), firstDay, lastDay, price);
				db.store(theDB4oManagerAux); // To store the status of the control database
				db.store(o);
				db.commit();
				return o;
			}
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out.println("Error: com.db4o.ext.ObjectNotStorableException in createOffer " + e.getMessage());
			return null;
		}
		return null;
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
	public Booking createBooking(Offer offer, String telephone) {
		Booking book = new Booking(theDB4oManagerAux.nextBookingNumber(), telephone, offer);
		db.store(book);
		db.commit();
		return book;
	}

	public Booking bookOffer(Users user, String telephone, Offer offer) throws RemoteException {
		Booking book = createBooking(offer, telephone);
		((Client) user).addBook(book);
		db.store(book);
		db.commit();
		return book;
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

	private boolean checkRural(String city, String address, int number) throws RemoteException {
		return db.queryByExample(new RuralHouse(0, null, null, city, address, number)).size() == 0;
	}

	public RuralHouse storeRuralhouse(Owner owner, String description, String city, String address, int number) throws RemoteException, Exception {
		try {
			RuralHouse rh = new RuralHouse(theDB4oManagerAux.nextHouseNumber(), owner, description, city, address, number);
			if (checkRural(city, address, number)) {
				owner.addRuralHouse(rh);
				db.store(owner);
				db.store(rh);
				db.store(theDB4oManagerAux);
				db.commit();
				return rh;
			} else {
				return null;
			}
		} catch (com.db4o.ext.ObjectNotStorableException e) {
			System.out.println("Error: com.db4o.ext.ObjectNotStorableException in storeRuralhouse " + e.getMessage());
			return null;
		}
	}

	public RuralHouse updateRuralHouse(RuralHouse rh, Owner owner, String description, int index) throws RemoteException {
		List<RuralHouse> list = db.queryByExample(rh);
		list.get(0).setDescription(description);
		owner.updateRuralHouse(list.get(0), index);
		db.store(list.get(0));
		db.commit();
		return list.get(0);
	}

	public boolean deleteRuralHouse(RuralHouse rh, Owner owner, int index) throws RemoteException {
		List<RuralHouse> list = db.queryByExample(rh);
		List<Owner> listo = db.queryByExample(owner);
		listo.get(0).deleteRuralHouse(index);
		db.delete(list.get(0));
		db.store(listo.get(0));
		db.commit();
		return true;
	}

	public boolean existsOverlappingOffer(RuralHouse rh, Date firstDay, Date lastDay) throws RemoteException, OverlappingOfferExists {
		try {
			// if (c.isDatabaseLocal()==false) openObjectContainer();
			RuralHouse rhn = (RuralHouse) db.queryByExample(rh).get(0);
			if (rhn.overlapsWith(firstDay, lastDay) != null)
				throw new OverlappingOfferExists("Overlaping Offer");
			else
				return false;
		} finally {
			// db.close();
		}
	}

	public List<Offer> searchEngine(Date date) throws RemoteException {
		Offer offer = new Offer(0, null, date, null, 0);
		return db.queryByExample(offer);
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public String toString() {
		return "bookingNumber=" + theDB4oManagerAux.getBookingNumber() + " offerNumber=" + theDB4oManagerAux.getOfferNumber();
	}

	public ExtraActivity storeExtraActivity(Owner owner, String nombre, String lugar, Date fecha, String description) throws RemoteException {
		ExtraActivity actividad = new ExtraActivity(owner, nombre, lugar, fecha, description);
		List<ExtraActivity> data = db.queryByExample(actividad);
		if (data.size() == 0) {
			owner.addExtraActivities(actividad);
			db.store(owner);
			db.store(actividad);
			db.commit();
			return actividad;
		} else {
			return null;
		} // implementar el almacenamienro.
	}

	public Offer storeOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price, ArrayList<ExtraActivity> ExtraActi) throws RemoteException {
		Offer idem = new Offer(0, ruralHouse, firstDay, lastDay, price, ExtraActi);
		if (db.queryByExample(idem).size() == 0) {
			idem.setOfferNumber(theDB4oManagerAux.nextOffersNumber());
			List<RuralHouse> list = db.queryByExample(ruralHouse);
			list.get(0).getAllOffers().add(idem);
			db.store(theDB4oManagerAux); // Db4o Control
			db.store(idem); // Offer
			db.store(list.get(0)); // Ruralhouse
			db.commit();
			return idem;
		} else {
			return null;
		}
	}

	public Offer updateOffer(Offer o, RuralHouse rh, float price, Date firstDay, Date lastDay, Vector<ExtraActivity> vectorlistSeleccion)
			throws RemoteException {
		List<Offer> list = db.queryByExample(o);
		list.get(0).setExtraActivities(vectorlistSeleccion);
		list.get(0).setFirstDay(firstDay);
		list.get(0).setLastDay(lastDay);
		list.get(0).setPrice(price);
		db.store(list.get(0));
		List<RuralHouse> listR = db.queryByExample(rh);
		List<Owner> listO = db.queryByExample(listR.get(0).getOwner());
		listO.get(0).getRuralHouses().remove(listR.get(0));
		listR.get(0).getAllOffers().remove(o);
		listR.get(0).getAllOffers().add(list.get(0));
		listO.get(0).getRuralHouses().add(listR.get(0));
		db.store(listO.get(0));
		db.store(listR.get(0));
		db.commit();
		return list.get(0);
	}

	public boolean deleteOffer(Offer o) throws RemoteException {
		List<Offer> list = db.queryByExample(o);
		List<RuralHouse> listo = db.queryByExample(o.getRuralHouse());
		listo.get(0).getAllOffers().remove(o);
		db.store(listo.get(0));
		db.delete(list.get(0));
		db.commit();
		return true;
	}
}
