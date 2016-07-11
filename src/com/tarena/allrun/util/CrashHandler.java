package com.tarena.allrun.util;

import java.lang.Thread.UncaughtExceptionHandler;

import com.tarena.allrun.MainActivity;
import com.tarena.allrun.TApplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler {
	TApplication tApplication;

	public CrashHandler(TApplication tApplication) {
		this.tApplication = tApplication;
	}

	// �����κεط��������쳣��û��catch,
	// �ͻ�ִ��uncaughtException
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		ExceptionUtil.handleException(ex);

		// toast�����û�����
		new Thread() {
			public void run() {
				Looper.prepare();
				Toast.makeText(tApplication, "��Ǹ����������", Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			};
		}.start();

		Log.i("uncaughtException", "toastִ������");
		try {
			Thread.currentThread().sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// ʵ����������
//0x10000000
		Intent intent = new Intent(tApplication, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(tApplication,
				100, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

		// ��ʱ��
		AlarmManager manager = (AlarmManager) tApplication
				.getSystemService(Context.ALARM_SERVICE);

		manager.set(AlarmManager.RTC, System.currentTimeMillis() + 2000,
				pendingIntent);
		tApplication.finishActivity();

	}

	

}
