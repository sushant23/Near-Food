package com.tr.nearfood.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tr.nearfood.R;
import com.tr.nearfood.dragndrop.DragNDropListView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentAdminManageMenu extends Fragment {

	/** children items with a key and value list */
	private Map<String, ArrayList<String>> children;
	DragNDropListView eList;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_admin_dropndrag_menu,
				container, false);
		getData();
		eList = (DragNDropListView) view
				.findViewById(R.id.list_view_customizer);

		eList.setAdapter(new com.tr.nearfood.dragndrop.DragNDropAdapter(
				getActivity(), new int[] { R.layout.dropndrag_menu_listitem },
				new int[] { R.id.txt__customizer_item }, children));
		return view;
	}

	/**
	 * simple function to fill the list
	 */

	private void getData() {
		ArrayList<String> groups = new ArrayList<String>();
		children = Collections
				.synchronizedMap(new LinkedHashMap<String, ArrayList<String>>());
		for (int i = 0; i < 9; i++) {
			groups.add("Menu " + i);

		}
		for (String s : groups) {
			ArrayList<String> child = new ArrayList<String>();

			for (int i = 0; i < 4; i++) {

				child.add(s + " -Item" + i);

			}
			children.put(s, child);
		}
	}
}
