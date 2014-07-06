package com.tr.nearfood.fragment;

import com.tr.nearfood.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_menu_customer_show_order, container,false);
		initializeUIElements();
		showNotification();
		return view;
	}
	private void showNotification() {
		// TODO Auto-generated method stub
		TableLayout table = (TableLayout) view
				.findViewById(R.id.TableLayoutShowOrder);

		for (int i = 0; i < 10; i++) {
			TableRow tr = new TableRow(getActivity());
			// tr.setBackgroundColor(R.color.light_white);
			tr.setPadding(0, 0, 0, 2); // Border between rows

			TableRow.LayoutParams llp1 = new TableRow.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			llp1.setMargins(0, 0, 2, 0);// 2px right-margin

			
			// New Cell
			LinearLayout cellx1 = new LinearLayout(getActivity());
			// cellx1.setBackgroundColor(R.color.dark_white);
			cellx1.setLayoutParams(llp1);// 2px border on the right for the cell
			TextView tvx1 = new TextView(getActivity());
			tvx1.setText(i+". "+"You have a new message");
			tvx1.setPadding(0, 0, 4, 3);
			cellx1.addView(tvx1);
			tr.addView(cellx1);
			// add as many cells you want to a row, using the same approach

			
		}
	}
	private void initializeUIElements() {
		// TODO Auto-generated method stub
		confirm=(Button) view.findViewById(R.id.buttonConfirmOrder);
		confirm.setText("OK");
		
		notification=(TextView) view.findViewById(R.id.textViewOrderDetails);
		notification.setText("NOTIFICATIONS");
		
	}
}
