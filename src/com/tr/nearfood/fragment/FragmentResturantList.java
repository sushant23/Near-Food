package com.tr.nearfood.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tr.nearfood.R;
import com.tr.nearfood.adapter.CustomAdapterResturantLists;
import com.tr.nearfood.model.ResturantAddress;
import com.tr.nearfood.model.ResturantDTO;

public class FragmentResturantList extends Fragment {
	ListView listViewResturantList;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_resturant_list_layout,
				container, false);
		initializeUIElements();
		
		listViewResturantList.setAdapter(new CustomAdapterResturantLists(getActivity(),
				getDummyResturantList()));
		return view;
	}
	private void initializeUIElements() {
		listViewResturantList = (ListView) view.findViewById(R.id.listViewResturantList);

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
			tempResturantDTO.setResturantAddress(tempResturantAddress);

			dummyList.add(tempResturantDTO);
		}

		return dummyList;
	}
}
