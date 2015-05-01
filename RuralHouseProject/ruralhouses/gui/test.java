package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import domain.Offer;

public class test extends JFrame {

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

	private JLabel weathericonlbl1, weathericonlbl2, weathericonlbl3;
	private JLabel description1, description2, description3;
	private JLabel tmpMax1, tmpMax2, tmpMax3;
	private JLabel tmpMin1, tmpMin2, tmpMin3;
	private JLabel humidity1, humidity2, humidity3;
	private JLabel wind1, wind2, wind3;
	private JLabel clouds1, clouds2, clouds3;
	private JLabel rain1, rain2, rain3;

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

	@SuppressWarnings("deprecation")
	public test(Offer offer) throws IOException {
		setTitle("DEGUG! ON TEST");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1075, 587);
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

		/*
		 * File houseRatingFile = null; File ownerRatingFile = null;
		 * 
		 * switch (offer.getRuralHouse().getMark()) { case 1: houseRatingFile = new File(path_1_Star); break; case 2: houseRatingFile = new File(path_2_Star);
		 * break; case 3: houseRatingFile = new File(path_3_Star); break; case 4: houseRatingFile = new File(path_4_Star); break; case 5: houseRatingFile = new
		 * File(path_5_Star); break; default: System.out.println("No hay Opcion! a 0"); }
		 * 
		 * switch (offer.getRuralHouse().getOwner().getMark()) { case 1: ownerRatingFile = new File(path_1_Star); break; case 2: ownerRatingFile = new
		 * File(path_2_Star); break; case 3: ownerRatingFile = new File(path_3_Star); break; case 4: ownerRatingFile = new File(path_4_Star); break; case 5:
		 * ownerRatingFile = new File(path_5_Star); break; default: System.out.println("No hay Opcion! a 0"); }
		 * 
		 * BufferedImage ownerRatingImg = ImageIO.read(ownerRatingFile); JLabel ownerRatingImglbl = new JLabel(new ImageIcon(ownerRatingImg));
		 * ownerRatingImglbl.setBounds(611, 100, 140, 25); activitiesTextField.add(ownerRatingImglbl);
		 */

		JLabel houseRating = new JLabel("House Rating:");
		houseRating.setBounds(611, 38, 85, 14);
		activitiesTextField.add(houseRating);

		/*
		 * BufferedImage houseRatingImg = ImageIO.read(houseRatingFile); JLabel houseRatingImgLbl = new JLabel(new ImageIcon(houseRatingImg));
		 * houseRatingImgLbl.setBounds(611, 55, 140, 25); activitiesTextField.add(houseRatingImgLbl);
		 */

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
		// offerTextField.setText(Integer.toString(offer.getOfferNumber()));
		activitiesTextField.add(offerTextField);
		offerTextField.setColumns(10);

		lastdayTextField = new JTextField();
		lastdayTextField.setEditable(false);
		lastdayTextField.setColumns(10);
		lastdayTextField.setBounds(81, 109, 221, 20);
		lastdayTextField.setHorizontalAlignment(SwingConstants.LEFT);
		// lastdayTextField.setText(offer.getLastDay().toString());
		activitiesTextField.add(lastdayTextField);

		firstdayTextField = new JTextField();
		firstdayTextField.setEditable(false);
		firstdayTextField.setColumns(10);
		firstdayTextField.setHorizontalAlignment(SwingConstants.LEFT);
		firstdayTextField.setBounds(81, 72, 221, 20);
		// firstdayTextField.setText(offer.getFirstDay().toString());
		activitiesTextField.add(firstdayTextField);

		priceTextField = new JTextField();
		priceTextField.setEditable(false);
		priceTextField.setColumns(10);
		priceTextField.setBounds(429, 38, 96, 20);
		// priceTextField.setText(Float.toString(offer.getPrice()));
		activitiesTextField.add(priceTextField);

		ruralhouseTextField = new JTextField();
		ruralhouseTextField.setEditable(false);
		ruralhouseTextField.setColumns(10);
		ruralhouseTextField.setBounds(429, 72, 141, 20);
		// ruralhouseTextField.setText(offer.getRuralHouse().toString());
		activitiesTextField.add(ruralhouseTextField);

