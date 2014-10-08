package com.tr.nearfood.fragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tr.nearfood.R;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class FragmentGoogleMapRestaurant extends Fragment {
	public static double LATITUDE;
	public static double LONGITUDE;
	private GoogleMap map;
	Marker marker;
	Button setLocation;
	AutoCompleteTextView searchAddress;
	Button submitAddress;
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
		hideUIElements();
		map = ((SupportMapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		LatLng point = new LatLng(LATITUDE, LONGITUDE);
		marker = map.addMarker(new MarkerOptions().position(point));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(
				marker.getPosition(), 14));
		

		

		view.setFocusableInTouchMode(true);
		view.requestFocus();

		/*view.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						

						return true;
					}
				}
				return false;
			}
		});*/
		return view;
	}
	void initializaUIElements() {

		searchAddress = (AutoCompleteTextView) view
				.findViewById(R.id.editTextSearchResturantLists);
		submitAddress = (Button) view
				.findViewById(R.id.ButtonSearchAddressGoogleMap);
		
		setLocation = (Button) view
				.findViewById(R.id.buttonAadLocationFromGmap);
	}
	void hideUIElements(){
		searchAddress.setVisibility(View.GONE);
		submitAddress.setVisibility(View.GONE);
		setLocation.setVisibility(View.GONE);
	}
}
