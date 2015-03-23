package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import domain.Owner;
import domain.RuralHouse;
import businessLogic.ApplicationFacadeInterface;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.Font;

import javax.swing.JLabel;

import businessLogic.FacadeImplementation;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

public class EditMyHouseGUI extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public EditMyHouseGUI(Owner owner) {
		setTitle("Rural House System");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(179, 30, 2, 231);
		contentPane.add(separator);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(191, 138, 233, 71);
		contentPane.add(textArea);
		
		JLabel lblNewLabel = new JLabel("Edit My House");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 434, 30);
		contentPane.add(lblNewLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 30, 434, 2);
		contentPane.add(separator_1);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setBounds(191, 39, 53, 21);
		contentPane.add(lblNewLabel_1);
		
		JLabel label = new JLabel("");
		label.setBounds(268, 41, 156, 14);
		contentPane.add(label);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(191, 67, 53, 14);
		contentPane.add(lblAddress);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(268, 67, 156, 14);
		contentPane.add(label_1);
		
		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setBounds(191, 92, 53, 14);
		contentPane.add(lblNumber);
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(268, 92, 28, 14);
		contentPane.add(label_2);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(191, 117, 91, 14);
		contentPane.add(lblDescription);
		
		JList list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				RuralHouse rh =(RuralHouse) list.getSelectedValue();
				if (rh==null){
					label.setText("");
					label_1.setText("");
					label_2.setText("");
					textArea.setText("");
				}else{
				label.setText(rh.getCity());
				label_1.setText(rh.getAddress());
				label_2.setText(Integer.toString(rh.getNumber()));
				textArea.setText(rh.getDescription());
			}
			}
		});
		DefaultListModel<RuralHouse> houses = new DefaultListModel<RuralHouse>();
		for (RuralHouse h : owner.getRuralHouses()){
			houses.addElement(h);
		}
		list.setModel(houses);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(SystemColor.control);
		list.setBounds(0, 38, 174, 223);
		contentPane.add(list);		
		
		JButton Savebtn = new JButton("Save ");
		Savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RuralHouse rh =(RuralHouse) list.getSelectedValue();
				if(rh != null){
				String description = textArea.getText();
				int index = list.getSelectedIndex();
				if (description.equals("")){
					JOptionPane.showMessageDialog(Savebtn,"You have to insert a description");
				}else{
				try {
					facade.updateRuralHouse(rh, owner, description, index);
					JOptionPane.showMessageDialog(Savebtn,"Save Correctly");
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				}
				}else{
					JOptionPane.showMessageDialog(Savebtn,"There is no Rural House selected");
				}
			}
		});
		Savebtn.setBounds(191, 220, 115, 30);
		contentPane.add(Savebtn);
		
		JButton Deletebtn = new JButton("Delete");
		Deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RuralHouse rh =(RuralHouse) list.getSelectedValue();
				if (rh != null){
				if (rh.getAllOffers().size()==0){
					int index = list.getSelectedIndex();
					try{
						facade.deleteRuralHouse(rh,owner,index);
						JOptionPane.showMessageDialog(Deletebtn,"Delete Correctly");
						list.clearSelection();
						DefaultListModel<RuralHouse> houses = new DefaultListModel<RuralHouse>();
						for (RuralHouse h : owner.getRuralHouses()){
							houses.addElement(h);
						}
						list.setModel(houses);
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(Deletebtn, "This Rural House contains offers, cannot be deleted");
				}
				}else{
					JOptionPane.showMessageDialog(Deletebtn, "There is no Rural House selected");
				}
			}
		
		});
		Deletebtn.setBounds(316, 220, 108, 30);
		contentPane.add(Deletebtn);
		
	}
}
