package com.tr.nearfood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class FragmentShowCustomerOrder extends Fragment implements
		OnClickListener {
	View view;
	Button confirmOrderButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.fragment_menu_customer_show_order,
				container, false);
		confirmOrderButton = (Button) view
				.findViewById(R.id.buttonConfirmOrder);
		confirmOrderButton.setOnClickListener(this);
		showCustomerOrder();
		return view;
	}

	public void showCustomerOrder() {
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
			tvx1.setText((i + 1) + ". " + "Order Name" + i);
			tvx1.setPadding(0, 0, 4, 3);
			cellx1.addView(tvx1);
			tr.addView(cellx1);
			// add as many cells you want to a row, using the same approach

			LinearLayout cellx2 = new LinearLayout(getActivity());
			// cellx2.setBackgroundColor(R.color.dark_white);
			cellx2.setLayoutParams(llp1);// 2px border on the right for the cell
			TextView tvx2 = new TextView(getActivity());
			tvx2.setText("Quantity" + i);
			tvx2.setPadding(0, 0, 4, 3);
			cellx2.addView(tvx2);
			tr.addView(cellx2);

			LinearLayout cellx3 = new LinearLayout(getActivity());
			// cellx3.setBackgroundColor(R.color.light_white);
			cellx3.setLayoutParams(llp1);// 2px border on the right for the cell
			TextView tvx3 = new TextView(getActivity());
			tvx3.setText("Price" + i);
			tvx3.setPadding(0, 0, 4, 3);
			cellx3.addView(tvx3);
			tr.addView(cellx3);

			table.addView(tr);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.buttonConfirmOrder:
			Toast.makeText(getActivity(),
					"Your Order is Confirmed. Thankyou!!! ", Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	}
}
