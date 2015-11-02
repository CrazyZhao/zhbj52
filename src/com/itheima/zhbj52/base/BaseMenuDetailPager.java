package com.itheima.zhbj52.base;

import android.app.Activity;
import android.view.View;

/**
 * 菜单详情页基类
 * 
 * @author baoliang.zhao
 * 
 */
public abstract class BaseMenuDetailPager {

	public Activity mActivity;
	public View mRootView;// 根布局对象

	public BaseMenuDetailPager(Activity mActivity) {
		super();
		this.mActivity = mActivity;
		mRootView = initView();
	}

	// 初始化布局
	public abstract View initView();

	// 初始化数据
	public void initData() {

	}
}
