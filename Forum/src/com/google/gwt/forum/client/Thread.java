package com.google.gwt.forum.client;


import java.util.ArrayList;

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
	Thread(String tit){
		//TODO: recover id from DB
		no_messages = 0;
		messages = new ArrayList<Message>();
	}
	
	/**
	 * To add a message to the thread
	 */
	void add_message(Message new_message){
		messages.add(new_message);
		no_messages++;
	}
	
}
