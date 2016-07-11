package com.tarena.allrun.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.tarena.allrun.TApplication;

import android.util.Log;

public class ExceptionUtil {
	public static void handleException(Throwable ex) {
		// 判断是运行在真机上还是在开发中
		if (TApplication.ISRELEASE) {
			// 收集异常信息
			String info = ex.getMessage();
			Log.i("uncaughtException", "info=" + info);

			StringWriter stringWriter = new StringWriter();

			PrintWriter printWriter = new PrintWriter(stringWriter);
			// 详细异常信息打印到printWriter
			// printWriter又把信息打到StringWirter
			// 通过stringWriter转成string
			ex.printStackTrace(printWriter);
			info = stringWriter.toString();
			Log.i("uncaughtException", "info=" + info);

			// 联网发送给服务器，
			// 开发人员通过服务器随时看异常信息
		} else {
			ex.printStackTrace();
		}
	}
}
