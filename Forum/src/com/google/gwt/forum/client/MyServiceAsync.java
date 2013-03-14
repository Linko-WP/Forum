package com.google.gwt.forum.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

interface MyServiceAsync {
  public void initialize_db(String s, AsyncCallback<String> callback);
  public void get_topics(String s, AsyncCallback<String> callback);
  
  //Insert part
  public void insert_topic(String s, AsyncCallback<Integer> callback);
  public void insert_message(ArrayList<String> s, AsyncCallback<Integer> callback);
  public void insert_thread(ArrayList<String> s, AsyncCallback<Integer> callback);
  public void insert_user(ArrayList<String> s, AsyncCallback<String> callback);
  public void insert_into_db(String table, String values, AsyncCallback<String> callback);
  
}
