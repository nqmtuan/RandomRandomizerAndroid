package com.zimerick.zmrklibrary.zmrkdatasync;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ZMRKBasicDataSyncService extends Service  
{	
	private ZMRKDataSyncManager dataSyncManager;
	
	
	
	public ZMRKDataSyncManager getDataSyncManager() {
		return dataSyncManager;
	}

	public void setDataSyncManager(ZMRKDataSyncManager dataSyncManager) {
		this.dataSyncManager = dataSyncManager;
	}


	//private final IBinder mBinder = new LocalBinder ();
	public class ZMRKDataSyncServiceBinder extends Binder {
		public ZMRKBasicDataSyncService getService () {
			return ZMRKBasicDataSyncService.this;
		}
	}
	
	private final IBinder mBinder = new ZMRKDataSyncServiceBinder();

	public IBinder onBind (Intent i)
	{
		return mBinder;
	}
	
	public void onCreate ()
	{
	}
	
	
	public ZMRKBasicDataSyncService() {
	}
	

	public void sync ()
	{
		dataSyncManager.sync();
	}
}
