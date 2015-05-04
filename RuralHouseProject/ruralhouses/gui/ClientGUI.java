package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import domain.Client;
import exceptions.DataBaseNotInitialized;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ClientGUI(Client client) {
		setTitle("Rural House System");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 307, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblClientDashboard = new JLabel("Client Dashboard");
		lblClientDashboard.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblClientDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientDashboard.setBounds(0, 0, 291, 32);
		contentPane.add(lblClientDashboard);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 29, 291, 9);
		contentPane.add(separator);

		JButton lookOffersBtn = new JButton("Look for Offers");
		lookOffersBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFrame o = new QueryAvailabilityGUI(client);
					o.setVisible(true);
				} catch (DataBaseNotInitialized e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		lookOffersBtn.setBounds(10, 39, 268, 43);
		contentPane.add(lookOffersBtn);

		JButton editBookingBtn = new JButton("Edit my Bookings");
		editBookingBtn.setBounds(10, 93, 268, 43);
		contentPane.add(editBookingBtn);
		
		JButton qualifyBtn = new JButton("Qualify My Books");
		qualifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame q = new QualifyGUI(client);
				q.setVisible(true);
				
			}
		});
		qualifyBtn.setBounds(10, 147, 271, 43);
		contentPane.add(qualifyBtn);
	}
}
