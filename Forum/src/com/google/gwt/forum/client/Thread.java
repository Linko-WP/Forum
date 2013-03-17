package com.google.gwt.forum.client;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Threads into the topics of the forum identified by
 * id, title, number of messages and parent topic identifier.
 * Contains an array of messages inserted into the thread.
 */
public class Thread implements Serializable{
	
	public int id;
	public String title;
	//TODO: number of messages included in the thread
	public int no_messages;
	public int parent_topic_id;
	ArrayList<Message> messages;
	
	/**
	 * Default constructor;
	 */
	Thread(){
		id = -1;
		parent_topic_id = -1;
		title = null;
		no_messages = 0;
		messages = new ArrayList<Message>();
		
	}
	
	/**
	 * Constructor with parameters;
	 * @param i
	 * @param tit
	 */
	Thread(int i, String tit){
		id = i;
		parent_topic_id = -1;
		title = tit;
		no_messages = 0;
		messages = new ArrayList<Message>();
		
	}
	
	/**
	 * Constructor with parameters;
	 * @param idd
	 * @param p_id
	 * @param tit
	 * @param num
	 */
	public Thread(int idd, int p_id,  String tit, int num){
		id = idd;
		parent_topic_id = p_id;
		title = tit;
		no_messages = num;
		messages = new ArrayList<Message>();
		
	}
	
	/**
	 * Constructor with parameters and inserting into the DB;
	 * @param tit
	 * @param parent_id
	 */
	Thread(String tit, int parent_id){
		title = tit;
		messages = new ArrayList<Message>();
		parent_topic_id = parent_id;
		//TODO: obtener el autor de algun sitio
		//author = user;	
		
		ArrayList<String> param = new ArrayList<String>(Arrays.asList(String.valueOf(parent_topic_id), tit));
		MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

	    Service.insert_thread(this, new AsyncCallback<Integer>(){
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
	 * Adds a message to the thread
	 */
	void add_message(String new_message, String username){
		messages.add(new Message(new_message, this.id, username));
		no_messages++;
	}
	
}
