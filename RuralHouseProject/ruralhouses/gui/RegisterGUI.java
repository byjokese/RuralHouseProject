package gui;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;

public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;

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

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setTitle("Rural House System");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 357);
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

		JRadioButton userRadBut = new JRadioButton("Client");
		userRadBut.setSelected(true);
		userRadBut.setHorizontalAlignment(SwingConstants.CENTER);
		buttonGroup.add(userRadBut);
		userRadBut.setBounds(48, 169, 61, 23);
		contentPane.add(userRadBut);

		JLabel insertbankLabel = new JLabel("Insert your bank account: ");
		insertbankLabel.setEnabled(false);
		insertbankLabel.setBounds(48, 255, 202, 22);
		contentPane.add(insertbankLabel);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(43, 80, 101, 19);
		contentPane.add(lblName);

		nametextField = new JTextField();
		nametextField.setBounds(164, 81, 86, 20);
		contentPane.add(nametextField);
		nametextField.setColumns(10);

		JLabel errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(48, 302, 202, 14);
		contentPane.add(errorLabel);

		JRadioButton ownerRadBut = new JRadioButton("Owner");
		ownerRadBut.setHorizontalAlignment(SwingConstants.CENTER);
		ownerRadBut.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (ownerRadBut.isSelected()) {
					bankField.setEnabled(true);
					bankField.setEditable(true);
					insertbankLabel.setEnabled(true);
				} else {
					bankField.setEnabled(false);
					bankField.setEditable(false);
					bankField.setValue("");
					insertbankLabel.setEnabled(false);
				}
			}
		});
		buttonGroup.add(ownerRadBut);
		ownerRadBut.setBounds(174, 169, 76, 23);
		contentPane.add(ownerRadBut);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setToolTipText("Confirm the previous password");
		confirmPasswordField.setBounds(164, 142, 86, 20);
		contentPane.add(confirmPasswordField);

		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setBounds(43, 142, 114, 20);
		contentPane.add(lblConfirmPassword);

		MaskFormatter mask = null;

		try {
			mask = new MaskFormatter("####-####-##-#########");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		mask.setPlaceholderCharacter('0');

		bankField = new JFormattedTextField(mask);
		/*bankField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if (bankField.getText().length() == 4) {
					bankField.setText(bankField.getText() + "-");
				}
				if (bankField.getText().length() == 9) {
					bankField.setText(bankField.getText() + "-");
				}
				if (bankField.getText().length() == 12) {
					bankField.setText(bankField.getText() + "-");
				}
				if (bankField.getText().length() == 22) {
					// bankField.setEditable(false);
				}
			}
		});*/
		// bankField.setText("");
		// bankField.setHorizontalAlignment(SwingConstants.LEFT);
		bankField.setBounds(48, 277, 202, 22);
		contentPane.add(bankField);

		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings({ "deprecation"})
			public void actionPerformed(ActionEvent arg0) {
				if (passwordField.getText().equals(confirmPasswordField.getText())) {
					String username = usernameTextField.getText();
					String name = nametextField.getText();
					String password = passwordField.getText();
					String bankAccount = null;
					boolean isOwner;
					try {
						if (facade.checkUserAvailability(username)) {
							isOwner = ownerRadBut.isSelected();
							System.out.println(bankField.getText());
							try {
								if (isOwner & bankField.getText().length() == 22 && !bankField.getText().equals("0000-0000-00-000000000")) {
									bankAccount = bankField.getText();
									facade.addUserToDataBase(name, username, password, isOwner, bankAccount);
									JOptionPane.showMessageDialog(null, "Successfully Registered");
									dispose();
								} else if (isOwner & (bankField.getText().length() != 22 || bankField.getText().equals("0000-0000-00-000000000"))) {
									errorLabel.setText("Incorrect format for Bank Account");
								} else if (!isOwner) {
									facade.addUserToDataBase(name, username, password, false, null);
									JOptionPane.showMessageDialog(null, "Successfully Registered");
									dispose();
								}
							} catch (RemoteException e) {
								e.printStackTrace();
							}

						} else {
							JOptionPane.showMessageDialog(null, "User already taken, please choose another one.");
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Passwords do not match.");
				}

			}
		});
		btnNewButton.setBounds(43, 197, 207, 35);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Activate Account");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new ActivationGUI();
				a.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(43, 235, 207, 19);
		contentPane.add(btnNewButton_1);

	}
}
