package com.google.gwt.forum.client;


import java.util.Date;

public class Message {
	
	int id;
	Date date;
	String content;
	User author;
	
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
	
	
	

}
