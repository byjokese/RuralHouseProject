package businessLogic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import configuration.ConfigXML;

import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("deprecation")
public class BusinessLogicServer extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea textArea;
	ApplicationFacadeInterface server;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BusinessLogicServer dialog = new BusinessLogicServer();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BusinessLogicServer() {
		setTitle("BusinessLogicServer: running the business logic");
		setBounds(100, 100, 486, 209);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			textArea = new JTextArea();
			contentPanel.add(textArea);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.append("\n\n\nClosing the server... ");
					    try {
							server.close();
						} catch (RemoteException e1) {
						}
						System.exit(1);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		ConfigXML c=ConfigXML.getInstance();

		 
		System.setProperty("java.security.policy", c.getJavaPolicyPath());
		
		
		System.setSecurityManager(new RMISecurityManager());
		
		try {
			java.rmi.registry.LocateRegistry.createRegistry(Integer.parseInt(c.getPortRMI()));
			// Create RMIREGISTRY
		} catch (Exception e) {
			textArea.append(e.toString() + "Rmiregistry already running.");
		}

		try {

			try{
				server = new FacadeImplementation();
			}
			catch (com.db4o.ext.DatabaseFileLockedException e) {
				System.out.println("Error in BusinessLogicServer: "+e.toString());
				textArea.append("\nYou should have not launched DB4oManagerServer...\n");
				textArea.append("\n\nOr maybe there is a BusinessLogicServer already launched...\n");
				throw e;
			}
			

			String service= "//"+c.getBusinessLogicNode() +":"+ c.getPortRMI()+"/"+c.getServiceRMI();
			
			// Register the remote server
			Naming.rebind(service, server);
			textArea.append("Running service at:\n\t" + service);
			//This operation removes the actual database and initialize with predefined values
			
			textArea.append("\n\n\nPress button to exit this server... ");
			
		} catch (Exception e) {
			textArea.append(e.toString());
		}

	}
		
}

