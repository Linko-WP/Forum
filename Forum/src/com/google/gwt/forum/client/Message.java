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
		id = -1;
		parent_thread_id= p_id;	
		content = message;
		author = user;
		time_stamp = null;

	}
	
	

	
}
