package APIS;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.paypal.base.exception.SSLConfigurationException;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.svcs.services.InvoiceService;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.paypal.svcs.types.pt.CreateAndSendInvoiceRequest;
import com.paypal.svcs.types.pt.CreateAndSendInvoiceResponse;
import com.paypal.svcs.types.pt.InvoiceItemListType;
import com.paypal.svcs.types.pt.InvoiceItemType;
import com.paypal.svcs.types.pt.InvoiceType;

import domain.Offer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;

public class paypal extends JFrame {

	private static final boolean STATUS_ACTIVE = false;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					paypal frame = new paypal(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws PayPalRESTException
	 * @throws SSLConfigurationException
	 */
	public paypal(Offer offer) throws PayPalRESTException, SSLConfigurationException {
		setTitle("Paypal Payment");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 229);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel icon = new JLabel("");
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setIcon(new ImageIcon("C:\\Users\\PcCom\\Documents\\GitHub\\RuralHouseProject\\RuralHouseProject\\images\\icons\\Paypal-logo-pp-2014.png"));
		icon.setBounds(111, 11, 115, 115);
		contentPane.add(icon);
		
		JLabel icon_procsing = new JLabel("");
		icon_procsing.setIcon(new ImageIcon("C:\\Users\\PcCom\\Documents\\GitHub\\RuralHouseProject\\RuralHouseProject\\images\\icons\\Paypal-logo-pp-2014.png"));
		icon_procsing.setHorizontalAlignment(SwingConstants.CENTER);
		icon_procsing.setBounds(0, 11, 115, 115);
		icon_procsing.setVisible(false);
		contentPane.add(icon_procsing);
		
		JLabel procesingicon = new JLabel("icon");
		procesingicon.setVerticalAlignment(SwingConstants.TOP);
		procesingicon.setHorizontalAlignment(SwingConstants.LEFT);
		procesingicon.setIcon(new ImageIcon("C:\\Users\\PcCom\\Documents\\GitHub\\RuralHouseProject\\RuralHouseProject\\images\\icons\\loading-animation.gif"));
		procesingicon.setBounds(111, 87, 121, 39);
		procesingicon.setVisible(false);
		contentPane.add(procesingicon);
		
		JLabel precesinglbl = new JLabel("Procesing  Payment...");
		precesinglbl.setFont(new Font("Segoe WP Semibold", Font.PLAIN, 20));
		precesinglbl.setBounds(111, 26, 211, 50);
		precesinglbl.setVisible(false);
		contentPane.add(precesinglbl);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setSelectedIcon(new ImageIcon("C:\\Users\\PcCom\\Documents\\GitHub\\RuralHouseProject\\RuralHouseProject\\images\\icons\\paypal_button.png"));
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\PcCom\\Documents\\GitHub\\RuralHouseProject\\RuralHouseProject\\images\\icons\\paypal_button_normal.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
				if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						pay(offer);
						icon.setVisible(false);
						precesinglbl.setVisible(true);
						procesingicon.setVisible(true);
						icon_procsing.setVisible(true);
						btnNewButton.setEnabled(false);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(70, 137, 197, 39);
		contentPane.add(btnNewButton);
	}

	private static void pay(Offer offer) throws SSLConfigurationException {
		// Logger logger = Logger.getLogger(this.getClass().toString());

		// ##CreateAndSendInvoiceRequest
		// Use the CreateAndSendInvoiceRequest message to create and send a new
		// invoice. The requester should authenticate the caller and verify that
		// the merchant requesting the invoice has an existing PayPal account in
		// good standing. Once the invoice is created, PayPal sends it to the
		// specified payer, who is notified of the pending invoice.

		// The code for the language in which errors are returned, which must be
		// en_US.
		RequestEnvelope requestEnvelope = new RequestEnvelope();
		requestEnvelope.setErrorLanguage("en_US");

		List<InvoiceItemType> invoiceItemList = new ArrayList<InvoiceItemType>();

		// InvoiceItemType which takes mandatory params:
		//
		// * `Item Name` - SKU or name of the item.
		// * `Quantity` - Item count.
		// * `Amount` - Price of the item, in the currency specified by the
		// invoice.
		//String offername = offer.getRuralHouse().toString();
		InvoiceItemType invoiceItem = new InvoiceItemType("offername", Double.parseDouble("1"), Double.parseDouble(Float.toString(offer.getPrice())));
		//invoiceItem.setDate(Calendar.getInstance().getTime().toString());
		invoiceItemList.add(invoiceItem);

		// Invoice item.
		InvoiceItemListType itemList = new InvoiceItemListType(invoiceItemList);

		// InvoiceType which takes mandatory params:
		//
		// * `Merchant Email` - Merchant email address.
		// * `Personal Email` - Payer email address.
		// * `InvoiceItemList` - List of items included in this invoice.
		// * `CurrencyCode` - Currency used for all invoice item amounts and
		// totals.
		// * `PaymentTerms` - Terms by which the invoice payment is due. It is
		// one of the following values:
		// * DueOnReceipt - Payment is due when the payer receives the invoice.
		// * DueOnDateSpecified - Payment is due on the date specified in the
		// invoice.
		// * Net10 - Payment is due 10 days from the invoice date.
		// * Net15 - Payment is due 15 days from the invoice date.
		// * Net30 - Payment is due 30 days from the invoice date.
		// * Net45 - Payment is due 45 days from the invoice date.
		InvoiceType invoice = new InvoiceType("byjokese-facilitator@hotmail.com", "byjokese-buyer@hotmail.com", itemList, "USD");

		// CreateAndSendInvoiceRequest which takes mandatory params:
		//
		// * `Request Envelope` - Information common to each API operation, such
		// as the language in which an error message is returned.
		// * `Invoice` - Merchant, payer, and invoice information.
		CreateAndSendInvoiceRequest createAndSendInvoiceRequest = new CreateAndSendInvoiceRequest(requestEnvelope, invoice);
		System.out.println(createAndSendInvoiceRequest);

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		InvoiceService service = null;
		try {
			service = new InvoiceService("sdk_config.properties");
		} catch (IOException e) {
			System.out.println("Error Message : " + e.getMessage());
			// logger.severe("Error Message : " + e.getMessage());
		}
		CreateAndSendInvoiceResponse createAndSendInvoiceResponse = null;
		try {

			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			createAndSendInvoiceResponse = service.createAndSendInvoice(createAndSendInvoiceRequest);

		} catch (Exception e) {
			System.out.println("Error Message : " + e.getMessage());
			// logger.severe("Error Message : " + e.getMessage());
		}

		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (createAndSendInvoiceResponse.getResponseEnvelope().getAck().getValue().equalsIgnoreCase("Success")) {
			System.out.println(createAndSendInvoiceResponse);
			System.out.println("Invoice ID : " + createAndSendInvoiceResponse.getInvoiceID());
			// ID of the created invoice.
			// logger.info("Invoice ID : "
			// + createAndSendInvoiceResponse.getInvoiceID());

		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {

			System.out.println("API Error Message :" + createAndSendInvoiceResponse.getError().get(0).getMessage());
			// logger.severe("API Error Message : "
			// + createAndSendInvoiceResponse.getError().get(0)
			// .getMessage());
		}
	}
}
