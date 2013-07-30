package com.zimerick.zmrklibrary.zmrkdatasync;

import java.util.HashMap;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class ZMRKApplicationVariables{
	public static final String LOG_TAG = "ZRMKApplicationVariable";
	
	public  static String retrieveVariable(Context context,String key){
		try {
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			String buildIdentifier= pinfo.packageName;
			 SharedPreferences sharedPreferences = context.getSharedPreferences(buildIdentifier,Context.MODE_PRIVATE);
			 
		        String strSavedMem1 = sharedPreferences.getString(key, "");
		        return strSavedMem1;
		}catch (NameNotFoundException e) {
			ZMRKLog.d(LOG_TAG, "Retrieve variable Name Not Found Exception");
			 return null;
		}
	}
	
	
	public  static void storeVariable(Context context,String key,String value){
		try {
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			String buildIdentifier= pinfo.packageName;
			SharedPreferences sharedPreferences = context.getSharedPreferences(buildIdentifier,Context.MODE_PRIVATE);
	        SharedPreferences.Editor editor = sharedPreferences.edit();
	        editor.putString(key, value);
	        editor.commit();
		}catch (NameNotFoundException e) {
			ZMRKLog.d(LOG_TAG, "Store variable Name Not Found Exception");
		}
	} 

}