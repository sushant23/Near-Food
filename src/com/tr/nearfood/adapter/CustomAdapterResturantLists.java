package com.tr.nearfood.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tr.nearfood.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tr.nearfood.model.ResturantDTO;

public class CustomAdapterResturantLists extends BaseAdapter implements Filterable{
	private Context context;
	List<ResturantDTO> resturantLists;
	public static LayoutInflater layoutInflater;
	private RestauantFilter restaurantFilter;
	 
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
					.findViewById(R.id.ourListRestaurantsImageButton);

			convertedView.setTag(viewHolder);

		}

		else {
			viewHolder = (ViewHolder) convertedView.getTag();
		}

		if (resturantLists.size() == 0) {
			viewHolder.textViewRowResturantListResturantName
					.setText("Sorry, no resturant found");
		} else {
			ResturantDTO tempResturant = (ResturantDTO) resturantLists
					.get(position);
			if(tempResturant.getRegisrered())
			{
				viewHolder.textViewRowResturantListResturantCity
				.setText(tempResturant.getResturantAddress()
						.getReturantCityName());
				viewHolder.imageViewRowResturantListDirectionIcon
						.setVisibility(View.VISIBLE);
			}
			viewHolder.textViewRowResturantListResturantName
					.setText(tempResturant.getResturantName());
			viewHolder.textViewRowResturantListResturantStreet
					.setText(tempResturant.getResturantAddress()
							.getResturantStreetAddress());
			
			double value = Double.parseDouble(tempResturant.getResturantAddress()
					.getResturantDistance());
			double rounded = (double) Math.round(value * 100) / 100;
			viewHolder.textViewRowResturantListDistance.setText(String
					.valueOf(Double.toString(rounded)+"km"));
		}
		return convertedView;
	}
	private class RestauantFilter extends Filter {
	    @Override
	    protected FilterResults performFiltering(CharSequence constraint) {
			
	    	FilterResults results = new FilterResults();
	        // We implement here the filter logic
	        if (constraint == null || constraint.length() == 0) {
	            // No filter implemented we return all the list
	            results.values = resturantLists;
	            results.count = resturantLists.size();
	        }
	        else {
	            // We perform filtering operation
	            List<ResturantDTO> nRestaurantList = new ArrayList<ResturantDTO>();
	             
	            for (ResturantDTO p : resturantLists) {
	                if (p.getResturantName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
	                	nRestaurantList.add(p);
	            }
	             
	            results.values = nRestaurantList;
	            results.count = nRestaurantList.size();
	     
	        }
	        return results;
	    
	    }
	 
	    @Override
	    protected void publishResults(CharSequence constraint,FilterResults results) {
	    	// Now we have to inform the adapter about the new list filtered
	        if (results.count == 0)
	            notifyDataSetInvalidated();
	        else {
	        	resturantLists = (List<ResturantDTO>) results.values;
	            notifyDataSetChanged();
	        }
	    }
	     
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		 if (restaurantFilter == null)
		        restaurantFilter = new RestauantFilter();
		     
		    return restaurantFilter;
	}
}
