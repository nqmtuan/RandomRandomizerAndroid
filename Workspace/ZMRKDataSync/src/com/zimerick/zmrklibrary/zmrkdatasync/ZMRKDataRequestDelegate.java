package com.zimerick.zmrklibrary.zmrkdatasync;

import org.json.JSONObject;

public interface ZMRKDataRequestDelegate {
	public void handleDataRequestCancelled (ZMRKDataRequestQueueObject requestObject);
	public void handleDataRequestSuccessful (ZMRKDataRequestQueueObject requestObject, JSONObject response);
	public void handleDataRequestFailed (ZMRKDataRequestQueueObject requestObject, String error);
}
