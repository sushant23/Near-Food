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
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class FragmentRestaurantMenu extends Fragment implements OnClickListener {
	View view;
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
				//Toast.makeText(getActivity(),
					//	listDataHeader.get(groupPosition) + " Expanded",
						//Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
		//		Toast.makeText(getActivity(),
			//			listDataHeader.get(groupPosition) + " Collapsed",
				//		Toast.LENGTH_SHORT).show();

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
		listDataHeader.add("DRINKS");
		listDataHeader.add("STRATER");
		listDataHeader.add("PIZZA");

		// Adding child data
		List<String> drinks = new ArrayList<String>();
		drinks.add("DRINKS 1");
		drinks.add("DRINKS 2");
		drinks.add("DRINKS 3");
		drinks.add("DRINKS 4");
		drinks.add("DRINKS 5");
		drinks.add("DRINKS 6");
		drinks.add("DRINKS 7");

		List<String> strater = new ArrayList<String>();
		strater.add("STRATER 1");
		strater.add("STRATER 2");
		strater.add("STRATER 3");
		strater.add("STRATER 4");
		strater.add("STRATER 5");
		strater.add("STRATER 6");

		List<String> pizza = new ArrayList<String>();
		pizza.add("PIZZA 1");
		pizza.add("PIZZA 2");
		pizza.add("PIZZA 3");
		pizza.add("PIZZA 4");
		pizza.add("PIZZA 5");

		listDataChild.put(listDataHeader.get(0), drinks); // Header, Child data
		listDataChild.put(listDataHeader.get(1), strater);
		listDataChild.put(listDataHeader.get(2), pizza);
	}

	private void initializeUIElements() {
		// get the listview
		expListView = (ExpandableListView) view
				.findViewById(R.id.expandableListMenuCollasapable);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}
