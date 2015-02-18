package gui;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.DropMode;

@SuppressWarnings("serial")
public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JFormattedTextField bankField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 281, 304);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblRegisterPage = new JLabel("Register Page");
		lblRegisterPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegisterPage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRegisterPage.setBounds(0, 10, 265, 22);
		contentPane.add(lblRegisterPage);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 41, 300, 2);
		contentPane.add(separator);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(43, 50, 76, 20);
		contentPane.add(userLabel);

		userTextField = new JTextField();
		userTextField.setToolTipText("Inserter your username for loging.");
		userTextField.setBounds(123, 50, 86, 20);
		contentPane.add(userTextField);
		userTextField.setColumns(10);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(43, 111, 76, 20);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Insert the password of your account.");
		passwordField.setBounds(123, 111, 86, 20);
		contentPane.add(passwordField);

		JRadioButton userRadBut = new JRadioButton("User");
		buttonGroup.add(userRadBut);
		userRadBut.setBounds(43, 138, 61, 23);
		contentPane.add(userRadBut);

		JLabel insertbankLabel = new JLabel("Insert your bank account: ");
		insertbankLabel.setEnabled(false);
		insertbankLabel.setBounds(43, 214, 166, 22);
		contentPane.add(insertbankLabel);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(43, 80, 76, 19);
		contentPane.add(lblName);

		textField = new JTextField();
		textField.setBounds(123, 80, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Register");
		btnNewButton.setBounds(38, 168, 171, 35);
		contentPane.add(btnNewButton);

		MaskFormatter mask = null;

		try {
			mask = new MaskFormatter("####-####-##-##########");
		}
		catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mask.setPlaceholderCharacter('_');

		bankField = new JFormattedTextField(mask);
		bankField.setText("");
		bankField.setEnabled(false);
		bankField.setHorizontalAlignment(SwingConstants.LEFT);
		bankField.setBounds(43, 236, 166, 22);
		contentPane.add(bankField);

		JRadioButton ownerRadBut = new JRadioButton("Owner");
		ownerRadBut.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (ownerRadBut.isSelected()) {
					bankField.setEnabled(true);
					insertbankLabel.setEnabled(true);
				}
				else{
					bankField.setEnabled(false);
					bankField.setText("");
					insertbankLabel.setEnabled(false);
				}
			}
		});
		buttonGroup.add(ownerRadBut);
		ownerRadBut.setBounds(133, 138, 76, 23);
		contentPane.add(ownerRadBut);
	}
}
