package com.zimerick.zmrklibrary.zmrkdatasync;

import java.util.HashMap;

import org.json.JSONObject;

public abstract class ZMRKDataSyncManagerDecorator implements ZMRKDataSyncManager{
	protected ZMRKDataSyncManager dataSyncManager;
	
	public ZMRKDataSyncManagerDecorator (ZMRKDataSyncManager _dataSyncManager)
	{
		this.dataSyncManager = _dataSyncManager;
	}
	
	
	public ZMRKDataRequest getDataRequest () {
		return dataSyncManager.getDataRequest();
	}
	

	public void setDataRequest (ZMRKDataRequest dataRequest) {
		dataSyncManager.setDataRequest(dataRequest);
	}
	
	public String getMacAddress() {
		return dataSyncManager.getMacAddress();
	}

	public void sync() {
		dataSyncManager.sync();
	}

	public void handleService(String serviceName, HashMap<String, Object> params) {
		dataSyncManager.handleService(serviceName, params);
	}

	public boolean getShouldAuthenticate() {
		return dataSyncManager.getShouldAuthenticate();
	}

	public void setShouldAuthenticate(boolean _shouldAuthenticate) {
		dataSyncManager.setShouldAuthenticate(_shouldAuthenticate);
	}

	public int getDownloadBatchSize() {
		return dataSyncManager.getDownloadBatchSize();
	}

	public void setDownloadBatchSize(int _downloadBatchSize) {
		dataSyncManager.setDownloadBatchSize(_downloadBatchSize);
	}

	public int getUploadBatchSize() {
		return dataSyncManager.getUploadBatchSize();
	}

	public void setUploadBatchSize(int _uploadBatchSize) {
		dataSyncManager.setUploadBatchSize(_uploadBatchSize);
	}

	public String getAppVersion() {
		return dataSyncManager.getAppVersion();
	}

	public void setAppVersion(String _appVersion) {
		dataSyncManager.setAppVersion(_appVersion);
	}

	public void handleDataRequestCancelled(
			ZMRKDataRequestQueueObject requestObject) {
		dataSyncManager.handleDataRequestCancelled(requestObject);
	}

	public void handleDataRequestFailed(
			ZMRKDataRequestQueueObject requestObject, String error) {
		dataSyncManager.handleDataRequestFailed(requestObject, error);
	}

	public void handleDataRequestSuccessful(
			ZMRKDataRequestQueueObject requestObject, JSONObject response) {
		dataSyncManager.handleDataRequestSuccessful(requestObject, response);
	}

}
