package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity implements OnTaskCompleted {
		 
	boolean mFirst = false;
	SharedPreferences preferences;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_splash);  
	        loadPref();
	        if (!mFirst)
	        {
	        	MainActivity.mTask = (GetXMLTask) new GetXMLTask(this).execute("http://pda.alfabank.ru/_/rss/_rss.html");
	        	mFirst = true;
	        	Editor edit = preferences.edit();
	        	edit.putBoolean("first", mFirst);
	        }
	        Thread background = new Thread() {
	            public void run() {
	                 
	                try {	                	
	                    sleep(5*1000);
	                    Intent i = new Intent(getBaseContext(), MainActivity.class);
	                    startActivity(i);
	                    finish();	                     
	                } catch (Exception e) {
	                 
	                }
	            }
	        };
	        background.start();
	    }
	     
	    
	    private void loadPref()
	    {
			preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			mFirst = preferences.getBoolean("first", false);
	    }
	    @Override
	    protected void onDestroy() {
	         
	        super.onDestroy();
	         
	    }

		@Override
		public void onTaskCompleted(String result) {
			// TODO Auto-generated method stub
			
		}
}
