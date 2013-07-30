package com.nqmtuan.android.randomrandomizer.Logging;

import android.util.Log;

public class MyLog {
	public static boolean shouldLog = true;
	public static void e (String tag, String msg) {
		if (shouldLog) {
			Log.e(tag, msg);
		}
	}
	
	public static void i (String tag, String msg) {
		if (shouldLog) {
			Log.i(tag, msg);
		}
	}
	
	public static void d (String tag, String msg) {
		if (shouldLog) {
			Log.d(tag, msg);
		}
	}
	

	public static void w (String tag, String msg) {
		if (shouldLog) {
			Log.w(tag, msg);
		}
	}


	public static void v (String tag, String msg) {
		if (shouldLog) {
			Log.v(tag, msg);
		}
	}
	
}
