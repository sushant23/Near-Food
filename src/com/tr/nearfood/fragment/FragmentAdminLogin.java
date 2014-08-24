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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
	String auth = null;
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
		public void setButtonSignin(String auth);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonRestaurantManagerLogin:
			// email = adminEmail.getText().toString();
			// pass = adminPass.getText().toString();
			// new LoginHttpPostConnection().execute();
			// fragmentResturantAdminLoginCommunicator.setButtonSignin();
			email = adminEmail.getText().toString();
			pass = adminPass.getText().toString();
			new AdminLoginHttpPost().execute();
			break;

		default:
			break;
		}
	}

	public String LoginHttpPostConnection() {

		
		try {
			auth = android.util.Base64.encodeToString(
					(email + ":" + pass).getBytes("UTF-8"),
					android.util.Base64.NO_WRAP);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.d("hashkey", auth);

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(AppConstants.RESTAURANTS_LOGIN);
			get.addHeader("Authorization", "Basic " + auth);
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String result = convertStreamToString(is);
			Log.d("HttpGET Rest", result);
			return result;
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
		return null;
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

	public class AdminLoginHttpPost extends AsyncTask<String, Void, String> {
		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pd == null) {
				pd = new ProgressDialog(getActivity());
				pd.setCancelable(true);
				pd.setTitle("Please wait");
				pd.setMessage("Logging in ...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String response = LoginHttpPostConnection();
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pd.isShowing()) {
				pd.dismiss();
				pd = null;

				try {
					JSONObject login_status = new JSONObject(result);
					String sucess = login_status.getString("status");
					String message = login_status.getString("message");
					if (sucess.equals("success")) {
						Toast.makeText(getActivity(), message,
								Toast.LENGTH_SHORT).show();
						fragmentResturantAdminLoginCommunicator
								.setButtonSignin(auth);
					} else if (sucess.equals("error")) {
						Toast.makeText(getActivity(), message,
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}
}