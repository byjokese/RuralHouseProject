package gui;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.sql.rowset.Joinable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.lang.Thread;

import businessLogic.ApplicationFacadeInterface;

import com.toedter.calendar.JCalendar;

import domain.ExtraActivity;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;

public class EditMyOfferGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField priceField;
	private JList<String> availableActivitieslist;
	private Vector<ExtraActivity> availableActivityVector;
	private DefaultListModel<String> availableDataString;
	private JList<String> offerlist;
	private DefaultListModel<String> offersDataString;
	private ArrayList<Offer> offerArray;
	private Owner ownerI;
	private ApplicationFacadeInterface facade;

	/**
	 * Create the frame.
	 */
	public EditMyOfferGUI(Owner owner) {
		this.ownerI = owner;
		EditMyOfferGUI thisFrame = this;
		setTitle("Rural House System");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 985, 596);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		facade = StartWindow.getBusinessLogic();

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(179, 30, 2, 566);
		contentPane.add(separator);

		JLabel lblNewLabel = new JLabel("Edit My Offer");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 969, 30);
		contentPane.add(lblNewLabel);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 30, 969, 2);
		contentPane.add(separator_1);

		JLabel nameLbl = new JLabel("Name:");
		nameLbl.setBounds(443, 56, 53, 21);
		contentPane.add(nameLbl);

		JLabel nameField = new JLabel("");
		nameField.setBounds(484, 60, 124, 14);
		contentPane.add(nameField);

		JLabel adressLbl = new JLabel("Address:");
		adressLbl.setBounds(632, 56, 53, 21);
		contentPane.add(adressLbl);

		JLabel addressField = new JLabel("");
		addressField.setBounds(695, 60, 156, 14);
		contentPane.add(addressField);

		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setBounds(861, 56, 53, 21);
		contentPane.add(lblNumber);

		JLabel numberLbl = new JLabel("");
		numberLbl.setBounds(924, 60, 28, 14);
		contentPane.add(numberLbl);

		JLabel pricelbl = new JLabel("Price:");
		pricelbl.setBounds(443, 104, 53, 14);
		contentPane.add(pricelbl);

		JCalendar FirstDaycalendar = new JCalendar();
		FirstDaycalendar.getDayChooser().setDay(30);
		FirstDaycalendar.getMonthChooser().setMonth(6);
		FirstDaycalendar.setBounds(new Rectangle(150, 106, 225, 180));
		FirstDaycalendar.setBounds(443, 157, 242, 146);
		contentPane.add(FirstDaycalendar);

		JCalendar LastDaycalendar = new JCalendar();
		LastDaycalendar.getDayChooser().setDay(5);
		LastDaycalendar.setBounds(new Rectangle(150, 106, 225, 180));
		LastDaycalendar.setBounds(710, 157, 242, 146);
		contentPane.add(LastDaycalendar);

		priceField = new JTextField();
		priceField.setBounds(515, 101, 97, 20);
		contentPane.add(priceField);
		priceField.setColumns(10);

		JLabel firstdayLbl = new JLabel("First Day:");
		firstdayLbl.setBounds(443, 132, 66, 14);
		contentPane.add(firstdayLbl);

		JLabel lastDayLbl = new JLabel("Last Day:");
		lastDayLbl.setBounds(710, 132, 66, 14);
		contentPane.add(lastDayLbl);

		JList<String> selectedlist = new JList<String>(); // OBJECT
		DefaultListModel<String> selectedDataString = new DefaultListModel<String>(); // Activities list included in an offer, part of the GUI.
		Vector<ExtraActivity> selectedActivitiesVector = new Vector<ExtraActivity>();
		selectedlist.setBounds(443, 351, 509, 162);
		contentPane.add(selectedlist);

		offerlist = new JList<String>(); // OBJECT
		offersDataString = new DefaultListModel<String>(); // Contains the data shown in the list, visible to the owner in the GUI.
		offerArray = new ArrayList<Offer>(); // Contains all the offers of the owner.
		for (RuralHouse h : this.ownerI.getRuralHouses()) {
			for (Offer o : h.getAllOffers()) {
				offersDataString.addElement(o.getOfferNumber() + " || " + o.getPrice());
				offerArray.add(o);
			}
		}

		availableActivitieslist = new JList<String>(); // OBJECT
		availableDataString = new DefaultListModel<String>(); // Available activities, part of the GUI.
		availableActivityVector = new Vector<ExtraActivity>(); // Conatins all the Extra Activities from the owner.
		for (ExtraActivity a : this.ownerI.getExtraActivities()) {
			// Contains the data shown in the list, visible to the owner in the GUI.
			availableDataString.addElement(a.getNombre() + " || " + a.getLugar() + " || " + a.getFecha());
			availableActivityVector.add(a); // Contains all the ExtraActivities of the owner.
		}
		availableActivitieslist.setModel(availableDataString);
		availableActivitieslist.setBounds(191, 56, 191, 454);
		contentPane.add(availableActivitieslist);

		// Offer selector to edit it.
		offerlist.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("deprecation")
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {// This line prevents double events
					offerlist.setEnabled(false);
					int index = offerlist.getSelectedIndex();
					if (index == -1) {
						nameField.setText("");
						addressField.setText("");
						numberLbl.setText("");
						priceField.setText("");
					} else {
						selectedDataString.clear();
						selectedActivitiesVector.clear();
						availableActivitieslist.clearSelection();

						Offer o = offerArray.get(index);

						System.out.println("___________");
						System.out.println("Thread: " + Thread.currentThread().getId());
						System.out.println("Size: " + offerArray.get(index).getExtraActivities().size());
						System.out.println("index:" + index);
						System.out.println("Array: " + offerArray);
						System.out.println("List: " + offerlist);
						System.out.println("AllOffers: ");
						for (Offer of : offerArray) {
							System.out.println(of);
						}
						System.out.println("Activities: ");
						for (ExtraActivity a : offerArray.get(index).getExtraActivities()) {
							System.out.println(a);
						}

						FirstDaycalendar.getDayChooser().setDay(o.getFirstDay().getDay());
						FirstDaycalendar.getMonthChooser().setMonth(o.getFirstDay().getMonth());
						FirstDaycalendar.getYearChooser().setYear(o.getFirstDay().getYear());
						LastDaycalendar.getDayChooser().setDay(o.getLastDay().getDay());
						LastDaycalendar.getMonthChooser().setMonth(o.getLastDay().getMonth());
						LastDaycalendar.getYearChooser().setYear(o.getLastDay().getYear());
						nameField.setText(o.getRuralHouse().getCity());
						addressField.setText(o.getRuralHouse().getAddress());
						numberLbl.setText(Integer.toString(o.getRuralHouse().getNumber()));
						priceField.setText(Float.toString(o.getPrice()));

						for (ExtraActivity a : o.getExtraActivities()) {
							selectedDataString.addElement(a.getNombre() + " || " + a.getLugar() + " || " + a.getFecha());
							selectedActivitiesVector.add(a); // All the activities in the offer..
						}
						selectedlist.setModel(selectedDataString);
					}

					offerlist.setEnabled(true);
				}
			}
		});
		offerlist.setModel(offersDataString);
		offerlist.setBounds(10, 38, 164, 512);
		contentPane.add(offerlist);

		JButton addBtn = new JButton(">");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (availableActivitieslist.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "There is no activity selected");
				} else {
					String nombre = availableActivityVector.get(availableActivitieslist.getSelectedIndex()).getNombre();
					String lugar = availableActivityVector.get(availableActivitieslist.getSelectedIndex()).getLugar();
					String fecha = availableActivityVector.get(availableActivitieslist.getSelectedIndex()).getFecha().toString();
					String entrada = nombre + " || " + lugar + " || " + fecha;
					if (!validar(entrada, selectedDataString)) {
						selectedDataString.addElement(entrada);
						selectedActivitiesVector.add(availableActivityVector.get(availableActivitieslist.getSelectedIndex()));
					} else {
						JOptionPane.showMessageDialog(null, "This activity has already been selected");
					}
				}
			}
		});
		addBtn.setFont(new Font("Arial Black", Font.PLAIN, 13));
		addBtn.setBounds(390, 366, 43, 41);
		contentPane.add(addBtn);

		JButton removeBtn = new JButton("<");
		removeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedlist.getSelectedIndex() == -1) {

					JOptionPane.showMessageDialog(null, "There is no activity selected");
				} else {
					int index = selectedlist.getSelectedIndex();
					selectedDataString.remove(index);
					selectedActivitiesVector.remove(index);
				}
			}
		});
		removeBtn.setFont(new Font("Arial Black", Font.PLAIN, 13));
		removeBtn.setBounds(390, 418, 43, 41);
		contentPane.add(removeBtn);

		JButton Savebtn = new JButton("Save ");
		Savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = offerlist.getSelectedIndex();
				if (index != -1) {
					Offer o = offerArray.get(index);
					if (o.getBooking() == null) {
						Date firstDay = trim(new Date(FirstDaycalendar.getCalendar().getTime().getTime()));
						Date lastDay = trim(new Date(LastDaycalendar.getCalendar().getTime().getTime()));
						if (firstDay.compareTo(lastDay) <= 0) {
							float price = Float.parseFloat(priceField.getText());
							if (priceField.equals("") || price < 0) {
								JOptionPane.showMessageDialog(Savebtn, "Incorrect entry, check price Field.");
							} else {
								try {
									RuralHouse rh = getHouse(o);
									System.out.println(selectedActivitiesVector);
									Offer offer = facade.updateOffer(o, rh, price, firstDay, lastDay, selectedActivitiesVector);
									if (offer != null) {
										offerlist.clearSelection();
										availableActivitieslist.clearSelection();
										selectedActivitiesVector.clear();
										selectedDataString.clear();
										updateOffers();
										JOptionPane.showMessageDialog(Savebtn, "Save Correctly");
									}
								} catch (HeadlessException e1) {
									e1.printStackTrace();
								} catch (RemoteException e1) {
									e1.printStackTrace();
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "Invalid date");
						}
					} else {
						JOptionPane.showMessageDialog(Savebtn, "This offer contains bookings, cannot be update");
					}
				} else {
					JOptionPane.showMessageDialog(Savebtn, "There is no offer selected");
				}
			}
		});
		Savebtn.setBounds(466, 520, 115, 30);
		contentPane.add(Savebtn);

		JButton Deletebtn = new JButton("Delete");
		Deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = offerlist.getSelectedIndex();
				if (i != -1) {
					Offer o = offerArray.get(i);
					if (o.getBooking() == null) {
						try {
							facade.deleteOffer(o);
							JOptionPane.showMessageDialog(Deletebtn, "Deleted Correctly");
							offerlist.clearSelection();
							offersDataString.remove(i);
							offerArray.remove(o);
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(Deletebtn, "This Offer contains bookings, cannot be deleted");
					}
				} else {
					JOptionPane.showMessageDialog(Deletebtn, "There is no Rural House selected");
				}
			}

		});
		Deletebtn.setBounds(815, 520, 108, 30);
		contentPane.add(Deletebtn);

		JLabel availableActivitiesLbl = new JLabel("Available Activities:");
		availableActivitiesLbl.setBounds(190, 41, 164, 15);
		contentPane.add(availableActivitiesLbl);

		JLabel selectedLbl = new JLabel("Selected activities:");
		selectedLbl.setBounds(443, 325, 146, 15);
		contentPane.add(selectedLbl);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnClose.setBounds(642, 520, 115, 30);
		contentPane.add(btnClose);

		JButton createActivityBtn = new JButton("Create Activity");
		createActivityBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateExtraActivityGUI a = new CreateExtraActivityGUI(ownerI, thisFrame);
				a.setVisible(true);
			}
		});
		createActivityBtn.setBounds(191, 521, 191, 30);
		contentPane.add(createActivityBtn);
	}

	private boolean validar(String nuevo, DefaultListModel<String> lista) {
		return lista.contains(nuevo);
	}

	private ArrayList<Offer> getOffers() {
		return this.offerArray;
	}

	public void addActivity(ExtraActivity extra) throws RemoteException {
		availableDataString.addElement(extra.getNombre() + " || " + extra.getLugar() + " || " + extra.getFecha());
		availableActivitieslist.setModel(availableDataString);
		availableActivityVector.add(extra);
	}

	private RuralHouse getHouse(Offer offer) {
		for (RuralHouse rural : this.ownerI.getRuralHouses()) {
			if (rural.getAllOffers().contains(offer))
				return rural;
		}
		return null;
	}

	private void updateOffers() throws RemoteException {
		offerArray.clear();
		offersDataString.clear();
		for (Offer o : facade.getUpdatedOffers(ownerI)) {
			offerArray.add(o);
			offersDataString.addElement(new String(o.getOfferNumber() + " || " + o.getPrice()));
		}
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
}
