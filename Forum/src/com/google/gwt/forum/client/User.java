package com.google.gwt.forum.client;

import java.io.Serializable;


/**
 * Class that implements the user of the forum identified by 
 * the user_name, password, email and the privileges if 
 * is administrator.
 */
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String user_name;
	public String email;
	//TODO : password deberia estar codificado somehow
	public String password;
	public Boolean is_admin;
	
	/**
	 * Default constructor
	 */
	public User(){
		user_name = null;
		email = null;
		password = null;
		is_admin = false;
	}
	
	/**
	 * Constructor with parameters
	 * @param name
	 * @param mail
	 * @param pass
	 * @param admin
	 */
	public User(String name, String mail, String pass){
		user_name = name;
		email = mail;
		password = pass;
		is_admin = false;
	}
	
	/**
	 * Constructor with parameters
	 * @param name
	 * @param mail
	 * @param pass
	 * @param admin
	 */
	public User(String name, String mail, String pass, Boolean admin){
		user_name = name;
		email = mail;
		password = pass;
		is_admin = admin;
	}
	
	/**
	 * Inserts a new user in the database.
	 * @param name
	 * @param mail
	 * @param pass
	 * @param admin
	 */
	/*void insert_user(String name, String mail, String pass, Boolean admin){
		user_name = name;
		email = mail;
		password = pass;
		is_admin = admin;
		String ad = Boolean.toString(admin);
		
		ArrayList<String> param = new ArrayList<String>(Arrays.asList(name, mail, pass, ad));
		MyServiceAsync Service = (MyServiceAsync) GWT.create(MyService.class);

		    Service.insert_user(this, new AsyncCallback<String>(){
		    	public void onSuccess(String result) {
		        }
		        public void onFailure(Throwable caught) {
		        	Window.alert("Insert new user into the Data Base failed.");
		        }  
		    } );
	}*/
	

}
