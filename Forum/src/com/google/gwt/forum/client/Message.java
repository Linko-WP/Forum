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

/**
 * Messages into the threads in the forum identified by
 * id, parent thread id, time stamp, content and author.
 */
public class Message implements Serializable{
	
	public int id;
	public int parent_thread_id;
	public Timestamp time_stamp;
	public String content;
	public String author;
	
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
		id = -1;
		parent_thread_id= -1;
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
	 * Constructor with parameters and insertinf into the DB.
	 * @param message
	 * @param p_id
	 * @param user
	 */
	Message(String message, int p_id, String user){
		parent_thread_id= p_id;	
		content = message;
		author = user;

		ArrayList<String> param = new ArrayList<String>(Arrays.asList( message, String.valueOf(parent_thread_id),user));
	    MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

	    Service.insert_message(this, new AsyncCallback<String>(){
	    	public void onSuccess(String results) {	
	    		ArrayList<String> myList = new ArrayList<String>(Arrays.asList(results.split(", ")));
	    		for(int i=0; i<myList.size()-2; i=i+3){
		    		id = Integer.parseInt(myList.get(0));
		    		time_stamp = Timestamp.valueOf(myList.get(1));
	    		}
	          }
	          public void onFailure(Throwable caught) {
	        	Window.alert("Constructor and inserting message into BD failed.");
	      		System.out.println("Fail\n" + caught);
	          }
	    } );	

	}
	
	
	/**
	 * Gets the messages from the database given the parent id
	 * @param parent_id
	 */
	 static ArrayList<Message> get_messages(final int parent_id){

 		final ArrayList<Message> result = new ArrayList<Message>();	
	    MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

	    Service.get_messages(parent_id,  new AsyncCallback<ArrayList<Message>>(){
	    	public void onSuccess(ArrayList<Message> results) {}
	        public void onFailure(Throwable caught) {
	        	Window.alert("Getting messages from the Data Base failed.");
	        }
	    } );
			return result;	  
	  }
	
}
