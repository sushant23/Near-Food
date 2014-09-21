package com.tr.nearfood.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tr.nearfood.R;
import com.tr.nearfood.adapter.CustomAdapterResturantLists;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.model.ResturantAddress;
import com.tr.nearfood.model.ResturantContactInfo;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.utills.ActivityLayoutAdjuster;
import com.tr.nearfood.utills.AppConstants;

import com.tr.nearfood.utills.SampleApplication;

public class FragmentResturantList extends Fragment implements
		OnItemClickListener {
	ListView listViewResturantList;
	View view;
	FragmentResturantListCommunicator fragmentResturantListCommunicator;
	List<ResturantDTO> dummyList = null;
	List<ResturantDTO> resturantList = null;
	List<ResturantDTO> rangeList = null;
	public static int selected_catagory;
	public static double latitude;
	public static double longitude;
	DatabaseHelper db;
	CustomAdapterResturantLists adapter;
	EditText restaurantFilter;
	ImageButton showInRange;
	String url;
	// These tags will be used to cancel the requests
	private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";
	String TAG = "Making http request";
	private ProgressDialog pDialog;

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
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_resturant_list_layout,
				container, false);
		db = new DatabaseHelper(getActivity());
		initializeUIElements();
		ActivityLayoutAdjuster.assistActivity(getActivity());
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
		url = AppConstants.RESTAURANTS_LIST + selected_catagory + "&" + "lat="
				+ latitude + "&" + "lon=" + longitude;
		Log.d("URl", url);

		makeJsonArryReq();

		listViewResturantList.setOnItemClickListener(this);
		restaurantFilter.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				try {
					System.out.println("Text [" + s + "]");
					adapter.getFilter().filter(s.toString());
				} catch (NullPointerException e) {
					// TODO: handle exception
					Log.d("erroe", e.toString());
				}

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
					for (int i = 0; i < dummyList.size(); i++) {
						ResturantDTO ekItem = new ResturantDTO();
						ekItem = dummyList.get(i);
						String dist = ekItem.getResturantAddress()
								.getResturantDistance();
						if (Double.parseDouble(dist) < 1000) {
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
		Toast.makeText(getActivity(), "item clicked", Toast.LENGTH_SHORT)
				.show();
		ResturantDTO restaurant = (ResturantDTO) parent
				.getItemAtPosition(position);
		fragmentResturantListCommunicator.setClickedData(restaurant);
	}

	public static interface FragmentResturantListCommunicator {
		public void setClickedData(ResturantDTO resturantDTO);

	}

	public List<ResturantDTO> ParseJsonData(JSONArray restaurantlist) {
		try {
			// JSONArray restaurantlist = new JSONArray(jsonData);

			dummyList = new ArrayList<ResturantDTO>();
			if (restaurantlist != null) {
				for (int i = 0; i < restaurantlist.length(); i++) {
					JSONObject c = restaurantlist.getJSONObject(i);

					String id = c.getString("restaurant_id");
					String name = c.getString("restaurant_name");
					String street_address = c.getString("street_address");
					db.createRestaurantList(Integer.parseInt(id), name,
							street_address);
					String city_address = c.getString("city_address");
					String contact = c.getString("contact");
					String distance = c.getString("dist");
					Boolean registered = true;
					ResturantDTO tempResturantDTO = new ResturantDTO();
					ResturantAddress tempResturantAddress = new ResturantAddress();
					tempResturantDTO.setResturantID(Integer.parseInt(id));
					tempResturantDTO.setResturantName(name);
					tempResturantDTO.setRegisrered(registered);
					tempResturantAddress
							.setResturantStreetAddress(street_address);
					tempResturantAddress.setReturantCityName(city_address);
					tempResturantAddress.setResturantDistance(distance);
					ResturantContactInfo tempResturantContactInfo = new ResturantContactInfo();
					tempResturantContactInfo.setResturantphoneNoA(contact);
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

	private void showProgressDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideProgressDialog() {
		if (pDialog.isShowing())
			pDialog.hide();
	}

	private void makeJsonArryReq() {
		showProgressDialog();

		JsonArrayRequest req = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Log.d(TAG, response.toString());
						resturantList = ParseJsonData(response);
						Collections.sort(resturantList,
								new Comparator<ResturantDTO>() {
									@Override
									public int compare(ResturantDTO o1,
											ResturantDTO o2) {
										float first = Float.parseFloat(o1
												.getResturantAddress()
												.getResturantDistance());
										float second = Float.parseFloat(o2
												.getResturantAddress()
												.getResturantDistance());
										return (int) (first - second);
									}
								});
						adapter = new CustomAdapterResturantLists(
								getActivity(), resturantList);

						listViewResturantList.setAdapter(adapter);
						hideProgressDialog();

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d(TAG, "Error: " + error.getMessage());
						hideProgressDialog();
					}
				}) {

			/**
			 * Passing some request headers
			 * */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("api", AppConstants.API);
				return headers;
			}

		};

		// Adding request to request queue
		SampleApplication.getInstance().addToRequestQueue(req, tag_json_arry);

		// Cancelling request
		// ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);

	}
}
