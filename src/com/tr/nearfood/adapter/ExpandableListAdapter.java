package com.tr.nearfood.adapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tr.nearfood.R;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.fragment.FragmentsRestaurantAdminManageOrder;
import com.tr.nearfood.utills.AppConstants;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	String sucess = "";
	DatabaseHelper db;
	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	String sendingJson = "";
	LinearLayout linlayout;
	TextView status_tv;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
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
		TextView txtChildPrice = (TextView) convertView
				.findViewById(R.id.textViewMenuItemPrices);
		int itmID = db.getItemId(childText, "adminorder");
		int price = db.getItemPrice(itmID, "adminorder");

		txtListChild.setText(childText);
		txtChildPrice.setText(Integer.toString(price));
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
		final String headerTitle = (String) getGroup(groupPosition);
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
		linlayout = (LinearLayout) convertView
				.findViewById(R.id.linlayouteditparameters);
		linlayout.setVisibility(View.VISIBLE);
		final ImageButton confirmOrder = (ImageButton) convertView
				.findViewById(R.id.imageButtonCompleteOrder);
		final ImageButton rejectedOrder = (ImageButton) convertView
				.findViewById(R.id.imageButtonDeleteIcon);
		final ImageButton sendMessage = (ImageButton) convertView
				.findViewById(R.id.imageButtonMessageIcon);
		status_tv = (TextView) convertView.findViewById(R.id.textViewStatus);
		confirmOrder.setFocusable(false);
		rejectedOrder.setFocusable(false);
		sendMessage.setFocusable(false);

		confirmOrder.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub

				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					confirmOrder.setColorFilter(Color.argb(150, 155, 155, 155));
					try {
						sendingJson = db.getCustomerJsonDetails(headerTitle);

						Log.d("JSon COnfirmed", sendingJson);
					} catch (CursorIndexOutOfBoundsException e) {
						Log.d("exception",
								"cursor index out of bounds exception");

					}
					new confirmCustomerOrder().execute();
					
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					confirmOrder.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																				// null
					return true;
				}
				return false;
			}
		});
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

	public class confirmCustomerOrder extends AsyncTask<Void, Void, String> {

		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pd == null) {
				pd = new ProgressDialog(_context);
				pd.setCancelable(true);
				pd.setTitle("Please wait");
				pd.setMessage("Confirming the Order...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String json = "";
			json = httpPostResponse(AppConstants.RESTAURANTS_ORDER_CONFIRMING);
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pd.isShowing()) {
				pd.dismiss();
				pd = null;
				Log.d("Response ", result);
				try {
					JSONObject login_status = new JSONObject(result);
					sucess = login_status.getString("status");
					String message = login_status.getString("message");
					if (sucess.equals("success")) {
						Toast.makeText(_context, message, Toast.LENGTH_SHORT)
								.show();
						Log.d("CONFIRMED", "image button gone text on");
						linlayout.setVisibility(View.GONE);
						status_tv.setVisibility(View.VISIBLE);
						status_tv.setText("CONFIRMED");
					} else if (sucess.equals("error")) {
						Toast.makeText(_context, message, Toast.LENGTH_SHORT)
								.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// FragmentsRestaurantAdminManageOrder fm= new
				// FragmentsRestaurantAdminManageOrder();
				// fm.new getCustomersOrder().execute();
			}

		}

		public String httpPostResponse(String url) {
			Log.d("HTTPPOST", "Makilng http connection");
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setHeader("Authorization", "Basic "
					+ FragmentsRestaurantAdminManageOrder.AUTH);
			Log.d("Authentication", FragmentsRestaurantAdminManageOrder.AUTH);
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("json_string",
						sendingJson));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// httppost.setEntity(new StringEntity(sendingJson));
				HttpResponse resp;
				resp = httpclient.execute(httppost);
				HttpEntity ent = resp.getEntity();
				return EntityUtils.toString(ent);

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
