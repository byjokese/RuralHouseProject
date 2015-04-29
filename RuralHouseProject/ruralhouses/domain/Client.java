package domain;
//prueba unoss
import java.io.Serializable;
import java.util.Vector;

public class Client extends Users implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Vector<Booking> books;

	public Client(String name, String username, String password,
			Boolean activated, Boolean isOwner, Vector<Booking> offers) {
		super(name, username, password, activated, isOwner);
		this.books = offers;
	}


	public Client(String name, String username, String password,
			Boolean activated, Boolean isOwner) {
		super(name, username, password, activated, isOwner);
		this.books = new Vector<Booking>();
	}


	public Vector<Booking> getBooks() {
		return this.books;
	}

	public void addBook(int bookingNumber, String telephone, Offer offer) {
		Booking book = new Booking(bookingNumber, telephone, offer);
		this.books.add(book);
	}
	
	public void addBook(Booking book){
		this.books.add(book);
		
	}

}
