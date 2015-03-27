package gui;

import exceptions.DataBaseNotInitialized;
import businessLogic.ApplicationFacadeInterface;

import com.toedter.calendar.JCalendar;

import domain.Booking;
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
import java.util.List;
import java.util.Vector;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
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
	private JTextField cityField;
	private final JLabel MonthLabel = new JLabel("Month:");
	private final JLabel lblYear = new JLabel("Year:");
	private final JSpinner yearSpinner = new JSpinner();
	private final JLabel PriceLabel = new JLabel("Precio:");
	private final JSlider minSlider = new JSlider();
	private final JTextField minPriceTextField = new JTextField();
	private final JTextField maxPriceTextField = new JTextField();
	private final List<List<Offer>> offers = new Vector<>();;
	private final JSlider maxSlider = new JSlider();
	private List<List<Offer>> availableOffers;

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
		dateTextField.setText("1/January/2015");
		numberNightLabel.setBounds(151, 52, 100, 19);
		numberNightLabel.setText("Number of nights:");
		jTextField3.setBounds(261, 52, 54, 19);
		jTextField3.setText("0");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*if (user == null) {
					JPopupMenu a = new JPopupMenu();
					a.setVisible(true);
				} else {*/
					Offer offer = availableOffers.get(0).get(table.getSelectedRow());
					String telephone = JOptionPane.showInputDialog(new JTextField(), "Insert the telephone number:");
					System.out.println("Selected Offer:" + offer.toString());
					try {
						facade.bookOffer(user, telephone, offer);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				//}
			}
		});
		jButton1.setBounds(42, 548, 430, 30);
		jButton1.setText("Accept");

		jButton2.setText("Close");
		jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		jButton2.setBounds(482, 548, 413, 30);

		jLabel4.setBounds(55, 390, 305, 30);
		jLabel4.setForeground(Color.red);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
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

		cityField = new JTextField();
		cityField.setBounds(55, 49, 86, 20);
		getContentPane().add(cityField);
		cityField.setColumns(10);

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
				int daySlected = (int) daySpinner.getValue();
				switch (monthComboBox.getSelectedItem().toString()) {
				case "January":
					daySpinner.setModel(new SpinnerNumberModel(daySlected, 1, 31, 1));
					break;
				case "February":
					daySpinner.setModel(new SpinnerNumberModel((daySlected > 28) ? 28 : daySlected, 1, 28, 1));
					break;
				case "March":
					daySpinner.setModel(new SpinnerNumberModel(daySlected, 1, 31, 1));
					break;
				case "April":
					daySpinner.setModel(new SpinnerNumberModel((daySlected > 30) ? 30 : daySlected, 1, 30, 1));
					break;
				case "May":
					daySpinner.setModel(new SpinnerNumberModel(daySlected, 1, 31, 1));
					break;
				case "June":
					daySpinner.setModel(new SpinnerNumberModel((daySlected > 30) ? 30 : daySlected, 1, 30, 1));
					break;
				case "July":
					daySpinner.setModel(new SpinnerNumberModel(daySlected, 1, 31, 1));
					break;
				case "August":
					daySpinner.setModel(new SpinnerNumberModel(daySlected, 1, 31, 1));
					break;
				case "September":
					daySpinner.setModel(new SpinnerNumberModel((daySlected > 30) ? 30 : daySlected, 1, 30, 1));
					break;
				case "October":
					daySpinner.setModel(new SpinnerNumberModel(daySlected, 1, 31, 1));
					break;
				case "November":
					daySpinner.setModel(new SpinnerNumberModel((daySlected > 30) ? 30 : daySlected, 1, 30, 1));
					break;
				case "December":
					daySpinner.setModel(new SpinnerNumberModel(daySlected, 1, 31, 1));
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
				updateDate(daySpinner, monthComboBox);
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
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				String city = cityField.getText();
				String numberOfNights = numberNightLabel.getText();
				int startDay = (int) daySpinner.getValue();
				int month = monthComboBox.getSelectedIndex() + 1;
				int year = (int) yearSpinner.getValue();
				Date date = new Date(year, month, startDay);
				int minPrice = Integer.parseInt(minPriceTextField.getText());
				int maxPrice = Integer.parseInt(maxPriceTextField.getText());
				try {
					if (offers != null)
						offers.removeAll(offers);
					List<List<Offer>> availableOffers = facade.searchAvailableOffers(city, numberOfNights, date, minPrice, maxPrice);
					if (availableOffers.get(0).size() != 0) {
						System.out.println(availableOffers.get(0).toString());
						offers.add(availableOffers.get(0));
					} else if (availableOffers.get(1).size() != 0) {
						offers.add(availableOffers.get(1));
					} else {
						JOptionPane.showMessageDialog(null, "NO Offers Found");
					}
					updateTable(availableOffers);
				} catch (RemoteException e) {
					System.out.println("Error at searchAvailableOffers in QueryAvailability: " + e.getMessage());

				}

			}

		});
		btnNewButton.setBounds(42, 121, 853, 23);
		getContentPane().add(btnNewButton);

		JButton btnSeeOfferDetails = new JButton("See Offer Details");
		btnSeeOfferDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame ad = new AditionaOfferInfoGUI(offers.get(0).get(table.getSelectedRow()));
				ad.setVisible(true);
			}
		});
		btnSeeOfferDetails.setBounds(42, 514, 853, 23);
		getContentPane().add(btnSeeOfferDetails);
	}

	protected void updateTable(List<List<Offer>> availableOffers) {
		Object[][] data = new Object[2][];
		int row = 0;
		for (Offer offer : availableOffers.get(0)) {
			Object[] tmp = { new Integer(offer.getOfferNumber()), new String(offer.getRuralHouse().toString()), new String(offer.getFirstDay().toString()),
					new String(offer.getLastDay().toString()), new Float(offer.getPrice()) };
			data[row] = tmp;
			row++;
		}
		DefaultTableModel tabmodel = new DefaultTableModel(data, columnNames);
		table.setModel(tabmodel);
	}

	protected void updateDate(JSpinner daySpinner, JComboBox<Object> monthComboBox) {
		String day = ((int) daySpinner.getValue() >= 0 && (int) daySpinner.getValue() < 10) ? "0" + daySpinner.getValue() : "" + daySpinner.getValue();
		dateTextField.setText(day + "/" + (String) monthComboBox.getSelectedItem() + "/" + yearSpinner.getValue());
	}

	class AditionaOfferInfoGUI extends JFrame {

		private static final long serialVersionUID = 1L;
		private JPanel activitiesTextField;
		private JTextField offerTextField;
		private JTextField lastdayTextField;
		private JTextField firstdayTextField;
		private JTextField priceTextField;
		private JTextField ruralhouseTextField;
		private JTextField offerNumTextField;
		private JTable table;
		private DefaultTableModel tableModel;

		/**
		 * Create the frame.
		 */
		public AditionaOfferInfoGUI(Offer offer) {
			// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 738, 355);
			activitiesTextField = new JPanel();
			activitiesTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(activitiesTextField);
			activitiesTextField.setLayout(null);

			JLabel lblNewLabel = new JLabel("Offer Info");
			lblNewLabel.setBounds(5, 0, 707, 27);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			activitiesTextField.add(lblNewLabel);

			JSeparator separator = new JSeparator();
			separator.setBounds(0, 28, 722, 11);
			activitiesTextField.add(separator);

			JLabel lblOffer = new JLabel("Offer #:");
			lblOffer.setBounds(15, 38, 46, 14);
			activitiesTextField.add(lblOffer);

			JLabel lblFirstDay = new JLabel("First day:");
			lblFirstDay.setBounds(15, 75, 56, 14);
			activitiesTextField.add(lblFirstDay);

			JLabel lblLastDay = new JLabel("Last day:");
			lblLastDay.setBounds(15, 112, 56, 14);
			activitiesTextField.add(lblLastDay);

			JLabel lblPrice = new JLabel("Price:");
			lblPrice.setBounds(351, 38, 46, 14);
			activitiesTextField.add(lblPrice);

			JLabel lblRuralhouse = new JLabel("RuralHouse:");
			lblRuralhouse.setBounds(351, 75, 75, 14);
			activitiesTextField.add(lblRuralhouse);

			JLabel lblOptionalActivities = new JLabel("Optional Activities:");
			lblOptionalActivities.setBounds(351, 112, 96, 14);
			activitiesTextField.add(lblOptionalActivities);

			offerTextField = new JTextField();
			offerTextField.setEditable(false);
			offerTextField.setBounds(81, 35, 96, 20);
			offerTextField.setText(Integer.toString(offer.getOfferNumber()));
			activitiesTextField.add(offerTextField);
			offerTextField.setColumns(10);

			lastdayTextField = new JTextField();
			lastdayTextField.setEditable(false);
			lastdayTextField.setColumns(10);
			lastdayTextField.setBounds(81, 109, 221, 20);
			lastdayTextField.setHorizontalAlignment(SwingConstants.LEFT);
			lastdayTextField.setText(offer.getLastDay().toString());
			activitiesTextField.add(lastdayTextField);

			firstdayTextField = new JTextField();
			firstdayTextField.setEditable(false);
			firstdayTextField.setColumns(10);
			firstdayTextField.setHorizontalAlignment(SwingConstants.LEFT);
			firstdayTextField.setBounds(81, 72, 221, 20);
			firstdayTextField.setText(offer.getFirstDay().toString());
			activitiesTextField.add(firstdayTextField);

			priceTextField = new JTextField();
			priceTextField.setEditable(false);
			priceTextField.setColumns(10);
			priceTextField.setBounds(429, 38, 96, 20);
			priceTextField.setText(Float.toString(offer.getPrice()));
			activitiesTextField.add(priceTextField);

			ruralhouseTextField = new JTextField();
			ruralhouseTextField.setEditable(false);
			ruralhouseTextField.setColumns(10);
			ruralhouseTextField.setBounds(429, 72, 141, 20);
			ruralhouseTextField.setText(offer.getRuralHouse().toString());
			activitiesTextField.add(ruralhouseTextField);

			offerNumTextField = new JTextField();
			offerNumTextField.setEditable(false);
			offerNumTextField.setColumns(10);
			offerNumTextField.setBounds(453, 112, 117, 20);
			offerNumTextField.setText(offer.getExtraActivities().size() + ": Activities");
			activitiesTextField.add(offerNumTextField);

			String columnNames[] = new String[] { "Name", "Description", "Owner", "Place", "Date" };
			Object[][] data = new Object[offer.getExtraActivities().size()][];
			for (int i = 0; i < offer.getExtraActivities().size(); i++) {
				Object[] tmp = { new String(offer.getExtraActivities().get(i).getNombre()), new String(offer.getExtraActivities().get(i).getDescription()),
						new String(offer.getExtraActivities().get(i).getOwner().getName()), new String(offer.getExtraActivities().get(i).getLugar()),
						new String(offer.getExtraActivities().get(i).getFecha().toString()) };
				data[i] = tmp;
			}

			JButton closeBtn = new JButton("Close");
			closeBtn.setBounds(10, 283, 702, 25);
			closeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			activitiesTextField.add(closeBtn);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 138, 702, 134);
			activitiesTextField.add(scrollPane);
			table = new JTable();
			table.setEnabled(false);
			table.setFillsViewportHeight(true);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane.setViewportView(table);
			tableModel = new DefaultTableModel(data, columnNames);
			table.setModel(tableModel);

		}
	}
}