		offerNumTextField = new JTextField();
		offerNumTextField.setEditable(false);
		offerNumTextField.setColumns(10);
		offerNumTextField.setBounds(453, 112, 117, 20);
		// offerNumTextField.setText(offer.getExtraActivities().size() + ": Activities");
		activitiesTextField.add(offerNumTextField);

		String columnNames[] = new String[] { "Name", "Description", "Owner", "Place", "Date" };
		/*
		 * Object[][] data = new Object[offer.getExtraActivities().size()][]; Vector<ExtraActivity> activityList = offer.getExtraActivities(); for (int i = 0; i
		 * < activityList.size(); i++) { Object[] tmp = { new String(activityList.get(i).getNombre()), new String(activityList.get(i).getDescription()), new
		 * String(activityList.get(i).getOwner().getName()), new String(activityList.get(i).getLugar()), new String(activityList.get(i).getFecha().toString())
		 * }; data[i] = tmp; }
		 */

		JButton closeBtn = new JButton("Close");
		closeBtn.setBounds(5, 512, 1039, 25);
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		activitiesTextField.add(closeBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 170, 702, 134);
		activitiesTextField.add(scrollPane);
		table = new JTable();
		table.setEnabled(false);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		// tableModel = new DefaultTableModel(data, columnNames);
		tableModel = new DefaultTableModel(null, columnNames); // FOR TEST CLASS
		table.setModel(tableModel);

		JLabel lblComments = new JLabel("Comments:");
		lblComments.setBounds(760, 38, 75, 14);
		activitiesTextField.add(lblComments);

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(761, 58, 288, 82);
		activitiesTextField.add(textArea);

		JLabel lblOptionalActivities_1 = new JLabel("Optional Activities:");
		lblOptionalActivities_1.setBounds(15, 151, 104, 14);
		activitiesTextField.add(lblOptionalActivities_1);

		JLabel lblWeatherForecast = new JLabel("Weather forecast: ");
		lblWeatherForecast.setBounds(760, 151, 167, 14);
		activitiesTextField.add(lblWeatherForecast);

		weathericonlbl1 = new JLabel();
		weathericonlbl1.setBounds(871, 177, 46, 32);
		activitiesTextField.add(weathericonlbl1);

		JLabel dayLbl1 = new JLabel("Day 1:");
		dayLbl1.setFont(new Font("Tahoma", Font.BOLD, 13));
		dayLbl1.setBounds(760, 172, 56, 27);
		activitiesTextField.add(dayLbl1);

		JLabel tmpMax1Lbl = new JLabel("Tmp Max: ");
		tmpMax1Lbl.setBounds(789, 220, 61, 14);
		activitiesTextField.add(tmpMax1Lbl);

		tmpMax1 = new JLabel("");
		tmpMax1.setEnabled(false);
		tmpMax1.setBounds(871, 220, 46, 14);
		activitiesTextField.add(tmpMax1);

		JLabel tmpMinLbl1 = new JLabel("Tmp Min: ");
		tmpMinLbl1.setBounds(789, 235, 61, 14);
		activitiesTextField.add(tmpMinLbl1);

		tmpMin1 = new JLabel("");
		tmpMin1.setEnabled(false);
		tmpMin1.setBounds(871, 235, 46, 14);
		activitiesTextField.add(tmpMin1);

		JLabel humidityLbl1 = new JLabel("Humidity:");
		humidityLbl1.setBounds(789, 250, 61, 14);
		activitiesTextField.add(humidityLbl1);

		humidity1 = new JLabel("");
		humidity1.setEnabled(false);
		humidity1.setBounds(871, 250, 46, 14);
		activitiesTextField.add(humidity1);

		description1 = new JLabel("");
		description1.setEnabled(false);
		description1.setBounds(927, 189, 122, 14);
		activitiesTextField.add(description1);

