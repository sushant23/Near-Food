package com.tr.nearfood.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tr.nearfood.R;
import com.tr.nearfood.fragment.FragmentResturantList.FragmentResturantListCommunicator;
import com.tr.nearfood.model.ResturantDTO;

public class FragmentResturantProfile extends Fragment implements OnClickListener{
	View view;
	Button sendMessage,chooseMenu;
	FragmentResturantProfileCommunicator fragmentResturantProfileCommunicator;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {

			fragmentResturantProfileCommunicator = (FragmentResturantProfileCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentResturantProfileCommunicator");
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_resturant_profile, container,
				false);
		initializeUIElements();
		chooseMenu.setOnClickListener(this);
		
		return view;
	}

	private void initializeUIElements() {
		
		sendMessage=(Button) view.findViewById(R.id.buttonSend);
		chooseMenu=(Button) view.findViewById(R.id.buttonChooseMenu);
	}

	@Override
	public void onClick(View click) {
		// TODO Auto-generated method stub
		switch (click.getId()) {
		case R.id.buttonChooseMenu:
			fragmentResturantProfileCommunicator.setButtonClicked();
		//	Toast.makeText(getActivity(), "Choose mnu clicked", 1000).show();
			break;
		case R.id.buttonSend:
			//Toast.makeText(getActivity(), "Mesessage send button clicked", 1000).show();
			break;

		default:
			break;
		}
	}
	public static interface FragmentResturantProfileCommunicator {
		public void setButtonClicked();
	}

}
