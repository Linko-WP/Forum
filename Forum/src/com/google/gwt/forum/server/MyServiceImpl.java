package com.google.gwt.forum.server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.google.gwt.forum.client.Message;
import com.google.gwt.forum.client.Thread;
import com.google.gwt.forum.client.Topics;
import com.google.gwt.forum.client.User;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysql.jdbc.*;


public class MyServiceImpl extends RemoteServiceServlet implements com.google.gwt.forum.client.MyService {

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
	 * Erases a message from the database
	 * @param id Id of the message to erase
	 */
	public String erase_message(Integer id) {  
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
	     Statement stat = (Statement) conn.createStatement();	     
	     stat.executeUpdate("delete from messages where message_id = "+id+";");
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	  disconnect(conn);
	  
	  return str;
    }
	
	/**
	 * Erases a thread from the database and all the associated messages
	 * @param id Id of the thread to erase
	 */
	public String erase_thread(Integer id) {
	  
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
	     Statement stat = (Statement) conn.createStatement();
	     stat.executeUpdate("delete from threads where thread_id = "+id+";");
	     stat.executeUpdate("delete from messages where parent_thread_id = "+id+";");
	     
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	  disconnect(conn);  
	  return str;
	}
	

	/**
	 * Erases a topic from the database and all the associated messages and threads
	 * @param id Id of the topic to erase
	 */
	public String erase_topic(Integer id) {
	  
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
	     Statement stat = (Statement) conn.createStatement();
	     Statement consult = (Statement) conn.createStatement();
	     stat.executeUpdate("delete from topics where topic_id = "+id+";");
	     ResultSet thread_id = consult.executeQuery("select thread_id from threads"+
					" where parent_topic_id="+id+";");	     
		     while (thread_id.next()) {
		    	 str +=  thread_id.getString("thread_id");
		    	 str += ", ";  	
		     }
		   //Format the result into a User object if not null
			  ArrayList<String> myList = new ArrayList<String>(Arrays.asList(str.split(", ")));
	    		for(int i=0; i<myList.size(); i++){
	    			Integer t_id = Integer.parseInt(myList.get(i));
	    			stat.executeUpdate("delete from messages where parent_thread_id = "+t_id+";");
	    		}		
		     stat.executeUpdate("delete from threads where parent_topic_id = "+id+";");
	     } catch (Exception e) {
	    	 str += e.toString();
	    	 e.printStackTrace();
	     } 
	  disconnect(conn);
	  