		JLabel rainLbl1 = new JLabel("Rain:");
		rainLbl1.setBounds(920, 220, 46, 14);
		activitiesTextField.add(rainLbl1);

		rain1 = new JLabel("");
		rain1.setEnabled(false);
		rain1.setBounds(964, 220, 46, 14);
		activitiesTextField.add(rain1);

		JLabel windLbl1 = new JLabel("Wind:");
		windLbl1.setBounds(920, 235, 46, 14);
		activitiesTextField.add(windLbl1);

		wind1 = new JLabel("");
		wind1.setEnabled(false);
		wind1.setBounds(964, 235, 46, 14);
		activitiesTextField.add(wind1);

		JLabel lblClouds = new JLabel("Clouds:");
		lblClouds.setBounds(920, 250, 46, 14);
		activitiesTextField.add(lblClouds);

		clouds1 = new JLabel("");
		clouds1.setEnabled(false);
		clouds1.setBounds(964, 250, 46, 14);
		activitiesTextField.add(clouds1);

		JSeparator separator1 = new JSeparator();
		separator1.setBounds(766, 272, 293, 2);
		activitiesTextField.add(separator1);

		weathericonlbl2 = new JLabel((Icon) null);
		weathericonlbl2.setBounds(871, 280, 46, 32);
		activitiesTextField.add(weathericonlbl2);

		JLabel tmpMaxLbl2 = new JLabel("Tmp Max: ");
		tmpMaxLbl2.setBounds(789, 323, 61, 14);
		activitiesTextField.add(tmpMaxLbl2);

		tmpMax2 = new JLabel("");
		tmpMax2.setEnabled(false);
		tmpMax2.setBounds(871, 323, 46, 14);
		activitiesTextField.add(tmpMax2);

		JLabel tmpMinLbl2 = new JLabel("Tmp Min: ");
		tmpMinLbl2.setBounds(789, 338, 61, 14);
		activitiesTextField.add(tmpMinLbl2);

		tmpMin2 = new JLabel("");
		tmpMin2.setEnabled(false);
		tmpMin2.setBounds(871, 338, 46, 14);
		activitiesTextField.add(tmpMin2);

		JLabel humidityLbl2 = new JLabel("Humidity:");
		humidityLbl2.setBounds(789, 353, 61, 14);
		activitiesTextField.add(humidityLbl2);

		humidity2 = new JLabel("");
		humidity2.setEnabled(false);
		humidity2.setBounds(871, 353, 46, 14);
		activitiesTextField.add(humidity2);

		description2 = new JLabel("");
		description2.setEnabled(false);
		description2.setBounds(927, 292, 122, 14);
		activitiesTextField.add(description2);

		JLabel rainLbl2 = new JLabel("Rain:");
		rainLbl2.setBounds(920, 323, 46, 14);
		activitiesTextField.add(rainLbl2);

		rain2 = new JLabel("");
		rain2.setEnabled(false);
		rain2.setBounds(964, 323, 46, 14);
		activitiesTextField.add(rain2);

		JLabel windLbl2 = new JLabel("Wind:");
		windLbl2.setBounds(920, 338, 46, 14);
		activitiesTextField.add(windLbl2);

		wind2 = new JLabel("");
		wind2.setEnabled(false);
		wind2.setBounds(964, 338, 46, 14);
		activitiesTextField.add(wind2);

		JLabel cloudsLbl2 = new JLabel("Clouds:");
		cloudsLbl2.setBounds(920, 353, 46, 14);
		activitiesTextField.add(cloudsLbl2);

		clouds2 = new JLabel("");
		clouds2.setEnabled(false);
		clouds2.setBounds(964, 353, 46, 14);
		activitiesTextField.add(clouds2);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(766, 375, 293, 2);
		activitiesTextField.add(separator_1);

		JLabel dayLbl2 = new JLabel("Day 2:");
		dayLbl2.setFont(new Font("Tahoma", Font.BOLD, 13));
		dayLbl2.setBounds(760, 275, 56, 27);
		activitiesTextField.add(dayLbl2);

