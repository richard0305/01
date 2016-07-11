package com.tarena.allrun.activity;

import java.util.ArrayList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tarena.allrun.BaseActivity;
import com.tarena.allrun.R;
import com.tarena.allrun.TApplication;

/**
 * Created by yanglijun 2016-6-30����11:32:32
 */
public class MainFragmentActivity extends BaseActivity {
	SportFragment sportFragment;
	ArrayList<Fragment> fragmentList = new ArrayList();
	ArrayList<Button> btnList = new ArrayList();
	int currentFragment = 0;
	int clickBtn;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		int orientation=newConfig.orientation;
		Log.i("yanglijun", "orientation------>>>>>>>>>>>"+orientation);
		super.onConfigurationChanged(newConfig);
	}
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		try {
			this.setContentView(R.layout.main_fragment);
			// ��ʾ��һ��Fragment
			sportFragment = new SportFragment();
			FragmentManager manager = getSupportFragmentManager();

			// Transaction ����
			FragmentTransaction transaction = manager.beginTransaction();

			// ��sportFragment��ʾ��LinearLAyout��
			transaction.add(R.id.fragment_container, sportFragment);
			transaction.show(sportFragment);

			// �ύִ��add,show
			transaction.commit();

			DiscoverFragment discoverFragment = new DiscoverFragment();
			MeFragment meFragment = new MeFragment();
			fragmentList.add(sportFragment);
			fragmentList.add(discoverFragment);
			fragmentList.add(meFragment);

			Button sportBtn = (Button) findViewById(R.id.btn_main_fragment_sport);
			Button discoverBtn = (Button) findViewById(R.id.btn_main_fragment_discover);
			Button meBtn = (Button) findViewById(R.id.btn_main_fragment_me);
			btnList.add(sportBtn);

			btnList.add(discoverBtn);
			btnList.add(meBtn);
			
			btnList.get(currentFragment).setSelected(true);
			
			for (Button btn : btnList) {
				btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// �жϵ��������ĸ�button
						switch (v.getId()) {
						case R.id.btn_main_fragment_sport:
							clickBtn = 0;

							break;
						case R.id.btn_main_fragment_discover:
							clickBtn = 1;

							break;
						case R.id.btn_main_fragment_me:
							clickBtn = 2;
							
							break;

						}
						// �ж�Ҫ��Ҫ��ʾ���Fragment
						if (clickBtn != currentFragment) {
							Fragment fragment = fragmentList.get(clickBtn);
							FragmentTransaction transaction = getSupportFragmentManager()
									.beginTransaction();
							// �ж�fragment�Ƿ���ӹ���
							if (!fragment.isAdded()) {
								transaction.add(R.id.fragment_container,
										fragment);
							}
							// ������ǰ��fragment,��ʾ�µ�fragment
							transaction.hide(fragmentList.get(currentFragment));
							transaction.show(fragment);
							transaction.commit();
							// currentFragmentֵҪ��
							btnList.get(currentFragment).setSelected(false);
							btnList.get(clickBtn).setSelected(true);
							currentFragment = clickBtn;
						}
					}
				});
			}

		} catch (Exception e) {
		}

	}
}
