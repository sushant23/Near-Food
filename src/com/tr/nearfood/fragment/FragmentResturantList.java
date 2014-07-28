package com.tr.nearfood.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.tr.nearfood.R;
import com.tr.nearfood.adapter.CustomAdapterResturantLists;
import com.tr.nearfood.model.ResturantAddress;
import com.tr.nearfood.model.ResturantContactInfo;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.utills.ActivityLayoutAdjuster;
import com.tr.nearfood.utills.AppConstants;

public class FragmentResturantList extends Fragment implements
		OnItemClickListener {
	ListView listViewResturantList;
	View view;
	FragmentResturantListCommunicator fragmentResturantListCommunicator;
	List<ResturantDTO> dummyList = null;
	List<ResturantDTO> resturantList = null;
	public static int selected_catagory;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {

			fragmentResturantListCommunicator = (FragmentResturantListCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentResturantListCommunicator");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_resturant_list_layout,
				container, false);

		initializeUIElements();
		ActivityLayoutAdjuster.assistActivity(getActivity());

		new makeHttpGetConnection().execute();

		listViewResturantList.setOnItemClickListener(this);
		return view;
	}

	private void initializeUIElements() {
		listViewResturantList = (ListView) view
				.findViewById(R.id.listViewResturantList);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		fragmentResturantListCommunicator.setClickedData(resturantList
				.get(position));
	}

	public static interface FragmentResturantListCommunicator {
		public void setClickedData(ResturantDTO resturantDTO);

	}

	public class makeHttpGetConnection extends
			AsyncTask<String, String, String> {
		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pd == null) {
				pd = new ProgressDialog(view.getContext());
				pd.setCancelable(true);
				pd.setTitle("Please wait");
				pd.setMessage("Restaurants list is loading...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String jsonData = "";
			String url = AppConstants.RESTAURANTS_LIST + selected_catagory;
			Log.d("URl", url);
			try {
				jsonData = httpGETConnection(url);
			} catch (ConnectTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonData;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pd.isShowing()) {
				pd.dismiss();
				pd = null;
			}
			resturantList = ParseJsonData(result);

			listViewResturantList.setAdapter(new CustomAdapterResturantLists(
					getActivity(), resturantList));

		}
	}

	public static String httpGETConnection(String url)
			throws ConnectTimeoutException {
		Log.d("HTTPGET", "Makilng http connection");

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.d("HTTPCONNECTIOn", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			// Toast.makeText(this, "Network timeout reached!",
			// Toast.LENGTH_SHORT).show();
			Log.d("+++++++++++++++++ ", "Network timeout reached!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();

	}

	public List<ResturantDTO> ParseJsonData(String jsonData) {
		try {
			JSONArray restaurantlist = new JSONArray(jsonData);

			dummyList = new ArrayList<ResturantDTO>();
			if (restaurantlist != null) {
				for (int i = 0; i < restaurantlist.length(); i++) {
					JSONObject c = restaurantlist.getJSONObject(i);

					String id = c.getString("id");
					String name = c.getString("name");
					String address = c.getString("location");
					
					ResturantDTO tempResturantDTO = new ResturantDTO();
					ResturantAddress tempResturantAddress = new ResturantAddress();
					tempResturantDTO.setResturantID(Integer.parseInt(id));
					tempResturantDTO.setResturantName(name);
					tempResturantAddress.setResturantStreetAddress(address);
					tempResturantAddress.setReturantCityName(address);
					tempResturantAddress.setResturantDistance(1000);
					ResturantContactInfo tempResturantContactInfo = new ResturantContactInfo();
					tempResturantContactInfo
							.setResturantphoneNoA("+97798949389" + i);
					tempResturantDTO
							.setResturantContactInfo(tempResturantContactInfo);
					tempResturantDTO.setResturantAddress(tempResturantAddress);

					dummyList.add(tempResturantDTO);

				}

			} else {
				Toast.makeText(getActivity(), "NO DATA TO PARSE",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dummyList;
	}
}
