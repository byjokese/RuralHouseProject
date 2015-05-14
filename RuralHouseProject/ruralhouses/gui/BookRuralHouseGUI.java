package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import businessLogic.ApplicationFacadeInterface;
import domain.Booking;
import domain.Client;
import domain.ExtraActivity;
import domain.Offer;

import javax.swing.JScrollPane;

public class BookRuralHouseGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> availLis = new DefaultListModel<String>();
	private DefaultListModel<String> selectes = new DefaultListModel<String>();
	private Vector<ExtraActivity> selected = new Vector<ExtraActivity>();
	private JTextField mailtextField;

	/**
	 * Create the frame.
	 * @param frame 
	 */
	public BookRuralHouseGUI(Offer o, Client c, ClientGUI frame) {
		setBounds(100, 100, 719, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		JLabel lblClientName = new JLabel("Client Name: ");
		lblClientName.setBounds(12, 53, 236, 15);
		lblClientName.setText(lblClientName.getText() + c.getName());
		contentPane.add(lblClientName);

		for (ExtraActivity ex : o.getExtraActivities()) {
			availLis.addElement(ex.getNombre() + " en " + ex.getLugar());
		}

		JLabel lblAvailableExraactivities = new JLabel("Availables Activities:");
		lblAvailableExraactivities.setBounds(12, 79, 236, 15);
		contentPane.add(lblAvailableExraactivities);

		JLabel lblOfferDetaills = new JLabel("Offer Detaills:");
		lblOfferDetaills.setBounds(236, 79, 256, 15);
		contentPane.add(lblOfferDetaills);

		JLabel lblFisrtDay = new JLabel("Fisrt Day: ");
		lblFisrtDay.setBounds(236, 127, 357, 15);
		lblFisrtDay.setText(lblFisrtDay.getText() + o.getFirstDay());
		contentPane.add(lblFisrtDay);

		JLabel lblLastDay = new JLabel("Last Day: ");
		lblLastDay.setBounds(236, 153, 357, 15);
		lblLastDay.setText(lblLastDay.getText() + o.getLastDay());
		contentPane.add(lblLastDay);

		JLabel lblPrice = new JLabel("Price: ");
		lblPrice.setBounds(236, 179, 357, 15);
		lblPrice.setText(lblPrice.getText() + o.getPrice());
		contentPane.add(lblPrice);

		JLabel lblPhoneNumber = new JLabel("Phone Number: ");
		lblPhoneNumber.setBounds(236, 205, 175, 15);
		contentPane.add(lblPhoneNumber);
		// fromato de numero telefonoco
		MaskFormatter mask = null;

		try {
			mask = new MaskFormatter("###-###-###");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		mask.setPlaceholderCharacter('0');
		JFormattedTextField formattedTextField = new JFormattedTextField(mask);
		formattedTextField.setBounds(387, 205, 175, 20);
		contentPane.add(formattedTextField);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 105, 195, 378);
		contentPane.add(scrollPane);

		JList<String> Availableslist = new JList<String>();
		scrollPane.setViewportView(Availableslist);
		Availableslist.setModel(availLis);

		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Availableslist.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(button, "Select An Extra Activity to add");
				} else {
					int idx = Availableslist.getSelectedIndex();
					String datos = o.getExtraActivities().get(idx).getNombre() + " en " + o.getExtraActivities().get(idx).getLugar();
					if (selectes.contains(datos)) {
						JOptionPane.showMessageDialog(button, "this Extra Activity is Ready Choosed");
					} else {
						selectes.addElement(datos);
						selected.add(o.getExtraActivities().get(idx));
					}

				}
			}
		});
		button.setBounds(236, 286, 44, 41);
		contentPane.add(button);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(303, 267, 390, 144);
		contentPane.add(scrollPane_1);

		JList<String> selectedList = new JList<String>();
		scrollPane_1.setViewportView(selectedList);
		selectedList.setModel(selectes);

		JButton button_1 = new JButton("<");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedList.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(button, "Select an choosed Extra Activity to remove");
				} else {
					int a = selectedList.getSelectedIndex();
					selectes.remove(a);
					selected.remove(a);

				}

			}
		});
		button_1.setBounds(236, 338, 44, 41);
		contentPane.add(button_1);

		JButton btnNewButton = new JButton("Booking It");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formattedTextField.getText().compareToIgnoreCase("000-000-000") == 0) {
					JOptionPane.showMessageDialog(formattedTextField, "Wrong phone Number");
				} else {
					if (isValidEmailAddress(mailtextField.getText())) {
						try {
							// System.out.println(c.getUsername());
							Booking book = facade.bookOffer(c, o, selected, formattedTextField.getText(), mailtextField.getText());
							c.addBook(book);
							JOptionPane.showMessageDialog(null, "Booking Made.");
							frame.updateClient();
							dispose();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(formattedTextField, "Invalid Mail Adress..");
					}

				}
			}
		});
		btnNewButton.setBounds(303, 425, 390, 58);
		contentPane.add(btnNewButton);

		JLabel lblBookHouse = new JLabel("Book House");
		lblBookHouse.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookHouse.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBookHouse.setBounds(0, 0, 721, 34);
		contentPane.add(lblBookHouse);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 30, 721, 15);
		contentPane.add(separator);

		JLabel lblMail = new JLabel("Mail: ");
		lblMail.setBounds(236, 231, 115, 15);
		contentPane.add(lblMail);

		mailtextField = new JTextField();
		mailtextField.setBounds(387, 231, 175, 20);
		contentPane.add(mailtextField);
		mailtextField.setColumns(10);

	}

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
}
