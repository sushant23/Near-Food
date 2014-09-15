package com.tr.nearfood.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tr.nearfood.R;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.model.MigratingDatas;

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

public class ExpandableListAdapterForCustomerOrder extends BaseExpandableListAdapter{
	public Context _context;
	DatabaseHelper db;
	public List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	public HashMap<String, List<String>> _listDataChild;
	public static MigratingDatas migratingDtos = new MigratingDatas();
	List<Integer> selectedMenuItemList = new ArrayList<Integer>();

	public ExpandableListAdapterForCustomerOrder(Context context,
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

		
		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.textViewMenuItemName);
		TextView txtListChildPrice = (TextView) convertView
				.findViewById(R.id.textViewMenuItemPrices);
		int itmID = db.getItemId(childText,"item");
		int price = db.getItemPrice(itmID,"item");

		txtListChild.setText(childText);
		txtListChildPrice.setText(Integer.toString(price));

		
		

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
		TextView status=(TextView) convertView.findViewById(R.id.textViewStatus);
		
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);
		//status.setVisibility(View.VISIBLE);
		status.setTypeface(null, Typeface.BOLD);
		status.setText("PENDING");

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
