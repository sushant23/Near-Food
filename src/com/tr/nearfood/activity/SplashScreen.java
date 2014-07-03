package com.tr.nearfood.activity;



import com.tr.nearfood.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



public class SplashScreen extends Activity{

	@Override
	protected void onCreate(Bundle rajkumar) {
		// TODO Auto-generated method stub
		super.onCreate(rajkumar);
		setContentView(R.layout.splash);
		
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);
			
				}catch (InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingPoint = new Intent(getApplicationContext(),RestaurantCatagory.class);
					startActivity(openStartingPoint);
					finish();
				}
					
				}
			
		};
		timer.start();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
}
