package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import businessLogic.ApplicationFacadeInterface;
import domain.Users;

public class ActivationGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JFormattedTextField bankField;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	public ActivationGUI() {
		setTitle("Rural House System");
		ApplicationFacadeInterface facade = StartWindow.facadeInterface;
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 244, 285);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(33, 44, 76, 20);
		contentPane.add(userLabel);

		userTextField = new JTextField();
		userTextField.setToolTipText("Inserter your username for loging.");
		userTextField.setBounds(113, 44, 86, 20);
		contentPane.add(userTextField);
		userTextField.setColumns(10);

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(33, 79, 76, 20);
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Insert the password of your account.");
		passwordField.setBounds(113, 79, 86, 20);
		contentPane.add(passwordField);

		JLabel insertbankLabel = new JLabel("Insert your bank account: ");
		insertbankLabel.setEnabled(false);
		insertbankLabel.setBounds(20, 179, 197, 22);
		contentPane.add(insertbankLabel);

		JRadioButton userRadBut = new JRadioButton("User");
		userRadBut.setSelected(true);
		buttonGroup.add(userRadBut);
		userRadBut.setBounds(33, 106, 61, 23);
		contentPane.add(userRadBut);

		JRadioButton ownerRadBut = new JRadioButton("Owner");
		buttonGroup.add(ownerRadBut);
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
		ownerRadBut.setBounds(123, 106, 76, 23);
		contentPane.add(ownerRadBut);

		JLabel BannerLabel = new JLabel("Account Activation");
		BannerLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		BannerLabel.setBounds(20, 0, 200, 37);
		contentPane.add(BannerLabel);

		JLabel errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(20, 228, 200, 14);
		contentPane.add(errorLabel);

		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("####-####-##-#########");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		mask.setPlaceholderCharacter('0');
		bankField = new JFormattedTextField(mask);
		bankField.setBounds(17, 201, 200, 22);
		contentPane.add(bankField);

		JButton loginBtn = new JButton("Activate Account");
		loginBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				String username = userTextField.getText();
				String password = passwordField.getText();
				String bankaccount = bankField.getText();
				boolean isOwner = ownerRadBut.isSelected();
				try {
					Users user = facade.checkLogin(username, password, !isOwner);
					if (!bankaccount.equals("0000-0000-00-000000000")) {
						if (user != null) {
							facade.activateAccount(username, isOwner, bankaccount);
							JOptionPane.showMessageDialog(null, "Successfully Activated.");
						} else
							JOptionPane.showMessageDialog(null, "Username or password incorrect.");
					} else {
						errorLabel.setText("Incorrect format for Bank Account");
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		loginBtn.setBounds(15, 136, 205, 39);
		contentPane.add(loginBtn);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 35, 247, 2);
		contentPane.add(separator);
	}
}
