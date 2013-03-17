package com.google.gwt.forum.client;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Threads into the topics of the forum identified by
 * id, title, number of messages and parent topic identifier.
 * Contains an array of messages inserted into the thread.
 */
public class Thread implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int id;
	public String title;
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
	}
	
	/**
	 * Adds a message to the thread
	 */
	void add_message(String new_message, String username){
		messages.add(new Message(new_message, this.id, username));
		no_messages++;
	}
	
}
