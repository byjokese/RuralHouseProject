package gui;

/**
 * @author Software Engineering teachers
 */
import domain.Users;
import exceptions.DB4oManagerCreationException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import configuration.ConfigXML;
// import businessLogic.FacadeImplementation;
import businessLogic.ApplicationFacadeInterface;
import businessLogic.FacadeImplementation;

import java.rmi.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StartWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane = null;
	private JTextField userTextField;
	private JPasswordField passwordField;
	private JLabel lblNewLabel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static configuration.ConfigXML c;

	public static ApplicationFacadeInterface facadeInterface;

	// private JLabel lblNewLabel;

	/**
	 * This is the default constructor
	 */
	public StartWindow() {
		super();
		setTitle("Rural House");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ApplicationFacadeInterface facade = StartWindow.facadeInterface;
				try {
					if (c.isBusinessLogicLocal())
						facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static ApplicationFacadeInterface getBusinessLogic() {
		return facadeInterface;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		setBounds(100, 100, 248, 318);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(12, 126, 205, 16);
		contentPane.add(lblNewLabel);

		/** User Data **/
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

		/** User type Selection Buttons **/
		/* User */
		JRadioButton userRadBut = new JRadioButton("User");
		userRadBut.setSelected(true);
		buttonGroup.add(userRadBut);
		userRadBut.setBounds(33, 106, 61, 23);
		contentPane.add(userRadBut);
		/* Owner */
		JRadioButton ownerRadBut = new JRadioButton("Owner");
		buttonGroup.add(ownerRadBut);
		ownerRadBut.setBounds(123, 106, 76, 23);
		contentPane.add(ownerRadBut);

		/** Login and Register buttons & Look for offers **/
		/* Login */
		JButton loginBtn = new JButton("Log In");
		loginBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				String username = userTextField.getText();
				String password = passwordField.getText();
				boolean isOwner = ownerRadBut.isSelected();
				try {
					if (facadeInterface.checkLogin(username, password, isOwner))
						JOptionPane.showMessageDialog(null, "Successfully loged in.");
						/**Redirects to User's Interface**/
					else
						JOptionPane.showMessageDialog(null, "Username or password incorrect.");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		loginBtn.setBounds(12, 150, 205, 39);
		contentPane.add(loginBtn);
		/* register */
		JButton RegisterBtn = new JButton("Register");
		RegisterBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Wait until Database is initialized
				JFrame a = new RegisterGUI();
				a.setVisible(true);
			}
		});
		RegisterBtn.setForeground(Color.BLACK);
		RegisterBtn.setBounds(131, 200, 86, 17);
		contentPane.add(RegisterBtn);
		/* Offers */
		JButton LookForOffersBtn = new JButton("Look for Offers");
		LookForOffersBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame b;
				try {
					b = new QueryAvailabilityGUI();
					b.setVisible(true);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					lblNewLabel.setText(e.getMessage());
				}

			}
		});
		LookForOffersBtn.setBounds(12, 228, 205, 39);
		contentPane.add(LookForOffersBtn);
		/** Extra Decoration **/
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 35, 247, 2);
		contentPane.add(separator);

		JLabel BannerLabel = new JLabel("Rural House System");
		BannerLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		BannerLabel.setBounds(0, 0, 247, 37);
		contentPane.add(BannerLabel);

	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		StartWindow a = new StartWindow();
		a.setVisible(true);

		try {

			c = ConfigXML.getInstance();

			System.setProperty("java.security.policy", c.getJavaPolicyPath());

			System.setSecurityManager(new RMISecurityManager());

			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			c = configuration.ConfigXML.getInstance();
			if (c.isBusinessLogicLocal())
				facadeInterface = new FacadeImplementation();
			else {

				final String businessLogicNode = c.getBusinessLogicNode();
				// Remote service name
				String serviceName = "/" + c.getServiceRMI();
				// RMI server port number
				int portNumber = Integer.parseInt(c.getPortRMI());
				// RMI server host IP IP
				facadeInterface = (ApplicationFacadeInterface) Naming.lookup("rmi://" + businessLogicNode + ":" + portNumber + serviceName);
			}

		} catch (java.rmi.ConnectException e) {
			a.lblNewLabel.setText("No business logic: Run BusinessLogicServer first!!");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());
		} catch (java.rmi.NotBoundException e) {
			a.lblNewLabel.setText("No business logic: Maybe problems running BusinessLogicServer");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());
		} catch (com.db4o.ext.DatabaseFileLockedException e) {
			a.lblNewLabel.setText("Database locked: Do not run BusinessLogicServer or BusinessLogicServer!!");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());
		} catch (DB4oManagerCreationException e) {
			a.lblNewLabel.setText("No database: Run DB4oManagerServer first!!");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());

		} catch (Exception e) {
			a.lblNewLabel.setText("Error: " + e.toString());
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: " + e.toString());
		}
		// a.pack();

	}
}
