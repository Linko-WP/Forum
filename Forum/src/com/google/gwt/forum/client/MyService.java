package com.google.gwt.forum.client;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ms")
public interface MyService extends RemoteService {
	
  public String initialize_db(String s);
  public User check_user(String username, String password);
  
  
  //Getting part
  public ArrayList<Topics> get_topics(String s);
  public String get_users(String s);
  
  
  public ArrayList<Message> get_messages(Integer s);
  public ArrayList<Thread> get_threads(Integer s);
  
  //Insert Part
  public int insert_topic(String s);
  public String insert_message(ArrayList<String> s);
  public int insert_thread(ArrayList<String> s);
  public String insert_user(ArrayList<String> s);
  public String insert_into_db(String table, String values);
  
  //Deleting Part
  public String erase_message(Integer s);
  public String erase_thread(Integer s);
  public String erase_topic(Integer s);
}
