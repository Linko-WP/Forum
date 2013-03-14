package com.google.gwt.forum.client;


import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Message {
	
	int id;
	Date date;
	String content;
	User author;
	
	//TODO: autor e id de la thread
	
	/**
	 * Default constructor.
	 */
	Message(){
		id = -1;
		date = null;
		content = null;
		author = null;	
	}
	
	/**
	 * Constructor with parameters.
	 * @param message
	 * @param user
	 */
	Message(String message, User user){
		//TODO: obtener id de la base de datos
		//id = -1;
		//TODO: comprobar que se establece la fecha correcta
		date = new Date();
		content = message;
		author = user;	
		
		/*
		 Para recuperar el autoincrement de la BD 
		  String sql = "INSERT INTO table (column1, column2) values(?, ?)";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		 */
	}
	
	/**
	 * Constructor with parameters.
	 * @param message
	 */
	Message(String message){
		//TODO: comprobar que se establece la fecha correcta
		date = new Date();
		content = message;
		//TODO: obtener el autor de algœn sitio
		//author = user;	

		    MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

		    Service.insert_message(message, new AsyncCallback<Integer>(){
		    	public void onSuccess(Integer obt_id) {
		    		System.out.println("ID AUTOGENERADO:" + obt_id);
		    		id = obt_id;

		          }

		          public void onFailure(Throwable caught) {
		        	Window.alert("Insert message into BD failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		    } );	

	}
	
	
	

}
