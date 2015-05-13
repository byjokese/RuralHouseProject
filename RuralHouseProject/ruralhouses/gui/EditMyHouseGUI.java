package gui;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businessLogic.ApplicationFacadeInterface;
import domain.Owner;
import domain.RuralHouse;

public class EditMyHouseGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @param frame 
	 */
	public EditMyHouseGUI(Owner owner, OwnerGUI frame) {
		setTitle("Rural House System");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 300);
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
		textArea.setBounds(191, 138, 351, 71);
		contentPane.add(textArea);

		JLabel lblNewLabel = new JLabel("Edit My House");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 560, 30);
		contentPane.add(lblNewLabel);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 30, 560, 2);
		contentPane.add(separator_1);

		JLabel lblNewLabel_1 = new JLabel("City:");
		lblNewLabel_1.setBounds(191, 39, 53, 21);
		contentPane.add(lblNewLabel_1);

		JLabel label = new JLabel("");
		label.setBounds(268, 41, 274, 14);
		contentPane.add(label);

		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(191, 67, 53, 14);
		contentPane.add(lblAddress);

		JLabel label_1 = new JLabel("");
		label_1.setBounds(268, 67, 274, 14);
		contentPane.add(label_1);

		JLabel lblNumber = new JLabel("Number:");
		lblNumber.setBounds(191, 92, 53, 14);
		contentPane.add(lblNumber);

		JLabel label_2 = new JLabel("");
		label_2.setBounds(268, 92, 274, 14);
		contentPane.add(label_2);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(191, 117, 91, 14);
		contentPane.add(lblDescription);

		JList<RuralHouse> list = new JList<RuralHouse>();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				RuralHouse rh = (RuralHouse) list.getSelectedValue();
				if (rh == null) {
					label.setText("");
					label_1.setText("");
					label_2.setText("");
					textArea.setText("");
				} else {
					label.setText(rh.getCity());
					label_1.setText(rh.getAddress());
					label_2.setText(Integer.toString(rh.getNumber()));
					textArea.setText(rh.getDescription());
				}
			}
		});
		DefaultListModel<RuralHouse> houses = new DefaultListModel<RuralHouse>();
		for (RuralHouse h : owner.getRuralHouses()) {
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
				RuralHouse rh = (RuralHouse) list.getSelectedValue();
				if (rh != null) {
					String description = textArea.getText();
					if (description.equals("")) {
						JOptionPane.showMessageDialog(Savebtn, "You have to insert a description");
					} else {
						try {
							RuralHouse rh1 = facade.updateRuralHouse(rh, owner, description, rh.getMark(), rh.getComments());
							owner.updateRuralHouse(rh1);
							JOptionPane.showMessageDialog(Savebtn, "Save Correctly");
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(Savebtn, "There is no Rural House selected");
				}
			}
		});
		Savebtn.setBounds(191, 220, 115, 30);
		contentPane.add(Savebtn);

		JButton Deletebtn = new JButton("Delete");
		Deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RuralHouse rh = (RuralHouse) list.getSelectedValue();
				if (rh != null) {
					if (rh.getAllOffers().size() == 0) {
						int index = list.getSelectedIndex();
						try {
							facade.deleteRuralHouse(rh, owner, index);
							JOptionPane.showMessageDialog(Deletebtn, "Delete Correctly");
							list.clearSelection();
							DefaultListModel<RuralHouse> houses = new DefaultListModel<RuralHouse>();
							for (RuralHouse h : owner.getRuralHouses()) {
								houses.addElement(h);
							}
							list.setModel(houses);
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(Deletebtn, "This Rural House contains offers, cannot be deleted");
					}
				} else {
					JOptionPane.showMessageDialog(Deletebtn, "There is no Rural House selected");
				}
			}

		});
		Deletebtn.setBounds(316, 220, 108, 30);
		contentPane.add(Deletebtn);

		JButton closebtn = new JButton("Close");
		closebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closebtn.setBounds(434, 220, 108, 30);
		contentPane.add(closebtn);

	}
}
