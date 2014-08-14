package com.tr.nearfood.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tr.nearfood.R;
import com.tr.nearfood.utills.AppConstants;

public class FragmentAdminLogin extends Fragment implements OnClickListener {
	View view;

	FragmentResturantAdminLoginCommunicator fragmentResturantAdminLoginCommunicator;
	Button signin;
	EditText adminEmail, adminPass;
	String email, pass;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {

			fragmentResturantAdminLoginCommunicator = (FragmentResturantAdminLoginCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentResturantAdminLOginCommunicator");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_restaurant_admin_login,
				container, false);
		initializeUIElements();
		signin.setOnClickListener(this);
		return view;
	}

	private void initializeUIElements() {
		// TODO Auto-generated method stub
		signin = (Button) view.findViewById(R.id.buttonRestaurantManagerLogin);
		adminEmail = (EditText) view
				.findViewById(R.id.editTextRestaurantManagerUserName);
		adminPass = (EditText) view
				.findViewById(R.id.editTextRestaurantManagerPassword);
	}

	public static interface FragmentResturantAdminLoginCommunicator {
		public void setButtonSignin();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonRestaurantManagerLogin:
			email = adminEmail.getText().toString();
			pass = adminPass.getText().toString();
			//HttpPostConnection();
			//fragmentResturantAdminLoginCommunicator.setButtonSignin();
			break;

		default:
			break;
		}
	}

	public void HttpPostConnection() {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(AppConstants.RESTAURANTS_LOGIN);
		List<NameValuePair> registrationData = new ArrayList<NameValuePair>();
		registrationData.add(new BasicNameValuePair("email", email));
		registrationData.add(new BasicNameValuePair("password", pass));

		try {
			post.setEntity(new UrlEncodedFormEntity(registrationData));
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String result = convertStreamToString(is);
			Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
			Log.d("HttpPOst Rest", result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}