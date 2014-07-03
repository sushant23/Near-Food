package com.tr.nearfood.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.tr.nearfood.R;
import com.tr.nearfood.adapter.NearFoodTextView;

public class RestaurantCatagory extends Activity {

	ImageButton takeAway, table, delivery;
	Button suscribe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catagory);
		NearFoodTextView.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");

		takeAway = (ImageButton) findViewById(R.id.ibTakeAway);
		table = (ImageButton) findViewById(R.id.ibTable);
		delivery = (ImageButton) findViewById(R.id.ibDelivery);
		suscribe=(Button) findViewById(R.id.buttonSuscribe);
		takeAway.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent start = new Intent(getApplicationContext(),RestaurantList.class);
				startActivity(start);
			}
		});
		table.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent start = new Intent(getApplicationContext(),RestaurantList.class);
				startActivity(start);
			}
		});
		delivery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				Intent start = new Intent(getApplicationContext(),RestaurantList.class);
				startActivity(start);
			}
		});
		suscribe.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent start = new Intent(getApplicationContext(),Login.class);
				startActivity(start);
			}
		});
	}

}
