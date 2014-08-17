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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.tr.nearfood.R;
import com.tr.nearfood.activity.ChooseLoginMethod;
import com.tr.nearfood.adapter.ExpandableMenuListAdapter;
import com.tr.nearfood.model.Catagory;
import com.tr.nearfood.model.ItemMenuDTO;
import com.tr.nearfood.model.MigratingDatas;
import com.tr.nearfood.utills.AppConstants;
import com.tr.nearfood.dbhelper.DatabaseHelper;

public class FragmentRestaurantMenu extends Fragment implements OnClickListener {
	DatabaseHelper db;
	View view;
	ExpandableMenuListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	Button sendOrder, showOrder;
	FragmentResturantMenuListCommunicator fragmentResturantMenuListCommunicator;
	public static int SELECTED_RESTAURANTID;
	List<ItemMenuDTO> menuItemList = null;
	List<Integer> catagoryIdList = null;
	Catagory fromsqliteCatagory;
	List<ItemMenuDTO> fromsqliteMenuItem = null;
	public static MigratingDatas migratingdata;
	public static String SETDATETIME;

	// menu changed
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {

			fragmentResturantMenuListCommunicator = (FragmentResturantMenuListCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentResturantProfileCommunicator");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_restraurant_menu_list,
				container, false);

		initializeUIElements();
		db = new DatabaseHelper(getActivity());

		sendOrder.setVisibility(View.VISIBLE);
		showOrder.setVisibility(View.VISIBLE);
		sendOrder.setOnClickListener(this);
		showOrder.setOnClickListener(this);
		new makeHttpGetConnection().execute();

		return view;
	}

	private void initializeUIElements() {
		// get the listview
		expListView = (ExpandableListView) view
				.findViewById(R.id.expandableListMenuCollasapable);

		sendOrder = (Button) view.findViewById(R.id.buttonSendYourOrder);
		showOrder = (Button) view.findViewById(R.id.buttonShowYourOrder);
	}

	// make the array of catagory with unique id
	public static int[] toArray(List<Integer> src) {

		int[] res = new int[src.size()];
		res[0] = src.get(0);
		for (int i = 0; i < src.size(); i++) {
			int count = 0;
			int temp = src.get(i);
			for (int j = 0; j < i; j++) {
				if (temp == res[j])
					count = count + 1;
			}
			if (count == 0)
				res[i] = src.get(i);
		}
		return res;
	}

	// prepare the menu list items and is get from the sqlite
	private void prepareListData() throws IndexOutOfBoundsException {

		// preparing the list for menu from data stored in sqlite
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		catagoryIdList = new ArrayList<Integer>();
		List<String> menuItem = null;
		catagoryIdList = db.getcatagorylist(SELECTED_RESTAURANTID);
		int[] uniqueCatagoryIdArray = toArray(catagoryIdList);

		for (int i = 0; i < (uniqueCatagoryIdArray.length); i++) {
			if (uniqueCatagoryIdArray[i] != 0) {
				fromsqliteCatagory = db.getCatagory(uniqueCatagoryIdArray[i]);
				listDataHeader.add(fromsqliteCatagory.getCatagoryName());
				fromsqliteMenuItem = db.getItems(fromsqliteCatagory.getId(),
						SELECTED_RESTAURANTID);
				int size = fromsqliteMenuItem.size();
				Log.d("size of menu item", Integer.toString(size));
				menuItem = new ArrayList<String>();
				for (int j = 0; j < fromsqliteMenuItem.size(); j++) {
					menuItem.add(fromsqliteMenuItem.get(j).getItemName());
				}
				listDataChild.put(listDataHeader.get(i), menuItem);
			}

		}

	}

