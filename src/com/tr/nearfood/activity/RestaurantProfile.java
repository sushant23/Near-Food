package com.tr.nearfood.activity;

import com.tr.nearfood.R;


import com.tr.nearfood.utills.NearFoodTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RestaurantProfile extends Activity {

	EditText etName, etEmail, etPhone, etMessage;
	Button send,chooseMenu;
	String name, email, phone, message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_resturant_profile);
		NearFoodTextView.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");

		chooseMenu=(Button) findViewById(R.id.buttonChooseMenu);
		chooseMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				name = etName.getText().toString();
				email = etEmail.getText().toString();
				phone = etPhone.getText().toString();
				message = etMessage.getText().toString();

				sendMail();

			}
		});

	}

	public void sendMail() {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		String sendMailTo = "rakushah123@gmail.com";
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { sendMailTo });
		// email.putExtra(Intent.EXTRA_CC, new String[]{ to});
		// email.putExtra(Intent.EXTRA_BCC, new String[]{to});
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, name);
		emailIntent.putExtra(Intent.EXTRA_TEXT, message + "\nName:" + name
				+ "\nEmail:" + email + "\nPhone:" + phone);

		// need this to prompts email client only
		emailIntent.setType("message/rfc822");

		startActivity(Intent.createChooser(emailIntent,
				"Choose an Email client :"));
		// startActivity(emailIntent);
	}

}
