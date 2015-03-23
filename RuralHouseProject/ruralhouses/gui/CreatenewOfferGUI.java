package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businessLogic.ApplicationFacadeInterface;

import com.toedter.calendar.JCalendar;

import domain.ExtraActivity;
import domain.Owner;
import domain.RuralHouse;

public class CreatenewOfferGUI extends JFrame {

	private JPanel contentPane;
	private JTextField OffersPricetextField;
	private JLabel lblListOfHouses;

	/**
	 * Create the frame.
	 */
	public CreatenewOfferGUI(Owner owner) {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ApplicationFacadeInterface facade = StartWindow.getBusinessLogic();
		setTitle("Rural House System");
		setBounds(100, 100, 1118, 735);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(290, 103, 54, 15);
		contentPane.add(lblPrice);

		OffersPricetextField = new JTextField();
		OffersPricetextField.setBounds(354, 97, 114, 27);
		contentPane.add(OffersPricetextField);
		OffersPricetextField.setColumns(10);

		lblListOfHouses = new JLabel("List of Houses: ");
		lblListOfHouses.setBounds(906, 48, 114, 15);
		contentPane.add(lblListOfHouses);

		JLabel lblFirstDay = new JLabel("First Day:");
		lblFirstDay.setBounds(290, 147, 87, 15);
		contentPane.add(lblFirstDay);

		JLabel lblLastDay = new JLabel("Last Day:");
		lblLastDay.setBounds(581, 147, 87, 15);

		contentPane.add(lblLastDay);

		JCalendar FirstDaycalendar = new JCalendar();
		FirstDaycalendar.setBounds(new Rectangle(150, 106, 225, 180));

		FirstDaycalendar.setBounds(290, 170, 272, 171);

		contentPane.add(FirstDaycalendar);

		JCalendar LastDaycalendar = new JCalendar();
		LastDaycalendar.setBounds(new Rectangle(150, 106, 225, 180));

		LastDaycalendar.setBounds(581, 170, 260, 171);
		contentPane.add(LastDaycalendar);

		JLabel lblInfo = new JLabel("");
		lblInfo.setBounds(292, 591, 549, 32);
		contentPane.add(lblInfo);

		// rellenamos la lista de casas
		DefaultListModel<RuralHouse> houses = new DefaultListModel<RuralHouse>();
		for (RuralHouse rh : owner.getRuralHouses()) {
			houses.addElement(rh);
		}
		JList RHouseslist = new JList();
		RHouseslist.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String adres = houses.elementAt(RHouseslist.getSelectedIndex()).getAddress();
				String city = houses.elementAt(RHouseslist.getSelectedIndex()).getCity();
				String des = houses.elementAt(RHouseslist.getSelectedIndex()).getDescription();
				lblInfo.setText("Address: " + adres + " || City: " + city + " || Description:  " + des);
			}
		});
		RHouseslist.setModel(houses);
		RHouseslist.setBounds(906, 64, 200, 494);
		contentPane.add(RHouseslist);

		// rellenamos las actividades
		DefaultListModel<String> Activities = new DefaultListModel<String>();
		ArrayList<ExtraActivity> lista = new ArrayList<ExtraActivity>();
		for (ExtraActivity rh : owner.getExtraActivities()) {
			Activities.addElement(rh.getNombre());
			lista.add(rh);
		}

		JList list = new JList();
		list.setModel(Activities);
		list.setBounds(12, 64, 200, 494);
		contentPane.add(list);

		JLabel lblAvailablesActivities = new JLabel("Availables Activities:");

		lblAvailablesActivities.setBounds(12, 48, 164, 15);
		contentPane.add(lblAvailablesActivities);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 35, 1106, 15);

		contentPane.add(separator);

		JButton btnCreateActivity = new JButton("Create Activity");
		btnCreateActivity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateExtraActivityGUI(owner);
				a.setVisible(true);
