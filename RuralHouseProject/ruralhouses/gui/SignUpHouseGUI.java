package gui;

import java.awt.HeadlessException;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.JTextArea;

import businessLogic.ApplicationFacadeInterface;
import domain.Owner;
import domain.RuralHouse;

import javax.swing.JSeparator;

public class SignUpHouseGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ciudadtextField;
	private JTextField calletextField;
	private JTextField numerotextField;

	/**
	 * Create the frame.
	 */
	public SignUpHouseGUI(Owner owner, OwnerGUI parent) {
		setTitle("Rural House System");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 544, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		JLabel lblCiudadcity = new JLabel("City :");
		lblCiudadcity.setBounds(24, 46, 58, 21);
		contentPane.add(lblCiudadcity);

		JLabel lblCalle = new JLabel("Address :");
		lblCalle.setBounds(24, 94, 58, 21);
		contentPane.add(lblCalle);

		JLabel lblNumero = new JLabel("Number:");
		lblNumero.setBounds(360, 94, 60, 21);
		contentPane.add(lblNumero);

		JLabel lblDescripccion = new JLabel("Description:");
		lblDescripccion.setHorizontalAlignment(SwingConstants.CENTER);
		lblDescripccion.setBounds(24, 141, 475, 21);
		contentPane.add(lblDescripccion);

		JTextArea destextArea = new JTextArea();
		destextArea.setBounds(24, 169, 475, 111);
		contentPane.add(destextArea);

		JButton RegistrarBtn = new JButton("Register");
		RegistrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// no dejar ningun campo vacio
				if (ciudadtextField.getText().equals("") || calletextField.getText().equals("") || numerotextField.getText().equals("")
						|| destextArea.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "You have to fill every blank");
				} else {
					String city = ciudadtextField.getText();
					String address = calletextField.getText();
					String description = destextArea.getText();
					int number;
					try {
						number = Integer.parseInt(numerotextField.getText());
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(numerotextField, "Insert a valid number:");
						number = -1;
					}
					if (number >= 0) {
						try {
							RuralHouse rh = facade.storeRuralhouse(owner, description, city, address, number);
							if (rh == null) {
								JOptionPane.showMessageDialog(numerotextField, "This rural house already exists");
							} else {
								JOptionPane.showMessageDialog(numerotextField, "Successfully Registered");
								//owner.addRuralHouse(rh);
								parent.updateOwner();
								dispose();
							}
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		RegistrarBtn.setBounds(24, 295, 475, 47);
		contentPane.add(RegistrarBtn);

		ciudadtextField = new JTextField();
		ciudadtextField.setToolTipText("insert the city");
		ciudadtextField.setBounds(92, 47, 238, 19);
		contentPane.add(ciudadtextField);
		ciudadtextField.setColumns(10);

		calletextField = new JTextField();
		calletextField.setToolTipText("insert the street");
		calletextField.setColumns(10);
		calletextField.setBounds(92, 95, 238, 19);
		contentPane.add(calletextField);

		numerotextField = new JTextField();
		numerotextField.setToolTipText("insert the street number");
		numerotextField.setColumns(10);
		numerotextField.setBounds(430, 94, 69, 19);
		contentPane.add(numerotextField);

		JLabel lblNewLabel = new JLabel("House Registration");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(0, 0, 527, 35);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 37, 527, 10);
		contentPane.add(separator);

	}
}
