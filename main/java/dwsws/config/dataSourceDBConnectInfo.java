package dwsws.config;

public class dataSourceDBConnectInfo {
	//For database connection
//	public static final String databaseUrl = "jdbc:mysql://localhost:3306/sws_dev?serverTimezone=UTC&useSSL=false";
//	public static final String databaseUsername = "root";
//	public static final String databasePassword = "";
	
	//For database connection
	public static final String databaseUrl = "jdbc:mysql://121.78.169.205:3306/sws_dev?serverTimezone=UTC&useSSL=false";
	public static final String databaseUsername = "sws_admin";
	public static final String databasePassword = "sws1!";
	
	//For database connection
//	public static final String databaseUrl = "jdbc:mysql://10.0.101.88:3306/sws?serverTimezone=UTC&useSSL=false";
//	public static final String databaseUsername = "sws_mng";
//	public static final String databasePassword = "sws1!";
	
	//For Groupware API sync
	public static final String userInfoAPIId = "smart";
	public static final String userInfoAPIPassword = "smart!35";
	public static final String GroupWareTokenGetURL = "http://121.78.169.136:8280/alfresco/service/api/login?u=";
	public static final String GroupWareUserInfoGetURL = "http://121.78.169.136:8280/alfresco/service/org/v1/externalInterface/person?sr_schema=sr00003&alf_ticket=";
	
	//For Groupware SSO sync
	public static final String encrypterKey = "~gw-smartweb-SecretKey";
	public static final String encrypterInitializer = "~gw-smartweb-InitVector";
}
