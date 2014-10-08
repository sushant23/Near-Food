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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tr.nearfood.R;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.utills.AppConstants;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
	String sucess = "";
	DatabaseHelper db;
	private Context _context;
	private String _whoseAdapter;
	private String _AUTH;
	private String _Type_Order_Or_Reserve;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	String sendingJson = "";
	LinearLayout linlayout;
	TextView status_tv;
	ImageButton confirmOrder;
	String watToDo;
	String message_to_customer = "";
	String chooseRespondMethod;
	String headName;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData, String whoseAdapter,
			String auth, String type_Order_Or_Reserve) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		this._whoseAdapter = whoseAdapter;
		this._AUTH = auth;
		this._Type_Order_Or_Reserve = type_Order_Or_Reserve;
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
		if (_Type_Order_Or_Reserve.equals("RESERVE")) {
			txtChildPrice.setVisibility(View.GONE);

			int no_of_people = db.getNoOfPeople(headName, childText);
			txtListChild.setText("Message: " + childText + "\n"
					+ "No. Of People: " + Integer.toString(no_of_people));
		} else {
			txtListChild.setText(childText);
			txtChildPrice.setText(Integer.toString(price));
		}

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
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final String headerTitle = (String) getGroup(groupPosition);
		headName = headerTitle;
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
		if (_whoseAdapter.equals("PENDING")) {
			linlayout = (LinearLayout) convertView
					.findViewById(R.id.linlayouteditparameters);
			linlayout.setVisibility(View.VISIBLE);
			confirmOrder = (ImageButton) convertView
					.findViewById(R.id.imageButtonCompleteOrder);
			final ImageButton rejectedOrder = (ImageButton) convertView
					.findViewById(R.id.imageButtonDeleteIcon);
			final ImageButton sendMessage = (ImageButton) convertView
					.findViewById(R.id.imageButtonMessageIcon);
			status_tv = (TextView) convertView
					.findViewById(R.id.textViewStatus);
			confirmOrder.setFocusable(false);
			rejectedOrder.setFocusable(false);
			sendMessage.setFocusable(false);

			sendMessage.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent me) {
					// TODO Auto-generated method stub
					if (me.getAction() == MotionEvent.ACTION_DOWN) {
						sendMessage.setColorFilter(Color.argb(150, 155, 155,
								155));
						final AlertDialog.Builder alert = new AlertDialog.Builder(
								_context);
						final EditText input = new EditText(_context);
						alert.setView(input);
						alert.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										message_to_customer = input.getText()
												.toString().trim();
										Toast.makeText(_context,
												message_to_customer,
												Toast.LENGTH_SHORT).show();
									}
								});

						alert.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										dialog.cancel();
									}
								});
						alert.show();

						return true;
					} else if (me.getAction() == MotionEvent.ACTION_UP) {
						sendMessage.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																					// null
						return true;
					}
					return false;
				}
			});
			confirmOrder.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent me) {
					// TODO Auto-generated method stub
					chooseRespondMethod = "PENDING";
					if (me.getAction() == MotionEvent.ACTION_DOWN) {
						confirmOrder.setColorFilter(Color.argb(150, 155, 155,
								155));
						try {
							if (_Type_Order_Or_Reserve.equals("ORDER")) {
								sendingJson = db.getCustomerJsonDetails(
										headerTitle, "customer");
							} else if (_Type_Order_Or_Reserve.equals("RESERVE")) {
								sendingJson = db.getCustomerJsonDetails(
										headerTitle, "reservetable");
							}
							Log.d("JSon COnfirmed", sendingJson);
						} catch (CursorIndexOutOfBoundsException e) {
							Log.d("exception",
									"cursor index out of bounds exception");

						}
						new confirmCustomerOrder().execute();

						return true;
					} else if (me.getAction() == MotionEvent.ACTION_UP) {
						confirmOrder.setColorFilter(Color
								.argb(0, 155, 155, 155)); // or
															// null
						return true;
					}
					return false;
				}
			});
			rejectedOrder.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent me) {
					// TODO Auto-generated method stub
					chooseRespondMethod = "REJECTING";

					if (me.getAction() == MotionEvent.ACTION_DOWN) {
						rejectedOrder.setColorFilter(Color.argb(150, 155, 155,
								155));
						try {
							if (_Type_Order_Or_Reserve.equals("ORDER")) {
								sendingJson = db.getCustomerJsonDetails(
										headerTitle, "customer");
							} else if (_Type_Order_Or_Reserve.equals("RESERVE")) {
								sendingJson = db.getCustomerJsonDetails(
										headerTitle, "reservetable");
							}
							Log.d("JSon COnfirmed", sendingJson);
						} catch (CursorIndexOutOfBoundsException e) {
							Log.d("exception",
									"cursor index out of bounds exception");

						}
						new confirmCustomerOrder().execute();

						return true;
					} else if (me.getAction() == MotionEvent.ACTION_UP) {
						rejectedOrder.setColorFilter(Color.argb(0, 155, 155,
								155)); // or
										// null
						return true;
					}
					return false;
				}
			});

		} else if (_whoseAdapter.equals("CONFIRMED")) {
			linlayout = (LinearLayout) convertView
					.findViewById(R.id.linlayouteditparameters);
			linlayout.setVisibility(View.GONE);
		} else if (_whoseAdapter.equals("REJECTED")) {
			linlayout = (LinearLayout) convertView
					.findViewById(R.id.linlayouteditparameters);
			linlayout.setVisibility(View.VISIBLE);
			confirmOrder = (ImageButton) convertView
					.findViewById(R.id.imageButtonCompleteOrder);
			final ImageButton rejectedOrder = (ImageButton) convertView
					.findViewById(R.id.imageButtonDeleteIcon);
			final ImageButton sendMessage = (ImageButton) convertView
					.findViewById(R.id.imageButtonMessageIcon);
			rejectedOrder.setVisibility(View.GONE);
			// sendMessage.setVisibility(View.GONE);

			confirmOrder.setFocusable(false);
			rejectedOrder.setFocusable(false);
			sendMessage.setFocusable(false);
			sendMessage.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent me) {
					// TODO Auto-generated method stub
					if (me.getAction() == MotionEvent.ACTION_DOWN) {
						sendMessage.setColorFilter(Color.argb(150, 155, 155,
								155));
						final AlertDialog.Builder alert = new AlertDialog.Builder(
								_context);
						final EditText input = new EditText(_context);
						alert.setView(input);
						alert.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										message_to_customer = input.getText()
												.toString().trim();
										Toast.makeText(_context,
												message_to_customer,
												Toast.LENGTH_SHORT).show();
									}
								});

						alert.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										dialog.cancel();
									}
								});
						alert.show();

						return true;
					} else if (me.getAction() == MotionEvent.ACTION_UP) {
						sendMessage.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																					// null
						return true;
					}
					return false;
				}
			});
			confirmOrder.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent me) {
					// TODO Auto-generated method stub
					watToDo = "CONFIRMED";
					chooseRespondMethod = "REJECTED";
					if (me.getAction() == MotionEvent.ACTION_DOWN) {
						confirmOrder.setColorFilter(Color.argb(150, 155, 155,
								155));
						try {
							if (_Type_Order_Or_Reserve.equals("ORDER")) {
								sendingJson = db.getCustomerJsonDetails(
										headerTitle, "customer");
							} else if (_Type_Order_Or_Reserve.equals("RESERVE")) {

								sendingJson = db.getCustomerJsonDetails(
										headerTitle, "reservetable");
							}
							Log.d("JSon COnfirmed", sendingJson);
						} catch (CursorIndexOutOfBoundsException e) {
							Log.d("exception", e.toString());

						}
						new confirmCustomerOrder().execute();

						return true;
					} else if (me.getAction() == MotionEvent.ACTION_UP) {
						confirmOrder.setColorFilter(Color
								.argb(0, 155, 155, 155)); // or
															// null
						return true;
					}
					return false;
				}
			});
		}

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
				pd.setMessage("Processing...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			String json = "";
			if (chooseRespondMethod.equals("PENDING")) {

				if (_Type_Order_Or_Reserve.equals("ORDER"))
					json = httpPostResponse(AppConstants.RESTAURANTS_ORDER_CONFIRMING);
				else if (_Type_Order_Or_Reserve.equals("RESERVE"))
					json = httpPostResponse(AppConstants.RESTAURANT_CHANGE_RESERVATION_STAUTS
							+ "CONFIRMED");

			} else if (chooseRespondMethod.equals("REJECTED")) {

				if (_Type_Order_Or_Reserve.equals("ORDER"))
					json = httpPostResponse(AppConstants.RESTAURANTS_CONFIRM_REJECTED_ORDER);
				else if (_Type_Order_Or_Reserve.equals("RESERVE"))
					json = httpPostResponse(AppConstants.RESTAURANT_CHANGE_RESERVATION_STAUTS
							+ "CONFIRMED");

			} else if (chooseRespondMethod.equals("REJECTING")) {

				if (_Type_Order_Or_Reserve.equals("ORDER"))
					json = httpPostResponse(AppConstants.RESTAURANTS_ORDER_REJECTING);
				else if (_Type_Order_Or_Reserve.equals("RESERVE"))
					json = httpPostResponse(AppConstants.RESTAURANT_CHANGE_RESERVATION_STAUTS
							+ "CANCELLED");
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
				Log.d("Response ", result);
				try {
					JSONObject login_status = new JSONObject(result);
					sucess = login_status.getString("status");
					String message = login_status.getString("message");
					if (sucess.equals("success")) {
						Toast.makeText(_context, message, Toast.LENGTH_SHORT)
								.show();
						Log.d("CONFIRMED", "image button gone text on");
						confirmOrder.setFocusable(true);
						/*
						 * status_tv.setVisibility(View.VISIBLE);
						 * status_tv.setText("CONFIRMED");
						 */
					} else if (sucess.equals("error")) {
						Toast.makeText(_context, message, Toast.LENGTH_SHORT)
								.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		public String httpPostResponse(String url) {
			Log.d("HTTPPOST", "Makilng http connection URL=" + url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			if (chooseRespondMethod.equals("PENDING")
					|| chooseRespondMethod.equals("REJECTING")) {
				httppost.setHeader("Authorization", "Basic " + _AUTH);
				nameValuePairs.add(new BasicNameValuePair("json_string",
						sendingJson));
				nameValuePairs.add(new BasicNameValuePair("message",
						message_to_customer));
				Log.d("requesting json", nameValuePairs.toString());
			} else if (chooseRespondMethod.equals("REJECTED")) {
				httppost.setHeader("Authorization", "Basic " + _AUTH);
				// Log.d("REJECTED ORDER",
				// FragmentRestaurantOrderRejected.AUTH);
				Log.d("iofsfsdfsfsd", sendingJson);

				try {
					if (_Type_Order_Or_Reserve.equals("ORDER")) {
						JSONObject jObject = new JSONObject(sendingJson);
						int id = jObject.getInt("id");
						nameValuePairs.add(new BasicNameValuePair("order_id",
								String.valueOf(id)));
						nameValuePairs.add(new BasicNameValuePair("status",
								watToDo));
						nameValuePairs.add(new BasicNameValuePair("message",
								message_to_customer));
					}
					if (_Type_Order_Or_Reserve.equals("RESERVE")) {
						nameValuePairs.add(new BasicNameValuePair(
								"json_string", sendingJson));
						nameValuePairs.add(new BasicNameValuePair("message",
								message_to_customer));
					}
					/*
					 * nameValuePairs.add(new BasicNameValuePair("message",
					 * message_to_customer));
					 */
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			httppost.setHeader("api", AppConstants.API);
			// Log.d("Authentication",
			// FragmentsRestaurantAdminManageOrder.AUTH);
			try {

				Log.d("PARAMS", nameValuePairs.toString());
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
