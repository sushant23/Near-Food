package com.tr.nearfood.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.tr.nearfood.R;
import com.tr.nearfood.utills.NearFoodTextView;

public class RestaurantCatagory extends Activity {

	ImageButton takeAway, table, delivery, suscribeIB;
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
		suscribeIB = (ImageButton) findViewById(R.id.buttonSuscribe);

	
		suscribeIB.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					suscribeIB.setColorFilter(Color.argb(150, 155, 155, 155));
					Intent start = new Intent(getApplicationContext(),
							RestaurantSubscribtion.class);
					startActivity(start);
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					suscribeIB.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																			// null
					return true;
				}
				return false;
			}

		});
		takeAway.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					takeAway.setColorFilter(Color.argb(150, 155, 155, 155));
					Intent start = new Intent(getApplicationContext(),
							RestaurantList.class);
					startActivity(start);
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					takeAway.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																			// null
					return true;
				}
				return false;
			}

		});
		table.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					table.setColorFilter(Color.argb(150, 155, 155, 155));
					Intent start = new Intent(getApplicationContext(),
							RestaurantList.class);
					startActivity(start);
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					table.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																		// null
					return true;
				}
				return false;
			}

		});
		delivery.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					delivery.setColorFilter(Color.argb(150, 155, 155, 155));
					Intent start = new Intent(getApplicationContext(),
							RestaurantList.class);
					startActivity(start);
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					delivery.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																			// null
					return true;
				}
				return false;
			}

		});
	}
}
