package com.tr.nearfood.fragment;

import java.util.ArrayList;
import java.util.List;

import android.R.interpolator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tr.nearfood.R;
import com.tr.nearfood.adapter.CustomAdapterResturantLists;
import com.tr.nearfood.model.ResturantAddress;
import com.tr.nearfood.model.ResturantContactInfo;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.utills.AndroidBug5497Workaround;

public class FragmentResturantList extends Fragment implements
		OnItemClickListener {
	ListView listViewResturantList;
	View view;
	FragmentResturantListCommunicator fragmentResturantListCommunicator;

	List<ResturantDTO> resturantList;

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
		AndroidBug5497Workaround.assistActivity(getActivity());
		resturantList = getDummyResturantList();

		listViewResturantList.setAdapter(new CustomAdapterResturantLists(
				getActivity(), resturantList));

		listViewResturantList.setOnItemClickListener(this);
		return view;
	}

	private void initializeUIElements() {
		listViewResturantList = (ListView) view
				.findViewById(R.id.listViewResturantList);
	}

	public static List<ResturantDTO> getDummyResturantList() {
		List<ResturantDTO> dummyList = new ArrayList<ResturantDTO>();

		for (int i = 1; i <= 10; i++) {
			ResturantDTO tempResturantDTO = new ResturantDTO();
			ResturantAddress tempResturantAddress = new ResturantAddress();
			tempResturantDTO.setResturantName("Resturant " + i);
			tempResturantAddress.setResturantStreetAddress("Street " + i);
			tempResturantAddress.setReturantCityName("City " + i);
			tempResturantAddress.setResturantDistance(1000);
			ResturantContactInfo tempResturantContactInfo = new ResturantContactInfo();
			tempResturantContactInfo.setResturantphoneNoA("+97798949389" + i);
			tempResturantDTO.setResturantContactInfo(tempResturantContactInfo);
			tempResturantDTO.setResturantAddress(tempResturantAddress);

			dummyList.add(tempResturantDTO);
		}

		return dummyList;
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
}
