package com.tr.nearfood.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tr.nearfood.R;

import com.tr.nearfood.adapter.CustomAdapterResturantLists;


import com.tr.nearfood.model.ResturantAddress;
import com.tr.nearfood.model.ResturantContactInfo;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.utills.ActivityLayoutAdjuster;
import com.tr.nearfood.utills.AppConstants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentNearByRestaurantList extends Fragment implements
		OnItemClickListener {

	public static double latitude;
	public static double longitude;
	ListView listViewResturantList;
	View view;
	EditText restaurantFilter;
	ImageButton showInRange;
	ProgressDialog mProgressDialog;
	CustomAdapterResturantLists adapter;

	List<ResturantDTO> ourList = null;
	List<ResturantDTO> googleList = null;
	List<ResturantDTO> completeList = null;

	List<ResturantDTO> dummyList = null;
	List<ResturantDTO> resturantList = null;
	List<ResturantDTO> rangeList = null;
	FragmentNearByResturantListCommunicator fragmentResturantListCommunicator;
	int currentPage = 1;
	String nextToken = "", URL;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {

			fragmentResturantListCommunicator = (FragmentNearByResturantListCommunicator) activity;
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

		listViewResturantList.setOnItemClickListener(this);

		restaurantFilter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				System.out.println("Text [" + s + "]");
				adapter.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		showInRange.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					showInRange.setColorFilter(Color.argb(150, 155, 155, 155));
					rangeList = new ArrayList<ResturantDTO>();
					for (int i = 0; i < completeList.size(); i++) {
						ResturantDTO ekItem = new ResturantDTO();
						ekItem = completeList.get(i);
						String dist = ekItem.getResturantAddress()
								.getResturantDistance();
						if (Double.parseDouble(dist) < 5) {
							rangeList.add(ekItem);
						}

					}
					adapter = new CustomAdapterResturantLists(getActivity(),
							rangeList);

					listViewResturantList.setAdapter(adapter);
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					showInRange.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																				// null
					return true;
				}
				return false;
			}

		});
		new makeHttpGetConnection().execute();
		return view;
	}

	private void initializeUIElements() {
		listViewResturantList = (ListView) view
				.findViewById(R.id.listViewResturantList);
		restaurantFilter = (EditText) view
				.findViewById(R.id.editTextSearchResturantLists);
		showInRange = (ImageButton) view.findViewById(R.id.imageButtonGPS);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ResturantDTO restaurant = (ResturantDTO) parent
				.getItemAtPosition(position);
		fragmentResturantListCommunicator.setClickedNearByList(restaurant);
	}

	public static interface FragmentNearByResturantListCommunicator {
		public void setClickedNearByList(ResturantDTO resturantDTO);

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

			URL = AppConstants.RESTAURANT_LIST_FROM_GOOGLE + latitude + "/"
					+ longitude;
			Log.d("URl", URL);
			try {
				jsonData = httpGETConnection(URL);
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
			/*
			 * Collections.sort(resturantList, new Comparator<ResturantDTO>() {
			 * 
			 * @Override public int compare(ResturantDTO o1, ResturantDTO o2) {
			 * float first = Float.parseFloat(o1.getResturantAddress()
			 * .getResturantDistance()); float second =
			 * Float.parseFloat(o2.getResturantAddress()
			 * .getResturantDistance()); return (int) (first - second); } });
			 */
			adapter = new CustomAdapterResturantLists(getActivity(),
					resturantList);

			listViewResturantList.setAdapter(adapter);

			// Create an OnScrollListener
			listViewResturantList.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) { // TODO Auto-generated method stub
					int threshold = 1;
					int count = listViewResturantList.getCount();

					if (scrollState == SCROLL_STATE_IDLE) {
						if (listViewResturantList.getLastVisiblePosition() >= count
								- threshold) {
							// Execute LoadMoreDataTask AsyncTask
							new LoadMoreDataTask().execute();
						}
					}
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub

				}

			});
		}
	}

	public static String httpGETConnection(String url)
			throws ConnectTimeoutException {
		Log.d("HTTPGET", "Makilng http connection");

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("api", AppConstants.API);
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
		Log.d("restaurant list", builder.toString());
		return builder.toString();

	}

	public List<ResturantDTO> ParseJsonData(String jsonData) {
		try {
			JSONObject restaurantlist = new JSONObject(jsonData);
			nextToken = restaurantlist.getString("next");
			JSONArray ourList = restaurantlist.getJSONArray("our");
			JSONArray googleList = restaurantlist.getJSONArray("google");
			completeList = new ArrayList<ResturantDTO>();
			if (ourList != null) {
				for (int i = 0; i < ourList.length(); i++) {
					JSONObject c = ourList.getJSONObject(i);

					String id = c.getString("restaurant_id");
					String name = c.getString("restaurant_name");
					String street_address = c.getString("street_address");
					String city_address = c.getString("city_address");
					String contact = c.getString("contact");
					String distance = c.getString("dist");
					Boolean registered = true;
					Boolean loadOurList=false;
					ResturantDTO tempResturantDTO = new ResturantDTO();
					ResturantAddress tempResturantAddress = new ResturantAddress();
					tempResturantDTO.setResturantID(Integer.parseInt(id));
					tempResturantDTO.setResturantName(name);
					tempResturantDTO.setRegisrered(registered);
					tempResturantDTO.setLoadOurList(loadOurList);
					tempResturantAddress
							.setResturantStreetAddress(street_address);
					tempResturantAddress.setReturantCityName(city_address);
					tempResturantAddress.setResturantDistance(distance);
					ResturantContactInfo tempResturantContactInfo = new ResturantContactInfo();
					tempResturantContactInfo.setResturantphoneNoA(contact);
					tempResturantDTO
							.setResturantContactInfo(tempResturantContactInfo);
					tempResturantDTO.setResturantAddress(tempResturantAddress);

					completeList.add(tempResturantDTO);

				}
				Collections.sort(completeList, new Comparator<ResturantDTO>() {
					@Override
					public int compare(ResturantDTO o1, ResturantDTO o2) {
						float first = Float.parseFloat(o1.getResturantAddress()
								.getResturantDistance());
						float second = Float.parseFloat(o2
								.getResturantAddress().getResturantDistance());
						return (int) (first - second);
					}
				});

			} else {
				Toast.makeText(getActivity(), "NO DATA TO PARSE",
						Toast.LENGTH_SHORT).show();
			}

			if (googleList != null) {
				for (int i = 0; i < googleList.length(); i++) {
					JSONObject c = googleList.getJSONObject(i);

					String city_address = "";
					String contact = null;
					String name = c.getString("name");
					String street_address = c.getString("address");
					String distance = c.getString("dist");
					long latitude = c.getLong("lat");
					long longitude = c.getLong("lon");
					Boolean registered = false;
					Boolean loadOurList=false;
					ResturantDTO tempResturantDTO = new ResturantDTO();
					ResturantAddress tempResturantAddress = new ResturantAddress();

					tempResturantDTO.setResturantName(name);
					tempResturantDTO.setRegisrered(registered);
					tempResturantDTO.setLoadOurList(loadOurList);
					tempResturantAddress
							.setResturantStreetAddress(street_address);
					tempResturantAddress.setReturantCityName(city_address);
					tempResturantAddress.setResturantLatitude(latitude);
					tempResturantAddress.setResturantLongitude(longitude);
					tempResturantAddress.setResturantDistance(distance);
					ResturantContactInfo tempResturantContactInfo = new ResturantContactInfo();
					tempResturantContactInfo.setResturantphoneNoA(contact);
					tempResturantDTO
							.setResturantContactInfo(tempResturantContactInfo);
					tempResturantDTO.setResturantAddress(tempResturantAddress);

					completeList.add(tempResturantDTO);

				}

			} else {
				Toast.makeText(getActivity(), "NO DATA TO PARSE",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return completeList;
	}

	private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(getActivity());
			// Set progressdialog title
			mProgressDialog.setTitle("Please Wait");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading more...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			URL=URL+"/"+nextToken;
			String jdata;
			try {
				 jdata=httpGETConnection(URL);
				 JSONObject restaurantlist = new JSONObject(jdata);
					nextToken = restaurantlist.getString("next");
					JSONArray googleList = restaurantlist.getJSONArray("google");
				 if (googleList != null) {
						for (int i = 0; i < googleList.length(); i++) {
							JSONObject c = googleList.getJSONObject(i);

							String city_address = "";
							String contact = null;
							String name = c.getString("name");
							String street_address = c.getString("address");
							String distance = c.getString("dist");
							long latitude = c.getLong("lat");
							long longitude = c.getLong("lon");
							Boolean registered = false;
							Boolean loadOurList=false;
							ResturantDTO tempResturantDTO = new ResturantDTO();
							ResturantAddress tempResturantAddress = new ResturantAddress();

							tempResturantDTO.setResturantName(name);
							tempResturantDTO.setRegisrered(registered);
							tempResturantDTO.setLoadOurList(loadOurList);
							tempResturantAddress
									.setResturantStreetAddress(street_address);
							tempResturantAddress.setReturantCityName(city_address);
							tempResturantAddress.setResturantLatitude(latitude);
							tempResturantAddress.setResturantLongitude(longitude);
							tempResturantAddress.setResturantDistance(distance);
							ResturantContactInfo tempResturantContactInfo = new ResturantContactInfo();
							tempResturantContactInfo.setResturantphoneNoA(contact);
							tempResturantDTO
									.setResturantContactInfo(tempResturantContactInfo);
							tempResturantDTO.setResturantAddress(tempResturantAddress);

							resturantList.add(tempResturantDTO);

						}

					} else {
						Toast.makeText(getActivity(), "NO DATA TO PARSE",
								Toast.LENGTH_SHORT).show();
					}
			} catch (ConnectTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// Locate listview last item
			int position = listViewResturantList.getLastVisiblePosition();
			// Pass the results into ListViewAdapter.java
			adapter = new CustomAdapterResturantLists(getActivity(), resturantList);
			// Binds the Adapter to the ListView
			listViewResturantList.setAdapter(adapter);
			// Show the latest retrived results on the top
			listViewResturantList.setSelectionFromTop(position, 0);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}

}
