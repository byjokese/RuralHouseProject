package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;

import domain.Booking;
import domain.Client;

import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import java.rmi.RemoteException;
import java.util.Calendar;

import javax.swing.JTextArea;

import businessLogic.ApplicationFacadeInterface;

public class QualifyGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private Client client;

	/**
	 * Create the frame.
	 */
	public QualifyGUI(Client client) {
		this.client = client;
		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblQualifyMyBooks = new JLabel("Qualify My Books");
		lblQualifyMyBooks.setHorizontalAlignment(SwingConstants.CENTER);
		lblQualifyMyBooks.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblQualifyMyBooks.setBounds(0, 0, 639, 32);
		contentPane.add(lblQualifyMyBooks);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 31, 639, 8);
		contentPane.add(separator);

		JLabel ownerLbl = new JLabel("Owner:");
		ownerLbl.setBounds(434, 71, 46, 14);
		contentPane.add(ownerLbl);

		JLabel ownerContentLbl = new JLabel("");
		ownerContentLbl.setBounds(481, 71, 147, 14);
		contentPane.add(ownerContentLbl);

		JSlider ownerSlider = new JSlider();
		ownerSlider.setEnabled(false);
		ownerSlider.setPaintTicks(true);
		ownerSlider.setPaintLabels(true);
		ownerSlider.setMinorTickSpacing(1);
		ownerSlider.setFont(new Font("Tahoma", Font.PLAIN, 10));
		ownerSlider.setMajorTickSpacing(1);
		ownerSlider.setValue(3);
		ownerSlider.setMinimum(1);
		ownerSlider.setMaximum(5);
		ownerSlider.setBounds(434, 96, 200, 44);
		contentPane.add(ownerSlider);

		JLabel houseLbl = new JLabel("House:");
		houseLbl.setBounds(434, 167, 46, 14);
		contentPane.add(houseLbl);

		JLabel houseContentLbl = new JLabel("");
		houseContentLbl.setBounds(481, 167, 147, 14);
		contentPane.add(houseContentLbl);

		JSlider houseSlider = new JSlider();
		houseSlider.setEnabled(false);
		houseSlider.setValue(3);
		houseSlider.setPaintTicks(true);
		houseSlider.setPaintLabels(true);
		houseSlider.setMinorTickSpacing(1);
		houseSlider.setMinimum(1);
		houseSlider.setMaximum(5);
		houseSlider.setMajorTickSpacing(1);
		houseSlider.setFont(new Font("Tahoma", Font.PLAIN, 10));
		houseSlider.setBounds(438, 184, 200, 44);
		contentPane.add(houseSlider);

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		initializeTable();
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);

		ListSelectionModel selectionModel = table.getSelectionModel();

		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Booking book = client.getBooks().get(table.getSelectedRow());
				ownerSlider.setEnabled(true);
				houseSlider.setEnabled(true);
				ownerContentLbl.setText(book.getOffer().getRuralHouse().getOwner().getName());
				houseContentLbl.setText(book.getOffer().getRuralHouse().toString());
			}
		});

		scrollPane.setBounds(10, 62, 414, 166);
		contentPane.add(scrollPane);

		JLabel lblSelectABook = new JLabel("Select a book to qualify:");
		lblSelectABook.setBounds(10, 43, 147, 14);
		contentPane.add(lblSelectABook);

		JCheckBox anonimouschckbx = new JCheckBox("Anonimous");
		anonimouschckbx.setBounds(10, 235, 200, 23);
		contentPane.add(anonimouschckbx);

		JTextArea commentTextArea = new JTextArea();
		commentTextArea.setBounds(10, 258, 618, 81);
		contentPane.add(commentTextArea);

		JButton qualifyBtn = new JButton("Qualify");
		qualifyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int housemark = houseSlider.getValue();
				int ownermark = ownerSlider.getValue();
				boolean anonious = anonimouschckbx.isSelected();
				String comment = commentTextArea.getText();
				try {
					if (!commentTextArea.equals("")) {
						facade.qualify(ownermark, housemark, anonious, comment, client.getName(), client.getBooks().get(table.getSelectedRow()));
					} else
						JOptionPane.showMessageDialog(null, "Inserte un comentaio.");
				} catch (RemoteException e1) {
					System.out.println(e1.getMessage());
				}
			}
		});
		qualifyBtn.setBounds(10, 350, 303, 32);
		contentPane.add(qualifyBtn);

		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		closeBtn.setBounds(323, 351, 305, 32);
		contentPane.add(closeBtn);

	}

	private void initializeTable() {
		Object[][] data = new Object[client.getBooks().size()][];
		int row = 0;
		Calendar a = Calendar.getInstance();
		Calendar b = Calendar.getInstance();
		Calendar current = Calendar.getInstance();
		for (Booking book : client.getBooks()) {
			a.setTime(book.getOffer().getFirstDay());
			b.setTime(book.getOffer().getLastDay());
			if (b.getTime().compareTo(current.getTime()) < 0) {
				Object[] tmp = { new String(Integer.toString(book.getBookingNumber())),
						new String(Integer.toString(b.get(Calendar.DAY_OF_YEAR) - a.get(Calendar.DAY_OF_YEAR))),
						new String(book.getOffer().getRuralHouse().toString()), new String(book.getOffer().getFirstDay().toString()),
						new String(book.getOffer().getLastDay().toString()), new String(Float.toString(book.getOffer().getPrice())) };
				data[row] = tmp;
				row++;
			}
		}
		table.setModel(new DefaultTableModel(data, new String[] { "Book#", "Nights", "Rural House", "First Day", "Last Day", "Price" }));
	}
}
