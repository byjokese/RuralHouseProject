package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JSeparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 263, 299);
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
		
		JRadioButton userRadBut = new JRadioButton("User");
		buttonGroup.add(userRadBut);
		userRadBut.setBounds(33, 106, 61, 23);
		contentPane.add(userRadBut);
		
		JRadioButton ownerRadBut = new JRadioButton("Owner");
		buttonGroup.add(ownerRadBut);
		ownerRadBut.setBounds(123, 106, 76, 23);
		contentPane.add(ownerRadBut);
		
		JLabel BannerLabel = new JLabel("Rural House System");
		BannerLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		BannerLabel.setBounds(20, 0, 200, 37);
		contentPane.add(BannerLabel);
		
		JButton loginBtn = new JButton("Log In");
		loginBtn.setBounds(20, 136, 205, 39);
		contentPane.add(loginBtn);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new RegisterGUI();
				a.setVisible(true);
			}
		});
		btnRegister.setForeground(Color.BLACK);
		btnRegister.setBounds(139, 186, 86, 17);
		contentPane.add(btnRegister);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 35, 247, 2);
		contentPane.add(separator);
		
		JButton btnLookForOffers = new JButton("Look for Offers");
		btnLookForOffers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a;
				try {
					a = new QueryAvailabilityGUI();
					a.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnLookForOffers.setBounds(20, 214, 205, 39);
		contentPane.add(btnLookForOffers);
	}
}
