package com.tarena.allrun;

import java.util.ArrayList;

import com.baidu.mapapi.SDKInitializer;
import com.tarena.allrun.util.CrashHandler;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

/**
 * Created by yanglijun 2016-6-30����9:34:01
 */
public class TApplication extends Application {
	//�ڶ��֣�ȫ�ֱ���
	public static boolean networkIsNone=false;
	public static boolean networkIsMobile=false;
	public static boolean networkIsWifi=false;
	
	//release:�����ˣ������������û��豸��
	//false:����
	public final static boolean ISRELEASE=false;
	public ArrayList<Activity> activitylist = new ArrayList<Activity>();

	public void finishActivity() {
		for (Activity activity : activitylist) {
			try {
				activity.finish();
			} catch (Exception e) {
			}
		}
		Process.killProcess(Process.myPid());
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// ��ʼ����ͼ
		SDKInitializer.initialize(this);

		// CrashHandler carshHandler=new CrashHandler(this);
		// //�����쳣û�м�catch����ܵ�UncaughtExceptionHandler
		// Thread.setDefaultUncaughtExceptionHandler(carshHandler);

	}
}