	@Override
	public void onClick(View click) {
		// TODO Auto-generated method stub

		switch (click.getId()) {
		case R.id.buttonSendYourOrder:

			try {
				Intent startLogin = new Intent(getActivity(),
						ChooseLoginMethod.class);
				startLogin.putIntegerArrayListExtra("confirmedMenuItems",
						(ArrayList<Integer>) migratingdata
								.getConfirmedOrderList());
				startActivity(startLogin);
				Log.d("Size of confirmed orders is ", Integer
						.toString(migratingdata.getConfirmedOrderList().size()));
			} catch (NullPointerException e) {
				Toast.makeText(getActivity(), "Order Not Confirmed",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.buttonShowYourOrder:

			List<Integer> SELECTED_MENU_ITEM_LIST = ExpandableMenuListAdapter.migratingDtos
					.getConfirmedOrderList();
			fragmentResturantMenuListCommunicator
					.setMenuButtonClicked(SELECTED_MENU_ITEM_LIST);
			break;

		default:
			break;
		}
	}

	public static interface FragmentResturantMenuListCommunicator {
		public void setMenuButtonClicked(List<Integer> confirmed_Menu_Item_List);

	}

	// get the json data from the restaurant menu items of a selected restaurant
	// as per
	// restaurant id
	public class makeHttpGetConnection extends
			AsyncTask<String, String, String> {
		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pd == null) {
				pd = new ProgressDialog(view.getContext());
				pd.setCancelable(true);
				pd.setTitle("Please wait");
				pd.setMessage("Menu Item list is loading...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String jsonData = "";
			String url = AppConstants.ITEMS_BY_RESTAURANTS
					+ SELECTED_RESTAURANTID;
			Log.d("URl", url);
			try {
				jsonData = httpGETConnection(url);
				Log.d("Menu Items", jsonData);
			} catch (ConnectTimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return jsonData;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pd.isShowing()) {
				pd.dismiss();
				pd = null;
				try {
					jsonDataParse(result);
					// preparing list data
					prepareListData();
					listAdapter = new ExpandableMenuListAdapter(getActivity(),
							listDataHeader, listDataChild);

					// setting list adapter
					expListView.setAdapter(listAdapter);
					db.closeDB();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public static String httpGETConnection(String url)
			throws ConnectTimeoutException {
		Log.d("HTTPGET", "Makilng http connection");

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(url);
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

	// parse the json data of menu items and store it in the sqllite for further
	// processing
	void jsonDataParse(String jsonData) throws JSONException {
		JSONArray menuArray = new JSONArray(jsonData);
		List<Integer> itemList = db.getItemIdList();
		if (menuArray != null) {
			for (int i = 0; i < menuArray.length(); i++) {
				JSONObject c = menuArray.getJSONObject(i);

				int item_id = c.getInt("id");
				String item_name = c.getString("item");
				int catagory_id = c.getInt("category_id");
				int restaurant_id = c.getInt("restaurant_id");
				int item_price = c.getInt("price");
				String item_is_active = c.getString("is_active");
				String item_created_at = c.getString("created_at");
				String item_updated_at = c.getString("updated_at");

				JSONObject catagory = c.getJSONObject("category");
				int id = catagory.getInt("id");
				String catagory_name = catagory.getString("name");
				String catagory_description = catagory.getString("description");
				String catagory_is_active = catagory.getString("is_active");
				String catagory_created_at = catagory.getString("created_at");
				String catagory_updated_at = catagory.getString("updated_at");

				// itemune dto ma data set gareko
				ItemMenuDTO itemMenuDTO = new ItemMenuDTO();
				Catagory catagoryDTO = new Catagory();
				itemMenuDTO.setId(item_id);
				itemMenuDTO.setItemCatagoryID(catagory_id);
				itemMenuDTO.setRestaurants_id(restaurant_id);
				itemMenuDTO.setItemName(item_name);
				itemMenuDTO.setItemPrice(item_price);
				itemMenuDTO.setIs_active(item_is_active);
				itemMenuDTO.setCreated_at(item_created_at);
				itemMenuDTO.setUpdated_at(item_updated_at);

				// catagory dto ma set gareko data
				catagoryDTO.setId(id);
				catagoryDTO.setCatagoryName(catagory_name);
				catagoryDTO.setDescription(catagory_description);
				catagoryDTO.setIs_active(catagory_is_active);
				catagoryDTO.setCreated_at(catagory_created_at);
				catagoryDTO.setUpdated_at(catagory_updated_at);

				if (db.checkItemPresence(SELECTED_RESTAURANTID, item_id)) {
					db.createItems(itemMenuDTO);
					db.createCatagory(catagoryDTO);
				}
				for (int it = 0; it < itemList.size(); it++) {
					if (itemList.get(it) == item_id) {
						itemList.remove(it);
					}
				}

			}

		} else {
			Toast.makeText(getActivity(), "NO DATA TO PARSE",
					Toast.LENGTH_SHORT).show();
		}
		for (int remove = 0; remove < itemList.size(); remove++) {
			db.deleteItem(itemList.get(remove));
		}
	}
}
