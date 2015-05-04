package gui;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import domain.ExtraActivity;
import domain.Owner;
import domain.RuralHouse;

import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import java.awt.Rectangle;

public class EditMyActivityGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField placeTextField;

	/**
	 * Create the frame.
	 */
	public EditMyActivityGUI(Owner owner) {
		setTitle("Rural House System");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 574, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(179, 30, 2, 367);
		contentPane.add(separator);

		JTextArea descriptionTextArea = new JTextArea();
		descriptionTextArea.setBounds(191, 274, 351, 71);
		contentPane.add(descriptionTextArea);

		JLabel lblNewLabel = new JLabel("Edit My Activity");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 542, 30);
		contentPane.add(lblNewLabel);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 30, 558, 2);
		contentPane.add(separator_1);

		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setBounds(191, 39, 53, 21);
		contentPane.add(lblNewLabel_1);

		JLabel nameLabel = new JLabel("");
		nameLabel.setBounds(268, 41, 274, 14);
		contentPane.add(nameLabel);

		JLabel lblPlace = new JLabel("Place:");
		lblPlace.setBounds(191, 67, 53, 14);
		contentPane.add(lblPlace);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(191, 249, 91, 14);
		contentPane.add(lblDescription);
		
		JCalendar calendar = new JCalendar();
		calendar.setBounds(new Rectangle(150, 106, 225, 180));
		calendar.setBounds(270, 92, 267, 164);
		contentPane.add(calendar);

		DefaultListModel<String> activitiesString = new DefaultListModel<String>();
		ArrayList<ExtraActivity> activitiesList = new ArrayList<ExtraActivity>();
		JList<String> list = new JList<String>();
		list.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("deprecation")
			public void valueChanged(ListSelectionEvent e) {
				int i = list.getSelectedIndex();
				if (i == -1) {
					nameLabel.setText("");
					placeTextField.setText("");
					descriptionTextArea.setText("");
				} else {
					ExtraActivity ex = activitiesList.get(i);
					nameLabel.setText(ex.getNombre());
					placeTextField.setText(ex.getLugar());
					descriptionTextArea.setText(ex.getDescription());
					calendar.getDayChooser().setDay(ex.getFecha().getDay());
					calendar.getMonthChooser().setMonth(ex.getFecha().getMonth());
					calendar.getYearChooser().setYear(ex.getFecha().getYear());
				}
			}
		});
		for (ExtraActivity a : owner.getExtraActivities()) {
			activitiesList.add(a);
			activitiesString.addElement(a.getNombre() + " || " + a.getLugar() + " || " + a.getFecha());
		}
		list.setModel(activitiesString);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(SystemColor.control);
		list.setBounds(0, 38, 174, 223);
		contentPane.add(list);

		JButton Savebtn = new JButton("Save ");
		Savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1) {
					ExtraActivity ex = activitiesList.get(index);
					String description = descriptionTextArea.getText();
					String place = placeTextField.getText();
					Date activityDate = trim(new Date (calendar.getCalendar().getTime().getTime()));
					if (description.equals("") || place.equals("")) {
						JOptionPane.showMessageDialog(Savebtn, "You have to insert a description and a place");
					} else {
						try {
							facade.updateExtraActivity(ex, owner, description, index, place, activityDate);
							JOptionPane.showMessageDialog(Savebtn, "Save Correctly");
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(Savebtn, "There is no Extra Activity selected");
				}
			}
		});
		Savebtn.setBounds(191, 356, 115, 30);
		contentPane.add(Savebtn);

		JButton Deletebtn = new JButton("Delete");
		Deletebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1) {
						ExtraActivity ex = activitiesList.get(index);
						try {
							facade.deleteExtraActivity(ex, owner, index);
							JOptionPane.showMessageDialog(Deletebtn, "Delete Correctly");
							list.clearSelection();
							DefaultListModel<String> activitiesString = new DefaultListModel<String>();
							activitiesList.clear();
							for (ExtraActivity a : owner.getExtraActivities()) {
								activitiesList.add(a);
								activitiesString.addElement(a.getNombre() + " || " + a.getLugar() + " || " + a.getFecha());
							}
							list.setModel(activitiesString);
						} catch (HeadlessException e1) {
							e1.printStackTrace();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
				} else {
					JOptionPane.showMessageDialog(Deletebtn, "There is no Extra Activity selected");
				}
			}

		});
		Deletebtn.setBounds(316, 356, 108, 30);
		contentPane.add(Deletebtn);
		
		JButton closebtn = new JButton("Close");
		closebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closebtn.setBounds(434, 356, 108, 30);
		contentPane.add(closebtn);
		
		placeTextField = new JTextField();
		placeTextField.setBounds(268, 66, 191, 20);
		contentPane.add(placeTextField);
		placeTextField.setColumns(10);
		
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(191, 148, 46, 14);
		contentPane.add(lblDate);

	}
	
	private Date trim(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		return calendar.getTime();
	}
}
