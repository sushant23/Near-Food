package com.tr.nearfood.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.tr.nearfood.R;

public class RestaurantList extends ActionBarActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		try{
			ActionBar myActionBar = getSupportActionBar();
			myActionBar.hide();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
