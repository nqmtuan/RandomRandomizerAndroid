package com.zimerick.zmrklibrary.zmrkdatasync;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ZMRKBasicDataSyncManager implements ZMRKDataSyncManager {
	//Singleton
	private static ZMRKBasicDataSyncManager sharedInstance = null;
	
	private boolean shouldAuthenticate;
	private int downloadBatchSize;
	private int uploadBatchSize;
	private String appVersion = "1.0";
	private ZMRKDataRequest dataRequest;
	private ZMRKCoreDataManager coreDataManager;
	private String lastTokenTimeStamp = null;
	private String authToken = null;
	private String userName = "";
	private String password = "";
	private Context context;
	private ZMRKDataSyncDelegate delegate = null;
	private boolean isSyncing = false;
	
	
	@SuppressWarnings("unused")
	private String serverTime = "";
	

	
	
	private static final String SERVER_RECORD_LAST_MODIFIED_TIME = "ServerRecordLastModifiedTime";
	private static final String APP_INDEX = "AppIndex";
	
	
	private static String LOG_TAG = "DataSyncService"; 
	private static final int TOKEN_RENEW_PERIOD = 1000 * 60 * 30;

	
	public static ZMRKBasicDataSyncManager getSharedInstance ()
	{
		if (sharedInstance == null)
		{
			sharedInstance = new ZMRKBasicDataSyncManager();
			ZMRKDataRequest dataRequest = ZMRKDataRequest.getSharedInstance();
			dataRequest.setDelegate(sharedInstance);
			sharedInstance.coreDataManager = ZMRKCoreDataManager.getSharedInstance();			
		}
		return sharedInstance;
	}
	
	public ZMRKDataRequest getDataRequest ()
	{
		return this.dataRequest;
	}
	
	public void setDataRequest (ZMRKDataRequest _dataRequest)
	{
		this.dataRequest = _dataRequest;
	}
	
	
	public ZMRKCoreDataManager getCoreDataManager() {
		return coreDataManager;
	}

	public void setCoreDataManager(ZMRKCoreDataManager coreDataManager) {
		this.coreDataManager = coreDataManager;
	}




	public boolean getShouldAuthenticate() {
		// TODO Auto-generated method stub
		return this.shouldAuthenticate;
	}

	
	public void setShouldAuthenticate(boolean _shouldAuthenticate) {
		this.shouldAuthenticate = _shouldAuthenticate;
	}

	
	public int getDownloadBatchSize() {
		return this.downloadBatchSize;
	}

	
	public void setDownloadBatchSize(int _downloadBatchSize) {
		if (_downloadBatchSize > 0)
			this.downloadBatchSize = _downloadBatchSize;
	}

	
	public int getUploadBatchSize() {
		return this.uploadBatchSize;
	}

	
	public void setUploadBatchSize(int _uploadBatchSize) {
		if (_uploadBatchSize > 0)
			this.uploadBatchSize = _uploadBatchSize;
	}

	
	public String getAppVersion() {
		return this.appVersion;
	}

	
	public void setAppVersion(String _appVersion) {
		this.appVersion = _appVersion;
	}
	
	
	public String getUserName() {
		return userName;
	}




	public void setUserName(String userName) {
		this.userName = userName;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	//Cannot use MAC address, since it is not available on 3g
	public String getMacAddress ()
	{
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}		
	
	
	public void setDataSyncDelegate (ZMRKDataSyncDelegate delegate) {
		this.delegate = delegate;
	}
	
	public void sync ()
	{		
		if (this.isSyncing == false) {
			this.isSyncing = true;
			if (shouldPerformAuthentication()) {
				this.getServerTime();
			}
			else {
				this.sendDataInQueue();
			}
		}
	}
	
	
	
	
	
	private void getServerTime ()
	{
		dataRequest.requestData(ZMRK_SERVER_TIME, null, ZMRKDataRequestQueue.QueuePriority.QUEUE_PRIORITY_MEDIUM, null);
	}
	
	private void getAuthToken ()
	{
		HashMap <String, Object> map = new HashMap<String, Object>();
		map.put("UDID", getMacAddress());
		map.put("UserName", userName);
		map.put("Password", password);
		map.put("AuthKey", "123");
		map.put("Version", appVersion);
		
		JSONObject data =  new JSONObject(map);
		dataRequest.requestData(ZMRK_GET_AUTH_TOKEN, data, ZMRKDataRequestQueue.QueuePriority.QUEUE_PRIORITY_MEDIUM, null);
	}
	
	
	private int getAppIndex ()
	{
		String appIndexStr = ZMRKApplicationVariables.retrieveVariable(context, APP_INDEX);
		Integer  appIndex;
		if (appIndexStr == null || appIndexStr.equals(""))
		{
			
			appIndex = 1;
		}
		else
		{
			appIndex = Integer.parseInt(appIndexStr);
			appIndex = appIndex + 1;
		}
		ZMRKApplicationVariables.storeVariable(context, APP_INDEX, appIndex.toString());
		return appIndex;
		//return 1;
	}
	
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	protected void sendDataInQueue ()
	{		
		ZMRKLog.d(LOG_TAG, "Send data in queue");		
//		int appIndexToSet = getAppIndex();
//		List <ZMRKCoreDataQueueObject> queueObjects = coreDataManager.getNextBatch(this.getUploadBatchSize(), appIndexToSet);
//		if (queueObjects == null) {
//			ZMRKLog.d(LOG_TAG, "queueObjects = null");
//			return;
//		}
//		else {
//			ZMRKLog.d(LOG_TAG, "queueObjects has " + queueObjects.size());
//		}
//		
//		JSONObject data = convertListQueueObjectToJSON (queueObjects, appIndexToSet);
//		dataRequest.requestData(ZMRK_DATA_SYNC, data, ZMRKDataRequestQueue.QueuePriority.QUEUE_PRIORITY_MEDIUM, null);

		SendDataInQueueAsyncTask task = new SendDataInQueueAsyncTask();
		task.execute ();
	}
	
	
	
	//AsyncTask
    public class SendDataInQueueAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        // Actual download method, run in the task thread
        protected JSONObject doInBackground(String... params) {        	
    		int appIndexToSet = getAppIndex();
//    		List <ZMRKCoreDataQueueObject> queueObjects = new ArrayList <ZMRKCoreDataQueueObject> ();
    		List <ZMRKCoreDataQueueObject> queueObjects = coreDataManager.getNextBatch(ZMRKBasicDataSyncManager.this.getUploadBatchSize(), appIndexToSet);
    		if (queueObjects == null) {
    			ZMRKLog.d(LOG_TAG, "queueObjects = null");
    		}
    		else {
    			ZMRKLog.d(LOG_TAG, "queueObjects has " + queueObjects.size());
    		}
    		JSONObject data = convertListQueueObjectToJSON (queueObjects, appIndexToSet);
    		return data;
        }

        @Override
        // Once the image is downloaded, associates it to the imageView
        protected void onPostExecute(JSONObject data) {
        	dataRequest.requestData(ZMRK_DATA_SYNC, data, ZMRKDataRequestQueue.QueuePriority.QUEUE_PRIORITY_MEDIUM, null);
        }
    }
	
		
	
	public void handleService (String serviceName, HashMap <String, Object> params)
	{
		//Will be overridden by other subclasses
	}
	

	
	
	public void handleZMRKWSServerTime (JSONObject response)
	{
		try {
			String result = response.getString("Result");
			this.serverTime = result;
			this.getAuthToken();
		} catch (JSONException e) {
			ZMRKLog.e(LOG_TAG, e.getMessage());
			dataRequest.cancelAllDataRequests();
			this.isSyncing = false;
		}
		
	}
	
	public void handleZMRKWSGetAuthToken (JSONObject response)
	{
		try {
			String result = response.getString("Result");
			if (result.equals("0")) {			
				String token = response.getString("Token");
				this.authToken = token;
				String currentTime = this.getCurrentTime();
				this.lastTokenTimeStamp = currentTime;
				this.sendDataInQueue();
			}
			else {
				dataRequest.cancelAllDataRequests();
				this.isSyncing = false;
			}
		} catch (JSONException e) {
			ZMRKLog.e(LOG_TAG, e.getMessage());
			dataRequest.cancelAllDataRequests();
			this.isSyncing = false;
		}			
	}
	
	public void handleZMRKWSDataSync (JSONObject response)	
	{		
//		try {
//			String result = response.getString("Result");
//			
//			if (result.equals("0") || result.equals("1")) {
//				int appIndex = response.getInt("AppDataIndex");
//				coreDataManager.removeObjectsWithAppIndex(appIndex);
//				
//				
//				
//				//Save to core data manager
//				JSONArray list = response.getJSONArray("List");
//				for (int i = 0; i < list.length(); i++)
//				{
//					JSONObject jsonObj = list.getJSONObject(i);
//					String entityName = jsonObj.getString("tableName");
//					
//					String entityIDStr = null;
//					String entityIDValueStr = null;
//					if (jsonObj.has("entityID"))
//						entityIDStr = jsonObj.getString("entityID");
//					if (jsonObj.has("entityIDValue"))
//						entityIDValueStr = jsonObj.getString("entityIDValue");
//					
//					@SuppressWarnings("unchecked")
//					HashMap <String, Object> entityAttributes = new ObjectMapper().readValue(jsonObj.toString(), HashMap.class);
//					entityAttributes.remove("tableName");
//					entityAttributes.remove("entityID");
//					entityAttributes.remove("entityIDValue");
//					
//					
//					Object entity = null;
//					if (entityIDStr != null)
//						entity = coreDataManager.createOrUpdateEntityWithNameAndAttributes(entityName, entityAttributes, entityIDStr);
//					else
//						entity = coreDataManager.createOrUpdateEntityWithNameAndAttributes(entityName, entityAttributes);
//					
//					if (entity == null)
//					{
//						dataRequest.cancelAllDataRequests();
//						ZMRKLog.e(LOG_TAG, "Cannot create entity " + entityName);
//						return;
//					}
//				}
//				
//				
//				String recordLastModifiedTime = response.getString("RecordLastModifiedTime");
//				ZMRKApplicationVariables.storeVariable(context, SERVER_RECORD_LAST_MODIFIED_TIME, recordLastModifiedTime);
//				
//				//Check if there is any new data to upload
//				int nQueueObjects = coreDataManager.getNQueueObjects();
//				if (nQueueObjects > 0)
//					this.sendDataInQueue();
//				else
//				{
//					if (result.equals("0"))
//						this.sendDataInQueue();
//				}
//			}
//			
//			else {
//				dataRequest.cancelAllDataRequests();
//				String errorCode = response.getString("ErrorCode");
//				String errorDetail = response.getString("ErrorDetail");
//				ZMRKLog.e(LOG_TAG, "Error " + errorCode + " in datasync: " +  errorDetail);
//			}
//			
//		} catch (Exception e) {
//			ZMRKLog.e(LOG_TAG, e.getMessage());
//			dataRequest.cancelAllDataRequests();
//		}
		
		ZMRKStoreInCoreDataAsyncTask task = new ZMRKStoreInCoreDataAsyncTask(response);
		task.execute();
	}
	
	
	
	//Handle ZMRKWSDataSync in AsyncTask
	//AsyncTask
    public class ZMRKStoreInCoreDataAsyncTask extends AsyncTask<String, Void, String> {
        private JSONObject response;
        private String errorMessage = null;
        private String result;
        
        public ZMRKStoreInCoreDataAsyncTask(JSONObject _response) {
            this.response = _response;
        }

        @Override
        // Actual download method, run in the task thread
        protected String doInBackground(String... params) {        	
    		try {
    			result = response.getString("Result");
    			
    			if (result.equals("0") || result.equals("1")) {
    				int appIndex = response.getInt("AppDataIndex");
    				coreDataManager.removeObjectsWithAppIndex(appIndex);
    				
    				
    				
    				//Save to core data manager
    				JSONArray list = response.getJSONArray("List");
    				for (int i = 0; i < list.length(); i++)
    				{
    					JSONObject jsonObj = list.getJSONObject(i);
    					String entityName = jsonObj.getString("tableName");
    					
    					String entityIDStr = null;
    					String entityIDValueStr = null;
    					if (jsonObj.has("entityID"))
    						entityIDStr = jsonObj.getString("entityID");
    					if (jsonObj.has("entityIDValue"))
    						entityIDValueStr = jsonObj.getString("entityIDValue");
    					
    					@SuppressWarnings("unchecked")
    					HashMap <String, Object> entityAttributes = new ObjectMapper().readValue(jsonObj.toString(), HashMap.class);
    					entityAttributes.remove("tableName");
    					entityAttributes.remove("entityID");
    					entityAttributes.remove("entityIDValue");
    					
    					List <String> listAttributeToIgnore = coreDataManager.getListExcludedAttributeForEntity(entityName);
    					for (String attributeName:listAttributeToIgnore) {
//    						ZMRKLog.d(LOG_TAG, "Ignore entity: " + attributeName);
    						entityAttributes.remove(attributeName);
    					}
    					
    					
    					Object entity = null;
    					if (entityIDStr != null)
    						entity = coreDataManager.createOrUpdateEntityWithNameAndAttributes(entityName, entityAttributes, entityIDStr);
    					else
    						entity = coreDataManager.createOrUpdateEntityWithNameAndAttributes(entityName, entityAttributes);
    					
    					if (entity == null)
    					{
    						errorMessage =  "Cannot create entity " + entityName + " field name = " + jsonObj.toString();
    						return null;
    					}
    				}
    				
    				
    				String recordLastModifiedTime = response.getString("RecordLastModifiedTime");
    				ZMRKApplicationVariables.storeVariable(context, SERVER_RECORD_LAST_MODIFIED_TIME, recordLastModifiedTime);
    				
    			}
    			
    			else {
    				String errorCode = response.getString("ErrorCode");
    				String errorDetail = response.getString("ErrorDetail");
    				errorMessage = errorCode + " : " + errorDetail;
    			}
    			
    		} catch (Exception e) {
    			errorMessage = e.getMessage();    			
    		}
    		return null;
    	}

        @Override
        // Once the image is downloaded, associates it to the imageView
        protected void onPostExecute(String string) {
        	if (errorMessage != null)
        	{
    			ZMRKLog.e(LOG_TAG, "Error in datasync: " + errorMessage);
    			dataRequest.cancelAllDataRequests();        		
    			ZMRKBasicDataSyncManager.this.isSyncing = false;
				if (delegate != null)
					delegate.syncFinish();    			
        	}
        	else
        	{
				//Check if there is any new data to upload
				int nQueueObjects = coreDataManager.getNQueueObjects();
				if (nQueueObjects > 0)
					ZMRKBasicDataSyncManager.this.sendDataInQueue();
				else if (result.equals("0")) {
						ZMRKBasicDataSyncManager.this.sendDataInQueue();
				}
				else {
					if (delegate != null)
						delegate.syncFinish();
					ZMRKBasicDataSyncManager.this.isSyncing = false;
				}
        		
        	}
        }
    }
	
	
	
	
	//Date time
	private Calendar getCurrentCalendar ()
	{
		return Calendar.getInstance();
	}
	
	private String getCurrentTime ()
	{
//		DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
//		Calendar cal = Calendar.getInstance();
//		return dateFormat.format(cal.getTime());
		
		Calendar c = Calendar.getInstance();
	    System.out.println("current: "+c.getTime());

	    TimeZone z = c.getTimeZone();
	    int offset = z.getRawOffset();
	    if(z.inDaylightTime(new Date())){
	        offset = offset + z.getDSTSavings();
	    }
	    int offsetHrs = offset / 1000 / 60 / 60;
	    int offsetMins = offset / 1000 / 60 % 60;

	    System.out.println("offset: " + offsetHrs);
	    System.out.println("offset: " + offsetMins);

	    c.add(Calendar.HOUR_OF_DAY, (-offsetHrs));
	    c.add(Calendar.MINUTE, (-offsetMins));

	    DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	    return dateFormat.format(c.getTime());
		
	}
	
	private Calendar getCalendarFromString (String dateString)
	{
		try {
			DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			Date date = dateFormat.parse(dateString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	private boolean shouldPerformAuthentication ()
	{
		if (shouldAuthenticate == false)
			return false;
		if (this.lastTokenTimeStamp == null)
			return true;
		Calendar cal1 = getCurrentCalendar();
		Calendar cal2 = getCalendarFromString(this.lastTokenTimeStamp);
		long timeDiff = Math.abs (cal1.getTimeInMillis() - cal2.getTimeInMillis());
		return (timeDiff > TOKEN_RENEW_PERIOD);
	}
	
	
	protected JSONObject convertListQueueObjectToJSON (List <ZMRKCoreDataQueueObject> queueObjects, int appIndex)
	{
//		HashMap<String, Object> data = new HashMap<String, Object>(); //Data to send
//
//		String macAddress = getMacAddress();
//		String deviceCurrentTime = getCurrentTime ();
//		
//		String deviceOS = DEVICE_OS;
//		String recordLastModifiedTime = (String) ZMRKApplicationVariables.retrieveVariable(SERVER_RECORD_LAST_MODIFIED_TIME);
//		
//		String token = (this.authToken == null)?"":this.authToken;
//		data.put ("AuthToken", token);
//		data.put("MacAddress", macAddress);
//		data.put("AppDataIndex", appIndex);
//		
//		
//		data.put("DeviceCurTime", deviceCurrentTime);
//		
//		String time = (recordLastModifiedTime == null)?"":recordLastModifiedTime;
//		data.put("ServerLastModifiedTime", time);
//		data.put("DeviceOS", deviceOS);
//		data.put("Version", appVersion);
//		
//		int index = 1;
//		for (ZMRKCoreDataQueueObject queueObject:queueObjects)
//		{
//			try {
//				String value = queueObject.getData();
//				JSONObject jsonObj = new JSONObject(value);
//				HashMap <String, Object> hash = new ObjectMapper().readValue(jsonObj.toString(), HashMap.class);
//				String key = "Data" + index;
//				//data.put(key, value);
//				data.put(key, hash);
//				index++;
//			} catch (JsonParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JsonMappingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		HashMap<String, Object> data = new HashMap<String, Object>(); //Data to send

		String macAddress = getMacAddress();
		String deviceCurrentTime = getCurrentTime ();
		
		String deviceOS = DEVICE_OS;
		String recordLastModifiedTime = (String) ZMRKApplicationVariables.retrieveVariable(context, SERVER_RECORD_LAST_MODIFIED_TIME);
		ZMRKLog.d (LOG_TAG, "Retrieve record last modified time: " + recordLastModifiedTime);
		
		String token = (this.authToken == null)?"":this.authToken;
		data.put ("AuthToken", token);
		data.put("MacAddress", macAddress);
		data.put("AppDataIndex", appIndex);
		
		
		data.put("DeviceCurTime", deviceCurrentTime);
		
//		ZMRKLog.d (LOG_TAG, "RecordLastModifiedTime: " + recordLastModifiedTime);
		String time = (recordLastModifiedTime == null)?"":recordLastModifiedTime;
//		ZMRKLog.d (LOG_TAG, "Time: " + recordLastModifiedTime);
		
		data.put("ServerLastModifiedTime", time);
		data.put("DeviceOS", deviceOS);
		data.put("Version", appVersion);
		
//		ZMRKLog.d (LOG_TAG, "Data: " + data.toString());
		
		JSONObject jsonObj = new JSONObject(data);
		
//		ZMRKLog.d (LOG_TAG, "jsonObj1: " + jsonObj.toString());
		
		int index = 1;
		for (ZMRKCoreDataQueueObject queueObject:queueObjects)
		{
			
			try {
				String value = queueObject.getData();
				JSONObject jsonObj1 = new JSONObject(value);
				String key = "Data" + index;
				jsonObj.put(key, jsonObj1);
				index++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
//		ZMRKLog.d (LOG_TAG, "jsonObj2: " + jsonObj.toString());
		return jsonObj;
	}
	
	
	
	public void handleDataRequestCancelled(ZMRKDataRequestQueueObject requestObject) {
		if (requestObject != null && requestObject.getDelegate() != null)
		{
			requestObject.getDelegate().handleDataRequestCancelled(requestObject);
		}
//		ZMRKBasicDataSyncManager.this.isSyncing = false;
//		if (delegate != null)
//			delegate.syncFinish();    					
	}

	public void handleDataRequestFailed(ZMRKDataRequestQueueObject requestObject, String error) {
		if (requestObject != null && requestObject.getDelegate() != null)
		{
			requestObject.getDelegate().handleDataRequestFailed(requestObject, error);
		}
		ZMRKBasicDataSyncManager.this.isSyncing = false;
		if (delegate != null)
			delegate.syncFinish();    			
		
	}

	public void handleDataRequestSuccessful(ZMRKDataRequestQueueObject requestObject, JSONObject response) {
		try {
			String serviceName = response.getString(ZMRK_SERVICE_NAME);
			String methodName = "handle" + serviceName;
			Method method = this.getClass().getMethod(methodName, new Class [] {JSONObject.class});
			method.invoke(this, new Object[]{response});

			if (requestObject.getDelegate() != null)
				requestObject.getDelegate().handleDataRequestSuccessful(requestObject, response);						
		}
		catch (Exception e)
		{
			e.printStackTrace();
			dataRequest.cancelAllDataRequests();
			ZMRKBasicDataSyncManager.this.isSyncing = false;
			if (delegate != null)
				delegate.syncFinish();    			
			
		}
	}
		
}
