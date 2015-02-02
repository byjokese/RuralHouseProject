package exceptions;
public class BadDates extends Exception {
 private static final long serialVersionUID = 1L;
 
 public BadDates()
  {
    super();
  }
  /**This exception is triggered if first date is greater than last date in offers
  *@param String
  *@return None
  */
  public BadDates(String s)
  {
    super(s);
  }
}