package com.itheima.zhbj52.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima.zhbj52.base.BaseMenuDetailPager;

/**
 * 菜单详情页-互动
 * 
 * @author baoliang.zhao
 * 
 */
public class InteractMenuDetailPager extends BaseMenuDetailPager {

	public InteractMenuDetailPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	public View initView() {
		TextView text = new TextView(mActivity);
		text.setText("菜单详情页-互动");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		return text;
	}

}
