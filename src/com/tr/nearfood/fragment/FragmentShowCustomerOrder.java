package com.tr.nearfood.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.tr.nearfood.R;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.fragment.FragmentRestaurantMenu.FragmentResturantMenuListCommunicator;
import com.tr.nearfood.model.MigratingDatas;
import com.tr.nearfood.utills.AppConstants;

public class FragmentShowCustomerOrder extends Fragment implements
		OnClickListener {
	View view;
	Button confirmOrderButton;
	DatabaseHelper db;
	FragmentShowCustomerOrderCommunicator fragmentShowCustomerOrderCommunicator;
	public static List<Integer> SELECTED_MENU_ITEM_LIST;
	int restaurant_id;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {

			fragmentShowCustomerOrderCommunicator = (FragmentShowCustomerOrderCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentShowCustomerOrderCommunicator");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.fragment_menu_customer_show_order,
				container, false);
		db = new DatabaseHelper(getActivity());
		confirmOrderButton = (Button) view
				.findViewById(R.id.buttonConfirmOrder);
		confirmOrderButton.setOnClickListener(this);
		showCustomerOrder();
		return view;
	}

	public void showCustomerOrder() {
		TableLayout table = (TableLayout) view
				.findViewById(R.id.TableLayoutShowOrder);
		try {
			if (SELECTED_MENU_ITEM_LIST != null) {
				restaurant_id = db.getRestaurantId(SELECTED_MENU_ITEM_LIST
						.get(0));
				for (int i = 0; i < SELECTED_MENU_ITEM_LIST.size(); i++) {
					TableRow tr = new TableRow(getActivity());
					// tr.setBackgroundColor(R.color.light_white);
					tr.setPadding(0, 0, 0, 2); // Border between rows

					TableRow.LayoutParams llp1 = new TableRow.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					llp1.setMargins(0, 0, 2, 0);// 2px right-margin

					// New Cell
					LinearLayout cellx1 = new LinearLayout(getActivity());
					// cellx1.setBackgroundColor(R.color.dark_white);
					cellx1.setLayoutParams(llp1);// 2px border on the right for
													// the
													// cell
					TextView tvx1 = new TextView(getActivity());
					String itemname = db.getItemName(SELECTED_MENU_ITEM_LIST
							.get(i));
					tvx1.setText((i + 1) + ". " + itemname);
					tvx1.setPadding(0, 0, 4, 3);
					cellx1.addView(tvx1);
					tr.addView(cellx1);
					// add as many cells you want to a row, using the same
					// approach

					LinearLayout cellx2 = new LinearLayout(getActivity());
					// cellx2.setBackgroundColor(R.color.dark_white);
					cellx2.setLayoutParams(llp1);// 2px border on the right for
													// the
													// cell
					TextView tvx2 = new TextView(getActivity());
					tvx2.setText("  ");
					tvx2.setPadding(0, 0, 4, 3);
					cellx2.addView(tvx2);
					tr.addView(cellx2);

					LinearLayout cellx3 = new LinearLayout(getActivity());
					// cellx3.setBackgroundColor(R.color.light_white);
					cellx3.setLayoutParams(llp1);// 2px border on the right for
													// the
													// cell
					TextView tvx3 = new TextView(getActivity());
					int price = db.getItemPrice(SELECTED_MENU_ITEM_LIST.get(i),"item");
					tvx3.setText(Integer.toString(price));
					tvx3.setPadding(0, 0, 4, 3);
					cellx3.addView(tvx3);
					tr.addView(cellx3);

					table.addView(tr);
				}
			} else {
				TextView tvx4 = new TextView(getActivity());

				tvx4.setText("NO ITEM IS SELECTED");
			}
		} catch (IndexOutOfBoundsException e) {
			Toast.makeText(getActivity(), "No Item Selected",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.buttonConfirmOrder:
			try {

				Log.d("size of app constant at show order fragment",
						Integer.toString(SELECTED_MENU_ITEM_LIST.size()));
				MigratingDatas migratingDatasDto = new MigratingDatas();
				migratingDatasDto
						.setConfirmedOrderList(SELECTED_MENU_ITEM_LIST);
				migratingDatasDto.setRestaurant_id(restaurant_id);
				Toast.makeText(getActivity(),
						"Your Order is Confirmed. Thankyou!!! ",
						Toast.LENGTH_SHORT).show();
				fragmentShowCustomerOrderCommunicator
						.setConfirmButtonClicked(migratingDatasDto);
			} catch (NullPointerException e) {
				Toast.makeText(getActivity(), "No Item is Selected",
						Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	public static interface FragmentShowCustomerOrderCommunicator {
		public void setConfirmButtonClicked(MigratingDatas migratingDatasDto);

	}
}
