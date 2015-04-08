package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JSeparator;

import domain.Users;
import businessLogic.ApplicationFacadeInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	public LoginGUI(Users intoUser) throws Exception{
		synchronized (this) {
			ApplicationFacadeInterface facade = StartWindow.facadeInterface;
			// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 244, 235);
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
			userRadBut.setSelected(true);
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
			loginBtn.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent arg0) {
					String username = userTextField.getText();
					String password = passwordField.getText();
					boolean isOwner = ownerRadBut.isSelected();
					try {
						Users user = facade.checkLogin(username, password, isOwner);
						if (user != null) {
							JOptionPane.showMessageDialog(null, "Successfully loged in.");
							System.out.println(user.toString());
							throw new Exception("Loged");
						} else
							JOptionPane.showMessageDialog(null, "Username or password incorrect.");
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
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
}
