package com.tarena.allrun.util;

import com.tarena.allrun.TApplication;

import android.util.Log;

//��־������
public class LogUtil {
	public static void i(String tag, Object msg) {
		if (TApplication.ISRELEASE) {
			// �������û��������
			return;
		}
     //������
		Log.i(tag, String.valueOf(msg));
	}
	
//	public static void i(String tag,int msg)
//	{
//		i(tag,String.valueOf(msg));
//	}
}
