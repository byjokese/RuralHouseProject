package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import com.toedter.calendar.JCalendar;

import domain.ExtraActivity;
import domain.Owner;
import domain.RuralHouse;

import java.awt.Rectangle;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import java.awt.Font;
import java.util.Iterator;

import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class CreatenewOfferGUI extends JFrame {

	private JPanel contentPane;
	private JTextField OffersPricetextField;
	private JLabel lblListOfHouses;
	private JTable SelectedActivititable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreatenewOfferGUI frame = new CreatenewOfferGUI(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreatenewOfferGUI(Owner owner) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("REVISAR FORMATO EN WINDOWS");
		setBounds(100, 100, 1118, 662);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPrice = new JLabel("price:");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setBounds(379, 65, 54, 15);
		contentPane.add(lblPrice);
		
		OffersPricetextField = new JTextField();
		OffersPricetextField.setBounds(448, 59, 114, 27);
		contentPane.add(OffersPricetextField);
		OffersPricetextField.setColumns(10);
		
		lblListOfHouses = new JLabel("List Of Houses: ");
		lblListOfHouses.setBounds(939, 37, 114, 15);
		contentPane.add(lblListOfHouses);
		
		JLabel lblFirstDay = new JLabel("First Day:");
		lblFirstDay.setBounds(357, 147, 87, 15);
		contentPane.add(lblFirstDay);
		
		JLabel lblLastDay = new JLabel("Last Day:");
		lblLastDay.setBounds(655, 147, 87, 15);
		contentPane.add(lblLastDay);
		
		JCalendar FirstDaycalendar = new JCalendar();
		FirstDaycalendar.setBounds(new Rectangle(150, 106, 225, 180));
		FirstDaycalendar.setBounds(290, 170, 272, 171);
		contentPane.add(FirstDaycalendar);
		
		JCalendar LastDaycalendar = new JCalendar();
		LastDaycalendar.setBounds(new Rectangle(150, 106, 225, 180));
		LastDaycalendar.setBounds(581, 170, 260, 171);
		contentPane.add(LastDaycalendar);
		
		JButton btnCreateOffer = new JButton("Create Offer");
		btnCreateOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//control de precio
				int precio;
				try {
				precio = Integer.parseInt(OffersPricetextField.getText());
				} catch (Exception e2) {
					precio = -1;
				}
				
				if(precio >= 0){
				JOptionPane.showMessageDialog(null, " precio bien puesto");
					
				}else{
					
					JOptionPane.showMessageDialog(null, "El precio es incorrecto");
				}
				
			}
		});
		btnCreateOffer.setBounds(501, 576, 181, 44);
		contentPane.add(btnCreateOffer);
		//rellenamos la lista de casas
		DefaultListModel<RuralHouse> houses = new DefaultListModel<RuralHouse>();
		for(RuralHouse rh : owner.getRuralHouses()){
							houses.addElement(rh);
						}
		JList RHouseslist = new JList();
		RHouseslist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});
		RHouseslist.setModel(houses);
		RHouseslist.setBounds(906, 64, 200, 494);
		contentPane.add(RHouseslist);
		//rellenamos las actividades
		DefaultListModel<ExtraActivity> Activities = new DefaultListModel<ExtraActivity>();
		for(ExtraActivity rh : owner.getExtraActivities()){
			Activities.addElement(rh);
						}
		
		
		JList list = new JList();
		list.setModel(Activities);
		list.setBounds(12, 64, 200, 494);
		contentPane.add(list);
		
		JLabel lblAvailablesActivities = new JLabel("Availables Activities:");
		lblAvailablesActivities.setBounds(23, 25, 164, 15);
		contentPane.add(lblAvailablesActivities);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(115, 91, 790, 50);
		contentPane.add(separator);
		
		JButton btnCreateActivity = new JButton("Create Activity");
		btnCreateActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateExtraActivityGUI(owner);
				a.setVisible(true);
			}
		});
		btnCreateActivity.setBounds(12, 573, 200, 50);
		contentPane.add(btnCreateActivity);
		
		JButton btnCreateHouse = new JButton("Create House");
		btnCreateHouse.setBounds(906, 573, 200, 50);
		contentPane.add(btnCreateHouse);
		
		JButton AddActivitybutton = new JButton(">");
		AddActivitybutton.setFont(new Font("Arial Black", Font.BOLD, 14));
		AddActivitybutton.setBounds(216, 291, 44, 50);
		contentPane.add(AddActivitybutton);
		
		SelectedActivititable = new JTable();
		SelectedActivititable.setBounds(245, 380, 629, 163);
		contentPane.add(SelectedActivititable);
		
		JLabel lblSelectedActivities = new JLabel("Selected activities:");
		lblSelectedActivities.setBounds(364, 353, 146, 15);
		contentPane.add(lblSelectedActivities);
		
		
		
		
	}

}
