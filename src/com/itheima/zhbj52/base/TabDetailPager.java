package com.itheima.zhbj52.base;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima.zhbj52.domain.NewsData.NewsMenuData.NewsTabData;

public class TabDetailPager extends BaseMenuDetailPager {

	private NewsTabData mNewsTabData;
	private TextView tvText;

	public TabDetailPager(Activity mActivity, NewsTabData newsTabData) {
		super(mActivity);
		mNewsTabData = newsTabData;
	}

	@Override
	public View initView() {
		tvText = new TextView(mActivity);
		tvText.setTextColor(Color.RED);
		tvText.setTextSize(25);
		tvText.setGravity(Gravity.CENTER);
		return tvText;
	}

	@Override
	public void initData() {
		tvText.setText(mNewsTabData.title);
	}

}
