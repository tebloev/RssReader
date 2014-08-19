package com.example.project;

import android.text.Spanned;


public class Item  {

	String title;
	Spanned link;
	String description;
	String pubDate;
	
	Item(String t, Spanned l, String d, String p){
		this.title = t;
		this.link = l;
		this.description = d;
		this.pubDate = p;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Spanned getLink() {
		return link;
	}

	public void setLink(Spanned link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}	
	
}
