package gui;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.JButton;


import domain.Booking;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookRuralHouseConfirmationWindow extends JFrame {
 private static final long serialVersionUID = 1L;
	
  private JLabel jLabel1 = new JLabel();
  private JTextField jTextField1 = new JTextField();
  private JLabel jLabel2 = new JLabel();
  private JTextField jTextField2 = new JTextField();
  private JLabel jLabel3 = new JLabel();
  private JButton jButton1 = new JButton();
  private JLabel jLabel4 = new JLabel();
  private JLabel jLabel5 = new JLabel();
  private JTextField jTextField3 = new JTextField();
  private JTextField jTextField4 = new JTextField();

  public BookRuralHouseConfirmationWindow(Booking book)
  {
    try
    {
      jbInit(book);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

  private void jbInit(Booking book) throws Exception
  {
	  

    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(416, 316));
    this.setTitle("See Booking Details");
    this.setResizable(false);
    jLabel1.setText("Owner Bank account number:");
    jLabel1.setBounds(new Rectangle(20, 20, 200, 25));
    jTextField1.setBounds(new Rectangle(225, 20, 165, 25));
    jTextField1.setEditable(false);

    jTextField1.setText(book.getOffer().getRuralHouse().getOwner().getBankAccount());
    
    jLabel2.setText("Booking number:");
    jLabel2.setBounds(new Rectangle(20, 60, 130, 25));
    jTextField2.setBounds(new Rectangle(225, 60, 165, 25));
    jTextField2.setEditable(false);

    jTextField2.setText(Integer.toString(book.getBookingNumber()));
    
    jLabel3.setText("You must deposit 20% of the total ammount of a book in the next three days.");
    jLabel3.setBounds(new Rectangle(20, 105, 370, 25));
    jButton1.setText("Close");
    jButton1.setBounds(new Rectangle(135, 235, 130, 30));
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton1_actionPerformed(e);
        }
      });
    jLabel4.setText("Total:");
    jLabel4.setBounds(new Rectangle(70, 140, 85, 25));
    jLabel5.setText("Deposit ammount:");
    jLabel5.setBounds(new Rectangle(70, 175, 100, 25));
    jTextField3.setBounds(new Rectangle(180, 140, 115, 25));
    jTextField3.setEditable(false);

    jTextField3.setText(Float.toString(book.getPrice()) + " €");
    jTextField4.setBounds(new Rectangle(180, 175, 115, 25));
    jTextField4.setEditable(false);
    jTextField4.setText(Float.toString(book.getPrice()*(float)0.2) + " €");
    this.getContentPane().add(jTextField4, null);
    this.getContentPane().add(jTextField3, null);
    this.getContentPane().add(jLabel5, null);
    this.getContentPane().add(jLabel4, null);
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jLabel3, null);
    this.getContentPane().add(jTextField2, null);
    this.getContentPane().add(jLabel2, null);
    this.getContentPane().add(jTextField1, null);
    this.getContentPane().add(jLabel1, null);
  }

  private void jButton1_actionPerformed(ActionEvent e)
  {
    this.setVisible(false);
  }
}