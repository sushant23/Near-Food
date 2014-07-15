package com.tr.nearfood.activity;

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
	Button addContact,submit;
	LinearLayout contactContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_registration);
		intitializrUIElements();
		AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteUserAddress);
	    autoCompView.setAdapter(new PlaceAutoCompleteAdapter(this, R.layout.autocomplete_list));
		addContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View addView = layoutInflater.inflate(
						R.layout.user_contact, null);
				TextView textOut = (TextView) addView
						.findViewById(R.id.textViewUserContactNumber);
				textOut.setText(contactNumber.getText().toString());
				Button buttonRemove = (Button) addView
						.findViewById(R.id.buttonRemoveContactNumber);
				buttonRemove.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						((LinearLayout) addView.getParent())
								.removeView(addView);
					}
				});

				contactContainer.addView(addView);
			}
		});
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!android.util.Patterns.EMAIL_ADDRESS.matcher(officialEmail.getText().toString()).matches() && !TextUtils.isEmpty(officialEmail.getText().toString())) {
				    officialEmail.setError("Invalid Email");
				    officialEmail.requestFocus();
				}
				if (!android.util.Patterns.EMAIL_ADDRESS.matcher(personalEmail.getText().toString()).matches() && !TextUtils.isEmpty(personalEmail.getText().toString())) {
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
		officialEmail = (EditText) findViewById(R.id.editTextOfficialEmail);
		personalEmail = (EditText) findViewById(R.id.editTextPersonalEmail);
		contactNumber = (EditText) findViewById(R.id.editTextUserContact);

		addContact = (Button) findViewById(R.id.buttonAddContact);
		submit=(Button) findViewById(R.id.buttonSubmitUserInformation);

		contactContainer = (LinearLayout) findViewById(R.id.linLayoutContactContainer);
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
