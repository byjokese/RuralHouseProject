package gui;

/**
 * @author Software Engineering teachers
 */
import exceptions.DB4oManagerCreationException;

import javax.swing.*;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class StartWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton boton1 = null;
	private JButton boton2 = null;
	private JButton boton3 = null;
	private static configuration.ConfigXML c;

	public static ApplicationFacadeInterface facadeInterface;
	private JLabel lblNewLabel;
	

	/**
	 * This is the default constructor
	 */
	public StartWindow() {
		super();

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
		// this.setSize(271, 295);
		this.setSize(495, 290);
		this.setContentPane(getJContentPane());
		this.setTitle("Rural Houses");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(4);
			gridLayout.setColumns(1);
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton3(), null);
			jContentPane.add(getBoton2(), null);
			jContentPane.add(getBoton1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton1() {
		if (boton1 == null) {
			boton1 = new JButton();
			boton1.setText("Book rural house");
			boton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// C?digo cedido por la univerdad
					JFrame a = new BookRuralHouseGUI();
					a.setVisible(true);
				}
			});
		}
		return boton1;
	}

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (boton2 == null) {
			boton2 = new JButton();
			boton2.setText("Set availability");
			boton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// C?digo cedido por la universidad
					JFrame a = new SetAvailabilityGUI();
					a.setVisible(true);
				}
			});
		}
		return boton2;
	}
	
	/**
	 * This method initializes boton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (boton3 == null) {
			boton3 = new JButton();
			boton3.setText("Query availability");
			boton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// C?digo cedido por la universidad
					//JFrame a = new QueryAvailabilityWindow();
					JFrame a = new QueryAvailabilityGUI();

					a.setVisible(true);
				}
			});
		}
		return boton3;
	}
	public static void main(String[] args) {

		StartWindow a = new StartWindow();
		a.setVisible(true);
		
		try {

			c=ConfigXML.getInstance();

			System.setProperty("java.security.policy", c.getJavaPolicyPath());
						
			System.setSecurityManager(new RMISecurityManager());

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

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

