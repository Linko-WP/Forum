package com.google.gwt.forum.server;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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
	    			if(myList.get(i)!=""){
	    			Integer t_id = Integer.parseInt(myList.get(i));
	    			stat.executeUpdate("delete from messages where parent_thread_id = "+t_id+";");
	    			}
	    		}		
		     stat.executeUpdate("delete from threads where parent_topic_id = "+id+";");
	     } catch (Exception e) {
	    	 str += e.toString();
	    	 e.printStackTrace();
	     } 
	  disconnect(conn);
	  
    return str;
  }
	
	
	/**
	 * Erases an user from the database
	 * @param id Id of the user to erase
	 */
	public String erase_user(String user_name) {  
	  String str = "";
	  Connection conn = connect();	// Connect to database
	  try {
	     Statement stat = (Statement) conn.createStatement();	     
	     stat.executeUpdate("delete from users where username = '"+ user_name +"';");
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
			int temp =Integer.parseInt(myList.get(i+3));
			boolean admin=false;
			if(temp==1) admin=true;
			System.out.print("IS ADMIN: "+admin);
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
			output.no_messages = count_messages(output.id);
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
	public Topics insert_topic(Topics topic) {
	
	  //int auto_id = -1; //If it's null
	  String topic_sub = topic.subject;
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO topics(name) values('"+ topic_sub +"');";
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	     prep.execute();
	     
	     ResultSet rs= prep.getGeneratedKeys();
	     rs.next();
	     topic.id = rs.getInt(1);
	  } catch (Exception e) {
		 System.out.println(e.toString());	// On server console
	     e.printStackTrace();
	  } 	
	  disconnect(conn);
	  
    return topic;
  }
	
	/**
	 * Inserts a new message into the database
	 * */
	public Message insert_message(Message msg) {
	
	  int auto_id = -1; //If it's null
	  int parent_id = msg.parent_thread_id;
	  
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO messages(parent_thread_id, content, author_username) values("
				  	+parent_id+", '"+ msg.content +"', '"+ msg.author +"');";	  
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	      prep.execute();	     
	      ResultSet rs= prep.getGeneratedKeys();
	      rs.next();
	      auto_id = rs.getInt(1);
		  msg.id = auto_id;
	      msg.time_stamp = obtain_time_stamp(auto_id);
	     
	  } catch (Exception e) {
		 System.out.println(e.toString());	// On server console
	     e.printStackTrace();
	  } 
	  
	  disconnect(conn);

	  //TODO: check que pasa con el texto rico.
	  
    return msg;
  }
	
	/**
	 * Insert a new thread into the database
	 * */
	public Thread insert_thread(Thread thread) {
	
	  int parent_id = thread.parent_topic_id;

	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO threads(parent_topic_id, name) values("+parent_id+", '"+ thread.title +"');";
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	     prep.execute();     
	     ResultSet rs= prep.getGeneratedKeys();
	     rs.next();
	     thread.id= rs.getInt(1);
	  } catch (Exception e) {
		 System.out.println(e.toString());	// On server console
	     e.printStackTrace();
	  } 
	  disconnect(conn);
	  
	  thread.no_messages=count_messages(thread.id);
    return thread;
  }
	
	
	/**
	 * Insert a new user into the database
	 * */
	public User insert_user(User usr) {
		String name = usr.user_name;
		String email = usr.email;
		String password = usr.password;
		Boolean admin = usr.is_admin;
		
	  Connection conn = connect();	// Connect to database
	  try {
		  String sql = "INSERT INTO users values ('"+ name + "', '"+email+"', '"
				  		+ password + "', "+admin+");";
		  
		  PreparedStatement prep = (PreparedStatement) conn
				  .prepareStatement(sql);
	     prep.execute();  
	  } catch (Exception e) {
	     System.out.println(e.toString());	// On server console
	     e.printStackTrace();
	  } 	
	  disconnect(conn);
	  
    return usr;
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
	public Timestamp obtain_time_stamp(Integer auto_id){
		System.out.print("\nAUTO ID: "+auto_id);
		Timestamp ts=null;
		  Connection conn = connect();	// Connect to database
		  try {
		     Statement stat = (Statement) conn.createStatement();
		     
		     ResultSet rs = stat.executeQuery("select ts from messages where message_id="+String.valueOf(auto_id)+";");
		     while (rs.next()) {
		    	 ts = rs.getTimestamp("ts");
		    	 System.out.print("\nTIMESTAMP: "+ts);
		     }
		     
		  } catch (Exception e) {
			 System.out.println(e.toString());	// On server console
		     e.printStackTrace();
		  } 
	  
	    return ts;		
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
		
	
	/**
	 * Counts all the messages that belongs to a thread
	 * @param thread_id
	 * @return number_msgs 
	 */
	public int count_messages(Integer thread_id) {
		  int number_msgs = 0;
		  Connection conn = connect();	// Connect to database
		  try {
		    Statement stat = (Statement) conn.createStatement();
		    ResultSet rs = stat.executeQuery("select count(message_id) from messages where parent_thread_id ="+thread_id+";");
		    rs.next();
		    number_msgs = rs.getInt(1);
		  } catch (Exception e) {
			 System.out.println(e.toString());	// On server console
		     e.printStackTrace();
		  } 
		  disconnect(conn);  
		  return number_msgs;
		}
	
		
	public Boolean exists_user(String username){		
		  Boolean exists = false;
		  int result=-1;
		  
		  Connection conn = connect();	// Connect to database
		  try {
		    Statement stat = (Statement) conn.createStatement();
		    ResultSet rs = stat.executeQuery("select count(username) from users where username = '"+username+"';");
		    rs.next();
		    result = rs.getInt(1);
		  } catch (Exception e) {
			 System.out.println(e.toString());	// On server console
		     e.printStackTrace();
		  } 
		  if(result == 1) exists = true;
		  disconnect(conn);  
		  	System.out.print("USUARIO EXISTE: " +exists);
		    return exists;
		  }
}

