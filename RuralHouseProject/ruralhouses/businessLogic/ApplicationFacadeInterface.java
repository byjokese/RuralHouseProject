package businessLogic;

import java.rmi.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Date;

import domain.Booking;
import domain.ExtraActivity;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.Users;
import exceptions.OfferCanNotBeBooked;

public interface ApplicationFacadeInterface extends Remote {

	
	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */

	public Booking bookOffer(Users user, String telephone, Offer offer) throws RemoteException;

	/**
	 * This method retrieves the existing owners
	 * 
	 * @return a Set of owners
	 */
	public Vector<Owner> getOwners() throws RemoteException, Exception;

	/**
	 * This method retrieves the existing rural houses
	 * 
	 * @return a Set of rural houses
	 */
	public Vector<RuralHouse> getAllRuralHouses() throws RemoteException, Exception;

	public void close() throws RemoteException;

	public boolean checkUserAvailability(String username) throws RemoteException;

	public Users checkLogin(String username, String password, boolean isOwner) throws RemoteException;

	public Users addUserToDataBase(String name, String login, String password, boolean isOwner, String BankAcount) throws RemoteException;


	public RuralHouse storeRuralhouse(int houseNumber, Owner owner, String description, String city, String address, int aumber) throws RemoteException;
	
	public void activateAccount(String username, boolean isOwner, String bank)throws RemoteException;
	
	public RuralHouse updateRuralHouse(RuralHouse rh, Owner owner, String description, int index) throws RemoteException;
	
	public boolean deleteRuralHouse(RuralHouse rh, Owner owner, int index) throws RemoteException;
	
	public ExtraActivity storeExtraActivity(Owner owner, String nombre, String lugar, Date fecha, String description) throws RemoteException;

	public List<List<Offer>> searchAvailableOffers(String city, String numberOfNights, Date date, int minPrice, int maxPrice) throws RemoteException;

	public Offer storeOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price, ArrayList<ExtraActivity> ExtraActi) throws RemoteException;
	
	public Offer updateOffer(Offer o, float price, Date firstDay, Date lastDay, Vector<ExtraActivity> vectorlistSeleccion) throws RemoteException;
	
	public boolean deleteOffer(Offer o) throws RemoteException;

}