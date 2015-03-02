package gui;

import businessLogic.ApplicationFacadeInterface;

import com.toedter.calendar.*;

import domain.Booking;
import domain.RuralHouse;
import exceptions.OfferCanNotBeBooked;

import java.beans.*;
import java.util.Calendar;
import java.util.Date;
import java.text.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class BookRuralHouseGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

	private JLabel jLabel1 = new JLabel();
	private JComboBox<RuralHouse> jComboBox1;
	Vector<RuralHouse> ruralHouses;
	private JLabel jLabel2 = new JLabel();
	private JLabel jLabel3 = new JLabel();
	private JLabel jLabel4 = new JLabel();
	private JTextField jTextField2 = new JTextField();
	private JTextField jTextField3 = new JTextField();
	private JTextField jTextField4 = new JTextField();
	private JButton jButton2 = new JButton();
	private JButton jButton3 = new JButton();

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JLabel jLabel5 = new JLabel();

	public BookRuralHouseGUI() {
		try {
			jbInit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BookRuralHouseGUI(int houseNumber, Date firstDay, Date lastDay) {
		try {
			jbInit();

			// Put the "houseNumber", "firstDay" and "lastDay" in the graphical components of the user interface
			for (int i = 0; i < ruralHouses.size(); i++) {
				if (((RuralHouse) ruralHouses.get(i)).getHouseNumber() == houseNumber) {
					jComboBox1.setSelectedIndex(i);
					break;
				}
			}

			Calendar c = new GregorianCalendar();
			c.setTime(firstDay);
			// jCalendar1.setCalendar(new GregorianCalendar(firstDay.getYear()+1900,firstDay.getMonth(),firstDay.getDate()));
			jCalendar1.setCalendar(c);

			long numberOfDays = (long) (lastDay.getTime() - firstDay.getTime()) / MILLSECS_PER_DAY;
			jTextField3.setText(Long.toString(numberOfDays));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(410, 413));
		this.setTitle("Book Rural House");
		jLabel1.setText("Rural house:");
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		ruralHouses = facade.getAllRuralHouses();

		jComboBox1 = new JComboBox<RuralHouse>(ruralHouses);

		jLabel1.setBounds(new Rectangle(15, 10, 115, 20));
		jComboBox1.setBounds(new Rectangle(120, 10, 175, 20));

		jTextField3.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				jTextField3_focusLost();
			}
		});
		jTextField4.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				jTextField4_focusLost();
			}
		});
		jLabel2.setText("Arrival day:");
		jLabel2.setBounds(new Rectangle(15, 40, 115, 20));
		jLabel3.setText("Number of nights:");
		jLabel3.setBounds(new Rectangle(15, 240, 115, 20));
		jLabel4.setText("Telephone contact number:");
		jLabel4.setBounds(new Rectangle(15, 270, 140, 20));
		jTextField2.setBounds(new Rectangle(155, 205, 140, 20));
		jTextField2.setEditable(false);
		jTextField3.setBounds(new Rectangle(155, 240, 140, 20));
		jTextField3.setText("0");
		jTextField4.setBounds(new Rectangle(155, 270, 140, 20));
		jTextField4.setText("0");
		jButton2.setText("Accept");
		jButton2.setBounds(new Rectangle(50, 345, 130, 30));
		jButton2.setSize(new Dimension(130, 30));
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// House code
				RuralHouse house = (RuralHouse) jComboBox1.getSelectedItem();
				// Arrival date
				Date firstDay = trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

				System.out.println("firstDay=" + firstDay);
				// Last day
				// Number of days expressed in milliseconds
				long nights = 1000 * 60 * 60 * 24 * Integer.parseInt(jTextField3.getText());
				Date lastDay = new Date((long) (firstDay.getTime() + nights));
				// Telephone contact
				System.out.println("lastDay=" + lastDay);

				String telephone = jTextField4.getText();
				try {

					// Obtain the business logic from a StartWindow class (local or remote)
					ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();

					Booking book = facade.createBooking(house, firstDay, lastDay, telephone);
					if (book != null) {
						BookRuralHouseConfirmationWindow confirmWindow = new BookRuralHouseConfirmationWindow(book);
						confirmWindow.setVisible(true);
						jLabel5.setText("Booking made");

					} else
						jLabel5.setText("There is not available offer for these dates");
				} catch (OfferCanNotBeBooked e1) {
					jLabel5.setText("Error: It is not possible to book");
					JFrame a;
					try {
						a = new QueryAvailabilityGUI();
						a.setVisible(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}

				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});
		jButton3.setText("Cancel");
		jButton3.setBounds(new Rectangle(220, 345, 130, 30));
		jButton3.setSize(new Dimension(130, 30));
		jButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton3_actionPerformed(e);
			}
		});
		jLabel5.setBounds(new Rectangle(50, 310, 300, 20));
		jLabel5.setForeground(Color.red);
		jCalendar1.setBounds(new Rectangle(155, 50, 235, 145));
		this.getContentPane().add(jCalendar1, null);
		this.getContentPane().add(jLabel5, null);
		this.getContentPane().add(jButton3, null);
		this.getContentPane().add(jButton2, null);
		this.getContentPane().add(jTextField4, null);
		this.getContentPane().add(jTextField3, null);
		this.getContentPane().add(jTextField2, null);
		this.getContentPane().add(jLabel4, null);
		this.getContentPane().add(jLabel3, null);
		this.getContentPane().add(jLabel2, null);
		this.getContentPane().add(jComboBox1, null);
		this.getContentPane().add(jLabel1, null);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
					DateFormat dateformat = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jTextField2.setText(dateformat.format(calendarMio.getTime()));

				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jTextField2.setText(dateformat1.format(calendarMio.getTime()));
					jCalendar1.setCalendar(calendarMio);
				}
			}
		});
	}

	private Date trim(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTime();
	}

	private void jButton3_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	public void setConfirmBooking(boolean b) {
		if (b)
			jLabel5.setText("Booking made");
	}

	private void jTextField3_focusLost() {
		try {
			new Integer(jTextField3.getText());
			jLabel5.setText("");
		} catch (NumberFormatException ex) {
			jLabel5.setText("Error: Introduce a number");
		}
	}

	private void jTextField4_focusLost() {
		try {
			new Integer(jTextField4.getText());
			jLabel5.setText("");
		} catch (NumberFormatException ex) {
			jLabel5.setText("Error: Introduce a number");
		}
	}
}