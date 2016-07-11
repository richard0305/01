package com.tarena.allrun;

import java.util.ArrayList;

import com.baidu.mapapi.SDKInitializer;
import com.tarena.allrun.util.CrashHandler;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

/**
 * Created by yanglijun 2016-6-30上午9:34:01
 */
public class TApplication extends Application {
	//第二种：全局变量
	public static boolean networkIsNone=false;
	public static boolean networkIsMobile=false;
	public static boolean networkIsWifi=false;
	
	//release:发布了，程序运行在用户设备上
	//false:开发
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
		// 初始化地图
		SDKInitializer.initialize(this);

		// CrashHandler carshHandler=new CrashHandler(this);
		// //除了异常没有加catch，框架吊UncaughtExceptionHandler
		// Thread.setDefaultUncaughtExceptionHandler(carshHandler);

	}
}
