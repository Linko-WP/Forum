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

		    MyServiceAsync emailService = (MyServiceAsync) GWT.create(MyService.class);

		    String temp = " cadena ";
		    emailService.get_topics(temp, new AsyncCallback<String>(){
		    	public void onSuccess(String result) {
		    		System.out.println("TOPICS:" + result);
		    //		results = result;
		    		
		    	/*	ArrayList<String> myList = new ArrayList<String>(Arrays.asList(results.split(", ")));
		    		for(int i=0; i<myList.size()-1; i=i+2){
		    			int obt_id = Integer.parseInt(myList.get(i));
		    			String obt_topic = myList.get(i+1);
		    		*/	
		    			
		    	//		topics.add(new Topics(obt_id, obt_topic));


				
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
	void add_thread(Thread new_thread){
		threads.add(new_thread);
	}
	
	
	
}
