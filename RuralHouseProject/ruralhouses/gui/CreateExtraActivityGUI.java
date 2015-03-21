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
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

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
		setTitle("Rural House System");
		setBounds(100, 100, 613, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		/**
		 * Bussines logic
		 */
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(12, 68, 70, 15);
		contentPane.add(lblName);
		
		NametextField = new JTextField();
		NametextField.setBounds(102, 65, 114, 19);
		contentPane.add(NametextField);
		NametextField.setColumns(10);
		
		JLabel lblLugar = new JLabel("lugar:");
		lblLugar.setBounds(12, 98, 70, 15);
		contentPane.add(lblLugar);
		
		LugartextField_1 = new JTextField();
		LugartextField_1.setBounds(102, 95, 114, 19);
		contentPane.add(LugartextField_1);
		LugartextField_1.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(12, 173, 144, 15);
		contentPane.add(lblDescription);
		
		JTextArea DescriptiontextArea = new JTextArea();
		DescriptiontextArea.setBounds(10, 193, 266, 137);
		contentPane.add(DescriptiontextArea);
		
		JCalendar Datecalendar = new JCalendar();
		Datecalendar.setBounds(new Rectangle(150, 106, 225, 180));
		Datecalendar.setBounds(302, 94, 272, 171);
		contentPane.add(Datecalendar);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBounds(302, 68, 70, 15);
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
//					JOptionPane.showMessageDialog(contentPane,"la fecha es"+ fecha);
//					JOptionPane.showMessageDialog(contentPane,"el nombre es"+ name);
//					JOptionPane.showMessageDialog(contentPane,"el lugar es"+ lugar);
//					JOptionPane.showMessageDialog(contentPane,"la descripcion es"+ description);
					try {
						if(facade.storeExtraActivity(owner, name, lugar, fecha, description) != null){
							JOptionPane.showMessageDialog(contentPane,"Actividad Añadida");
						}else{
							JOptionPane.showMessageDialog(contentPane,"Actividad EXISTENTE");
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
				
			}
		});
		btnCreateActivity.setBounds(302, 276, 272, 54);
		contentPane.add(btnCreateActivity);
		
		JLabel lblNewLabel = new JLabel("Create a new Extra Activity");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(0, 0, 597, 32);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 31, 597, 8);
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
