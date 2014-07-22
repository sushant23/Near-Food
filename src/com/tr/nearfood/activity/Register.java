package com.tr.nearfood.activity;

import java.util.StringTokenizer;

import com.tr.nearfood.R;
import com.tr.nearfood.adapter.PlaceAutoCompleteAdapter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class Register extends Activity {

	EditText firstName, lastName, userName, password, officialEmail,
			personalEmail, contactNumber;
	Button submit;
	LinearLayout userLoginInfo, emailAddress;
	String id = "", name = "";
	Boolean fromgoogel = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_registration);
		Bundle googlePlusData = getIntent().getExtras();
		if (googlePlusData != null) {
			fromgoogel = googlePlusData.getBoolean("googlePlus");
			id = googlePlusData.getString("id");
			name = googlePlusData.getString("name");
		}
		intitializrUIElements();
		userLoginInfo.setVisibility(View.VISIBLE);
		emailAddress.setVisibility(View.VISIBLE);

		if (fromgoogel) {
			StringTokenizer tokens = new StringTokenizer(name, " ");
			String first = tokens.nextToken();
			String second = tokens.nextToken();
			String third = tokens.nextToken();
			firstName.setText(first);
			lastName.setText(second + " " + third);
			userLoginInfo.setVisibility(View.GONE);
			emailAddress.setVisibility(View.GONE);
		}
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteUserAddress);
		autoCompView.setAdapter(new PlaceAutoCompleteAdapter(this,
				R.layout.autocomplete_list));

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
						officialEmail.getText().toString()).matches()
						&& !TextUtils.isEmpty(officialEmail.getText()
								.toString())) {
					officialEmail.setError("Invalid Email");
					officialEmail.requestFocus();
				}
				if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
						personalEmail.getText().toString()).matches()
						&& !TextUtils.isEmpty(personalEmail.getText()
								.toString())) {
					personalEmail.setError("Invalid Email");
					personalEmail.requestFocus();
				}
			}
		});

	}

	private void intitializrUIElements() {
		// TODO Auto-generated method stub
		firstName = (EditText) findViewById(R.id.editTextFirstName);
		lastName = (EditText) findViewById(R.id.editTextLastName);
		userName = (EditText) findViewById(R.id.editTextUserName);
		password = (EditText) findViewById(R.id.editTextPassword);

		personalEmail = (EditText) findViewById(R.id.editTextPersonalEmail);
		contactNumber = (EditText) findViewById(R.id.editTextUserContact);

		submit = (Button) findViewById(R.id.buttonSubmitUserInformation);

		userLoginInfo = (LinearLayout) findViewById(R.id.linLayoutLoginInfo);
		emailAddress = (LinearLayout) findViewById(R.id.linLayoutUserEmail);
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

}
