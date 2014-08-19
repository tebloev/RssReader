package com.example.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;

public class GetXMLTask extends AsyncTask<String, String, String>
{
    private Activity activity;
    private Runnable run;
    private OnTaskCompleted callback;
	
    public GetXMLTask (Activity act){
        this.activity = act;
        this.callback = (OnTaskCompleted)act;
    }
	
	public GetXMLTask(Runnable runnable) {
		this.run = runnable;
		this.callback = (OnTaskCompleted)run;
	}

	@Override
	protected String doInBackground(String... urls) {
		String result = null;	
		try {		
				URL url = new URL(urls[0]);
				HttpGet httpGet = new HttpGet(url.toString());
				DefaultHttpClient client = new DefaultHttpClient();					
				HttpResponse response = client.execute(httpGet);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"WINDOWS-1251"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				  while ((line = reader.readLine()) != null) {			   
					   sb.append(line + System.getProperty("line.separator"));
				   }  
				result = sb.toString();	
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}        
        			
		return result;
	}
	
	@Override 
	protected void onPreExecute()
	{
	}
	
	@Override
	protected void onPostExecute(String result) {
		callback.onTaskCompleted(result);
		super.onPostExecute(result);
		
	}
}
