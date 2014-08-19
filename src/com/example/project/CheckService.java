package com.example.project;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class CheckService extends Service {
  

  public void onCreate() {
    super.onCreate();
 }
  
  public int onStartCommand(Intent intent, int flags, int startId) {
	  new Handler().postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	MainActivity.mTask = (GetXMLTask) new GetXMLTask(this).execute("http://pda.alfabank.ru/_/rss/_rss.html");	
	        }
	    }, 5*60*1000);
    return super.onStartCommand(intent, flags, startId);
  }

  public void onDestroy() {
    super.onDestroy();
  }

  public IBinder onBind(Intent intent) {
    return null;
  }  

}