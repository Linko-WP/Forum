package com.google.gwt.forum.client;

import java.util.ArrayList;

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
	Topics(String sub){
		//TODO: recuperar id de la BD
		subject = sub;
		threads = new ArrayList<Thread>();
	}
	
	/**
	 * Function to add a thread to the topic.
	 * @param new_thread
	 */
	void add_thread(Thread new_thread){
		threads.add(new_thread);
	}
	

}
