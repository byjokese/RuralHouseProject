package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

public class SignUpHouseGUI extends JFrame {

	private JPanel contentPane;
	private JTextField ciudadtextField;
	private JTextField calletextField;
	private JTextField numerotextField;
	private JTextField descripciontextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpHouseGUI frame = new SignUpHouseGUI();
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
	public SignUpHouseGUI() {
		setTitle("SIGN UP HOUSE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCiudadcity = new JLabel("ciudad :");
		lblCiudadcity.setBounds(24, 46, 111, 21);
		contentPane.add(lblCiudadcity);
		
		JLabel lblCalle = new JLabel("calle :");
		lblCalle.setBounds(24, 94, 111, 21);
		contentPane.add(lblCalle);
		
		JLabel lblNumero = new JLabel("numero:");
		lblNumero.setBounds(360, 97, 111, 21);
		contentPane.add(lblNumero);
		
		JLabel lblDescripccion = new JLabel("descripccion:");
		lblDescripccion.setBounds(243, 157, 111, 21);
		contentPane.add(lblDescripccion);
		
		JButton RegistrarBtn = new JButton("Registrar");
		RegistrarBtn.setBounds(255, 317, 117, 25);
		contentPane.add(RegistrarBtn);
		
		ciudadtextField = new JTextField();
		ciudadtextField.setBounds(92, 47, 238, 19);
		contentPane.add(ciudadtextField);
		ciudadtextField.setColumns(10);
		
		calletextField = new JTextField();
		calletextField.setColumns(10);
		calletextField.setBounds(92, 95, 238, 19);
		contentPane.add(calletextField);
		
		numerotextField = new JTextField();
		numerotextField.setColumns(10);
		numerotextField.setBounds(430, 98, 69, 19);
		contentPane.add(numerotextField);
		
		descripciontextField = new JTextField();
		descripciontextField.setBounds(92, 190, 429, 115);
		contentPane.add(descripciontextField);
		descripciontextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("House Registration");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(12, 0, 529, 25);
		contentPane.add(lblNewLabel);
	}
}