    return str;
  }
	
	//TODO: borrar esta funcion, verdaD?
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
	 * Obtains all the topics from the database
	 * */
	public ArrayList<Topics> get_topics(String s) {
			
	  ArrayList<Topics> topics = new ArrayList<Topics>();
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
	  
	  //Formating of the result
	  ArrayList<String> myList = new ArrayList<String>(Arrays.asList(str.split(", ")));
		for(int i=0; i<myList.size()-1; i=i+2){
			int obt_id = Integer.parseInt(myList.get(i));
			String obt_topic = myList.get(i+1);
			
			topics.add(new Topics(obt_id, obt_topic));
		}
    return topics;
  }
	
	/**
	 * Obtains all the users from the database
	 * */
	public ArrayList<User> get_users(String s) {
	  
	  ArrayList<User> users = new ArrayList<User>();
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
	     Statement stat = (Statement) conn.createStatement();
	     
	     ResultSet rs = stat.executeQuery("select  username, email, password, is_admin from users;");
	     while (rs.next()) {
	        str +=  rs.getString("username");
	        str += ", " + rs.getString("email");
	        str += ", " + rs.getString("password");
	        str += ", " + rs.getString("is_admin") + ", ";
	     }     
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	
	  disconnect(conn);
	  //Formating of the result
	  ArrayList<String> myList = new ArrayList<String>(Arrays.asList(str.split(", ")));
		for(int i=0; i<myList.size()-3; i=i+4){
			String user = myList.get(i);
			String mail = myList.get(i+1);
			String pass = myList.get(i+2);
			Boolean admin = Boolean.parseBoolean(myList.get(i+3));
			
			users.add(new User(user, mail, pass, admin));
		}
			
    return users;
  }
	
	
	/**
	 * Obtains all the threads from a given topic
	 *@param id parent topic id 	 
	 * */
	public ArrayList<Thread> get_threads(Integer id) {
	  
	  ArrayList<Thread> result = new ArrayList<Thread>();
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
	     Statement stat = (Statement) conn.createStatement();     
	     ResultSet rs = stat.executeQuery("select thread_id, name, messages_no from threads" +
	     									" where parent_topic_id="+String.valueOf(id)+";");
	     while (rs.next()) {
	        str +=  rs.getString("thread_id");
	        str += ", " + rs.getString("name");
	        str += ", " + rs.getString("messages_no") + ", ";
	        
	     }    
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	
	  	ArrayList<String> myList = new ArrayList<String>(Arrays.asList(str.split(", ")));
		for(int i=0; i<myList.size()-2; i=i+3){		
			Thread output = new Thread(Integer.parseInt(myList.get(i)), id, myList.get(i+1), Integer.parseInt(myList.get(i+2)));
			result.add(output);
		}
	  disconnect(conn);
	  
    return result;
  }
	
	/**
	 * Obtains the messages from the database given a thread id.
	 * @param id parent thread id.
	 * */
	public ArrayList<Message> get_messages(Integer id) {
	  
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  ArrayList<Message> result = new ArrayList<Message>();
	  try {
	     Statement stat = (Statement) conn.createStatement();
	     
	     ResultSet rs = stat.executeQuery("select message_id, ts, content, author_username from messages" +
	     									" where parent_thread_id="+String.valueOf(id)+";");
	     while (rs.next()) {
	        str +=  rs.getString("message_id");
	        str += ", " + rs.getString("ts");
	        str += ", " + rs.getString("content");
	        str += ", " + rs.getString("author_username") + ", ";        
	     }
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	  disconnect(conn);
	  
	  //Formating the result
	  ArrayList<String> myList = new ArrayList<String>(Arrays.asList(str.split(", ")));
		for(int i=0; i<myList.size()-3; i=i+4){
			int id_n = Integer.parseInt(myList.get(i));
		    Timestamp ts = Timestamp.valueOf(myList.get(i+1));  
			String ct = myList.get(i+2);
			String a_name = myList.get(i+3);
			Message output = new Message(id_n, ts, ct, id, a_name);

			result.add(output);
		}
	  
    return result;
  }
	
	/**
	 * Insert a new topic into the database
	 * */
	public int insert_topic(String s) {
	
	  int auto_id = -1; //If it's null
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO topics(name) values('"+ s +"');";
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	     prep.execute();
	     
	     ResultSet rs= prep.getGeneratedKeys();
	     rs.next();
	     auto_id = rs.getInt(1);
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 	
	  disconnect(conn);
	  
    return auto_id;
  }
	
	/**
	 * Inserts a new message into the database
	 * */
	public String insert_message(ArrayList<String> s) {
	
	  int auto_id = -1; //If it's null
	  String str = "";  
	  int parent_id = Integer.parseInt(s.get(1));
	  
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO messages(parent_thread_id, content, author_username) values("
				  	+parent_id+", '"+ s.get(0) +"', '"+ s.get(2) +"');";	  
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	     prep.execute();	     
	     ResultSet rs= prep.getGeneratedKeys();
	     rs.next();
	     str += rs.getString(1);
	     
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	  
	  disconnect(conn);
	  str += obtain_time_stamp(auto_id);
	  //TODO: check que pasa con el texto rico.
	  //TODO: eliminar este system.print
	  System.out.print("INSER MESSAGE: " + str);
	  
    return str;
  }
	
	/**
	 * Insert a new thread into the database
	 * */
	public int insert_thread(ArrayList<String> s) {
	
	  int parent_id = Integer.parseInt(s.get(0));
	  int auto_id = -1; //If it's null
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO threads(parent_topic_id, name) values("+parent_id+", '"+ s.get(1) +"');";
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	     prep.execute();     
	     ResultSet rs= prep.getGeneratedKeys();
	     rs.next();
	     auto_id = rs.getInt(1);
	     
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 
	  disconnect(conn);
	  
    return auto_id;
  }
	
	
	/**
	 * Insert a new user into the database
	 * */
	public String insert_user(ArrayList<String> s) {
		String name = s.get(0);
		String email = s.get(1);
		String password = s.get(2);
		Boolean admin = Boolean.parseBoolean(s.get(3));
		String str = "";
		
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO users values ('"+ name + "', '"+email+"', '"
				  		+ password + "', "+admin+");";
		  
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql);
	     prep.execute();  
	  } catch (Exception e) {
	     str += e.toString();
	     e.printStackTrace();
	  } 	
	  disconnect(conn);
	  
    return str;
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

	
	/**
	 * Obtains the timeStamp from the database for the given message_id;
	 */
	String obtain_time_stamp(int auto_id){
	
		 
		  String str = "";
		  Connection conn = connect();	// Connect to database
		  try {
		     Statement stat = (Statement) conn.createStatement();
		     
		     ResultSet rs = stat.executeQuery("select ts from messages where parent_thread_id="+String.valueOf(auto_id)+";");
		     while (rs.next()) {
		        str += ", " + rs.getString("ts");        
		     }
		     
		  } catch (Exception e) {
		     str += e.toString();
		     e.printStackTrace();
		  } 
		
		  disconnect(conn);
		  
	    return str;		
	}

	/**
	 * Checks if the logged user has the same user data in the database
	 * @param username
	 * @param password
	 */
	public User check_user(String username, String password){
			
		String str = "";
		User checked = null;
		Connection conn = connect();	// Connect to database
			  try {
			     Statement stat = (Statement) conn.createStatement();		     
			     ResultSet rs = stat.executeQuery("select email, is_admin from users where username='"+username+
			    		 							"' and password='"+password+"';");
			     while (rs.next()) {
			        str += ", " + rs.getString("email");
			        str += ", " + rs.getString("is_admin") + ", ";     
			        //Format the result into a User object if not null
					  ArrayList<String> myList = new ArrayList<String>(Arrays.asList(str.split(", ")));
			    		for(int i=0; i<myList.size()-1; i=i+2){
			    			String mail = myList.get(i);
			    			Boolean admin = Boolean.parseBoolean(myList.get(i+1));	
							checked = new User(username, mail, password, admin);
			    		}
			     }
			   
			  } catch (Exception e) {
			     str += e.toString();
			     e.printStackTrace();
			  } 
			  disconnect(conn);
			  
		    return checked;
		  }
		
	
}

