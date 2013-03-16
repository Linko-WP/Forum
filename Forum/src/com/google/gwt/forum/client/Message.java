package com.google.gwt.forum.client;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Message implements Serializable{
	
	int id;
	int parent_thread_id;
	Timestamp time_stamp;
	String content;
	String author;
	
	
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
	Message(String message, String user){
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
	public Message(int new_id, Timestamp ts, String cont, int p_id, String auth){
		id = new_id;
		time_stamp = ts;
		content = cont;
		parent_thread_id= p_id;
		author = auth;

	}
	
	/**
	 * Constructor with parameters.
	 * @param message
	 */
	Message(String message, int p_id, String user){

		parent_thread_id= p_id;	
		String time = "";
		content = message;
		String aut = user;
		
	// 	String aut = author.user_name;
		
		//TODO: obtener el autor de algun sitio
		//author = user;	
		
			ArrayList<String> param = new ArrayList<String>(Arrays.asList( message, String.valueOf(parent_thread_id),aut));
		    MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

		    Service.insert_message(param, new AsyncCallback<String>(){
		    	public void onSuccess(String results) {
		    		
		    		ArrayList<String> myList = new ArrayList<String>(Arrays.asList(results.split(", ")));
		    		for(int i=0; i<myList.size()-2; i=i+3){

			    		int id = Integer.parseInt(myList.get(0));
			    		Timestamp ts = Timestamp.valueOf(myList.get(1));
			    		System.out.println("\nEN MESSAGE:" + id +"y "+ ts);
		    		}
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

		    Service.get_messages(parent_id,  new AsyncCallback<ArrayList<Message>>(){
		    	public void onSuccess(ArrayList<Message> results) {


		    		}
	    	
		          public void onFailure(Throwable caught) {
		        	Window.alert("RPC to initialize_db() failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		    } );
			return result;	  
	  }
	
	

}
