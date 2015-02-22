package domain;

import java.io.Serializable;
import java.util.Vector;




@SuppressWarnings("serial")
public class Owner implements Serializable {

	private String bankAccount = "";
	private String name="";
	private String login="";
	private String password="";
	private Vector<RuralHouse> ruralHouses;


	public Owner(String name,String login, String password){
		this.name=name;
		this.login=login;
		this.password=password;
		ruralHouses=new Vector<RuralHouse>();
		
	}
	public Owner(String name,String login, String password,String bankAccount){
		this.bankAccount=bankAccount;
		this.name=name;
		this.login=login;
		this.password=password;
		ruralHouses=new Vector<RuralHouse>();
		
	}
	/**
	 * This method returns the name
	 * 
	 * @return owner name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * This method sets the owner name 
	 * 
	 * @param name
	 *            owner name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the owner login
	 * 
	 * @return The owner login
	 */
	
	/**
	 * This method returns the owner bank account number
	 * 
	 * @return The bank account number
	 */
	public String getBankAccount() {
		return this.bankAccount;
	}

	/**
	 * This method sets the owner account number 
	 * 
	 * @param bankAccount
	 *            bank account number
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	/**
	 * This method returns the owner login
	 * 
	 * @return The owner login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * This method sets the owner login 
	 * 
	 * @param login
	 *            the owner login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * This method returns the owner password
	 * 
	 * @return The owner login
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * This method sets the owner password 
	 * 
	 * @param password
	 *            the owner password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * This method obtains an owner's(userId) rural houses 
	 * 
	 * @param userId
	 *            user key
	 * @return a vector of Rural Houses
	 */
	
	public Vector<RuralHouse> getRuralHouses() {
		return ruralHouses;
	}
	
	public RuralHouse addRuralHouse(int houseNumber, String description, String city) {
     RuralHouse rh=new RuralHouse( houseNumber,  this,  description,  city);
	 ruralHouses.add(rh);
	 return rh;
	 
	}
	public RuralHouse addRuralHouse(int houseNumber, String description, String city,String address,int number) {
	     RuralHouse rh=new RuralHouse( houseNumber,  this,  description,  city,address,number);
		 ruralHouses.add(rh);
		 return rh;
		 
		}
	
	public String toString(){
		return name;
	}
	
}