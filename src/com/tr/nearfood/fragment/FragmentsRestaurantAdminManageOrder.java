package com.tr.nearfood.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tr.nearfood.R;
import com.tr.nearfood.adapter.ExpandableListAdapter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class FragmentsRestaurantAdminManageOrder extends Fragment {
	View view;
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_restraurant_menu_list,
				container, false);
		initializeUIElements();
		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader,
				listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// Toast.makeText(getActivity(),
				// listDataHeader.get(groupPosition) + " Expanded",
				// Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				// Toast.makeText(getActivity(),
				// listDataHeader.get(groupPosition) + " Collapsed",
				// Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(
						getActivity(),
						listDataHeader.get(groupPosition)
								+ " : "
								+ listDataChild.get(
										listDataHeader.get(groupPosition)).get(
										childPosition), Toast.LENGTH_SHORT)
						.show();
				return false;
			}
		});

		return view;
	}

	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("ABELA");
		listDataHeader.add("PEDRO");
		listDataHeader.add("BUFFON");

		// Adding child data
		List<String> orderList1 = new ArrayList<String>();
		orderList1.add("1.DRINKS");
		orderList1.add("2.STRATER");
		orderList1.add("3.PIZZA");
		orderList1.add("4.DESERTS");

		List<String> orderList2 = new ArrayList<String>();
		orderList2.add("1.DRINKS");
		orderList2.add("2.STRATER");
		orderList2.add("3.PIZZA");
		orderList2.add("4.DESERTS");

		List<String> orderList3 = new ArrayList<String>();
		orderList3.add("1.DRINKS");
		orderList3.add("2.STRATER");
		orderList3.add("3.PIZZA");
		orderList3.add("4.DESERTS");

		listDataChild.put(listDataHeader.get(0), orderList1); // Header, Child
																// data
		listDataChild.put(listDataHeader.get(1), orderList2);
		listDataChild.put(listDataHeader.get(2), orderList3);
	}

	private void initializeUIElements() {
		// TODO Auto-generated method stub
		expListView = (ExpandableListView) view
				.findViewById(R.id.expandableListMenuCollasapable);
	
	}
}
