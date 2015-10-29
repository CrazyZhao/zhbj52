package com.itheima.zhbj52.base.imp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.itheima.zhbj52.base.BasePager;

/**
 * 首页实现
 * 
 * @author baoliang.zhao
 * 
 */
public class SmartServicePager extends BasePager {

	public SmartServicePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		tvTitle.setText("智慧北京");
		TextView text = new TextView(mActivity);
		text.setText("智慧服务");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		enableSlidingMenu(true);
		// 向FrameLayout中动态添加布局
		subContent.addView(text);
	}

}
