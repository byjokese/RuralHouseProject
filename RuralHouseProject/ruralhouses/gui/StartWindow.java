package gui;

/**
 * @author Software Engineering teachers
 */
import exceptions.DB4oManagerCreationException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import configuration.ConfigXML;
//import businessLogic.FacadeImplementation;
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
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static configuration.ConfigXML c;

	public static ApplicationFacadeInterface facadeInterface;
	private JLabel lblNewLabel;
	

	/**
	 * This is the default constructor
	 */
	public StartWindow() {
		super();
		setTitle("Rural House Proyect");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ApplicationFacadeInterface facade=StartWindow.facadeInterface;
				try {
					if (c.isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static ApplicationFacadeInterface getBusinessLogic(){
		return facadeInterface;
	}
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
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
				JFrame a = new QueryAvailabilityGUI();
				a.setVisible(true);
			}
		});
		btnLookForOffers.setBounds(20, 214, 205, 39);
		contentPane.add(btnLookForOffers);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	

	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	
	public static void main(String[] args) {

		StartWindow a = new StartWindow();
		a.setVisible(true);
		
		try {

			c=ConfigXML.getInstance();

			System.setProperty("java.security.policy", c.getJavaPolicyPath());
						
			System.setSecurityManager(new RMISecurityManager());

			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			c=configuration.ConfigXML.getInstance();
			if (c.isBusinessLogicLocal())
				facadeInterface=new FacadeImplementation();
			else {
				
				final String businessLogicNode = c.getBusinessLogicNode();
				// Remote service name
				String serviceName = "/"+c.getServiceRMI();
				// RMI server port number
				int portNumber = Integer.parseInt(c.getPortRMI());
				// RMI server host IP IP 
				facadeInterface = (ApplicationFacadeInterface) Naming.lookup("rmi://"
					+ businessLogicNode + ":" + portNumber + serviceName);
			} 

		} catch (java.rmi.ConnectException e) {
			a.lblNewLabel.setText("No business logic: Run BusinessLogicServer first!!");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: "+e.toString());
		} catch (java.rmi.NotBoundException e) {
			a.lblNewLabel.setText("No business logic: Maybe problems running BusinessLogicServer");
			a.lblNewLabel.setForeground(Color.RED);
			System.out.println("Error in StartWindow: "+e.toString());
		} catch (com.db4o.ext.DatabaseFileLockedException e) {
			a.lblNewLabel.setText("Database locked: Do not run BusinessLogicServer or BusinessLogicServer!!");
			a.lblNewLabel.setForeground(Color.RED);		
			System.out.println("Error in StartWindow: "+e.toString());
		} catch (DB4oManagerCreationException e) {
			a.lblNewLabel.setText("No database: Run DB4oManagerServer first!!");
			a.lblNewLabel.setForeground(Color.RED);		
			System.out.println("Error in StartWindow: "+e.toString());

			
		}catch (Exception e) {
			a.lblNewLabel.setText("Error: "+e.toString());
			a.lblNewLabel.setForeground(Color.RED);		
			System.out.println("Error in StartWindow: "+e.toString());
		}
		//a.pack();

		

	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Select option:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setForeground(Color.BLACK);
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblNewLabel;
	}

} // @jve:decl-index=0:visual-constraint="0,0"

