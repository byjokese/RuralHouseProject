/*package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.ExtraActivity;
import domain.Offer;
import javax.swing.JTextArea;

public class test extends JFrame {


	 
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Container activitiesTextField;
	private Component table;
	private DefaultTableModel tableModel;
	private Component ruralhouseTextField;
	private AbstractButton priceTextField;
	private Component offerNumTextField;
	private JTextField firstdayTextField;
	private Component lastdayTextField;
	private AbstractButton offerTextField;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public test(Offer offer) throws IOException {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1075, 355);
		activitiesTextField = new JPanel();
		activitiesTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(activitiesTextField);
		activitiesTextField.setLayout(null);
		
		JLabel ownerRating = new JLabel("Owner Rating:");
		ownerRating.setBounds(611, 82, 85, 14);
		activitiesTextField.add(ownerRating);
		
		String path_1_Star = "images/rating_stars/1_stars.png";
		String path_2_Star = "images/rating_stars/2_stars.png";
		String path_3_Star = "images/rating_stars/3_stars.png";
		String path_4_Star = "images/rating_stars/4_stars.png";
		String path_5_Star = "images/rating_stars/5_stars.png";
		
		File houseRatingFile = null;
		File ownerRatingFile = null;
		
		switch (offer.getRuralHouse().getMark()) {
			case 1:
				houseRatingFile = new File(path_1_Star);
				break;
			case 2:
				houseRatingFile = new File(path_2_Star);	
				break;
			case 3:
				houseRatingFile = new File(path_3_Star);
				break;
			case 4:
				houseRatingFile = new File(path_4_Star);
				break;
			case 5:
				houseRatingFile = new File(path_5_Star);
				break;
		}
		
		switch (offer.getRuralHouse().getOwner().getMark()) {
			case 1:
				ownerRatingFile = new File(path_1_Star);
				break;
			case 2:
				ownerRatingFile = new File(path_2_Star);	
				break;
			case 3:
				ownerRatingFile = new File(path_3_Star);
				break;
			case 4:
				ownerRatingFile = new File(path_4_Star);
				break;
			case 5:
				ownerRatingFile = new File(path_5_Star);
				break;
		}
		
	    BufferedImage ownerRatingImg = ImageIO.read(ownerRatingFile);
	    JLabel ownerRatingImglbl = new JLabel(new ImageIcon(ownerRatingImg));
	    ownerRatingImglbl.setBounds(611, 100, 140, 25);
	    activitiesTextField.add(ownerRatingImglbl);
	    
	    JLabel houseRating = new JLabel("House Rating:");
	    houseRating.setBounds(611, 38, 85, 14);
		activitiesTextField.add(houseRating);
		

	    BufferedImage houseRatingImg = ImageIO.read(houseRatingFile);
	    JLabel houseRatingImgLbl = new JLabel(new ImageIcon(houseRatingImg));
	    houseRatingImgLbl.setBounds(611, 55, 140, 25);
	    activitiesTextField.add(houseRatingImgLbl); 
	    
		JLabel lblNewLabel = new JLabel("Offer Info");
		lblNewLabel.setBounds(5, 0, 1054, 27);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		activitiesTextField.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 28, 1059, 11);
		activitiesTextField.add(separator);

		JLabel lblOffer = new JLabel("Offer #:");
		lblOffer.setBounds(15, 38, 46, 14);
		activitiesTextField.add(lblOffer);

		JLabel lblFirstDay = new JLabel("First day:");
		lblFirstDay.setBounds(15, 75, 56, 14);
		activitiesTextField.add(lblFirstDay);

		JLabel lblLastDay = new JLabel("Last day:");
		lblLastDay.setBounds(15, 112, 56, 14);
		activitiesTextField.add(lblLastDay);

		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(351, 38, 46, 14);
		activitiesTextField.add(lblPrice);

		JLabel lblRuralhouse = new JLabel("RuralHouse:");
		lblRuralhouse.setBounds(351, 75, 75, 14);
		activitiesTextField.add(lblRuralhouse);

		JLabel lblOptionalActivities = new JLabel("Optional Activities:");
		lblOptionalActivities.setBounds(351, 112, 96, 14);
		activitiesTextField.add(lblOptionalActivities);

		offerTextField = new JTextField();
		offerTextField.setEditable(false);
		offerTextField.setBounds(81, 35, 96, 20);
		offerTextField.setText(Integer.toString(offer.getOfferNumber()));
		activitiesTextField.add(offerTextField);
		offerTextField.setColumns(10);

		lastdayTextField = new JTextField();
		lastdayTextField.setEditable(false);
		lastdayTextField.setColumns(10);
		lastdayTextField.setBounds(81, 109, 221, 20);
		lastdayTextField.setHorizontalAlignment(SwingConstants.LEFT);
		lastdayTextField.setText(offer.getLastDay().toString());
		activitiesTextField.add(lastdayTextField);

		firstdayTextField = new JTextField();
		firstdayTextField.setEditable(false);
		firstdayTextField.setColumns(10);
		firstdayTextField.setHorizontalAlignment(SwingConstants.LEFT);
		firstdayTextField.setBounds(81, 72, 221, 20);
		firstdayTextField.setText(offer.getFirstDay().toString());
		activitiesTextField.add(firstdayTextField);

		priceTextField = new JTextField();
		priceTextField.setEditable(false);
		priceTextField.setColumns(10);
		priceTextField.setBounds(429, 38, 96, 20);
		priceTextField.setText(Float.toString(offer.getPrice()));
		activitiesTextField.add(priceTextField);

		ruralhouseTextField = new JTextField();
		ruralhouseTextField.setEditable(false);
		ruralhouseTextField.setColumns(10);
		ruralhouseTextField.setBounds(429, 72, 141, 20);
		ruralhouseTextField.setText(offer.getRuralHouse().toString());
		activitiesTextField.add(ruralhouseTextField);

		offerNumTextField = new JTextField();
		offerNumTextField.setEditable(false);
		offerNumTextField.setColumns(10);
		offerNumTextField.setBounds(453, 112, 117, 20);
		offerNumTextField.setText(offer.getExtraActivities().size() + ": Activities");
		activitiesTextField.add(offerNumTextField);
				

		String columnNames[] = new String[] { "Name", "Description", "Owner", "Place", "Date" };
		Object[][] data = new Object[offer.getExtraActivities().size()][];
		Vector<ExtraActivity> activityList = offer.getExtraActivities();
		for (int i = 0; i < activityList.size(); i++) {
			Object[] tmp = { new String(activityList.get(i).getNombre()), new String(activityList.get(i).getDescription()),
					new String(activityList.get(i).getOwner().getName()), new String(activityList.get(i).getLugar()),
					new String(activityList.get(i).getFecha().toString()) };
			data[i] = tmp;
		}

		JButton closeBtn = new JButton("Close");
		closeBtn.setBounds(10, 283, 702, 25);
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		activitiesTextField.add(closeBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 138, 702, 134);
		activitiesTextField.add(scrollPane);
		table = new JTable();
		table.setEnabled(false);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel = new DefaultTableModel(data, columnNames);
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		
		JLabel lblComments = new JLabel("Comments:");
		lblComments.setBounds(760, 38, 75, 14);
		activitiesTextField.add(lblComments);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(761, 58, 288, 80);
		activitiesTextField.add(textArea);
		for (String[] commet : offer.getRuralHouse().getComments()){
			textArea.append(commet[1]+ " - " + commet[2] + "\n");
		}
		

	}

}*/
