package com.google.forum.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("AwardDatas")
public interface InvestDataService extends RemoteService {
	
	InvestData[] getCities(InvestData[] cities) throws DelistedException;
}
