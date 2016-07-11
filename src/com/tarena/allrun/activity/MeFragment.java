package com.tarena.allrun.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tarena.allrun.R;
import com.tarena.allrun.TApplication;

/**
 *Created by yanglijun 2016-6-30ÉÏÎç11:26:02
 */
public class MeFragment extends Fragment{
	View view;
	Button btnExit;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view=inflater.inflate(R.layout.fragment_me, null);
		setViews();
		addListener();
		return view;
	}
	private void setViews( ) {
		btnExit=(Button) view.findViewById(R.id.btn_me_exit);
		
	}
	private void addListener() {
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TApplication tApplication=(TApplication) getActivity().getApplication();
				tApplication.finishActivity();
				
			}
		});
		
	}
}
