package businessLogic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.sql.SQLException;
import java.util.Vector;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import configuration.ConfigXML;
import dataAccess.DB4oManager;
import domain.Booking;
import domain.Client;
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
 

	public FacadeImplementation() throws RemoteException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException, DB4oManagerCreationException {
		owners=null;
		ruralHouses=null;
		try{
			dB4oManager=DB4oManager.getInstance();
		}
		catch (com.db4o.ext.DatabaseFileLockedException e) {
			System.out.println("Error in FacadeImplementation: "+e.toString());
			throw e;
		}
		catch (Exception e) {
			System.out.println("Error in FacadeImplementation: "+e.toString());
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
	public Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay,
			float price) throws OverlappingOfferExists, BadDates, RemoteException, Exception {
		if (firstDay.compareTo(lastDay)>=0) throw new BadDates();
		ruralHouses=null;
		owners=null;
		boolean b = dB4oManager.existsOverlappingOffer(ruralHouse,firstDay,lastDay); // The ruralHouse object in the client may not be updated
		if (!b) return dB4oManager.createOffer(ruralHouse,firstDay,lastDay,price);			
		return null;
	}

	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */
	public Booking createBooking(RuralHouse ruralHouse, Date firstDate, Date lastDate, String bookTelephoneNumber)
			throws OfferCanNotBeBooked {
		ruralHouses=null;
		owners=null;
		return dB4oManager.createBooking(ruralHouse,firstDate,lastDate,bookTelephoneNumber);
	}


	/**
	 * This method existing  owners 
	 * 
	 */
	public Vector<Owner> getOwners() throws RemoteException,
			Exception {
		
		if (owners!=null) { System.out.println("Owners obtained directly from business logic layer");
							return owners; }
		else return owners=dB4oManager.getOwners();
	}
		
	public Vector<RuralHouse> getAllRuralHouses() throws RemoteException,
	Exception {
		
		if (ruralHouses!=null) { System.out.println("RuralHouses obtained directly from business logic layer");
								 return ruralHouses; }
		else return ruralHouses=dB4oManager.getAllRuralHouses();

	}
	
	public boolean checkUserAvailability(String username){
		return dB4oManager.checkUserAvailability(username);
	}
	
	public void addUserToDataBase(String name, String login, String password, Users.type type){
		dB4oManager.addUserToDataBase(name, login, password, type);
	}
	
	public void close() throws RemoteException{
		dB4oManager.close();

	}

	}

