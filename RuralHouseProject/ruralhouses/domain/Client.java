package domain;

import java.util.Vector;

public class Client implements Users {

	private String name = "";
	private String login = "";
	private String password = "";
	private Boolean activated;
	private Users.type type;
	private Vector<Booking> offers;
		
	public Client(String name, String login, String password, Boolean activated) {
		super();
		this.name = name;
		this.login = login;
		this.password = password;
		this.activated = activated;
		type = domain.Users.type.CLIENT;
		this.offers = new Vector<Booking>();
	}
	
	public Vector<Booking> getOffers(){
		return this.offers;
	}
	
	public void addBook(int bookingNumber, String telephone,Offer offer){
		Booking book = new Booking(bookingNumber, telephone, offer);
		offers.add(book);
	}

	@Override
	public String getLogin() {
		return login;
	}

	@Override
	public void setLogin(String login) {
		this.login = login;

	}

	@Override
	public String getPassword() {
		return password;
	}

	

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}



}
