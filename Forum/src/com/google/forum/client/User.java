package com.google.forum.client;

public class User {
	String user_name;
	String email;
	
	//TODO : password deber?a estar codificado somehow
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
	
	

}
