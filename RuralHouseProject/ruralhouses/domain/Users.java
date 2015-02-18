package domain;

public interface Users {
	String username = "";
	String name = "";
	String password = "";
	Boolean activated = false;
	enum type {OWNER, CLIENT};
	/**
	 * Common operations for Clients and Owners.
	 */

	String getName();

	void setName(String name);

	String getLogin();

	void setLogin(String login);

	String getPassword();

	void setPassword(String password);

}
