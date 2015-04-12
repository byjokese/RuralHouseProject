package gui;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

import businessLogic.ApplicationFacadeInterface;

import com.toedter.calendar.JCalendar;

import domain.ExtraActivity;
import domain.Owner;

public class CreateExtraActivityGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField NametextField;
	private JTextField LugartextField_1;
	private ExtraActivity extraActivity;

	/**
	 * Create the frame.
	 */
	public CreateExtraActivityGUI(Owner owner, JFrame parent) {
		CreatenewOfferGUI createnewOfferGUI = (parent.getClass() == CreatenewOfferGUI.class) ? (CreatenewOfferGUI) parent : null;
		EditMyOfferGUI editMyOfferGUI = (parent.getClass() == EditMyOfferGUI.class) ? (EditMyOfferGUI) parent : null;

		setTitle("Rural House System");
		setBounds(100, 100, 613, 379);

		contentPane = new JPanel();
		contentPane.setBorder(new CompoundBorder());
		setContentPane(contentPane);
		contentPane.setLayout(null);
		/**
		 * Bussines logic
		 */
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		JLabel lblName = new JLabel("Name:");

		lblName.setBounds(12, 49, 70, 15);
		contentPane.add(lblName);

		NametextField = new JTextField();
		NametextField.setBounds(102, 46, 114, 19);

		contentPane.add(NametextField);
		NametextField.setColumns(10);

		JLabel lblLugar = new JLabel("Lugar:");

		lblLugar.setBounds(12, 102, 70, 15);
		contentPane.add(lblLugar);

		LugartextField_1 = new JTextField();
		LugartextField_1.setBounds(102, 100, 114, 19);

		contentPane.add(LugartextField_1);
		LugartextField_1.setColumns(10);

		JLabel lblDescription = new JLabel("Description:");

		lblDescription.setBounds(60, 180, 144, 15);
		contentPane.add(lblDescription);

		JTextArea DescriptiontextArea = new JTextArea();
		DescriptiontextArea.setBounds(12, 199, 266, 130);

		contentPane.add(DescriptiontextArea);

		JCalendar Datecalendar = new JCalendar();
		Datecalendar.setBounds(new Rectangle(150, 106, 225, 180));

		Datecalendar.setBounds(302, 72, 272, 171);
		contentPane.add(Datecalendar);

		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(302, 48, 70, 15);

		contentPane.add(lblDate);

		JButton btnCreateActivity = new JButton("Create Activity");
		btnCreateActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * controlamos que no dejen los campos vacios
				 */
				if (NametextField.getText().isEmpty() || LugartextField_1.getText().isEmpty() || DescriptiontextArea.getText().isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Fields are emphy.");
				} else {
					Date fecha = trim(new Date(Datecalendar.getCalendar().getTime().getTime()));
					String name = NametextField.getText();
					String lugar = LugartextField_1.getText();
					String description = DescriptiontextArea.getText();
					try {
						extraActivity = facade.storeExtraActivity(owner, name, lugar, fecha, description);
						if (extraActivity != null) {
							JOptionPane.showMessageDialog(contentPane, "Activity added.");
							if (parent.getClass() == CreatenewOfferGUI.class){
								createnewOfferGUI.addActivity(extraActivity);
							}
							else
								editMyOfferGUI.addActivity(extraActivity);
							dispose();
						} else {
							JOptionPane.showMessageDialog(contentPane, "Activity alredy Exist.");
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		btnCreateActivity.setBounds(302, 269, 272, 60);
		contentPane.add(btnCreateActivity);

		JLabel lblCreateAnExtra = new JLabel("Create an Extra Activity");
		lblCreateAnExtra.setBounds(302, 250, 175, 15);
		contentPane.add(lblCreateAnExtra);

		JLabel lblCreateANew = new JLabel("Create a new Extra Activity");
		lblCreateANew.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCreateANew.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateANew.setBounds(0, 0, 597, 31);
		contentPane.add(lblCreateANew);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 30, 597, 15);
		contentPane.add(separator);
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
