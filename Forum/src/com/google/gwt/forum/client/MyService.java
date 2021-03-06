package com.google.gwt.forum.client;


import java.sql.Timestamp;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ms")
public interface MyService extends RemoteService {
	
  public User check_user(String username, String password);
  public int count_messages(Integer thread_id);
  public Timestamp obtain_time_stamp(Integer auto_id);
  public Boolean exists_user(String username);
  
  //Getting from the database
  public ArrayList<Topics> get_topics(String s);
  public ArrayList<User> get_users(String s);
  public ArrayList<Message> get_messages(Integer s);
  public ArrayList<Thread> get_threads(Integer s);
  
  //Inserting into the database
  public Topics insert_topic(Topics topic);
  public Message insert_message(Message msj);
  public Thread insert_thread(Thread thread);
  public User insert_user(User usr);
  public String insert_into_db(String table, String values);
  
  //Deleting from the database
  public String erase_message(Integer s);
  public String erase_thread(Integer s);
  public String erase_topic(Integer s);
  public String erase_user(String user_name);
}
