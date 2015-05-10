package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import businessLogic.ApplicationFacadeInterface;
import domain.Client;
import domain.ExtraActivity;
import domain.Offer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class RuralHouseBookGUI extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<String> availLis = new DefaultListModel<String>();
	private DefaultListModel<String> selectes = new DefaultListModel<String>();
	private Vector<ExtraActivity> selected = new Vector<ExtraActivity>();
	private JTextField mailtextField;

	/**
	 * Create the frame.
	 */
	public RuralHouseBookGUI(Offer o, Client c) {
		setBounds(100, 100, 737, 610);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		JLabel lblClientName = new JLabel("Client Name: ");
		lblClientName.setBounds(25, 52, 236, 15);
		lblClientName.setText(lblClientName.getText() + c.getName());
		contentPane.add(lblClientName);

		JList<String> Availableslist = new JList<String>();
		Availableslist.setBounds(12, 120, 195, 363);

		for (ExtraActivity ex : o.getExtraActivities()) {
			availLis.addElement(ex.getNombre() + " en " + ex.getLugar());
		}
		Availableslist.setModel(availLis);
		contentPane.add(Availableslist);

		JLabel lblAvailableExraactivities = new JLabel("Availables ExraActivities");
		lblAvailableExraactivities.setBounds(12, 79, 236, 15);
		contentPane.add(lblAvailableExraactivities);

		JLabel lblOfferDetaills = new JLabel("Offer Detaills:");
		lblOfferDetaills.setBounds(347, 101, 256, 15);
		contentPane.add(lblOfferDetaills);

		JLabel lblFisrtDay = new JLabel("Fisrt Day: ");
		lblFisrtDay.setBounds(246, 128, 357, 15);
		lblFisrtDay.setText(lblFisrtDay.getText() + o.getFirstDay());
		contentPane.add(lblFisrtDay);

		JLabel lblLastDay = new JLabel("Last Day: ");
		lblLastDay.setBounds(246, 172, 357, 15);
		lblLastDay.setText(lblLastDay.getText() + o.getLastDay());
		contentPane.add(lblLastDay);

		JLabel lblPrice = new JLabel("Price: ");
		lblPrice.setBounds(246, 206, 357, 15);
		lblPrice.setText(lblPrice.getText() + o.getPrice());
		contentPane.add(lblPrice);

		JLabel lblPhoneNumber = new JLabel("Phone Number: ");
		lblPhoneNumber.setBounds(246, 245, 175, 15);
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
		formattedTextField.setBounds(455, 233, 236, 41);
		contentPane.add(formattedTextField);

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
		button.setBounds(219, 339, 44, 41);
		contentPane.add(button);

		JList<String> selectedList = new JList<String>();
		selectedList.setBounds(303, 351, 388, 144);
		selectedList.setModel(selectes);
		contentPane.add(selectedList);

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
		button_1.setBounds(219, 378, 44, 41);
		contentPane.add(button_1);

		JButton btnNewButton = new JButton("Booking It");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (formattedTextField.getText().compareToIgnoreCase("000-000-000") == 0) {
					JOptionPane.showMessageDialog(formattedTextField, "Wrong phone Number");
				} else {
					if(isValidEmailAddress(mailtextField.getText())){
						try {
//							System.out.println(c.getUsername());
							facade.bookOffer(c, o, selected, formattedTextField.getText(),mailtextField.getText());
							JOptionPane.showMessageDialog(null, "Booking Made.");
							dispose();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}else{
						JOptionPane.showMessageDialog(formattedTextField, "Invalid Mail Adress..");
						
					}

				}
			}
		});
		btnNewButton.setBounds(382, 517, 205, 58);
		contentPane.add(btnNewButton);

		JLabel lblBookHouse = new JLabel("Book House");
		lblBookHouse.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookHouse.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBookHouse.setBounds(0, 0, 721, 29);
		contentPane.add(lblBookHouse);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 26, 721, 15);
		contentPane.add(separator);
		
		JLabel lblMail = new JLabel("Mail: ");
		lblMail.setBounds(246, 301, 115, 15);
		contentPane.add(lblMail);
		
		mailtextField = new JTextField();
		mailtextField.setBounds(432, 286, 276, 29);
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
