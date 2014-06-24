package com.tr.nearfood.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.tr.nearfood.R;
import com.tr.nearfood.adapter.CustomAdapterResturantLists;
import com.tr.nearfood.model.ResturantAddress;
import com.tr.nearfood.model.ResturantDTO;

public class RestaurantList extends Activity {
	ListView listViewResturantList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		initializeUIElements();

		listViewResturantList.setAdapter(new CustomAdapterResturantLists(this,
				getDummyResturantList()));
	}

	private void initializeUIElements() {
		listViewResturantList = (ListView) findViewById(R.id.listViewResturantList);

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
