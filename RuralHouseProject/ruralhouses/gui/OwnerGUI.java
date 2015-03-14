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
	public OwnerGUI(Users owner) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 304, 253);
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
				JFrame s = new SignUpHouseGUI( (Owner) owner);
				s.setVisible(true);
			}
		});
		SignupHouseBtn.setBounds(10, 39, 268, 43);
		contentPane.add(SignupHouseBtn);
		
		JButton CreateNewOfferBtn = new JButton("Create new Offer");
		CreateNewOfferBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame s = new CreatenewOfferGUI( (Owner) owner);
				s.setVisible(true);
			}
		});
		CreateNewOfferBtn.setBounds(10, 93, 268, 43);
		contentPane.add(CreateNewOfferBtn);
		
		JButton EditMyOffersBtn = new JButton("Edit my Offers");
		EditMyOffersBtn.setBounds(10, 147, 268, 43);
		contentPane.add(EditMyOffersBtn);
		
		JLabel infoLabel = new JLabel("");
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setBounds(10, 194, 268, 14);
		contentPane.add(infoLabel);
	}
}
