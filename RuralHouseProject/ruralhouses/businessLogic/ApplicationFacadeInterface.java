package businessLogic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import domain.Booking;
import domain.Client;
import domain.ExtraActivity;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.Users;
import exceptions.OverlappingOfferExists;

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

	public RuralHouse storeRuralhouse(Owner owner, String description, String city, String address, int aumber) throws RemoteException;

	public void activateAccount(String username, boolean isOwner, String bank) throws RemoteException;

	public RuralHouse updateRuralHouse(RuralHouse rh, Owner owner, String description, int mark, List<String[]> comments) throws RemoteException;

	public Owner updateOwner(Owner owner, String bankAccount, Vector<RuralHouse> ruralHouses, Vector<ExtraActivity> extraActivities, int mark)
			throws RemoteException;

	public Client updateClient(Client client, Vector<Booking> books, Vector<Booking> qualifiedBookings) throws RemoteException;

	public boolean deleteRuralHouse(RuralHouse rh, Owner owner, int index) throws RemoteException;

	public ExtraActivity storeExtraActivity(Owner owner, String nombre, String lugar, Date fecha, String description) throws RemoteException;

	public List<List<Offer>> searchAvailableOffers(String city, String numberOfNights, Date date, int minPrice, int maxPrice) throws RemoteException;

	public Offer storeOffer(RuralHouse ruralHouse, Date firstDay, Date lastDay, float price, ArrayList<ExtraActivity> ExtraActi) throws RemoteException;

	public Offer updateOffer(Offer o, RuralHouse rh, float price, Date firstDay, Date lastDay, Vector<ExtraActivity> vectorlistSeleccion)
			throws RemoteException;

	public boolean existsOverlappingOffer(RuralHouse rh, Date firstDay, Date lastDay) throws RemoteException, OverlappingOfferExists;

	public boolean deleteOffer(Offer o) throws RemoteException;

	public ExtraActivity updateExtraActivity(ExtraActivity ex, Owner owner, String description, int index, String place, Date activityDate)
			throws RemoteException;

	public boolean deleteExtraActivity(ExtraActivity ex, Owner owner, int index) throws RemoteException;

	public boolean deletePassedOffers(Owner owner, Date today) throws RemoteException;

	public List<Object> qualify(int ownerMark, int houseMark, boolean isAnonmous, String comment, Client client, Booking book) throws RemoteException;

	public Booking bookOffer(Client client, Offer o, Vector<ExtraActivity> activieties, String telephon) throws RemoteException;

}