package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Window.Type;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import businessLogic.ApplicationFacadeInterface;
import businessLogic.FacadeImplementation;
import domain.Owner;
import domain.RuralHouse;

public class SignUpHouseGUI extends JFrame {

	private JPanel contentPane;
	private JTextField ciudadtextField;
	private JTextField calletextField;
	private JTextField numerotextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpHouseGUI frame = new SignUpHouseGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignUpHouseGUI(Owner owner) {
		setTitle("SIGN UP HOUSE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		JLabel lblCiudadcity = new JLabel("City :");
		lblCiudadcity.setBounds(24, 46, 111, 21);
		contentPane.add(lblCiudadcity);

		JLabel lblCalle = new JLabel("Address :");
		lblCalle.setBounds(24, 94, 111, 21);
		contentPane.add(lblCalle);

		JLabel lblNumero = new JLabel("Number:");
		lblNumero.setBounds(360, 97, 111, 21);
		contentPane.add(lblNumero);

		JLabel lblDescripccion = new JLabel("Description:");
		lblDescripccion.setBounds(243, 157, 111, 21);
		contentPane.add(lblDescripccion);

		JTextArea destextArea = new JTextArea();
		destextArea.setBounds(77, 190, 464, 111);
		contentPane.add(destextArea);

		JButton RegistrarBtn = new JButton("Register");
		RegistrarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//no dejar ningun campo vacio
				if(ciudadtextField.getText().equals("")||
						calletextField.getText().equals("") ||
						numerotextField.getText().equals("")||
						destextArea.getText().equals("")  ){JOptionPane.showMessageDialog(null,"NO Puede dejar ningun campo vacio");}
				else{
					String city = ciudadtextField.getText();
					String address = calletextField.getText();
					String description = destextArea.getText(); 
					int number;
					try {
						number = Integer.parseInt(numerotextField.getText());

					} catch (Exception e2) {
						JOptionPane.showMessageDialog(numerotextField, "introduzca un numero valido:");
						number =-1;
					}
					if(number >= 0){

						if(facade.storeRuralhouse(12, owner, description, city, address, number) == null){
							JOptionPane.showMessageDialog(numerotextField, "Esta RuralHouse ya EXISTE");
						}else{

							JOptionPane.showMessageDialog(numerotextField, "REGISTRED");
							 }

					}else{

						JOptionPane.showMessageDialog(numerotextField, "introduzca un numero valido:");
					}


				}

			}
		});
		RegistrarBtn.setBounds(255, 317, 117, 25);
		contentPane.add(RegistrarBtn);

		ciudadtextField = new JTextField();
		ciudadtextField.setToolTipText("introduce la ciudad");
		ciudadtextField.setBounds(92, 47, 238, 19);
		contentPane.add(ciudadtextField);
		ciudadtextField.setColumns(10);

		calletextField = new JTextField();
		calletextField.setToolTipText("introduce una calle");
		calletextField.setColumns(10);
		calletextField.setBounds(92, 95, 238, 19);
		contentPane.add(calletextField);

		numerotextField = new JTextField();
		numerotextField.setToolTipText("introduce un numero de calle o portal");
		numerotextField.setColumns(10);
		numerotextField.setBounds(430, 98, 69, 19);
		contentPane.add(numerotextField);



		JLabel lblNewLabel = new JLabel("House Registration");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(12, 0, 529, 25);
		contentPane.add(lblNewLabel);


	}
}
