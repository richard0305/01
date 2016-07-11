package com.tarena.allrun.receiver;

import com.tarena.allrun.TApplication;
import com.tarena.allrun.util.ExceptionUtil;
import com.tarena.allrun.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStateChangeReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			ConnectivityManager manage=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo=manage.getActiveNetworkInfo();
		if(activeNetworkInfo==null){
			LogUtil.i("������", "�û�  ������");
			TApplication.networkIsNone=true;
		}else{
			// �ж��û��򿪵����ƶ����绹��wifi
			// ��Ӱ
			// ����100MB 1MB��1��Ǯ
			// 4G 1��100MB 1�� 24*60*60*100*1=
			// ���ƶ������£���������ʾ���������ƶ����磬���ػ�������á�
			NetworkInfo mobileNetwork=manage.getNetworkInfo(manage.TYPE_MOBILE);
			if(mobileNetwork!=null&&mobileNetwork.isConnected()){
				LogUtil.i("������", "�û��򿪵����ƶ�����");
				TApplication.networkIsMobile=true;
			}
			NetworkInfo wifiNetworkInfo=manage.getNetworkInfo(manage.TYPE_WIFI);
			if(wifiNetworkInfo!=null&&wifiNetworkInfo.isConnected()){
				LogUtil.i("������", "�û��򿪵���wifi����");
				TApplication.networkIsWifi=true;
			}
			
			
			
		}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
		
	}

}
