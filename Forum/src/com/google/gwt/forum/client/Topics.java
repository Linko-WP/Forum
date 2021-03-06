package com.google.gwt.forum.client;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Implements the object Topic of the forum identified by the id,
 * the subject and an array of included threads.
 */
public class Topics implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int id;
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
 		 
	  }

	
	/**
	 * Adds a new thread to the topic.
	 * @param new_thread
	 */
	void add_thread(String new_thread){
		threads.add(new Thread(new_thread, this.id));
	}
	
}
