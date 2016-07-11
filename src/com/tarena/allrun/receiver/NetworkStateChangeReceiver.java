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
			LogUtil.i("官网打开", "用户  关网了");
			TApplication.networkIsNone=true;
		}else{
			// 判断用户打开的是移动网络还是wifi
			// 电影
			// 包月100MB 1MB是1块钱
			// 4G 1秒100MB 1天 24*60*60*100*1=
			// 在移动网络下，下载有提示“现在是移动网络，下载会产生费用”
			NetworkInfo mobileNetwork=manage.getNetworkInfo(manage.TYPE_MOBILE);
			if(mobileNetwork!=null&&mobileNetwork.isConnected()){
				LogUtil.i("关网打开", "用户打开的是移动网络");
				TApplication.networkIsMobile=true;
			}
			NetworkInfo wifiNetworkInfo=manage.getNetworkInfo(manage.TYPE_WIFI);
			if(wifiNetworkInfo!=null&&wifiNetworkInfo.isConnected()){
				LogUtil.i("关网打开", "用户打开的是wifi网络");
				TApplication.networkIsWifi=true;
			}
			
			
			
		}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
		
	}

}
