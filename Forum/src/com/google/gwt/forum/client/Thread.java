package com.google.gwt.forum.client;


import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Thread {
	int id;
	String title;
	int no_messages;//Counter for the number of messages in one thread.
	ArrayList<Message> messages;
	
	/**
	 * Default constructor;
	 */
	Thread(){
		id = -1;
		title = null;
		no_messages = 0;
		messages = null;
	}
	
	/**
	 * Constructor with parameters;
	 */
	Thread(int i, String tit){
		id = i;
		no_messages = 0;
		messages = new ArrayList<Message>();
	}
	
	/**
	 * Constructor with parameters.
	 * @param message
	 */
	Thread(String tit){
		//TODO: comprobar que se establece la fecha correcta
		title = tit;
		messages = new ArrayList<Message>();
		
		//TODO: obtener el autor de algœn sitio
		//author = user;	

		    MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

		    Service.insert_thread(tit, new AsyncCallback<Integer>(){
		    	public void onSuccess(Integer obt_id) {
		    		System.out.println("ID AUTOGENERADO:" + obt_id);
		    		id = obt_id;

		          }

		          public void onFailure(Throwable caught) {
		        	Window.alert("Insert thread into BD failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		    } );	

	}
	
	/**
	 * To add a message to the thread
	 */
	void add_message(String new_message){
		messages.add(new Message(new_message));
		no_messages++;
	}
	
}
