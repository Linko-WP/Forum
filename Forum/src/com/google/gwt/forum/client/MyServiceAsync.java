package com.google.gwt.forum.client;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

interface MyServiceAsync {
	
  public void initialize_db(String s, AsyncCallback<String> callback);
  public void check_user(String username, String password, AsyncCallback<User> callback);
  public void count_messages(Integer id, AsyncCallback<Integer> callback);
  public void  obtain_time_stamp(Integer auto_id, AsyncCallback<Timestamp> callback);
  public void  exists_user(String username, AsyncCallback<Boolean> callback);
  
  //Getting from the database
  public void get_topics(String s, AsyncCallback<ArrayList<Topics>> callback);
  public void get_users(String s, AsyncCallback<ArrayList<User>> callback);
  public void get_threads(Integer s, AsyncCallback<ArrayList<Thread>> callback);
  public void get_messages(Integer s, AsyncCallback<ArrayList<Message>> callback);
  
  //Inserting into the database
  public void insert_topic(Topics topic, AsyncCallback<Topics> callback);
  public void insert_message(Message msj, AsyncCallback<Message> callback);
  public void insert_thread(Thread thread, AsyncCallback<Thread> callback);
  public void insert_user(User usr, AsyncCallback<User> callback);
  public void insert_into_db(String table, String values, AsyncCallback<String> callback);
  
  //Deleting from the database
  public void erase_message(Integer s, AsyncCallback<String> callback);
  public void erase_thread(Integer s, AsyncCallback<String> callback);
  public void erase_topic(Integer s, AsyncCallback<String> callback);
  public void erase_user(String user_name, AsyncCallback<String > callback);
}
