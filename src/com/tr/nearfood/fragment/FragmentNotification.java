package com.tr.nearfood.fragment;

import java.util.ArrayList;
import java.util.List;

import com.tr.nearfood.R;
import com.tr.nearfood.dbhelper.DatabaseHelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class FragmentNotification extends Fragment {

	TextView notification;
	Button confirm;
	View view;
	DatabaseHelper db;
	List<String> messages=new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_menu_customer_show_order,
				container, false);
		db = new DatabaseHelper(getActivity());
		initializeUIElements();
		showNotification();
		return view;
	}

	private void showNotification() {
		// TODO Auto-generated method stub
		TableLayout table = (TableLayout) view
				.findViewById(R.id.TableLayoutShowOrder);
		messages=db.getallNotifications();
		String[] msgArray = new String[messages.size()];
		msgArray = messages.toArray(msgArray);
		Log.d("array list", msgArray.toString());
		for (int i = 0; i < messages.size(); i++) {
			TableRow tr = new TableRow(getActivity());
			// tr.setBackgroundColor(R.color.light_white);
			tr.setPadding(0, 0, 0, 2); // Border between rows
			
			String[] separated = msgArray[i].split("/");
			String first=separated[0];
			String second=separated[1];
			String third = separated[2];
			
			TableRow.LayoutParams llp1 = new TableRow.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp1.setMargins(0, 0, 2, 0);// 2px right-margin

			// New Cell
			LinearLayout cellx1 = new LinearLayout(getActivity());
			// cellx1.setBackgroundColor(R.color.dark_white);
			cellx1.setLayoutParams(llp1);// 2px border on the right for the cell
			TextView tvx1 = new TextView(getActivity());
			tvx1.setText(i+1+"."+first);
			tvx1.setPadding(0, 0, 3, 3);
			cellx1.addView(tvx1);
			tr.addView(cellx1);
			// add as many cells you want to a row, using the same approach

			LinearLayout cellx2 = new LinearLayout(getActivity());
			// cellx2.setBackgroundColor(R.color.dark_white);
			cellx2.setLayoutParams(llp1);// 2px border on the right for the cell
			TextView tvx2 = new TextView(getActivity());
			tvx2.setText(second);
			tvx2.setPadding(0, 0, 3, 3);
			cellx2.addView(tvx2);
			tr.addView(cellx2);

			LinearLayout cellx3 = new LinearLayout(getActivity());
			// cellx3.setBackgroundColor(R.color.light_white);
			cellx3.setLayoutParams(llp1);// 2px border on the right for the cell
			TextView tvx3 = new TextView(getActivity());
			tvx3.setText(third);
			tvx3.setPadding(0, 0, 2, 3);
			cellx3.addView(tvx3);
			tr.addView(cellx3);

			table.addView(tr);
		}
	}

	private void initializeUIElements() {
		// TODO Auto-generated method stub
		confirm = (Button) view.findViewById(R.id.buttonConfirmOrder);
		confirm.setText("OK");

		notification = (TextView) view.findViewById(R.id.textViewOrderDetails);
		notification.setText("NOTIFICATIONS");

	}
}
