package com.zimerick.zmrklibrary.zmrkdatasync;

import org.json.JSONObject;

public class ZMRKDataRequestQueueObject {
	private String serviceName;
	private JSONObject data;
	private ZMRKDataRequestDelegate delegate = null;
	
	public ZMRKDataRequestQueueObject() {
		// TODO Auto-generated constructor stub
	}

	public String getServiceName ()
	{
		return this.serviceName;
	}
	
	public void setServiceName (String _serviceName)
	{
		this.serviceName = _serviceName;
	}
	
	public JSONObject getData ()
	{
		return this.data;
	}
	
	public void setData (JSONObject _data)
	{
		this.data = _data;
	}

	public ZMRKDataRequestDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(ZMRKDataRequestDelegate delegate) {
		this.delegate = delegate;
	}
	
}
