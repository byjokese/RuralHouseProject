package configuration;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigXML {
	private String businessLogicNode;

	private String portRMI;

	private String serviceRMI;

	private String javaPolicyPath;

	private static String db4oFilename;

	//Two possible values: "open" or "initialize"
	private String dataBaseOpenMode;

	//Two possible values: true (no instance of RemoteServer needs to be launched) or false (RemoteServer needs to be run first)
	private boolean businessLogicLocal;

	//Two possible values: true (if the database is in same node as business logic ) or false (in other case)
	private boolean databaseLocal;
	
	private String databaseNode;

	private int activationDepth;
	
	private int updateDepth;
	
	private int databasePort;
	
	private String user;
	
	private String password;
	
	public int getDatabasePort() {
		return databasePort;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public int getActivationDepth() {
		return activationDepth;
	}

	public int getUpdateDepth() {
		return updateDepth;
	}
	
	public boolean isDatabaseLocal() {
		return databaseLocal;
	}

	public boolean isBusinessLogicLocal() {
		return businessLogicLocal;
	}
	private static ConfigXML theInstance = new ConfigXML();

	private ConfigXML(){
		
		  try {
			  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			  Document doc = dBuilder.parse(new File("config.xml"));
			  doc.getDocumentElement().normalize();

			  NodeList list = doc.getElementsByTagName("config");
			  Element config = (Element) list.item(0); // list.item(0) is a Node that is an Element

			  
				//Two possible values: true (no instance of RemoteServer needs to be launched) or false (RemoteServer needs to be run first)
			  String value= ((Element)config.getElementsByTagName("businessLogic").item(0)).getAttribute("local");
			  businessLogicLocal=value.equals("true");

			  businessLogicNode = getTagValue("businessLogicNode", config);

			  portRMI = getTagValue("portRMI", config);

			  serviceRMI = getTagValue("serviceRMI", config);

			  javaPolicyPath= getTagValue("javaPolicyPath", config);

			  db4oFilename = getTagValue("db4oFilename", config);

				//Two possible values: true (no instance of RemoteServer needs to be launched) or false (RemoteServer needs to be run first)
			  value= ((Element)config.getElementsByTagName("database").item(0)).getAttribute("local");
			  databaseLocal=value.equals("true");
			  
			  
			  //Two possible values: "open" or "initialize"
			  dataBaseOpenMode= getTagValue("dataBaseOpenMode", config);

	
			  databaseNode = getTagValue("databaseNode", config);

			  activationDepth=Integer.parseInt(getTagValue("activationDepth", config));

			  updateDepth=Integer.parseInt(getTagValue("updateDepth", config));
			  
			  
			  databasePort=Integer.parseInt(getTagValue("databasePort", config));
				
			  user=getTagValue("user", config);
				
			  password=getTagValue("password", config);

			  System.out.print("Read from config.xml: ");
			  System.out.print("\t businessLogicLocal="+businessLogicLocal);
			  System.out.print("\t databaseLocal="+databaseLocal);
			  System.out.println("\t dataBaseOpenMode="+dataBaseOpenMode); 
					  
		  } catch (Exception e) {
			System.out.println("Error in ConfigXML.java: problems with config.xml");
		    e.printStackTrace();
		  }		
		
		
		
		
	}

	private static String getTagValue(String sTag, Element eElement)
	 {
		  NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		  Node nValue = (Node) nlList.item(0);

		  return nValue.getNodeValue();

	 }
	
	public static ConfigXML getInstance() {
		return theInstance;
	}

	public String getBusinessLogicNode() {
		return businessLogicNode;
	}

	public String getPortRMI() {
		return portRMI;
	}

	public String getServiceRMI() {
		return serviceRMI;
	}
	public String getDb4oFilename(){
		return db4oFilename;
	}
	public String getJavaPolicyPath(){
		return javaPolicyPath;
	}
	public String getDataBaseOpenMode(){
		return dataBaseOpenMode;
	}

	public String getDatabaseNode() {
		return databaseNode;
	}

}
