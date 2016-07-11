package com.tarena.allrun.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.KeyEvent.DispatcherState;

/**
 *Created by yanglijun 2016-7-5下午2:41:58
 */
public class DisplayUtil {
	public static int dp2px(Context context,float dp){
		Resources resources=context.getResources();
		//Metrics度量
		DisplayMetrics displayMetrics=resources.getDisplayMetrics();
		//scaled 绽放
		//Density 密度
		//mdpi:1 
		//hdpi:1.5
		//xhdpi:2
		//xxhdpi:3
		
		float desity=displayMetrics.scaledDensity;
		//4.5+0.5=5.0-->5
		int px=(int) (desity*dp+0.5);
		return px;
		
	}
}
