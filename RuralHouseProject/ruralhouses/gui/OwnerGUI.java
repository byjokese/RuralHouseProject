package gui;

import domain.Owner;
import domain.Users;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OwnerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public OwnerGUI(Owner owner) {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 307, 288);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblOwnerDashboard = new JLabel("Owner Dashboard");
		lblOwnerDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblOwnerDashboard.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOwnerDashboard.setBounds(0, 0, 288, 29);
		contentPane.add(lblOwnerDashboard);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 29, 288, 2);
		contentPane.add(separator);

		JButton SignupHouseBtn = new JButton("Sign-Up House");
		SignupHouseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame s = new SignUpHouseGUI(owner);
				s.setVisible(true);
			}
		});
		SignupHouseBtn.setBounds(10, 39, 268, 43);
		contentPane.add(SignupHouseBtn);

		JButton CreateNewOfferBtn = new JButton("Create new Offer");
		CreateNewOfferBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame s = new CreatenewOfferGUI(owner);
				s.setVisible(true);
			}
		});
		CreateNewOfferBtn.setBounds(10, 147, 268, 43);
		contentPane.add(CreateNewOfferBtn);

		JButton EditMyOffersBtn = new JButton("Edit my Offers");
		EditMyOffersBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame s = new EditMyOfferGUI(owner);
				s.setVisible(true);
			}
		});
		EditMyOffersBtn.setBounds(10, 201, 268, 43);
		contentPane.add(EditMyOffersBtn);

		JLabel infoLabel = new JLabel("");
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setBounds(10, 194, 268, 14);
		contentPane.add(infoLabel);

		JButton EditMyHouseBtn = new JButton("Edit My House");
		EditMyHouseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame s = new EditMyHouseGUI(owner);
				s.setVisible(true);
			}
		});
		EditMyHouseBtn.setBounds(10, 93, 268, 43);
		contentPane.add(EditMyHouseBtn);
	}
}
