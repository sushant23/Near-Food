package com.tr.nearfood.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tr.nearfood.R;
import com.tr.nearfood.adapter.PlaceAutoCompleteAdapter;

public class FragmentGoogleMap extends Fragment {

	public static double LATITUDE;
	public static double LONGITUDE;
	private GoogleMap map;
	Marker marker;
	double latitude, longitude;
	Geocoder geocoder;
	List<Address> addresses;
	Button setLocation;
	FragmentGoogleMapListener fragmentGoogleMapListener;
	AutoCompleteTextView searchAddress;
	Button submitAddress;

	private static final String TAG_RESULTS = "results";
	private static final String TAG_GEOMETRY = "geometry";
	private static final String TAG_VIEWPORT = "location";
	private static final String TAG_NORTHEAST = "northeast";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	// contacts JSONArray
	JSONArray results = null;
	View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setRetainInstance(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		try {
			SupportMapFragment fragment = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(R.id.map);
			if (fragment != null)
				getFragmentManager().beginTransaction().remove(fragment)
						.commit();

		} catch (IllegalStateException e) {
			// handle this situation because you are necessary will get
			// an exception here :-(
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {

			fragmentGoogleMapListener = (FragmentGoogleMapListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentgoogleMaPListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null)
				parent.removeView(view);
		}
		try {
			view = inflater.inflate(R.layout.google_map_integration, container,
					false);

		} catch (InflateException e) {
			/* map is already there, just return view as it is */
		}
		initializaUIElements();
		map = ((SupportMapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		searchAddress.setAdapter(new PlaceAutoCompleteAdapter(getActivity(),
				R.layout.autocomplete_list));

		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng point) {
				// TODO Auto-generated method stub
				if (marker != null)
					marker.remove();
				String city = null, country = null;
				geocoder = new Geocoder(getActivity(), Locale.getDefault());
				try {
					latitude = point.latitude;
					longitude = point.longitude;
					addresses = geocoder.getFromLocation(point.latitude,
							point.longitude, 1);
					String address = addresses.get(0).getAddressLine(0);
					city = addresses.get(0).getAddressLine(1);
					country = addresses.get(0).getAddressLine(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				marker = map.addMarker(new MarkerOptions().position(point)
						.title(city).snippet(country));
			}
		});

		submitAddress.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (marker != null)
					marker.remove();
				String getAddress = searchAddress.getText().toString();
				LatLng point = getLatLngOfEnteredAddress(getAddress);
				String city = null, country = null;
				geocoder = new Geocoder(getActivity(), Locale.getDefault());
				try {
					latitude = point.latitude;
					longitude = point.longitude;
					addresses = geocoder.getFromLocation(point.latitude,
							point.longitude, 1);
					String address = addresses.get(0).getAddressLine(0);
					city = addresses.get(0).getAddressLine(1);
					country = addresses.get(0).getAddressLine(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				marker = map.addMarker(new MarkerOptions().position(point)
						.title(city).snippet(country));
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(
						marker.getPosition(), 14));
			}

		});

		setLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fragmentGoogleMapListener.onLocationSetClicked(longitude,
						latitude,true);
			}
		});
		
		view.setFocusableInTouchMode(true);
		view.requestFocus();

		view.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                    	fragmentGoogleMapListener.onLocationSetClicked(0,
        						0,false);

                    return true;
                    }
                }
				return false;
			}
		});
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initializaUIElements();
	}

	void initializaUIElements() {

		searchAddress = (AutoCompleteTextView) view
				.findViewById(R.id.editTextSearchResturantLists);
		submitAddress = (Button) view
				.findViewById(R.id.ButtonSearchAddressGoogleMap);
		submitAddress = (Button) view
				.findViewById(R.id.ButtonSearchAddressGoogleMap);
		setLocation = (Button) view
				.findViewById(R.id.buttonAadLocationFromGmap);
	}

	private LatLng getLatLngOfEnteredAddress(String address) {
		// TODO Auto-generated method stub
		// Creating JSON Parser instance

		Toast.makeText(getActivity(), address, Toast.LENGTH_SHORT).show();
		address = address.replaceAll(" ", "%20");
		String url = "http://maps.googleapis.com/maps/api/geocode/json?address="
				+ address + "&sensor=true";

		// getting JSON string from URL
		JSONObject json = getJSONFromUrl(url);

		try {
			// Getting Array of results
			results = json.getJSONArray(TAG_RESULTS);

			for (int i = 0; i < results.length(); i++) {
				JSONObject r = results.getJSONObject(i);

				// geometry and location is again JSON Object
				JSONObject geometry = r.getJSONObject(TAG_GEOMETRY);

				JSONObject viewport = geometry.getJSONObject(TAG_VIEWPORT);

				String lat = viewport.getString(TAG_LAT);
				String lng = viewport.getString(TAG_LNG);
				Toast.makeText(getActivity(), lat + "fsdfs=" + lng,
						Toast.LENGTH_SHORT).show();

				LatLng position = new LatLng(Double.parseDouble(lat),
						Double.parseDouble(lng));
				return position;
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject getJSONFromUrl(String url) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}

	public static interface FragmentGoogleMapListener {
		public void onLocationSetClicked(double longitude, double latitude,boolean status);

	}
}
