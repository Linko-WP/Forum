package com.google.gwt.forum.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

interface MyServiceAsync {
  public void initialize_db(String s, AsyncCallback<String> callback);
  public void get_topics(String s, AsyncCallback<String> callback);
  public void insert_into_db(String table, String values, AsyncCallback<String> callback);
}
