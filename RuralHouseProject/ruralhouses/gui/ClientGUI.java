package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JSeparator;
import javax.swing.JButton;

import domain.Client;
import exceptions.DataBaseNotInitialized;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ClientGUI(Client client) {
		setTitle("Rural House System");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 307, 288);
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
	}
}
