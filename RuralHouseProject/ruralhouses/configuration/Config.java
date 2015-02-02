package configuration;


public class Config{

	private final String serverRMI = "localhost";

	private final String portRMI = "1099";

	private final String serviceRMI = "RuralHouses";
	
	private final String javaPolicyPath="java.policy";
	
	final static String db4oFilename = "casas.db4o";
	
	//Two possible values: "open" or "initialize"
	private final String dataBaseOpenMode="initialize";

	//Two possible values: true (no instance of RemoteServer needs to be launched) or false (RemoteServer needs to be run first)
	private final boolean local=true;
	
	public boolean isLocal() {
		return local;
	}
	private static Config theInstance = new Config();

    private Config(){}
	
	public static Config getInstance() {
		return theInstance;
	}

	public String getServerRMI() {
		return serverRMI;
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

}