//CONTINUAR!!!!!!!!!!!!!!!!!!!
				System.out.println("Cerrada");
			}
		});

		btnCreateActivity.setBounds(12, 573, 200, 50);
		contentPane.add(btnCreateActivity);

		JButton btnCreateHouse = new JButton("Create House");

		btnCreateHouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new SignUpHouseGUI(owner);
				a.setVisible(true);
			}
		});
		btnCreateHouse.setBounds(906, 573, 200, 50);
		contentPane.add(btnCreateHouse);
		// lista de actividades seleccionadas
		DefaultListModel<String> seleccion = new DefaultListModel<String>();
		ArrayList<ExtraActivity> listaSeleccion = new ArrayList<ExtraActivity>();

		JList Selectedlist = new JList();
		Selectedlist.setBounds(290, 387, 549, 171);
		Selectedlist.setModel(seleccion);
		contentPane.add(Selectedlist);
		// cosas de las seleccionadas

		JButton AddActivitybutton = new JButton(">");
		AddActivitybutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// mostrar datos de las actividades
				if (list.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, " Seleccione Una Actividad para añadir a la lista");
				} else {
					// JOptionPane.showMessageDialog(null,lista.get(list.getSelectedIndex()).getNombre());
					String nombre = lista.get(list.getSelectedIndex()).getNombre();
					String lugar = lista.get(list.getSelectedIndex()).getLugar();
					String fecha = lista.get(list.getSelectedIndex()).getFecha().toString();
					String entrada = nombre + " || " + lugar + " || " + fecha;
					if (!validar(entrada, seleccion)) {
						seleccion.addElement(entrada);
						listaSeleccion.add(lista.get(list.getSelectedIndex()));
					} else {

						JOptionPane.showMessageDialog(null, "YA SE A SELECCIONADO ESTA ACTIVIDAD");
					}
				}
			}
		});
		AddActivitybutton.setFont(new Font("Arial Black", Font.BOLD, 14));
		AddActivitybutton.setBounds(216, 291, 44, 50);
		contentPane.add(AddActivitybutton);

		JLabel lblSelectedActivities = new JLabel("Selected activities:");
		lblSelectedActivities.setBounds(290, 363, 146, 15);
		contentPane.add(lblSelectedActivities);

		JButton DropSelectionbutton = new JButton("<");
		DropSelectionbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Selectedlist.getSelectedIndex() == -1) {

					JOptionPane.showMessageDialog(null, "Seleccione la actividad que quiere quitar");
				} else {
					int index = Selectedlist.getSelectedIndex();
					listaSeleccion.remove(index);
					seleccion.remove(index);
				}
			}
		});
		DropSelectionbutton.setFont(new Font("Arial Black", Font.BOLD, 14));
		DropSelectionbutton.setBounds(236, 452, 44, 50);
		contentPane.add(DropSelectionbutton);

		JLabel lblSelectedHouseInfo = new JLabel("Selected House Info:");
		lblSelectedHouseInfo.setBounds(290, 573, 244, 15);
		contentPane.add(lblSelectedHouseInfo);

		JButton btnCreateOffer = new JButton("Create Offer");
		btnCreateOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// control de precio
				int precio;
				try {
					precio = Integer.parseInt(OffersPricetextField.getText());
				} catch (Exception e2) {
					precio = -1;
				}
				// control de precio
				if (precio >= 0) {
					// control para obligar seleccionar una casa
					if (RHouseslist.getSelectedIndex() == -1) {
						JOptionPane.showMessageDialog(null, "Debes seleccionar Una casa para La Offer");
					} else {
						// control de validez de fecha
						Date firsDay = trim(new Date(FirstDaycalendar.getCalendar().getTime().getTime()));
						Date lastDay = trim(new Date(LastDaycalendar.getCalendar().getTime().getTime()));
						int res = firsDay.compareTo(lastDay);
						if (res <= 0) {
							try {
								if (facade.storeOffer((RuralHouse) RHouseslist.getSelectedValue(), firsDay, lastDay, precio, listaSeleccion) == null) {
									JOptionPane.showMessageDialog(null, "Esta Oferta ya existe");
								} else {
									JOptionPane.showMessageDialog(null, "OFFER CREATED");
								}
							} catch (HeadlessException | RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
								System.out.println("ERROR en el FACADE de create Offer");
							}
						} else {
							JOptionPane.showMessageDialog(null, "FECHA NO VALIDA");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "El precio es incorrecto");
				}
			}
		});
		btnCreateOffer.setBounds(501, 651, 181, 44);
		contentPane.add(btnCreateOffer);

		JLabel lblCreateANew = new JLabel("Create a new Offer");
		lblCreateANew.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateANew.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCreateANew.setBounds(0, 0, 1106, 37);
		contentPane.add(lblCreateANew);
	}

	private boolean validar(String nuevo, DefaultListModel<String> lista) {

		return lista.contains(nuevo);

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
