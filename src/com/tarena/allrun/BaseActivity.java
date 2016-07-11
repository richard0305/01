package com.tarena.allrun;

import android.os.Bundle;

import com.tarena.allrun.util.NetworkUtil;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		try {
			TApplication tApplication=(TApplication) getApplication();
			tApplication.activitylist.add(this);
			NetworkUtil.checkNetwork(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			TApplication tApplication=(TApplication) getApplication();
			tApplication.activitylist.remove(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
