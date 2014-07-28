package com.tr.nearfood.activity;

import java.util.List;
import java.util.StringTokenizer;

import com.tr.nearfood.R;
import com.tr.nearfood.adapter.PlaceAutoCompleteAdapter;
import com.tr.nearfood.utills.ActivityLayoutAdjuster;

import android.R.integer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
	LinearLayout emailAddress;
	String id = "", name = "";
	Boolean fromgoogel = false;
	List<Integer> confirmedMenuList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_registration);
		Bundle getConfirmedMenuList = getIntent().getExtras();
		if (getConfirmedMenuList != null) {
			confirmedMenuList = getConfirmedMenuList
					.getIntegerArrayList("confirmedMenuItems");
			Log.d("Register Activity", Integer.toString(confirmedMenuList.size()));
		}
		ActivityLayoutAdjuster.assistActivity(this);
		Bundle googlePlusData = getIntent().getExtras();
		if (googlePlusData != null) {
			fromgoogel = googlePlusData.getBoolean("googlePlus");
			id = googlePlusData.getString("id");
			name = googlePlusData.getString("name");
		}
		intitializrUIElements();

		emailAddress.setVisibility(View.VISIBLE);

		if (fromgoogel) {
			StringTokenizer tokens = new StringTokenizer(name, " ");
			String first = tokens.nextToken();
			String second = tokens.nextToken();
			String third = tokens.nextToken();
			firstName.setText(first);
			lastName.setText(second + " " + third);

		}
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteUserAddress);
		autoCompView.setAdapter(new PlaceAutoCompleteAdapter(this,
				R.layout.autocomplete_list));

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
						personalEmail.getText().toString()).matches()
						&& !TextUtils.isEmpty(personalEmail.getText()
								.toString())) {
					personalEmail.setError("Invalid Email");
					personalEmail.requestFocus();
					return;
				}
			}
		});

	}

	private void intitializrUIElements() {
		// TODO Auto-generated method stub
		firstName = (EditText) findViewById(R.id.editTextFirstName);
		lastName = (EditText) findViewById(R.id.editTextLastName);

		personalEmail = (EditText) findViewById(R.id.editTextPersonalEmail);
		contactNumber = (EditText) findViewById(R.id.editTextUserContact);

		submit = (Button) findViewById(R.id.buttonSubmitUserInformation);

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
