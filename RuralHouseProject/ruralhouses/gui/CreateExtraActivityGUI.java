package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Owner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import businessLogic.ApplicationFacadeInterface;

import com.toedter.calendar.JCalendar;

import java.awt.Rectangle;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

public class CreateExtraActivityGUI extends JFrame {

	private JPanel contentPane;
	private JTextField NametextField;
	private JTextField LugartextField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateExtraActivityGUI frame = new CreateExtraActivityGUI(null);
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
	public CreateExtraActivityGUI(Owner owner) {
		setTitle("Create a New  Extra Activity");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		/**
		 * Bussines logic
		 */
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(12, 48, 70, 15);
		contentPane.add(lblName);
		
		NametextField = new JTextField();
		NametextField.setBounds(102, 46, 114, 19);
		contentPane.add(NametextField);
		NametextField.setColumns(10);
		
		JLabel lblLugar = new JLabel("lugar:");
		lblLugar.setBounds(12, 102, 70, 15);
		contentPane.add(lblLugar);
		
		LugartextField_1 = new JTextField();
		LugartextField_1.setBounds(102, 100, 114, 19);
		contentPane.add(LugartextField_1);
		LugartextField_1.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(72, 172, 144, 15);
		contentPane.add(lblDescription);
		
		JTextArea DescriptiontextArea = new JTextArea();
		DescriptiontextArea.setBounds(12, 199, 266, 137);
		contentPane.add(DescriptiontextArea);
		
		JCalendar Datecalendar = new JCalendar();
		Datecalendar.setBounds(new Rectangle(150, 106, 225, 180));
		Datecalendar.setBounds(302, 48, 272, 171);
		contentPane.add(Datecalendar);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(392, 32, 70, 15);
		contentPane.add(lblDate);
		
		JButton btnCreateActivity = new JButton("Create Activity");
		btnCreateActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * controlamos que no dejen los campos vacios
				 */
				if(NametextField.getText().isEmpty()||
				   LugartextField_1.getText().isEmpty()||
				   DescriptiontextArea.getText().isEmpty()){
					JOptionPane.showMessageDialog(contentPane, "Campos vacios");
				}else{
					Date fecha =trim(new Date(Datecalendar.getCalendar().getTime().getTime()));
					String name = NametextField.getText();
					String lugar = LugartextField_1.getText();
					String description = DescriptiontextArea.getText();
					JOptionPane.showMessageDialog(contentPane,"la fecha es"+ fecha);
					JOptionPane.showMessageDialog(contentPane,"el nombre es"+ name);
					JOptionPane.showMessageDialog(contentPane,"el lugar es"+ lugar);
					JOptionPane.showMessageDialog(contentPane,"la descripcion es"+ description);
				}
				
				
				
			}
		});
		btnCreateActivity.setBounds(372, 301, 175, 54);
		contentPane.add(btnCreateActivity);
		
		JLabel lblCreateAnExtra = new JLabel("Create an Extra Activity");
		lblCreateAnExtra.setBounds(372, 255, 175, 15);
		contentPane.add(lblCreateAnExtra);
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
