package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import domain.Offer;

import javax.swing.JScrollPane;

public class AditionaOfferInfoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel activitiesTextField;
	private JTextField offerTextField;
	private JTextField lastdayTextField;
	private JTextField firstdayTextField;
	private JTextField priceTextField;
	private JTextField ruralhouseTextField;
	private JTextField offerNumTextField;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Create the frame.
	 */
	public AditionaOfferInfoGUI(Offer offer) {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 355);
		activitiesTextField = new JPanel();
		activitiesTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(activitiesTextField);
		activitiesTextField.setLayout(null);

		JLabel lblNewLabel = new JLabel("Offer Info");
		lblNewLabel.setBounds(5, 0, 707, 27);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		activitiesTextField.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 28, 722, 11);
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
		for(int i=0;i<offer.getExtraActivities().size();i++){
			Object[] tmp = { new String(offer.getExtraActivities().get(i).getNombre()), new String(offer.getExtraActivities().get(i).getDescription()),
							new String(offer.getExtraActivities().get(i).getOwner().getName()), new String(offer.getExtraActivities().get(i).getLugar()),
							new String(offer.getExtraActivities().get(i).getFecha().toString())};
			data[i] = tmp;
		}
		DefaultTableModel tabmodel = new DefaultTableModel(data, columnNames);

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

		scrollPane.setViewportView(table);
		tableModel = new DefaultTableModel(null, columnNames);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Description", "Owner", "Place", "Date"
			}
		));
				
	}
}