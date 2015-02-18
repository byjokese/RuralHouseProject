package gui;

import javax.swing.ComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComboBox;

import java.awt.Rectangle;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;

import businessLogic.ApplicationFacadeInterface;
import domain.Owner;
import domain.RuralHouse;
import javax.swing.JLabel;


public class SetAvailabilityGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JComboBox jComboBox = null;
	private JButton jButton = null;
	private JLabel lblNewLabel;

	/**
	 * This is the default constructor
	 */
	public SetAvailabilityGUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(449, 293);
		this.setContentPane(getJContentPane());
		this.setTitle("Choose Owner");
		
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getJButton(), null);
			
			lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(77, 194, 300, 32);
			jContentPane.add(lblNewLabel);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		
		if (jComboBox == null) {
			try {
			ApplicationFacadeInterface facade=StartWindow.getBusinessLogic();
				Vector<Owner> owners=facade.getOwners();
			jComboBox = new JComboBox(owners);
			jComboBox.setBounds(new Rectangle(136, 39, 175, 44));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
		
		return jComboBox;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(172, 115, 95, 59));
			jButton.setText("Aceptar");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Owner owner=(Owner)jComboBox.getSelectedItem();
					Vector<RuralHouse> houseList=null;
		  			try {
		  	    		
//		  				ApplicationFacadeInterface facade=StartWindow.getBusinessLogic();  	    		
//						houseList=facade.getRuralHouses(owner); // Not needed to ask the business logic because
						houseList=owner.getRuralHouses();		// Owner has been serialized with its rural houses !!
					
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if (houseList.isEmpty()!=true) {
						JFrame a = new SetAvailability2GUI(houseList);
						a.setVisible(true);
					} else if (houseList.isEmpty()==true){
						lblNewLabel.setText("Owner does not exist or without houses ");
					} 		
					
				}
			});
		}
		return jButton;
	}
}  //  @jve:decl-index=0:visual-constraint="222,33"
