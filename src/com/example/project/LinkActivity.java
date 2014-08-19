package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LinkActivity extends ActionBarActivity {
	ActionBar ab;
	WebView mWeb;
	String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		url = getIntent().getExtras().getString("url");
		mWeb = (WebView)findViewById(R.id.webView);
		
		mWeb.setWebViewClient(new MyWebViewClient());
		mWeb.loadUrl(url);
		abInit();
	}
	
	private class MyWebViewClient extends WebViewClient 
	{
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) 
	    {
	        view.loadUrl(url);
	        parseUrl(url);
	        return true;
	    }
	}
	
	private void abInit()
	{
	    ActionBar ab;
	    ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.link, menu);
		return true;
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.vk:
	    	mWeb.loadUrl("http://vkontakte.ru/share.php?url=" + url);
	      break;
	    case R.id.fb:
	    	mWeb.loadUrl("http://www.facebook.com/sharer.php?u=" + url + "&t=TestShare&src=sp");
	      break;
	    case R.id.twit:
	    	mWeb.loadUrl("http://twitter.com/share?url=" + url);
	      break;

	    }

	    return true;
	  } 
	
	private void parseUrl(String mUrl) {
	    try {
	        if(mUrl==null)
	            return;
	        if (mUrl.toString().contains("complete") || mUrl.toString().contains("success"))
	        {
	        	mWeb.loadUrl(url);  
	        	Toast.makeText(this, "Вы успешно поделились записью!", Toast.LENGTH_LONG).show();        	
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	

}
