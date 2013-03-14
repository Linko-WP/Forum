package com.google.gwt.forum.server;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysql.jdbc.*;


public class MyServiceImpl extends RemoteServiceServlet implements com.google.gwt.forum.client.MyService {

  /**
	 * 
	 */
	private static final long serialVersionUID = 7081621504101146086L;

	/**
	 * Inserts a row into a table of the database
	 * @param table	String contaning the name of the table
	 * @param values String of Values inside () separated by comma
	 * */
	public String insert_into_db(String table, String values) {
	  
	  String str = "Result: ";
	  Connection conn = connect();	// Connect to database
	  try {
		 Statement stat = (Statement) conn.createStatement();
		  
	     PreparedStatement prep = (PreparedStatement) conn
	           .prepareStatement("insert into "+ table +" values "+ values +";");
	     prep.execute();
	     
	     ResultSet rs = stat.executeQuery("select * from "+table+";");
	     while (rs.next()) {
	        str += "\n" + rs.getString("topic_id");
	        str += ", " + rs.getString("name");
	     }
	     str += "\n Good";
	     
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	
	  disconnect(conn);
	  
    return str;
  }
	
	/**
	 * Initializes the database with some default values for the table of cities
	 * @param s String Not needed 
	 * */
	public String initialize_db(String s) {
		// Do something interesting with 's' here on the server.
	  
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
	     Statement stat = (Statement) conn.createStatement();
	     stat.executeUpdate("drop table if exists cities;");
	
	     stat.executeUpdate("create table cities (name varchar(20), invest INT);");
	
	     PreparedStatement prep = (PreparedStatement) conn
	           .prepareStatement("insert into cities values (?, ?),(?, ?),(?, ?),(?, ?),(?, ?),(?, ?),(?, ?),(?, ?),(?, ?);");
	     prep.setString(1, "NEW YORK");
	     prep.setInt(2, 5000000);
	     prep.setString(3, "WASHINGTON");
	     prep.setInt(4, 3968339);
	     prep.setString(5, "CHICAGO");
	     prep.setInt(6, 4999553);
	     prep.setString(7, "PORTLAND");
	     prep.setInt(8, 6170483);
	     prep.setString(9, "BRIDGEPORT");
	     prep.setInt(10, 4999998);
	     prep.setString(11, "WESTMINSTER");
	     prep.setInt(12, 5000000);
	     prep.setString(13, "DENVER");
	     prep.setInt(14, 4999280);
	     prep.setString(15, "AUSTIN");
	     prep.setInt(16, 3968339);
	     prep.setString(17, "SAINT PAUL");
	     prep.setInt(18, 3968339);
	     prep.execute();
	
	     ResultSet rs = stat.executeQuery("select * from cities;");
	     while (rs.next()) {
	        str +=  rs.getString("name");
	        str += ", " + rs.getString("invest");
	        str += ", ";
	     }
	     rs.close();
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	
	  disconnect(conn);
	  
    return str;
  }
	
	/**
	 * Obtains the topics from the database
	 * */
	public String get_topics(String s) {
	  
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
	     Statement stat = (Statement) conn.createStatement();
	     
	     ResultSet rs = stat.executeQuery("select topic_id, name from topics;");
	     while (rs.next()) {
	        str +=  rs.getString("topic_id");
	        str += ", " + rs.getString("name") + ", ";
	     }
	     
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	
	  disconnect(conn);
	  
    return str;
  }
	
	/**
	 * Insert a new topic into the database
	 * */
	public int insert_topic(String s) {
	
	  int auto_id = -1; //If it's null
	  String str = "Result:";
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO topics(name) values('"+ s +"');";
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	     prep.execute();
	     
	     ResultSet rs= prep.getGeneratedKeys();
	     rs.next();
	     auto_id = rs.getInt(1);
	     
	     str += " Good";
	     
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	
	  disconnect(conn);
	  
    return auto_id;
  }
	
	/**
	 * Insert a new message into the database
	 * */
	public int insert_message(String s) {
	
	  int auto_id = -1; //If it's null
	  String str = "Result:";
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO messages(content) values('"+ s +"');";
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	     prep.execute();
	     
	     ResultSet rs= prep.getGeneratedKeys();
	     rs.next();
	     auto_id = rs.getInt(1);
	     
	     str += " Good";
	     
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	
	  disconnect(conn);
	  
    return auto_id;
  }
	
	/**
	 * Insert a new message into the database
	 * */
	public int insert_thread(String s) {
	
	  int auto_id = -1; //If it's null
	  String str = "Result:";
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO threads(name) values('"+ s +"');";
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	     prep.execute();
	     
	     ResultSet rs= prep.getGeneratedKeys();
	     rs.next();
	     auto_id = rs.getInt(1);
	     
	     str += " Good";
	     
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	
	  disconnect(conn);
	  
    return auto_id;
  }
	
	/**										*
	 * Connects to local database db_lab3	*
	 * 										*/
	private Connection connect(){
		
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "db_lab3";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "invitado"; 
		String password = "invipass";
		try {
			Class.forName(driver).newInstance();
			conn = (Connection) DriverManager.getConnection(url+dbName,userName,password);
			System.out.println("Connected to the database");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**									*
	 * Disconnects from local database	*
	 * 									*/
	private void disconnect(Connection conn){
		
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
