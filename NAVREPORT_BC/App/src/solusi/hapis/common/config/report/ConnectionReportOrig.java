package solusi.hapis.common.config.report;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.zkoss.spring.SpringUtil;

public class ConnectionReportOrig {

	public static Connection con;
	private static String jndi = (String) SpringUtil.getBean("jndiName");
	private static String jndiName = "java:comp/env/"+jndi;
	
	public static Connection getConnection() {
		if(con == null) {
			try {
				
				InitialContext ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup(jndiName);
				con = ds.getConnection();
				
				//Class.forName("org.postgresql.Driver");
				//con = DriverManager.getConnection("jdbc:postgresql://172.16.5.28:5432/hapisdev2", "hapis", "solusi");
			}
			//catch(ClassNotFoundException e) { e.printStackTrace(); }
			catch(Exception e) { e.printStackTrace(); }
		}
		return con;
	}
}

