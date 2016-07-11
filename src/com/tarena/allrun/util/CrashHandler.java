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

	// 程序任何地方，出了异常，没加catch,
	// 就会执行uncaughtException
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		ExceptionUtil.handleException(ex);

		// toast告诉用户重启
		new Thread() {
			public void run() {
				Looper.prepare();
				Toast.makeText(tApplication, "抱歉，程序将重启", Toast.LENGTH_LONG)
						.show();
				Looper.loop();
			};
		}.start();

		Log.i("uncaughtException", "toast执行完了");
		try {
			Thread.currentThread().sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 实现重启功能
//0x10000000
		Intent intent = new Intent(tApplication, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(tApplication,
				100, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

		// 定时器
		AlarmManager manager = (AlarmManager) tApplication
				.getSystemService(Context.ALARM_SERVICE);

		manager.set(AlarmManager.RTC, System.currentTimeMillis() + 2000,
				pendingIntent);
		tApplication.finishActivity();

	}

	

}
