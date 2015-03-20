package gui;

import exceptions.DataBaseNotInitialized;
import businessLogic.ApplicationFacadeInterface;

import com.toedter.calendar.JCalendar;

import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.Users;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class QueryAvailabilityGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField dateTextField = new JTextField();
	private JLabel numberNightLabel = new JLabel();
	private JTextField jTextField3 = new JTextField();
	private JButton jButton1 = new JButton();
	private JButton jButton2 = new JButton();
	private JLabel jLabel4 = new JLabel();
	private JScrollPane scrollPane = new JScrollPane();
	private JTable table;
	private DefaultTableModel tableModel;
	private final JLabel labelNoOffers = new JLabel("");
	private String[] columnNames = new String[] { "Offer#", "Rural House", "First Day", "Last Day", "Price" };

	@SuppressWarnings("unused")
	private static configuration.ConfigXML c;
	private final JLabel lblQueryMenu = new JLabel("Query Menu / Look for Offers");
	private final JSeparator separator = new JSeparator();
	private Object comboBox;
	private JTextField textField;
	private final JLabel MonthLabel = new JLabel("Month:");
	private final JLabel lblYear = new JLabel("Year:");
	private final JSpinner yearSpinner = new JSpinner();
	private final JLabel PriceLabel = new JLabel("Precio:");
	private final JSlider minSlider = new JSlider();
	private final JTextField minPriceTextField = new JTextField();
	private final JTextField maxPriceTextField = new JTextField();
	private final JSlider maxSlider = new JSlider();

	public QueryAvailabilityGUI(Users user) throws DataBaseNotInitialized {
		minPriceTextField.setEditable(false);
		minPriceTextField.setBounds(699, 54, 34, 20);
		minPriceTextField.setColumns(10);
		try {
			jbInit(user);
		} catch (DataBaseNotInitialized e) {
			throw new DataBaseNotInitialized("Data Base not intialized");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private void jbInit(Users user) throws Exception {
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		// wait till the database is loaded.
		try {
			Vector<RuralHouse> rhs = facade.getAllRuralHouses();
		} catch (NullPointerException e) {
			throw new DataBaseNotInitialized("Data Base not intialized");
		}
		this.setSize(new Dimension(940, 628));
		this.setTitle("Rural House System");
		dateTextField.setHorizontalAlignment(SwingConstants.CENTER);
		dateTextField.setBounds(491, 82, 155, 25);
		dateTextField.setEditable(false);
		numberNightLabel.setBounds(151, 52, 100, 19);
		numberNightLabel.setText("Number of nights:");
		jTextField3.setBounds(261, 52, 54, 19);
		jTextField3.setText("0");
		jButton1.setBounds(42, 548, 430, 30);
		jButton1.setText("Accept");
		jButton2.setBounds(482, 548, 413, 30);
		jButton2.setText("Close");

		jLabel4.setBounds(55, 390, 305, 30);
		jLabel4.setForeground(Color.red);

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
				} catch (Exception ex) {
					System.out.println("Error trying to call BookRuralHouseGUI: " + ex.getMessage());
				}
			}
		});
		scrollPane.setBounds(42, 166, 853, 321);

		scrollPane.setViewportView(table);
		tableModel = new DefaultTableModel(null, columnNames);

		table.setModel(tableModel);
		lblQueryMenu.setBounds(0, 0, 924, 41);
		lblQueryMenu.setForeground(Color.BLACK);
		lblQueryMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblQueryMenu.setFont(new Font("Tahoma", Font.PLAIN, 18));

		JLabel cityLabel = new JLabel("City:");
		cityLabel.setBounds(21, 52, 34, 14);
		getContentPane().setLayout(null);
		getContentPane().add(lblQueryMenu);
		getContentPane().add(cityLabel);
		getContentPane().add(dateTextField);
		getContentPane().add(scrollPane);
		labelNoOffers.setBounds(42, 498, 265, 14);
		getContentPane().add(labelNoOffers);
		getContentPane().add(jButton1);
		getContentPane().add(jButton2);
		getContentPane().add(jLabel4);
		getContentPane().add(numberNightLabel);
		getContentPane().add(jTextField3);
		separator.setBounds(0, 37, 924, 14);
		getContentPane().add(separator);

		textField = new JTextField();
		textField.setBounds(55, 49, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel dayLabel = new JLabel("Day:");
		dayLabel.setBounds(333, 54, 41, 14);
		getContentPane().add(dayLabel);

		JComboBox<Object> monthComboBox = new JComboBox<Object>();
		monthComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December " }));
		monthComboBox.setSelectedIndex(0);
		monthComboBox.setBounds(452, 51, 79, 20);
		getContentPane().add(monthComboBox);
		
		JSpinner daySpinner = new JSpinner();
		daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		updateDate(daySpinner, monthComboBox);
		daySpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateDate(daySpinner, monthComboBox);
			}
		});

		monthComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				switch (monthComboBox.getSelectedItem().toString()) {
				case "January":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
					break;
				case "February":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 28, 1));
					break;
				case "March":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
					break;
				case "April":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 30, 1));
					break;
				case "May":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
					break;
				case "June":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 30, 1));
					break;
				case "July":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
					break;
				case "August":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
					break;
				case "September":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 30, 1));
					break;
				case "October":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
					break;
				case "November":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 30, 1));
					break;
				case "December":
					daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
					break;
				default:
					break;
				}
				updateDate(daySpinner, monthComboBox);
			}
		});

		

		daySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		daySpinner.setBounds(363, 51, 40, 20);
		getContentPane().add(daySpinner);
		MonthLabel.setBounds(413, 54, 46, 14);

		getContentPane().add(MonthLabel);

		lblYear.setBounds(538, 54, 46, 14);
		getContentPane().add(lblYear);
		yearSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				String day = ((int) daySpinner.getValue() >= 0 || (int) daySpinner.getValue() < 10) ? "0" + daySpinner.getValue() : "" + daySpinner.getValue();
				dateTextField.setText(day + "/" + monthComboBox.getSelectedItem() + "/" + yearSpinner.getValue());
			}
		});
		yearSpinner.setModel(new SpinnerNumberModel(new Integer(2015), null, null, new Integer(1)));
		yearSpinner.setBounds(567, 51, 79, 20);
		getContentPane().add(yearSpinner);
		PriceLabel.setBounds(656, 54, 46, 14);

		getContentPane().add(PriceLabel);
		minSlider.setValue(400);
		minSlider.setMaximum(10000);
		minSlider.setSnapToTicks(true);
		minSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				minPriceTextField.setText(Integer.toString(minSlider.getValue()));
				if (minSlider.getValue() > maxSlider.getValue()) {
					maxSlider.setValue(minSlider.getValue());
				}
			}
		});
		minSlider.setMajorTickSpacing(1);
		minSlider.setBounds(743, 43, 126, 25);

		getContentPane().add(minSlider);

		getContentPane().add(minPriceTextField);
		maxPriceTextField.setEditable(false);
		maxPriceTextField.setColumns(10);
		maxPriceTextField.setBounds(873, 54, 41, 20);

		getContentPane().add(maxPriceTextField);
		maxSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				maxPriceTextField.setText(Integer.toString(maxSlider.getValue()));
				if (maxSlider.getValue() < minSlider.getValue()) {
					minSlider.setValue(maxSlider.getValue());
				}
			}
		});
		maxSlider.setMaximum(10000);
		maxSlider.setValue(7500);
		maxSlider.setBounds(743, 65, 126, 25);
		getContentPane().add(maxSlider);
		
		JButton btnNewButton = new JButton("Apply Filters");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String city = cityLabel.getText();
				String numberOfNights = numberNightLabel.getText();
				String startDay = (String) daySpinner.getValue();
			}
		});
		btnNewButton.setBounds(42, 121, 853, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnSeeOfferDetails = new JButton("See Offer Details");
		btnSeeOfferDetails.setBounds(42, 514, 853, 23);
		getContentPane().add(btnSeeOfferDetails);
	}

	private void updateDate(JSpinner daySpinner, JComboBox<Object> monthComboBox) {
		String day = ((int) daySpinner.getValue() >= 0 && (int) daySpinner.getValue() < 10) ? "0" + daySpinner.getValue() : "" + daySpinner.getValue();
		dateTextField.setText("1/January/2015");
	}
}