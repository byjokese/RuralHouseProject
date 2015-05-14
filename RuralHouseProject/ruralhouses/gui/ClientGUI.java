package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.paypal.base.exception.SSLConfigurationException;
import com.paypal.base.rest.PayPalRESTException;

import APIS.paypal;
import businessLogic.ApplicationFacadeInterface;
import domain.Booking;
import domain.Client;
import domain.ExtraActivity;
import domain.Offer;
import domain.Owner;
import domain.Users;
import exceptions.DataBaseNotInitialized;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ApplicationFacadeInterface facade;
	private Client client;

	/**
	 * Create the frame.
	 */
	public ClientGUI(Client clt, StartWindow starWindow) {
		setTitle("Rural House System");
		ClientGUI frame = this;
		this.client = clt;
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				starWindow.setVisible(true);
			}
		});
		ApplicationFacadeInterface facade = StartWindow.facadeInterface;
		setBounds(100, 100, 553, 477);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnSwitchUser = new JMenu("Switch user");
		menuBar.add(mnSwitchUser);

		JMenuItem mntmClient = new JMenuItem("Client");
		mntmClient.setEnabled(false);
		mnSwitchUser.add(mntmClient);

		JMenuItem mntmOwner = new JMenuItem("Owner");
		mntmOwner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Users owner = facade.checkLogin(client.getUsername(), client.getPassword(), true);
					if (owner != null) {
						OwnerGUI a = new OwnerGUI((Owner) owner, starWindow);
						a.setLocationRelativeTo(null); 
						a.setVisible(true);
						dispose();
					} else {
						if (JOptionPane.showConfirmDialog(null, "Would you like to activate the owner account?", "Account not activated", 0) == 0) {
							ActivationGUI a = new ActivationGUI();
							a.setLocationRelativeTo(null); 
							a.setVisible(true);
						}
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
		});
		mnSwitchUser.add(mntmOwner);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmChangePassword = new JMenuItem("Change password");
		mnEdit.add(mntmChangePassword);

		JMenuItem mntmEditContactInfo = new JMenuItem("Edit contact info");
		mnEdit.add(mntmEditContactInfo);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblClientDashboard = new JLabel("Client Dashboard");
		lblClientDashboard.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblClientDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientDashboard.setBounds(0, 0, 537, 32);
		contentPane.add(lblClientDashboard);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 29, 571, 12);
		contentPane.add(separator);

		JButton lookOffersBtn = new JButton("Look for Offers");
		lookOffersBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFrame o = new QueryAvailabilityGUI(client, frame);
					o.setLocationRelativeTo(null); 
					o.setVisible(true);
				} catch (DataBaseNotInitialized e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		lookOffersBtn.setBounds(271, 65, 260, 43);
		contentPane.add(lookOffersBtn);

		JButton editBookingBtn = new JButton("Edit my Bookings");

		editBookingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame q = new EditMyBookingGUI(client);
				q.setLocationRelativeTo(null); 
				q.setVisible(true);	
			}
		});
		editBookingBtn.setBounds(271, 119, 260, 43);

		contentPane.add(editBookingBtn);

		JButton qualifyBtn = new JButton("Qualify My Books");
		qualifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame q = new QualifyGUI(client);
				q.setLocationRelativeTo(null); 
				q.setVisible(true);

			}
		});
		qualifyBtn.setBounds(271, 173, 260, 43);
		contentPane.add(qualifyBtn);

		JLabel lblMyBookings = new JLabel("My Bookings");
		lblMyBookings.setBounds(10, 39, 85, 14);
		contentPane.add(lblMyBookings);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 251, 342);
		contentPane.add(scrollPane);

		JTree bookingTree = new JTree();
		bookingTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("My bookings") {
			private static final long serialVersionUID = 1L;
			{
				DefaultMutableTreeNode node_1;
				DefaultMutableTreeNode node_2;
				Date date;
				for (Booking book : client.getBooks()) {
					date = book.getBookingDate();
					node_1 = new DefaultMutableTreeNode(book.toString());
					node_1.add(new DefaultMutableTreeNode("Is Paid: " + book.isPaid()));
					node_1.add(new DefaultMutableTreeNode("Booking date: " + date.toString().substring(0, 9) + " " + date.toString().substring(25, 29)));
					if (!book.getActivieties().isEmpty()) {
						node_2 = new DefaultMutableTreeNode("Activities");
						for (ExtraActivity extra : book.getActivieties()) {
							node_2.add(new DefaultMutableTreeNode(extra));
						}
						node_1.add(node_2);
					}
					add(node_1);
				}
			}
		}));
		scrollPane.setViewportView(bookingTree);
		
		String path_house = "images/icons/house_max.png";
		File houseIconsFile = new File(path_house);
		BufferedImage houseImg = null;
		try {
			houseImg = ImageIO.read(houseIconsFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JLabel houseIconlbl = new JLabel(new ImageIcon(houseImg));
		houseIconlbl.setBounds(338, 278, 128, 128);
		contentPane.add(houseIconlbl);
		
		JButton btnProcessToPay = new JButton("Process to Pay");
		btnProcessToPay.setToolTipText("Select an offer from the left tree to pay.");
		btnProcessToPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(bookingTree.getSelectionPaths());
				Offer offer = new Offer(5, null, Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 2300);
				try {
					JFrame pay = new paypal(offer);
					pay.setVisible(true);
				} catch (SSLConfigurationException e) {
					e.printStackTrace();
				} catch (PayPalRESTException e) {
					e.printStackTrace();
				}
			}
		});
		btnProcessToPay.setBounds(271, 227, 256, 43);
		contentPane.add(btnProcessToPay);
	}
	
	public void updateClient() throws RemoteException {
		client = (Client) facade.updateUser(client.getUsername(), false);
		//owner = facade.updateOwner(owner, owner.getBankAccount(), owner.getRuralHouses(), owner.getExtraActivities(), owner.getMark());
	}
}