		weathericonlbl3 = new JLabel((Icon) null);
		weathericonlbl3.setBounds(871, 383, 46, 32);
		activitiesTextField.add(weathericonlbl3);

		JLabel tmpMaxLbl3 = new JLabel("Tmp Max: ");
		tmpMaxLbl3.setBounds(789, 426, 61, 14);
		activitiesTextField.add(tmpMaxLbl3);

		tmpMax3 = new JLabel("");
		tmpMax3.setEnabled(false);
		tmpMax3.setBounds(871, 426, 46, 14);
		activitiesTextField.add(tmpMax3);

		JLabel tmpMinLbl3 = new JLabel("Tmp Min: ");
		tmpMinLbl3.setBounds(789, 441, 61, 14);
		activitiesTextField.add(tmpMinLbl3);

		tmpMin3 = new JLabel("");
		tmpMin3.setEnabled(false);
		tmpMin3.setBounds(871, 441, 46, 14);
		activitiesTextField.add(tmpMin3);

		JLabel humidityLbl3 = new JLabel("Humidity:");
		humidityLbl3.setBounds(789, 456, 61, 14);
		activitiesTextField.add(humidityLbl3);

		humidity3 = new JLabel("");
		humidity3.setEnabled(false);
		humidity3.setBounds(871, 456, 46, 14);
		activitiesTextField.add(humidity3);

		description3 = new JLabel("");
		description3.setEnabled(false);
		description3.setBounds(927, 395, 122, 14);
		activitiesTextField.add(description3);

		JLabel rainLbl3 = new JLabel("Rain:");
		rainLbl3.setBounds(920, 426, 46, 14);
		activitiesTextField.add(rainLbl3);

		rain3 = new JLabel("");
		rain3.setEnabled(false);
		rain3.setBounds(964, 426, 46, 14);
		activitiesTextField.add(rain3);

		JLabel windLbl3 = new JLabel("Wind:");
		windLbl3.setBounds(920, 441, 46, 14);
		activitiesTextField.add(windLbl3);

		wind3 = new JLabel("");
		wind3.setEnabled(false);
		wind3.setBounds(964, 441, 46, 14);
		activitiesTextField.add(wind3);

		JLabel cloudsLbl3 = new JLabel("Clouds:");
		cloudsLbl3.setBounds(920, 456, 46, 14);
		activitiesTextField.add(cloudsLbl3);

		clouds3 = new JLabel("");
		clouds3.setEnabled(false);
		clouds3.setBounds(964, 456, 46, 14);
		activitiesTextField.add(clouds3);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(766, 478, 293, 2);
		activitiesTextField.add(separator_2);

		JLabel dayLbl3 = new JLabel("Day 3:");
		dayLbl3.setFont(new Font("Tahoma", Font.BOLD, 13));
		dayLbl3.setBounds(760, 378, 56, 27);
		activitiesTextField.add(dayLbl3);

		/*
		 * for (String[] commet : offer.getRuralHouse().getComments()) { textArea.append(commet[0] + " - " + commet[1] + "\n"); }
		 */

		/* ___________________________________________MANUAL MODE__________________________________________________ */
		HttpClient httpClient = HttpClientBuilder.create().build();
		JSONObject json = null;
		try {
			// Example URL: http://api.openweathermap.org/data/2.5/weather?q=London,uk
			// APPID=880c1d9d2518e01b6dfe8175ec6c4196
			// &units=metric
			HttpGet request = new HttpGet("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + offer.getRuralHouse().getCity()
					+ ",Es&cnt=3&mode=json&units=metric");
			request.addHeader("x-api-key:", "880c1d9d2518e01b6dfe8175ec6c4196");
			HttpResponse response = httpClient.execute(request);
			String json_string = EntityUtils.toString(response.getEntity());
			json = new JSONObject(json_string);
			updateWeatherInfo(json);
		} catch (Exception ex) {
			// handle exception here
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

	}

