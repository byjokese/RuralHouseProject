package gui;

import exceptions.DataBaseNotInitialized;
import businessLogic.ApplicationFacadeInterface;

import com.db4o.ext.DatabaseClosedException;
import com.toedter.calendar.JCalendar;

import configuration.ConfigXML;
import domain.Offer;
import domain.RuralHouse;
import exceptions.DataBaseNotInitialized;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class QueryAvailabilityGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel jLabel1 = new JLabel();
	private JLabel jLabel2 = new JLabel();
	private JTextField jTextField2 = new JTextField();
	private JLabel jLabel3 = new JLabel();
	private JTextField jTextField3 = new JTextField();
	private JButton jButton1 = new JButton();
	private JButton jButton2 = new JButton();

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JLabel jLabel4 = new JLabel();
	private JScrollPane scrollPane = new JScrollPane();
	private JComboBox comboBox;
	private JTable table;
	private DefaultTableModel tableModel;
	private final JLabel labelNoOffers = new JLabel("");
	private String[] columnNames = new String[] { "Offer#", "Rural House", "First Day", "Last Day", "Price" };

	private static configuration.ConfigXML c;
	private final JLabel lblQueryMenu = new JLabel("Query Menu");
	private final JSeparator separator = new JSeparator();

	public QueryAvailabilityGUI() throws DataBaseNotInitialized {
		try {
			jbInit();
		}
		catch (DataBaseNotInitialized e) {
			throw new DataBaseNotInitialized("Data Base not intialized");
		}
		catch (Exception e) {
			//e.printStackTrace();
		}

	}

	private void jbInit() throws Exception {
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		// wait till the database is loaded.
		try {
			Vector<RuralHouse> rhs = facade.getAllRuralHouses();
			comboBox = new JComboBox(rhs);
		}
		catch (NullPointerException e) {
			throw new DataBaseNotInitialized("Data Base not intialized");
		}

		// comboBox.setModel(new DefaultComboBoxModel(rhs));

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(433, 548));
		this.setTitle("Query availability");
		jLabel1.setText("Rural house code:");
		jLabel1.setBounds(new Rectangle(42, 66, 105, 25));
		jLabel2.setText("First day:");
		jLabel2.setBounds(new Rectangle(42, 101, 75, 25));
		jTextField2.setBounds(new Rectangle(150, 256, 155, 25));
		jTextField2.setEditable(false);
		jLabel3.setText("Number of nights:");
		jLabel3.setBounds(new Rectangle(42, 292, 115, 25));
		jTextField3.setBounds(new Rectangle(150, 292, 195, 25));
		jTextField3.setText("0");
		jButton1.setText("Accept");
		jButton1.setBounds(new Rectangle(55, 468, 130, 30));
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton1_actionPerformed(e);
			}
		});
		jButton2.setText("Close");
		jButton2.setBounds(new Rectangle(230, 468, 130, 30));

		jTextField3.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {}

			public void focusLost(FocusEvent e) {
				jTextField3_focusLost();
			}
		});
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		jLabel4.setBounds(new Rectangle(55, 311, 305, 30));
		jLabel4.setForeground(Color.red);
		jCalendar1.setBounds(new Rectangle(150, 106, 225, 150));
		scrollPane.setBounds(new Rectangle(55, 341, 320, 116));

		this.getContentPane().add(scrollPane, null);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int i = table.getSelectedRow();
					int houseNumber = (int) tableModel.getValueAt(i, 1);

					// Dates are represented as strings in the table model
					// They have to be converted to Dates "dd/mm/aa", removing
					// hh:mm:ss:ms with trim
					DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
					// Date firstDate=
					// trim(df.parse((String)tableModel.getValueAt(i,2)));
					// Date lastDate=
					// trim(df.parse((String)tableModel.getValueAt(i,3)));
					Date firstDate = df.parse((String) tableModel.getValueAt(i, 2));
					// firstDate=new Date(firstDate.getTime()+12*60*60*1000); //
					// to add 12 hours because that is how they are stored
					Date lastDate = df.parse((String) tableModel.getValueAt(i, 3));
					// lastDate=new Date(lastDate.getTime()+12*60*60*1000); //
					// to add 12 hours because that is how they are stored

					BookRuralHouseGUI b = new BookRuralHouseGUI(houseNumber, firstDate, lastDate);
					b.setVisible(true);
				}
				catch (Exception ex) {
					System.out.println("Error trying to call BookRuralHouseGUI: " + ex.getMessage());
				}
			}
		});

		scrollPane.setViewportView(table);
		tableModel = new DefaultTableModel(null, columnNames);

		table.setModel(tableModel);
		this.getContentPane().add(jCalendar1, null);
		this.getContentPane().add(jLabel4, null);
		this.getContentPane().add(jButton2, null);
		this.getContentPane().add(jButton1, null);
		this.getContentPane().add(jTextField3, null);
		this.getContentPane().add(jLabel3, null);
		this.getContentPane().add(jTextField2, null);
		this.getContentPane().add(jLabel2, null);
		this.getContentPane().add(jLabel1, null);
		comboBox.setBounds(new Rectangle(245, 22, 115, 20));
		comboBox.setBounds(149, 68, 115, 20);

		getContentPane().add(comboBox);
		labelNoOffers.setBounds(73, 432, 265, 14);

		getContentPane().add(labelNoOffers);
		lblQueryMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblQueryMenu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblQueryMenu.setBounds(0, 10, 417, 30);

		getContentPane().add(lblQueryMenu);
		separator.setBounds(0, 41, 417, 14);

		getContentPane().add(separator);

		// Codigo para el JCalendar
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

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void jTextField3_focusLost() {
		try {
			new Integer(jTextField3.getText());
			jLabel4.setText("");
		}
		catch (NumberFormatException ex) {
			jLabel4.setText("Error: Introduce a number");
		}
	}

	private void jButton1_actionPerformed(ActionEvent e) {
		// House object
		RuralHouse rh = (RuralHouse) comboBox.getSelectedItem();
		// First day removing the hour:minute:second:ms from the date
		Date firstDay = trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

		final long diams = 1000 * 60 * 60 * 24;
		long nights = diams * Integer.parseInt(jTextField3.getText());
		// Last day
		Date lastDay = new Date((long) (firstDay.getTime() + nights));

		try {

			ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();

			Vector<Offer> v = rh.getOffers(firstDay, lastDay);

			Enumeration<Offer> en = v.elements();
			Offer of;
			tableModel.setDataVector(null, columnNames);
			if (!en.hasMoreElements()) labelNoOffers.setText("There are no offers at these dates");
			else {
				labelNoOffers.setText("Select an offer if you want to book");

				while (en.hasMoreElements()) {
					of = en.nextElement();
					System.out.println("Offer retrieved: " + of.toString());
					Vector row = new Vector();
					row.add(of.getOfferNumber());
					row.add(of.getRuralHouse().getHouseNumber());

					// Dates are stored in db4o as java.util.Date objects
					// instead of java.sql.Date objects
					// They are converted to strings "dd/mm/aa"
					DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
					String firstDayString = df.format(of.getFirstDay());
					String lastDayString = df.format(of.getLastDay());
					row.add(firstDayString);
					row.add(lastDayString);
					row.add(of.getPrice());

					tableModel.addRow(row);
				}
			}

		}
		catch (Exception e1) {

			labelNoOffers.setText(e1.getMessage());
		}
	}
}