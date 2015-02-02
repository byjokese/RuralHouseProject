package exceptions;
public class OverlappingOfferExists extends Exception {
 private static final long serialVersionUID = 1L;
 
 public OverlappingOfferExists()
  {
    super();
  }
  /**This exception is triggered if there exists an overlapping offer
  *@param String
  *@return None
  */
  public OverlappingOfferExists(String s)
  {
    super(s);
  }
}