	private void updateWeatherInfo(JSONObject jsonContent) {
		URL iconPath1 = null;
		URL iconPath2 = null;
		URL iconPath3 = null;
		JSONObject jsonday;
		JSONArray dayweather;
		JSONObject daytemp;
		JSONObject dayWeather;
		String icon = null;
		try {
			JSONArray jsonlist = jsonContent.getJSONArray("list");
			for (int i = 0; i <= 2; i++) {
				switch (i) {
				case 0:
					jsonday = jsonlist.getJSONObject(i);
					daytemp = jsonday.getJSONObject("temp");
					tmpMax1.setText(Double.toString(daytemp.getDouble("max")) + "ºC");
					tmpMin1.setText(Double.toString(daytemp.getDouble("min")) + "ºC");
					dayweather = jsonday.getJSONArray("weather");
					dayWeather = dayweather.getJSONObject(0);
					description1.setText(dayWeather.getString("description"));
					icon = dayWeather.getString("icon");
					wind1.setText(Double.toString(jsonday.getDouble("speed")) + " m/s");
					clouds1.setText(Double.toString(jsonday.getDouble("clouds")) + "%");
					rain1.setText(Double.toString(jsonday.getDouble("rain")) + "%");
					humidity1.setText(Double.toString(jsonday.getDouble("humidity")) + "%");
					try {
						iconPath1 = new URL("http://openweathermap.org/img/w/" + icon + ".png");
					} catch (MalformedURLException e) {
						System.out.println("Weather Icon Not Found");
					}
					break;
				case 1:
					jsonday = jsonlist.getJSONObject(i);
					daytemp = jsonday.getJSONObject("temp");
					tmpMax2.setText(Double.toString(daytemp.getDouble("max")) + "ºC");
					tmpMin2.setText(Double.toString(daytemp.getDouble("min")) + "ºC");
					dayweather = jsonday.getJSONArray("weather");
					dayWeather = dayweather.getJSONObject(0);
					description2.setText(dayWeather.getString("description"));
					icon = dayWeather.getString("icon");
					wind2.setText(Double.toString(jsonday.getDouble("speed")) + " m/s");
					clouds2.setText(Double.toString(jsonday.getDouble("clouds")) + "%");
					rain2.setText(Double.toString(jsonday.getDouble("rain")) + "%");
					humidity2.setText(Double.toString(jsonday.getDouble("humidity")) + "%");
					try {
						iconPath2 = new URL("http://openweathermap.org/img/w/" + icon + ".png");
					} catch (MalformedURLException e) {
						System.out.println("Weather Icon Not Found");
					}
					break;
				case 2:
					jsonday = jsonlist.getJSONObject(i);
					daytemp = jsonday.getJSONObject("temp");
					tmpMax3.setText(Double.toString(daytemp.getDouble("max")) + "ºC");
					tmpMin3.setText(Double.toString(daytemp.getDouble("min")) + "ºC");
					dayweather = jsonday.getJSONArray("weather");
					dayWeather = dayweather.getJSONObject(0);
					description3.setText(dayWeather.getString("description"));
					icon = dayWeather.getString("icon");
					wind3.setText(Double.toString(jsonday.getDouble("speed")) + " m/s");
					clouds3.setText(Double.toString(jsonday.getDouble("clouds")) + "%");
					rain3.setText(Double.toString(jsonday.getDouble("rain")) + "%");
					humidity3.setText(Double.toString(jsonday.getDouble("humidity")) + "%");
					try {
						iconPath3 = new URL("http://openweathermap.org/img/w/" + icon + ".png");
					} catch (MalformedURLException e) {
						System.out.println("Weather Icon Not Found");
					}
					break;
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		BufferedImage weathericon1 = null;
		BufferedImage weathericon2 = null;
		BufferedImage weathericon3 = null;
		try {
			weathericon1 = ImageIO.read(iconPath1);
			weathericon2 = ImageIO.read(iconPath2);
			weathericon3 = ImageIO.read(iconPath3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		weathericonlbl1.setIcon(new ImageIcon(weathericon1));
		weathericonlbl2.setIcon(new ImageIcon(weathericon2));
		weathericonlbl3.setIcon(new ImageIcon(weathericon3));
	}
}
