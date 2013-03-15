package com.google.gwt.forum.client;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ms")
public interface MyService extends RemoteService {
  public String initialize_db(String s);
  public String get_topics(String s);
  
  //Insert Part
  public int insert_topic(String s);
  public ArrayList<Integer> insert_message(ArrayList<String> s);
  public int insert_thread(ArrayList<String> s);
  public String insert_user(ArrayList<String> s);
  public String insert_into_db(String table, String values);
}
