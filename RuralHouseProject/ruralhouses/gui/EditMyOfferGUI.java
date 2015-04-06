package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import domain.ExtraActivity;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import businessLogic.ApplicationFacadeInterface;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.Font;

import javax.swing.JLabel;

import com.toedter.calendar.JCalendar;

import businessLogic.FacadeImplementation;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class EditMyOfferGUI extends JFrame {

	private JPanel contentPane;
	private JTextField priceField;

	/**
	 * Create the frame.
	 */
	public EditMyOfferGUI(Owner owner) {
		setTitle("Rural House System");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 985, 596);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();

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

		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setBounds(435, 38, 53, 21);
		contentPane.add(lblNewLabel_1);

		JLabel label = new JLabel("");
		label.setBounds(498, 41, 124, 14);
		contentPane.add(label);

		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(632, 41, 53, 14);
		contentPane.add(lblAddress);

		JLabel label_1 = new JLabel("");
		label_1.setBounds(695, 41, 156, 14);
		contentPane.add(label_1);

		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setBounds(861, 42, 53, 14);
		contentPane.add(lblNumber);

		JLabel label_2 = new JLabel("");
		label_2.setBounds(924, 41, 28, 14);
		contentPane.add(label_2);

		JLabel lblPrecio = new JLabel("Price:");
		lblPrecio.setBounds(435, 82, 53, 14);
		contentPane.add(lblPrecio);

		JCalendar FirstDaycalendar = new JCalendar();
		FirstDaycalendar.setBounds(new Rectangle(150, 106, 225, 180));
		FirstDaycalendar.setBounds(443, 157, 242, 146);
		contentPane.add(FirstDaycalendar);

		JCalendar LastDaycalendar = new JCalendar();
		LastDaycalendar.setBounds(new Rectangle(150, 106, 225, 180));
		LastDaycalendar.setBounds(710, 157, 242, 146);
		contentPane.add(LastDaycalendar);

		priceField = new JTextField();
		priceField.setBounds(515, 79, 97, 20);
		contentPane.add(priceField);
		priceField.setColumns(10);

		JLabel lblFirstDay = new JLabel("First Day:");
		lblFirstDay.setBounds(515, 121, 66, 14);
		contentPane.add(lblFirstDay);

		JLabel lblLastDay = new JLabel("Last Day:");
		lblLastDay.setBounds(797, 121, 66, 14);
		contentPane.add(lblLastDay);

		JList selectedlist = new JList();
		DefaultListModel<String> selectedActivities = new DefaultListModel<String>();
		Vector<ExtraActivity> vectorlistSeleccion = new Vector<ExtraActivity>();
		selectedlist.setBounds(443, 351, 509, 162);
		contentPane.add(selectedlist);

		JList offerlist = new JList();
		DefaultListModel<String> offers = new DefaultListModel<String>();
		ArrayList<Offer> arrayOffer = new ArrayList<Offer>();
		for (RuralHouse h : owner.getRuralHouses()) {
			for (Offer o : h.getAllOffers()) {
				offers.addElement(o.getOfferNumber() +" || " + o.getPrice());
				arrayOffer.add(o);
			}
		}
		offerlist.addListSelectionListener(new ListSelectionListener() {
			public synchronized void valueChanged(ListSelectionEvent e) {
				int i = offerlist.getSelectedIndex();
				if (i == -1) {
					label.setText("");
					label_1.setText("");
					label_2.setText("");
					priceField.setText("");
				} else {
					selectedActivities.clear();
					Offer o = arrayOffer.get(i);
					label.setText(o.getRuralHouse().getCity());
					label_1.setText(o.getRuralHouse().getAddress());
					label_2.setText(Integer.toString(o.getRuralHouse().getNumber()));
					priceField.setText(Float.toString(o.getPrice()));
					for (ExtraActivity a : o.getExtraActivities()) {
						selectedActivities.addElement(a.getNombre() + " || " + a.getLugar() + " || " + a.getFecha());
						vectorlistSeleccion.add(a);
					}
					selectedlist.setModel(selectedActivities);
				}
			}
		});
		offerlist.setModel(offers);
		offerlist.setBounds(10, 38, 164, 512);
		contentPane.add(offerlist);

		JList availablelist = new JList();
		DefaultListModel<String> availableActivities = new DefaultListModel<String>();
		Vector<ExtraActivity> vectorlistAvailables = new Vector<ExtraActivity>();
		for (ExtraActivity a : owner.getExtraActivities()) {
			availableActivities.addElement(a.getNombre() + " || " + a.getLugar() + " || " + a.getFecha());
			vectorlistAvailables.add(a);
		}
		availablelist.setModel(availableActivities);
		availablelist.setBounds(191, 56, 191, 494);
		contentPane.add(availablelist);

		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (availablelist.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "There is no activity selected");
				} else {
					// JOptionPane.showMessageDialog(null,lista.get(list.getSelectedIndex()).getNombre());
					String nombre = vectorlistAvailables.get(availablelist.getSelectedIndex()).getNombre();
					String lugar = vectorlistAvailables.get(availablelist.getSelectedIndex()).getLugar();
					String fecha = vectorlistAvailables.get(availablelist.getSelectedIndex()).getFecha().toString();
					String entrada = nombre + " || " + lugar + " || " + fecha;
					if (!validar(entrada, selectedActivities)) {
						selectedActivities.addElement(entrada);
						vectorlistSeleccion.add(vectorlistAvailables.get(availablelist.getSelectedIndex()));
					} else {

						JOptionPane.showMessageDialog(null, "This activity has already been selected");
					}
				}
			}
		});
		button.setFont(new Font("Arial Black", Font.BOLD, 14));
		button.setBounds(390, 366, 53, 53);
		contentPane.add(button);

		JButton button_1 = new JButton("<");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedlist.getSelectedIndex() == -1) {

					JOptionPane.showMessageDialog(null, "There is no activity selected");
				} else {
					int index = selectedlist.getSelectedIndex();
					vectorlistSeleccion.remove(index);
					selectedActivities.remove(index);
				}
			}
		});
		button_1.setFont(new Font("Arial Black", Font.BOLD, 14));
		button_1.setBounds(392, 430, 51, 53);
		contentPane.add(button_1);

		JButton Savebtn = new JButton("Save ");
		Savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = offerlist.getSelectedIndex();
				if (i != -1) {
					Offer o = arrayOffer.get(i);
					if (o.getBooking() == null) {
						Date firstDay = trim(new Date(FirstDaycalendar.getCalendar().getTime().getTime()));
						Date lastDay = trim(new Date(LastDaycalendar.getCalendar().getTime().getTime()));
						int res = firstDay.compareTo(lastDay);
						if (res <= 0) {
							if (priceField.equals("")) {
								JOptionPane.showMessageDialog(Savebtn, "You have to insert a prize");
							} else {
								float price = Float.parseFloat(priceField.getText());
								if (price > 0) {
									try {
										Offer offe = facade.updateOffer(o, price, firstDay, lastDay, vectorlistSeleccion);
										JOptionPane.showMessageDialog(Savebtn, "Save Correctly");
										selectedActivities.clear();
										offers.remove(i);
										offers.add(i,offe.getOfferNumber()+ " || "+offe.getPrice());
										arrayOffer.remove(i);
										arrayOffer.add(i, offe);
									} catch (HeadlessException e1) {
										e1.printStackTrace();
									} catch (RemoteException e1) {
										e1.printStackTrace();
									}
								} else {
									JOptionPane.showMessageDialog(Savebtn, "You have to insert a correct prize");
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
					Offer o = arrayOffer.get(i);
					if (o.getBooking() == null) {
						try {
							facade.deleteOffer(o);
							JOptionPane.showMessageDialog(Deletebtn, "Delete Correctly");
							offerlist.clearSelection();
							offers.remove(i);
							arrayOffer.remove(o);
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
		Deletebtn.setBounds(775, 520, 108, 30);
		contentPane.add(Deletebtn);

		JLabel lblAvailableActivities = new JLabel("Available Activities:");
		lblAvailableActivities.setBounds(190, 41, 164, 15);
		contentPane.add(lblAvailableActivities);

		JLabel selectedlabel = new JLabel("Selected activities:");
		selectedlabel.setBounds(443, 325, 146, 15);
		contentPane.add(selectedlabel);

	}

	private boolean validar(String nuevo, DefaultListModel<String> lista) {

		return lista.contains(nuevo);

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
