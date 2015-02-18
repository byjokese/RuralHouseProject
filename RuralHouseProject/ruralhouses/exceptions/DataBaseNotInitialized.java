package exceptions;

public class DataBaseNotInitialized extends Exception {
	private static final long serialVersionUID = 1L;

	public DataBaseNotInitialized() {
		super();
	}
	/**This exception is triggered if system try to access Database before it is initialized
	  *@param String
	  *@return None
	  */
	public DataBaseNotInitialized(String s) {
		super(s);
	}
}
