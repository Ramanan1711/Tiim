package com.tiim.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class GetAccessDb {

	private static final String USERNAME = "rani";

	  private static final String PASSWORD = "rani";

	  private static final String DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";

	  private static final String URL = "jdbc:odbc:Driver={Microsoft Access Driver (*.accdb};DBQ=C:/Muniasamy/DB.accdb;}";
	//  private static final String URL = "jdbc:odbc:Driver={Microsoft Access Driver (*.accdb};DBQ=C:/Muniasamy/tiimdb.accdb;}";

	  public static void main(String[] args) throws Exception {

	    Class.forName(DRIVER);
	    System.out.println("after driver...");
	    Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

	    connection.close();
	  }
}
