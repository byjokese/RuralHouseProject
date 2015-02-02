package dataAccess;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import configuration.ConfigXML;

import javax.swing.JTextArea;

import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;

import domain.Offer;
import domain.Owner;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DB4oManagerServer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JTextArea textArea;
	ObjectServer server;
	private ServerConfiguration configurationCS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DB4oManagerServer dialog = new DB4oManagerServer();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DB4oManagerServer() {
		setTitle("DB4oManagerServer: running the database server");
		setBounds(100, 100, 486, 180);
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
						textArea.append("\n\n\nClosing the database... ");
					    try {
							server.close();
						} catch (Exception e1) {
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
		
		
		try{

			if (c.getDataBaseOpenMode().equals("initialize")) new File(c.getDb4oFilename()).delete();
			
			configurationCS = Db4oClientServer.newServerConfiguration();
			configurationCS.common().activationDepth(c.getActivationDepth());
			configurationCS.common().updateDepth(c.getUpdateDepth());
			configurationCS.common().objectClass(Owner.class).cascadeOnDelete(true);

			server = Db4oClientServer.openServer(configurationCS,
									 			 c.getDb4oFilename(),c.getDatabasePort());
			
			textArea.append("\nConnection to the database '"+c.getDb4oFilename()+"' opened in port "+c.getDatabasePort());
	
		    server.grantAccess(c.getUser(),c.getPassword());

		    textArea.append("\nAccess granted to: "+c.getUser());



			textArea.append("\nPress button to exit this database server... ");
			
		} catch (Exception e) {
			textArea.append("Something has happened in DB4oManagerServer: "+e.toString());

		}
	}
}
	
