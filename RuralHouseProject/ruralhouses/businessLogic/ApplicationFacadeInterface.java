package businessLogic;

import java.rmi.*;
import java.util.List;
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
	 * This method creates an offer with a house number, first day, last day and price
	 * 
	 * @param House
	 *            number, start day, last day and price
	 * @return None
	 */

	Offer createOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price) throws RemoteException, Exception;

	/**
	 * This method creates a book with a corresponding parameters
	 * 
	 * @param First
	 *            day, last day, house number and telephone
	 * @return a book
	 */
	Booking createBooking(RuralHouse ruralHouse, Date firstDay, Date lastDay, String telephoneNumber) throws RemoteException, OfferCanNotBeBooked;

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
	public ExtraActivity storeExtraActivity(Owner owner,String nombre,String lugar,Date fecha,String description) throws RemoteException;

	List<List<Offer>> searchAvailableOffers(String city, String numberOfNights, Date date, int minPrice, int maxPrice) throws RemoteException;

}