package com.google.gwt.forum.client;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class User {
	
	String user_name;
	String email;
	//TODO : password deberia estar codificado somehow
	String password;
	Boolean is_admin;
	
	
	User(){
		user_name = null;
		email = null;
		password = null;
		is_admin = false;
	}
	
	User(String name, String mail, String pass, Boolean admin){
		user_name = name;
		email = null;
		password = null;
		is_admin = false;
	}
	
	void insert_user(String name, String mail, String pass, Boolean admin){
		user_name = name;
		email = mail;
		password = pass;
		is_admin = admin;
		String result = "";
		String ad = Boolean.toString(admin);
		
		ArrayList<String> param = new ArrayList<String>(Arrays.asList(name, mail, pass, ad));
		
		 MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

		    Service.insert_user(param, new AsyncCallback<String>(){
		    	public void onSuccess(String result) {
		    		
		    		 result = "SUCCES CREATING NEW USER";
		    		 System.out.println(result);
		          }

		          public void onFailure(Throwable caught) {
		        	Window.alert("Insert thread into BD failed.");
		      		System.out.println("Fail\n" + caught);
		          }
		          
		    } );
	
	}
	

}
