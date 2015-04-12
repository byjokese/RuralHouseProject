package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import businessLogic.ApplicationFacadeInterface;
import domain.ExtraActivity;
import domain.Offer;
import domain.Users;
import exceptions.DataBaseNotInitialized;

public class QueryAvailabilityGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField dateTextField = new JTextField();
	private JLabel numberNightLabel = new JLabel();
	private JTextField NumberOfNightTextField = new JTextField();
	private JButton jButton1 = new JButton();
	private JButton jButton2 = new JButton();
	private JScrollPane scrollPane = new JScrollPane();
	private JTable table;
	private DefaultTableModel tablemodel;
	private CellRender renderer;
	private final JLabel infoLabel = new JLabel(
			"The offers are divided by a red line: Offers with inserted filters (up) and other similar offers you could be interested in (down) respectively.");
	private String[] columnNames = new String[] { "Offer#", "Nights", "Rural House", "First Day", "Last Day", "Price" };

	private final JLabel lblQueryMenu = new JLabel("Query Menu / Look for Offers");
	private final JSeparator separator = new JSeparator();
	private JTextField cityField;
	private final JLabel MonthLabel = new JLabel("Month:");
	private final JLabel lblYear = new JLabel("Year:");
	private final JSpinner yearSpinner = new JSpinner();
	private final JLabel PriceLabel = new JLabel("Precio:");
	private final JSlider minSlider = new JSlider();
	private final JTextField minPriceTextField = new JTextField();
	private final JTextField maxPriceTextField = new JTextField();
	private final Vector<List<Offer>> offers = new Vector<List<Offer>>();;
	private final JSlider maxSlider = new JSlider();
	private Users intoUser = null;
	private final JLabel infoMessagesLbl = new JLabel("");

	public QueryAvailabilityGUI(Users user) throws DataBaseNotInitialized {
		getContentPane().setForeground(SystemColor.textHighlight);
		intoUser = user;
		minPriceTextField.setEditable(false);
		minPriceTextField.setBounds(699, 54, 34, 20);
		minPriceTextField.setColumns(10);
		try {
			jbInit(intoUser);
		} catch (DataBaseNotInitialized e) {
			throw new DataBaseNotInitialized("Data Base not intialized");
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	private void jbInit(Users user) throws Exception {
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		// wait till the database is loaded.
		if (intoUser == null)
			infoMessagesLbl.setText("Not loged in!");
		/*
		 * try { facade.getAllRuralHouses(); } catch (NullPointerException e) { throw new DataBaseNotInitialized("Data Base not intialized"); }
		 */
		this.setSize(new Dimension(940, 628));
		this.setTitle("Rural House System");
		dateTextField.setToolTipText("Date of Start\r\n");
		dateTextField.setHorizontalAlignment(SwingConstants.CENTER);
		dateTextField.setBounds(491, 82, 155, 25);
		dateTextField.setEditable(false);
		dateTextField.setText("1/January/2015");
		numberNightLabel.setBounds(151, 52, 100, 19);
		numberNightLabel.setText("Number of nights:");
		NumberOfNightTextField.setBounds(261, 52, 54, 19);
		NumberOfNightTextField.setText("1");
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (intoUser == null) {
					JOptionPane.showMessageDialog(null, "You Must Login");
					dispose();
					// ****Fase 3****
					/**
					 * if (JOptionPane.showConfirmDialog(null, "Do you want to Login-in?", "You Must Login", 0) == 0) { JFrame login = null; try { login = new
					 * LoginGUI(intoUser); login.setVisible(true);
					 * 
					 * while (true) { } } catch (Exception e) { System.out.println(e.getMessage()); login.dispose(); } System.out.println(intoUser.toString());
					 * if (intoUser != null) infoMessagesLbl.setText(""); } else { dispose(); }
					 **/
				} else {
					JOptionPane.showMessageDialog(null, "Not Implemented yet! -- Booking > Fase 3");
					// ****Fase 3****
					/**
					 * Offer offer = availableOffers.get(0).get(table.getSelectedRow()); System.out.println("Selected Offer:" + offer.toString()); try {
					 * facade.bookOffer(intoUser, "", offer); } catch (RemoteException e) { e.printStackTrace(); }
					 **/
				}
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

		table = new JTable();
		infoLabel.setEnabled(false);
		infoLabel.setForeground(Color.GRAY);
		infoLabel.setLabelFor(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		tablemodel = new DefaultTableModel(null, columnNames);
		table.setModel(tablemodel);

		scrollPane.setBounds(42, 166, 853, 321);

		scrollPane.setViewportView(table);
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
		infoLabel.setBounds(42, 494, 853, 14);
		getContentPane().add(infoLabel);
		getContentPane().add(jButton1);
		getContentPane().add(jButton2);
		getContentPane().add(numberNightLabel);
		getContentPane().add(NumberOfNightTextField);
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

		JButton applyFilterBtn = new JButton("Apply Filters");
		applyFilterBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				String city = cityField.getText();
				String numberOfNights = NumberOfNightTextField.getText();
				int startDay = (int) daySpinner.getValue();
				int month = monthComboBox.getSelectedIndex();
				int year = (int) yearSpinner.getValue();
				Date date = new Date(year - 1900, month, startDay);
				int minPrice = Integer.parseInt(minPriceTextField.getText());
				int maxPrice = Integer.parseInt(maxPriceTextField.getText());
				if (!city.equals("") && !numberOfNights.equals("")) {
					try {
						if (offers != null)
							offers.removeAll(offers);
						List<List<Offer>> availableOffers = facade.searchAvailableOffers(city, numberOfNights, date, minPrice, maxPrice);
						offers.add(availableOffers.get(0));
						offers.add(availableOffers.get(1));
						if (availableOffers.get(0).size() == 0 && availableOffers.get(1).size() == 0) {
							JOptionPane.showMessageDialog(null, "No Offers Found");
						}
						updateTable(availableOffers);
					} catch (RemoteException e) {
						System.out.println("Error at searchAvailableOffers in QueryAvailability: " + e.getMessage());
					}
				}else
					JOptionPane.showMessageDialog(applyFilterBtn, "Select the city and the Number of nights");

			}

		});
		applyFilterBtn.setBounds(42, 121, 853, 23);
		getContentPane().add(applyFilterBtn);

		JButton seeOfferDetailsBtn = new JButton("See Offer Details");
		seeOfferDetailsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int index = table.getSelectedRow();
					Offer offer = (index < offers.get(0).size()) ? offers.get(0).get(index) : offers.get(1).get(index - offers.get(0).size());
					JFrame ad = new AditionaOfferInfoGUI(offer);
					ad.setVisible(true);
				} catch (ArrayIndexOutOfBoundsException e) {
					JOptionPane.showMessageDialog(null, "Select an Offer");
				}
			}
		});
		seeOfferDetailsBtn.setBounds(42, 514, 853, 23);
		getContentPane().add(seeOfferDetailsBtn);
		infoMessagesLbl.setForeground(SystemColor.textHighlight);
		infoMessagesLbl.setBounds(42, 87, 430, 14);

		getContentPane().add(infoMessagesLbl);
	}

	protected void updateTable(List<List<Offer>> availableOffers) {
		Object[][] data = new Object[availableOffers.get(0).size() + availableOffers.get(1).size()][];
		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();

		int row = 0;
		for (Offer offer : availableOffers.get(0)) {
			a.setTime(offer.getFirstDay());
			b.setTime(offer.getLastDay());
			Object[] tmp = { new String(Integer.toString(offer.getOfferNumber())),
					new String(Integer.toString(b.get(Calendar.DAY_OF_YEAR) - a.get(Calendar.DAY_OF_YEAR))), new String(offer.getRuralHouse().toString()),
					new String(offer.getFirstDay().toString()), new String(offer.getLastDay().toString()), new String(Float.toString(offer.getPrice())) };
			data[row] = tmp;
			row++;
		}
		for (Offer offer : availableOffers.get(1)) {
			a.setTime(offer.getFirstDay());
			b.setTime(offer.getLastDay());
			Object[] tmp = { new String(Integer.toString(offer.getOfferNumber())),
					new String(Integer.toString(b.get(Calendar.DAY_OF_YEAR) - a.get(Calendar.DAY_OF_YEAR))), new String(offer.getRuralHouse().toString()),
					new String(offer.getFirstDay().toString()), new String(offer.getLastDay().toString()), new String(Float.toString(offer.getPrice())) };
			data[row] = tmp;
			row++;
		}
		tablemodel = new DefaultTableModel(data, columnNames);
		table.setModel(tablemodel);
		setRenderer();
	}

	private void setRenderer() {
		renderer = new CellRender();
		table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
		table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		table.getColumnModel().getColumn(3).setCellRenderer(renderer);
		table.getColumnModel().getColumn(4).setCellRenderer(renderer);
		table.getColumnModel().getColumn(5).setCellRenderer(renderer);
	}

	protected void updateDate(JSpinner daySpinner, JComboBox<Object> monthComboBox) {
		String day = ((int) daySpinner.getValue() >= 0 && (int) daySpinner.getValue() < 10) ? "0" + daySpinner.getValue() : "" + daySpinner.getValue();
		dateTextField.setText(day + "/" + (String) monthComboBox.getSelectedItem() + "/" + yearSpinner.getValue());
	}

	class CellRender extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JComponent c = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			int bottom = 0;
			int top = 0;
			try {
				if (row == offers.get(0).size() - 1) {
					bottom = 2;
				}
				if ((offers.get(0).size() == 0 || offers.get(0) == null) && row == 0) {
					top = 2;
					bottom = 0;
				}
			} catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "Complete Filter for the search");
			}
			c.setBorder(new MatteBorder(top, 0, bottom, 0, Color.RED));
			return c;
		}
	}

	// ****FASE 3****
	/**
	 * private void setUser(Users user) { intoUser = user; }
	 **/

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
			Vector<ExtraActivity> activityList = offer.getExtraActivities();
			for (int i = 0; i < activityList.size(); i++) {
				Object[] tmp = { new String(activityList.get(i).getNombre()), new String(activityList.get(i).getDescription()),
						new String(activityList.get(i).getOwner().getName()), new String(activityList.get(i).getLugar()),
						new String(activityList.get(i).getFecha().toString()) };
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