package com.example.project;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements OnTaskCompleted{
	
	String mXml;
	XmlPullParser xpp;
	ListView mListView;
	static GetXMLTask mTask;
	static boolean onCompleteTask = false;
	ActionBar ab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getResult();
		abInit();
		final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				mSwipeRefreshLayout.setRefreshing(true);
				mTask = (GetXMLTask) new GetXMLTask(MainActivity.this).execute("http://pda.alfabank.ru/_/rss/_rss.html");	
				mSwipeRefreshLayout.setRefreshing(false);
			}
		});
		startService(new Intent(this, CheckService.class));
	}
	
	
	private void abInit()
	{
		ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
	    switch (item.getItemId()) {
	    case R.id.dev_info:
	    	Intent mIntent = new Intent(MainActivity.this, DevInfo.class);
	    	startActivity(mIntent);
	      break;
	    }
	    return true;
	}

    @Override
    public void onTaskCompleted(String result) {
        mXml = result;
        parseXML();
    }
	
	private void getResult() {
		    if (mTask == null) return;
		    try {   
		      mXml = mTask.get();
		      parseXML();
		    } catch (InterruptedException e) {
		      e.printStackTrace();
		    } catch (ExecutionException e) {
		      e.printStackTrace();
		    }
		  }
	
	private void parseXML()
	{
	    String tmp = "";
	    final ArrayList<Item> data = new ArrayList<Item>();
	   
	    try {
	    	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    factory.setNamespaceAware(true);
	    	xpp = factory.newPullParser();
		    StringReader mRead = new StringReader(mXml);
		    xpp.setInput(mRead);
		    String title = null, description = null, pubDate = null;
			Spanned link = null;
		    boolean mTitle = false, mLink = false, mDescr = false, mDate = false;
		    
	      while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
	    	   
	        switch (xpp.getEventType()) {	       
	        case XmlPullParser.START_DOCUMENT:
	          break;
	        case XmlPullParser.START_TAG:
	        	if (xpp.getName().equals("title"))
	        	{
	  	          	mTitle = true;
	        	}
	        	if (xpp.getName().equals("link"))
	        	{
	        		mLink = true;
	        	}
	        	if (xpp.getName().equals("description"))
	        	{
	        		mDescr = true;
	        	}
	        	if (xpp.getName().equals("pubDate"))
	        	{
	        		mDate = true;
	        	}
	          break;
	        case XmlPullParser.END_TAG:
	          break;
	        case XmlPullParser.TEXT:
	        	if (mTitle)
	        	{
	  	          	title = xpp.getText();
	  	          	mTitle = false;
	        	}
	        	if (mDescr)
	        	{
	  	          	description = xpp.getText();
	  	          	mDescr = false;
	        	}
	        	if (mLink)
	        	{
	  	          	link = Html.fromHtml(xpp.getText());
	  	          	mLink = false;
	        	}
	        	if (mDate)
	        	{
	  	          	pubDate = xpp.getText();
	  	          	mDate = false;
	        	}
	          break;

	        default:
	          break;
	        }
	        xpp.next();
        	if (title != null && link != null && pubDate != null && description != null)
        	{
        		data.add(new Item(title, link, description, pubDate));
        		title = null;
        		link = null; 
        		pubDate = null;
        		description = null;
        	}
	      }

	    } catch (XmlPullParserException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    mListView = (ListView) findViewById(R.id.listView1);
	    MyAdapter mAdapter = new MyAdapter(MainActivity.this, data);
	    mListView.setAdapter(mAdapter);
	    mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int item,
					long arg3) {
				String url = String.valueOf(data.get(item).getLink());
				Intent intent = new Intent(MainActivity.this, LinkActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);
				
			}
		});
	}
	
	
	
	
}
