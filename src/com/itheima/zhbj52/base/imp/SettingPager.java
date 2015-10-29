package com.itheima.zhbj52.base.imp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima.zhbj52.base.BasePager;

/**
 * 首页实现
 * 
 * @author baoliang.zhao
 * 
 */
public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		tvTitle.setText("智慧北京");
		TextView text = new TextView(mActivity);
		text.setText("设置");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		btnMenu.setVisibility(View.GONE);// 隐藏菜单栏
		enableSlidingMenu(false);
		// 向FrameLayout中动态添加布局
		subContent.addView(text);
	}

}
