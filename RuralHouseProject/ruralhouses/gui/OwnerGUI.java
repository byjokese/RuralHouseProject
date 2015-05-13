package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import businessLogic.ApplicationFacadeInterface;
import domain.Client;
import domain.Offer;
import domain.Owner;
import domain.RuralHouse;
import domain.Users;

public class OwnerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ApplicationFacadeInterface facade;
	private Owner owner;

	/**
	 * Create the frame.
	 */
	public OwnerGUI(Owner ow, StartWindow starWindow) {
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.owner = ow;
		OwnerGUI frame = this;
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				starWindow.setVisible(true);
			}
		});
		setBounds(100, 100, 553, 477);

		facade = StartWindow.facadeInterface;

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnSwitchUser = new JMenu("Switch user");
		menuBar.add(mnSwitchUser);

		JMenuItem mntmClient = new JMenuItem("Client");
		mntmClient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Users client = facade.checkLogin(owner.getUsername(), owner.getPassword(), false);
					ClientGUI a = new ClientGUI((Client) client, starWindow);
					a.setLocationRelativeTo(null);
					a.setVisible(true);
					dispose();

				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
		});
		mnSwitchUser.add(mntmClient);

		JMenuItem mntmOwner = new JMenuItem("Owner");
		mntmOwner.setEnabled(false);
		mnSwitchUser.add(mntmOwner);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblOwnerDashboard = new JLabel("Owner Dashboard");
		lblOwnerDashboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblOwnerDashboard.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOwnerDashboard.setBounds(0, 0, 537, 29);
		contentPane.add(lblOwnerDashboard);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 29, 637, 2);
		contentPane.add(separator);

		JButton SignupHouseBtn = new JButton("Sign-Up House");
		SignupHouseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame s = new SignUpHouseGUI(owner, frame);
				s.setLocationRelativeTo(null);
				s.setVisible(true);
			}
		});
		SignupHouseBtn.setBounds(279, 63, 248, 43);
		contentPane.add(SignupHouseBtn);

		JButton CreateNewOfferBtn = new JButton("Create new Offer");
		CreateNewOfferBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame s = new CreatenewOfferGUI(owner, frame);
				s.setLocationRelativeTo(null);
				s.setVisible(true);
			}
		});
		CreateNewOfferBtn.setBounds(279, 171, 248, 43);
		contentPane.add(CreateNewOfferBtn);

		JButton EditMyOffersBtn = new JButton("Edit my Offers");
		EditMyOffersBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame s = new EditMyOfferGUI(owner, frame);
				s.setLocationRelativeTo(null);
				s.setVisible(true);
			}
		});
		EditMyOffersBtn.setBounds(279, 225, 248, 43);
		contentPane.add(EditMyOffersBtn);

		JButton EditMyHouseBtn = new JButton("Edit My House");
		EditMyHouseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame s = new EditMyHouseGUI(owner, frame);
				s.setLocationRelativeTo(null);
				s.setVisible(true);
			}
		});
		EditMyHouseBtn.setBounds(279, 117, 248, 43);
		contentPane.add(EditMyHouseBtn);

		JButton EditMyActivityBtn = new JButton("Edit my Activity");
		EditMyActivityBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame s = new EditMyActivityGUI(owner,frame);
				s.setLocationRelativeTo(null);
				s.setVisible(true);
			}
		});
		EditMyActivityBtn.setBounds(279, 279, 248, 43);
		contentPane.add(EditMyActivityBtn);

		JLabel lblMyHouses = new JLabel("My Houses");
		lblMyHouses.setBounds(10, 40, 129, 14);
		contentPane.add(lblMyHouses);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 259, 343);
		contentPane.add(scrollPane);

		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("My Houses") {
			private static final long serialVersionUID = 1L;

			{
				DefaultMutableTreeNode node_1;
				DefaultMutableTreeNode node_2;
				if (!owner.getRuralHouses().isEmpty()) {
					for (RuralHouse rh : owner.getRuralHouses()) {
						node_1 = new DefaultMutableTreeNode(rh.toString());
						if (!rh.getOffers().isEmpty()) {
							for (Offer offer : rh.getOffers()) {
								node_2 = new DefaultMutableTreeNode(offer.toString());
								node_1.add(node_2);
							}
							add(node_1);
						} else {
							node_2 = new DefaultMutableTreeNode("No offers.");
							node_1.add(node_2);
						}
					}
				} else {
					node_1 = new DefaultMutableTreeNode("No houses.");
					add(node_1);
				}
			}
		}));
		scrollPane.setColumnHeaderView(tree);

		String path_house = "images/icons/house.png";
		File houseIconsFile = new File(path_house);
		BufferedImage houseImg = null;
		try {
			houseImg = ImageIO.read(houseIconsFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JLabel houseIconlbl = new JLabel(new ImageIcon(houseImg));
		houseIconlbl.setBounds(366, 331, 75, 75);
		contentPane.add(houseIconlbl);
	}

	public void updateOwner() throws RemoteException {
		owner = (Owner) facade.updateUser(owner.getUsername(), true);
		//owner = facade.updateOwner(owner, owner.getBankAccount(), owner.getRuralHouses(), owner.getExtraActivities(), owner.getMark());
		System.out.println(owner.getUsername());
	}
}
