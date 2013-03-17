package com.google.gwt.forum.client;

import java.io.Serializable;
import java.util.ArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Implements the object Topic of the forum identified by the id,
 * the subject and an array of included threads.
 */
public class Topics implements Serializable{
	int id;
	public String subject;
	ArrayList<Thread> threads;
	
	/**
	 * Default constructor.
	 */
	Topics(){
		id = -1;
		subject = null;
		threads = new ArrayList<Thread>();
	}
	
	/**
	 * Constructor with parameters.
	 * @param identifier
	 * @param sub
	 */
	public Topics(int identifier, String new_subject){
		id = identifier;
		subject = new_subject;
		threads = new ArrayList<Thread>();
		
	}
	
	
	/**
	 * Constructor that inserts the new topic into the database.
	 * @param sub
	 */
	 Topics(String sub){
 		subject = sub;
 		threads = new ArrayList<Thread>();	

		MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

	    Service.insert_topic(this, new AsyncCallback<Integer>(){
	    	public void onSuccess(Integer obt_id) {
	    		id = obt_id;
	          }
	          public void onFailure(Throwable caught) {
	        	Window.alert("Topics constructor and inserting into DB failed.");
	          }
	    } );	  
	  }

	
	/**
	 * Adds a new thread to the topic.
	 * @param new_thread
	 */
	void add_thread(String new_thread){
		threads.add(new Thread(new_thread, this.id));
	}
	
}
