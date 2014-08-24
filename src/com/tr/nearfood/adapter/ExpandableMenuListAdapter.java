package com.tr.nearfood.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tr.nearfood.R;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.model.MigratingDatas;
import com.tr.nearfood.utills.AppConstants;

public class ExpandableMenuListAdapter extends BaseExpandableListAdapter {
	public Context _context;
	DatabaseHelper db;
	public List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	public HashMap<String, List<String>> _listDataChild;
	public static MigratingDatas migratingDtos = new MigratingDatas();
	List<Integer> selectedMenuItemList = new ArrayList<Integer>();

	public ExpandableMenuListAdapter(Context context,
			List<String> listDataHeader,
			HashMap<String, List<String>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		db = new DatabaseHelper(_context);

	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.expandalelist_menu_lisitem, null);
		}

		Button addmenuItem = (Button) convertView
				.findViewById(R.id.buttonAddMenuItem);
		addmenuItem.setVisibility(View.VISIBLE);
		Button deleteMenuItem = (Button) convertView
				.findViewById(R.id.buttonDeleteMenuItem);
		deleteMenuItem.setVisibility(View.VISIBLE);
		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.textViewMenuItemName);
		TextView txtListChildPrice = (TextView) convertView
				.findViewById(R.id.textViewMenuItemPrices);
		int itmID = db.getItemId(childText,"item");
		int price = db.getItemPrice(itmID,"item");

		txtListChild.setText(childText);
		txtListChildPrice.setText(Integer.toString(price));

		addmenuItem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(_context, "ADDED" + childText,
						Toast.LENGTH_SHORT).show();
				int itmID = db.getItemId(childText,"item");
				if (itmID != 0)
					selectedMenuItemList.add(itmID);
				Log.d("Add button",
						"clicked ITEM ID= " + Integer.toString(itmID));
			}
		});
		deleteMenuItem.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(_context, "REMOVED" + childText,
						Toast.LENGTH_SHORT).show();
				int itmID = db.getItemId(childText,"item");
				boolean one_item_removed = false;
				if (itmID != 0) {
					for (int rem = 0; rem < selectedMenuItemList.size(); rem++) {
						if (!one_item_removed) {
							if (itmID == selectedMenuItemList.get(rem)) {
								selectedMenuItemList.remove(rem);
								one_item_removed = true;
							}
						}
					}
				}
				Log.d("Delete button", "clicked");
			}
		});
		Log.d("Size of the added items",
				Integer.toString(selectedMenuItemList.size()));
		migratingDtos = new MigratingDatas();
		migratingDtos.setConfirmedOrderList(selectedMenuItemList);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(
					R.layout.expandablelist_menu_header, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.textviewMenuHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
