package com.tr.nearfood.activity;

import com.tr.nearfood.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class Login extends Activity{

	TextView register;
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		register = (TextView) findViewById(R.id.link_to_register);
		login = (Button) findViewById(R.id.btnLogin);

		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), Register.class);
				startActivity(i);
			}
		});

		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						RestaurantCatagory.class);
				startActivity(i);
			}
		});

	}

}
