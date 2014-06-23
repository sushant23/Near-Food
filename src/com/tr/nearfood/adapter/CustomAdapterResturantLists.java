package com.tr.nearfood.adapter;

import java.util.List;

import com.tr.nearfood.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tr.nearfood.model.ResturantDTO;

public class CustomAdapterResturantLists extends BaseAdapter {
	private Context context;
	List<ResturantDTO> resturantLists;
	public static LayoutInflater layoutInflater;

	public CustomAdapterResturantLists(Context context,
			List<ResturantDTO> resturantLists) {
		this.context = context;
		this.resturantLists = resturantLists;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public static class ViewHolder {

		public TextView textViewRowResturantListResturantName;
		public TextView textViewRowResturantListResturantStreet;
		public TextView textViewRowResturantListResturantCity;
		public TextView textViewRowResturantListDistance;

		public ImageView imageViewRowResturantListDirectionIcon;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (resturantLists.size() != 0)
			return resturantLists.size();
		else
			return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return resturantLists.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View convertedView = convertView;
		ViewHolder viewHolder;

		if (convertedView == null) {
			convertedView = layoutInflater.inflate(R.layout.row_resturant_list,
					null);
			viewHolder = new ViewHolder();
			viewHolder.textViewRowResturantListResturantName = (TextView) convertedView
					.findViewById(R.id.textViewRowResturantListResturantName);
			viewHolder.textViewRowResturantListResturantStreet = (TextView) convertedView
					.findViewById(R.id.textViewRowResturantListResturantStreet);
			viewHolder.textViewRowResturantListResturantCity = (TextView) convertedView
					.findViewById(R.id.textViewRowResturantListResturantCity);
			viewHolder.textViewRowResturantListDistance = (TextView) convertedView
					.findViewById(R.id.textViewRowResturantListDistance);

			viewHolder.imageViewRowResturantListDirectionIcon = (ImageView) convertedView
					.findViewById(R.id.imageViewRowResturantListDirectionIcon);

			convertedView.setTag(viewHolder);

		}

		else {
			viewHolder = (ViewHolder) convertedView.getTag();
		}

		if (resturantLists.size() == 0) {
			viewHolder.textViewRowResturantListResturantName
					.setText("Sorry, no resturant found");
		} else {
			ResturantDTO tempResturant = (ResturantDTO) resturantLists.get(position);
			viewHolder.textViewRowResturantListResturantName.setText(tempResturant.getResturantName());
			viewHolder.textViewRowResturantListResturantStreet.setText(tempResturant.getResturantAddress().getResturantStreetAddress());
			viewHolder.textViewRowResturantListResturantCity.setText(tempResturant.getResturantAddress().getReturantCityName());
			viewHolder.textViewRowResturantListDistance.setText(String.valueOf(tempResturant.getResturantAddress().getResturantDistance()));
		}
		return null;
	}

}
