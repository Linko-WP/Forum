package com.google.forum.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InvestDataServiceAsync {

  void getCities(InvestData[] cities, AsyncCallback<InvestData[]> callback);

}