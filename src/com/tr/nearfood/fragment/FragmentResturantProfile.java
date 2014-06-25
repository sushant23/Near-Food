package com.tr.nearfood.fragment;

import com.tr.nearfood.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentResturantProfile extends Fragment {
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_resturant_profile, container,
				false);
		initializeUIElements();

		return view;
	}

	private void initializeUIElements() {
		
	}

}
