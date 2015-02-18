package gui;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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

import businessLogic.ApplicationFacadeInterface;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import domain.Users;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.DropMode;
import javax.swing.JInternalFrame;

import sun.tools.jar.resources.jar;

@SuppressWarnings("serial")
public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField nametextField;
	private JFormattedTextField bankField;
	private JPasswordField confirmPasswordField;

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
		setBounds(100, 100, 310, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();

		JLabel lblRegisterPage = new JLabel("Register Page");
		lblRegisterPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegisterPage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRegisterPage.setBounds(0, 10, 294, 22);
		contentPane.add(lblRegisterPage);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 41, 294, 2);
		contentPane.add(separator);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(43, 50, 101, 20);
		contentPane.add(userLabel);

		usernameTextField = new JTextField();
		usernameTextField.setToolTipText("Inserter your username for loging.");
		usernameTextField.setBounds(164, 50, 86, 20);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(43, 111, 101, 20);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Insert the password of your account.");
		passwordField.setBounds(164, 111, 86, 20);
		contentPane.add(passwordField);

		JRadioButton userRadBut = new JRadioButton("User");
		userRadBut.setSelected(true);
		userRadBut.setHorizontalAlignment(SwingConstants.CENTER);
		buttonGroup.add(userRadBut);
		userRadBut.setBounds(48, 173, 61, 23);
		contentPane.add(userRadBut);

		JLabel insertbankLabel = new JLabel("Insert your bank account: ");
		insertbankLabel.setEnabled(false);
		insertbankLabel.setBounds(48, 249, 202, 22);
		contentPane.add(insertbankLabel);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(43, 80, 101, 19);
		contentPane.add(lblName);

		nametextField = new JTextField();
		nametextField.setBounds(164, 81, 86, 20);
		contentPane.add(nametextField);
		nametextField.setColumns(10);

		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if (passwordField.getText().equals(confirmPasswordField.getText())) {
					String username = usernameTextField.getText();
					String name = nametextField.getText();
					String password = passwordField.getText();
					
					if (facade.checkUserAvailability(username)) {
						Users.type type = null;
						if (userRadBut.isSelected()) {
							type = type.CLIENT;
						} else {
							type = type.OWNER;
						}
						facade.addUserToDataBase(name, username, password, type);
					}

				} else {
					JOptionPane.showMessageDialog(null, "User already taken, please choose another one.");
				}

			}
		});
		btnNewButton.setBounds(43, 203, 207, 35);
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
		bankField.setBounds(48, 271, 202, 22);
		contentPane.add(bankField);

		JRadioButton ownerRadBut = new JRadioButton("Owner");
		ownerRadBut.setHorizontalAlignment(SwingConstants.CENTER);
		ownerRadBut.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (ownerRadBut.isSelected()) {
					bankField.setEnabled(true);
					insertbankLabel.setEnabled(true);
				} else {
					bankField.setEnabled(false);
					bankField.setText("");
					insertbankLabel.setEnabled(false);
				}
			}
		});
		buttonGroup.add(ownerRadBut);
		ownerRadBut.setBounds(174, 173, 76, 23);
		contentPane.add(ownerRadBut);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setToolTipText("Confirm the previous password");
		confirmPasswordField.setBounds(164, 142, 86, 20);
		contentPane.add(confirmPasswordField);

		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setBounds(43, 142, 114, 20);
		contentPane.add(lblConfirmPassword);
	}
}
