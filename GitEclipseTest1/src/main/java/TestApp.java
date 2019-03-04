import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;



public class TestApp {
	
	static Connection dbConnection; 
	static Properties configProperties;  

	public static void main(String[] args) throws Exception{ 
		
		String sql; 

		System.out.println("Loading config params...");
		configProperties = new Properties(); 
	    ClassLoader loader = Thread.currentThread().getContextClassLoader();           
	    InputStream stream = loader.getResourceAsStream("config.properties");
	    configProperties.load(stream); 
	    stream.close();
	    System.out.println("Config loaded..."); 
	    
	    System.out.println("Connectin to database...");	    
		Class.forName(configProperties.getProperty("dbDriver"));
		System.out.println("JDBC driver loaded...");
		dbConnection = DriverManager.getConnection(
				configProperties.getProperty("dbConnection"), 
				configProperties.getProperty("dbUserName"), 
				configProperties.getProperty("dbPassword")
				); 
		System.out.println("Connected to database.");
		
		 
		sql = "select id, match_id from foo_random limit 10"; 
		System.out.println(String.format("Fatching data...\n %s", sql));
		Statement sqlStatement = dbConnection.createStatement();  
		System.out.println(sql);
		ResultSet rs = sqlStatement.executeQuery(sql); 
		while(rs.next()) {
			System.out.println(String.format("Record #%d: %s", rs.getInt("id"), rs.getString("match_id")));
		}; 
		dbConnection.close();
 
	}

}
