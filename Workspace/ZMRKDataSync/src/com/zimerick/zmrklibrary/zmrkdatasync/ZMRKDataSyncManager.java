package com.zimerick.zmrklibrary.zmrkdatasync;

import java.util.HashMap;

import org.json.JSONObject;



public interface ZMRKDataSyncManager {
	public static String ZMRK_DATA_SYNC = "ZMRKWSDataSync";
	public static String ZMRK_SERVER_TIME = "ZMRKWSServerTime";
	public static String ZMRK_GET_AUTH_TOKEN = "ZMRKWSGetAuthToken";
	public static  String DEVICE_OS = "Android";
	public static String ZMRK_SERVICE_NAME = "ServiceName";
	
	
	public ZMRKDataRequest getDataRequest ();
	public void setDataRequest (ZMRKDataRequest dataRequest);
	public String getMacAddress ();
	public void sync ();
	public void handleService (String serviceName, HashMap <String, Object> params);
	public boolean getShouldAuthenticate ();
	public void setShouldAuthenticate (boolean _shouldAuthenticate);
	public int getDownloadBatchSize ();
	public void setDownloadBatchSize (int _downloadBatchSize);
	public int getUploadBatchSize ();
	public void setUploadBatchSize (int _uploadBatchSize);
	public String getAppVersion ();
	public void setAppVersion (String _appVersion);
	
	public void handleDataRequestCancelled (ZMRKDataRequestQueueObject requestObject);
	public void handleDataRequestFailed (ZMRKDataRequestQueueObject requestObject, String error);
	public void handleDataRequestSuccessful (ZMRKDataRequestQueueObject requestObject, JSONObject response);
}
