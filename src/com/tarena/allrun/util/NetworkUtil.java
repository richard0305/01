package com.tarena.allrun.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

public class NetworkUtil {
	public static void checkNetwork(final Activity activity) {
		try {
			// 1,判断有没有网络,加权限ACCESS_NETWORK_STATE
			ConnectivityManager manager = (ConnectivityManager) activity
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
			if (activeNetworkInfo == null) {
				// 2,没有网络,显示dialog
				AlertDialog.Builder dialog = new Builder(activity);
				dialog.setMessage("亲，现在没网");
				dialog.setNegativeButton("取消", null);
				dialog.setPositiveButton("打开网络", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						try {
							// 3,打开系统自带的网络管理activity
							// 得到android操作系统的版本号
							// 4.2 17
							// 5.0 22
							// 6.0 23
							int osVersion = Build.VERSION.SDK_INT;
							//得手机厂商，得手机号，得串号
							//不同android版本不同代码
							if (osVersion > 10) {
								// 网络管理是在设置
								Intent intent = new Intent(
										Settings.ACTION_WIRELESS_SETTINGS);
								activity.startActivity(intent);
							}
							dialog.cancel();
						} catch (Exception e) {
							ExceptionUtil.handleException(e);
						}
					}
				});

				dialog.show();

			}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		}
	}
}
