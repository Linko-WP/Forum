package com.google.gwt.forum.client;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Message {
	
	int id;
	int parent_thread_id;
	Timestamp time_stamp;
	String content;
	User author;
	
	
	//TODO: autor e id de la thread
	
	/**
	 * Default constructor.
	 */
	Message(){
		id = -1;
		parent_thread_id= -1;
		time_stamp = null;
		content = null;
		author = null;	
	}
	
	/**
	 * Constructor with parameters.
	 * @param message
	 * @param user
	 */
	Message(String message, User user){
		//TODO: obtener id de la base de datos
		id = -1;
		parent_thread_id= -1;
		//TODO: comprobar que se establece la fecha correcta
		time_stamp = null;
		content = message;
		author = user;	

	}
	
	/**
	 * Constructor with parameters
	 * @param new_id
	 * @param ts
	 * @param cont
	 * @param p_id
	 * @param auth
	 */
	Message(int new_id, Timestamp ts, String cont, int p_id, String auth){
		id = new_id;
		time_stamp = ts;
		content = cont;
		parent_thread_id= p_id;
		
		//TODO: hacer algo con el author author.name = auth;	

	}
	
	/**
	 * Constructor with parameters.
	 * @param message
	 */
	Message(String message, int p_id){

		parent_thread_id= p_id;
		

		// GET DATE & TIME IN ANY FORMAT
		/*TODO: ARREGLAR LA MALDITA HORA
		 import java.util.Calendar;
		import java.text.SimpleDateFormat;
		public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

		public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
		}*/
		
		String time = "";
		content = message;
		String aut = "PACO";
		
	//TODO: 	String aut = author.user_name;
		
		//TODO: obtener el autor de algœn sitio
		//author = user;	
		
			ArrayList<String> param = new ArrayList<String>(Arrays.asList(String.valueOf(parent_thread_id), time, message, aut));
		    MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

		    Service.insert_message(param, new AsyncCallback<ArrayList<Integer>>(){
		    	public void onSuccess(ArrayList<Integer> obt_id) {
		    		
		    		int id = obt_id.get(0);
		    		int time_st = obt_id.get(1);
		    		System.out.println("\nEN MESSAGE:" + id +"y "+time_st);

		          }

		          public void onFailure(Throwable caught) {
		        	Window.alert("Insert message into BD failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		    } );	

	}
	
	
	/**
	 * Class to get the threads from the dabatase
	 */
	 static ArrayList<Message> get_messages(final int parent_id){
		 
 		final ArrayList<Message> result = new ArrayList<Message>();	

		    MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

		    Service.get_messages(parent_id, new AsyncCallback<String>(){
		    	public void onSuccess(String results) {
		    		System.out.println("RESULTADO GET MESSGSS:" + results);

		    		ArrayList<String> myList = new ArrayList<String>(Arrays.asList(results.split(", ")));
		    		for(int i=0; i<myList.size()-2; i=i+3){
		    			int id_n = Integer.parseInt(myList.get(0));
		    			//Date d = Date.parse(myList.get(1));
		    		      Timestamp ts = Timestamp.valueOf(myList.get(1));

		    		        
		    			String ct = myList.get(2);
		    			String a_name = myList.get(3);
		    			Message output = new Message(i, ts, ct, parent_id, a_name);

		    			result.add(output);

		    		}

		          }
		    	
		          public void onFailure(Throwable caught) {
		        	Window.alert("RPC to initialize_db() failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		    } );
			return result;	  
	  }
	
	

}
