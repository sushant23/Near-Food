package com.tr.nearfood.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tr.nearfood.R;
import com.tr.nearfood.adapter.ExpandableListAdapter;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.model.CustomerInfoDTO;
import com.tr.nearfood.model.OrderedItemDTO;
import com.tr.nearfood.utills.AppConstants;

public class FragmentRestaurantOrderRejected extends Fragment {
	public static String AUTH;
	DatabaseHelper db;
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
		db = new DatabaseHelper(getActivity());
		initializeUIElements();
		// preparing list data
		// prepareListData();
		db.dropTable();
		new getCustomersOrder().execute();

		return view;
	}

	private void initializeUIElements() {
		// TODO Auto-genderated method stub
		expListView = (ExpandableListView) view
				.findViewById(R.id.expandableListMenuCollasapable);

	}

	public class getCustomersOrder extends AsyncTask<Void, Void, String> {

		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pd == null) {
				pd = new ProgressDialog(view.getContext());
				pd.setCancelable(true);
				pd.setTitle("Please wait");
				pd.setMessage("Rejected Order list is loading...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String json = "";
			try {

				json = httpGETConnection(AppConstants.RESTAURANTS_ORDER_REJECTED);

			} catch (ConnectTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pd.isShowing()) {
				pd.dismiss();
				pd = null;
			}
			ParseJsonOrders(result);

			listAdapter = new ExpandableListAdapter(getActivity(),
					listDataHeader, listDataChild,"REJECTED",AUTH,"ORDER");

			// setting list adapter
			expListView.setAdapter(listAdapter);
			db.closeDB();
		}

	}

	public static String httpGETConnection(String url)
			throws ConnectTimeoutException {
		Log.d("HTTPGET", "Makilng http connection");

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Authorization", "Basic " + AUTH);
		httpGet.addHeader("api",AppConstants.API);

		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.d("HTTPCONNECTIOn", "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			// Toast.makeText(this, "Network timeout reached!",
			// Toast.LENGTH_SHORT).show();
			Log.d("+++++++++++++++++ ", "Network timeout reached!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();

	}

	public void ParseJsonOrders(String jsonArray) {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		try {
			// Log.d("ORderJSONARRAY", jsonArray);
			JSONArray jArray = new JSONArray(jsonArray);
			if (jArray != null) {
				for (int i = 0; i < jArray.length(); i++) {

					JSONObject customer_details = jArray.getJSONObject(i);
					int customer_id = customer_details.getInt("id");
					String customer_name = customer_details.getString("name");
					CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
					customerInfoDTO.setId(customer_id);
					customerInfoDTO.setName(customer_name);
					customerInfoDTO.setJson(customer_details.toString());
					db.createCustomer(customerInfoDTO);

					listDataHeader.add(customer_name);
					JSONArray orderDetailsArray = customer_details
							.getJSONArray("order_detail");
					List<String> orderList = new ArrayList<String>();
					for (int j = 0; j < orderDetailsArray.length(); j++) {

						JSONObject item_details = orderDetailsArray
								.getJSONObject(j);
						int item_id = item_details.getInt("item_id");
						String item_name = item_details.getString("item");
						int item_price = item_details.getInt("price");
						int restaurant_id = item_details
								.getInt("restaurant_id");

						orderList.add(item_name);

						OrderedItemDTO orderedItem = new OrderedItemDTO();
						orderedItem.setItem_id(item_id);
						orderedItem.setItem(item_name);
						orderedItem.setPrice(item_price);
						orderedItem.setRestaurant_id(restaurant_id);

						if (db.checkItemPresence(restaurant_id, item_id,
								"adminorder")) {
							db.createOrderItem(orderedItem);

						}
					}
					listDataChild.put(customer_name, orderList);

				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

