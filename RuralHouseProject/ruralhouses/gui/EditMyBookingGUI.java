package gui;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import businessLogic.ApplicationFacadeInterface;
import domain.Booking;
import domain.Client;
import domain.ExtraActivity;
import domain.Owner;
import domain.RuralHouse;

import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import java.awt.Rectangle;

public class EditMyBookingGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField telephonetextField;
	private JTextField emailtextField;
	private MaskFormatter mask;

	/**
	 * Create the frame.
	 */
	public EditMyBookingGUI(Client client) {
		setTitle("Rural House System");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(179, 30, 2, 272);
		contentPane.add(separator);

		JLabel lblNewLabel = new JLabel("Edit My Booking");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 542, 30);
		contentPane.add(lblNewLabel);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 30, 558, 2);
		contentPane.add(separator_1);

		JLabel lblNewLabel_1 = new JLabel("City:");
		lblNewLabel_1.setBounds(191, 39, 53, 21);
		contentPane.add(lblNewLabel_1);

		JLabel cityLabel = new JLabel("");
		cityLabel.setBounds(268, 41, 274, 14);
		contentPane.add(cityLabel);

		JLabel lblPlace = new JLabel("Address:");
		lblPlace.setBounds(191, 67, 53, 14);
		contentPane.add(lblPlace);

		JLabel addresslabel = new JLabel("");
		addresslabel.setBounds(268, 67, 274, 14);
		contentPane.add(addresslabel);

		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setBounds(191, 92, 53, 21);
		contentPane.add(lblNumber);

		JLabel numberlabel = new JLabel("");
		numberlabel.setBounds(268, 95, 38, 14);
		contentPane.add(numberlabel);

		JLabel lblFirstDay = new JLabel("First Day:");
		lblFirstDay.setBounds(191, 124, 53, 21);
		contentPane.add(lblFirstDay);

		JLabel firstlabel = new JLabel("");
		firstlabel.setBounds(254, 127, 108, 14);
		contentPane.add(firstlabel);

		JLabel lblLastDay = new JLabel("Last Day:");
		lblLastDay.setBounds(384, 124, 53, 21);
		contentPane.add(lblLastDay);

		JLabel lastlabel = new JLabel("");
		lastlabel.setBounds(465, 127, 77, 14);
		contentPane.add(lastlabel);

		JLabel lblTelephoneNumber = new JLabel("Contact number:");
		lblTelephoneNumber.setBounds(191, 188, 108, 21);
		contentPane.add(lblTelephoneNumber);

		mask = null;

		try {
			mask = new MaskFormatter("###-###-###");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		mask.setPlaceholderCharacter('0');
		telephonetextField = new JFormattedTextField(mask);
		telephonetextField.setBounds(316, 188, 151, 21);
		contentPane.add(telephonetextField);

		JLabel lblBookingDay = new JLabel("Booking Day:");
		lblBookingDay.setBounds(191, 156, 77, 21);
		contentPane.add(lblBookingDay);

		JLabel bookinglabel = new JLabel("");
		bookinglabel.setBounds(278, 159, 115, 14);
		contentPane.add(bookinglabel);

		emailtextField = new JTextField();
		emailtextField.setBounds(316, 220, 226, 20);
		contentPane.add(emailtextField);
		emailtextField.setColumns(10);

		DefaultListModel<String> bookingsString = new DefaultListModel<String>();
		ArrayList<Booking> bookingsList = new ArrayList<Booking>();
		JList<String> list = new JList<String>();
		list.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("deprecation")
			public void valueChanged(ListSelectionEvent e) {
				int i = list.getSelectedIndex();
				if (i == -1) {
					cityLabel.setText("");
					addresslabel.setText("");
					firstlabel.setText("");
					lastlabel.setText("");
					numberlabel.setText("");
					bookinglabel.setText("");
					telephonetextField.setText("000-000-000");;
					emailtextField.setText("");
				} else {
					Booking bo = bookingsList.get(i);
					cityLabel.setText(bo.getOffer().getRuralHouse().getCity());
					addresslabel.setText(bo.getOffer().getRuralHouse().getAddress());
					numberlabel.setText(Integer.toString(bo.getOffer().getRuralHouse().getNumber()));
					firstlabel.setText(bo.getOffer().getFirstDay().toString().substring(0, 12));
					lastlabel.setText(bo.getOffer().getLastDay().toString().substring(0, 12));
					bookinglabel.setText(bo.getBookingDate().toString());
					telephonetextField.setText(bo.getTelephone());
					emailtextField.setText(bo.getEmail());
				}
			}
		});
		for (Booking booking : client.getBooks()) {
			Calendar calendario = Calendar.getInstance();
			Date today = calendario.getTime();
			if (today.compareTo(booking.getOffer().getFirstDay()) < 0) {
				bookingsList.add(booking);
				bookingsString.addElement(booking.getOffer().getRuralHouse().getCity() + " || " + booking.getOffer().getFirstDay().toString() + " || "
						+ booking.getOffer().getLastDay().toString());
			}
		}
		list.setModel(bookingsString);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(SystemColor.control);
		list.setBounds(0, 38, 174, 223);
		contentPane.add(list);

		JButton Savebtn = new JButton("Save ");
		Savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1) {
					Booking bo = bookingsList.get(index);
					String telephone = telephonetextField.getText();
					String email = emailtextField.getText();
					if (telephone.compareToIgnoreCase("000-000-000") == 0 || email.equals("")) {
						JOptionPane.showMessageDialog(Savebtn, "You have to insert a valid telephone number and e-mail");
					} else {
						try {
							facade.updateBooking(bo, client, telephone, email);
							JOptionPane.showMessageDialog(Savebtn, "Save Correctly");
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(Savebtn, "There is no Booking selected");
				}
			}
		});
		Savebtn.setBounds(191, 261, 115, 30);
		contentPane.add(Savebtn);

		JButton Cancelbtn = new JButton("Cancel");
		Cancelbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1) {
					Booking bo = bookingsList.get(index);
					Calendar calendario = Calendar.getInstance();
					Date today = calendario.getTime();
					if (comparaFechas(today, bo.getOffer().getFirstDay())) {
						try {
							facade.cancelBooking(bo, client);
							client.getBooks().remove(bo);
							JOptionPane.showMessageDialog(Cancelbtn, "Cancelled Correctly");
							list.clearSelection();
							DefaultListModel<String> bookingsString = new DefaultListModel<String>();
							bookingsList.clear();
							for (Booking booking : client.getBooks()) {
								if (today.compareTo(booking.getOffer().getFirstDay()) < 0) {
									bookingsList.add(booking);
									bookingsString.addElement(booking.getOffer().getRuralHouse().getCity() + " || " + booking.getOffer().getFirstDay().toString() + " || "
											+ booking.getOffer().getLastDay().toString());
								}
							}
							list.setModel(bookingsString);
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(Cancelbtn, "This Booking cannot be cancelled");
					}
				} else {
					JOptionPane.showMessageDialog(Cancelbtn, "There is no Booking selected");
				}
			}

		});
		Cancelbtn.setBounds(316, 261, 108, 30);
		contentPane.add(Cancelbtn);

		JButton closebtn = new JButton("Close");
		closebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closebtn.setBounds(434, 261, 108, 30);
		contentPane.add(closebtn);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(191, 220, 108, 21);
		contentPane.add(lblEmail);

	}

	private boolean comparaFechas(Date today, Date booking) { // me dice si hay más de 15 días de diferencia entre ambas
		if (booking.getTime() - today.getTime() > (15 * 24 * 60 * 60 * 1000))
			return true;
		else
			return false;
	}
}
