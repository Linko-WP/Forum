package com.google.gwt.forum.client;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Topics {
	int id;
	String subject;
	ArrayList<Thread> threads;
	
	/**
	 * Default constructor.
	 */
	Topics(){
		id = -1;
		subject = null;
		threads = null;
	}
	
	/**
	 * Constructor with parameters.
	 * @param sub
	 */
	Topics(int i, String sub){
		id = i;
		subject = sub;
		threads = new ArrayList<Thread>();
		
	}
	
	
	/**
	 * Class to load the topics int the topic object from the database
	 */
	 Topics(String sub){
 		subject = sub;
 		threads = new ArrayList<Thread>();	

		    MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

		    Service.insert_topic(sub, new AsyncCallback<Integer>(){
		    	public void onSuccess(Integer obt_id) {
		    		System.out.println("ID AUTOGENERADO:" + obt_id);
		    		id = obt_id;

		          }

		          public void onFailure(Throwable caught) {
		        	Window.alert("RPC to initialize_db() failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		    } );	  
	  }

	
	/**
	 * Function to add a thread to the topic.
	 * @param new_thread
	 */
	void add_thread(String new_thread){
		threads.add(new Thread(new_thread));
		
	}
	
	
	
}
