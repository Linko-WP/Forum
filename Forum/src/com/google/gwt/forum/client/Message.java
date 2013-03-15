package com.google.gwt.forum.client;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Message {
	
	int id;
	int parent_thread_id;
	Date date;
	String content;
	User author;
	
	
	//TODO: autor e id de la thread
	
	/**
	 * Default constructor.
	 */
	Message(){
		id = -1;
		parent_thread_id= -1;
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
		id = -1;
		parent_thread_id= -1;
		//TODO: comprobar que se establece la fecha correcta
		date = new Date();
		content = message;
		author = user;	

	}
	
	/**
	 * Constructor with parameters.
	 * @param message
	 */
	Message(String message, int p_id){

		parent_thread_id= p_id;
		date = new Date();

		// GET DATE & TIME IN ANY FORMAT
		/*TODO: ARREGLAR LA MALDITA HORA
		 import java.util.Calendar;
		import java.text.SimpleDateFormat;
		public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

		public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
		}*/
		
		String time = "";
		content = message;
		String aut = "PACO";
		
	//TODO: 	String aut = author.user_name;
		
		//TODO: obtener el autor de algœn sitio
		//author = user;	
		
			ArrayList<String> param = new ArrayList<String>(Arrays.asList(String.valueOf(parent_thread_id), time, message, aut));
		    MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

		    Service.insert_message(param, new AsyncCallback<ArrayList<Integer>>(){
		    	public void onSuccess(ArrayList<Integer> obt_id) {
		    		
		    		int id = obt_id.get(0);
		    		int time_st = obt_id.get(1);
		    		System.out.println("\nEN MESSAGE:" + id +"y "+time_st);

		          }

		          public void onFailure(Throwable caught) {
		        	Window.alert("Insert message into BD failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		    } );	

	}
	
	
	

}
