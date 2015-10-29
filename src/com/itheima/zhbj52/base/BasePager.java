package com.itheima.zhbj52.base;

import android.app.Activity;
import android.text.method.Touch;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.itheima.zhbj52.R;
import com.itheima.zhbj52.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 主页下五个子页面的基类
 * 
 * @author baoliang.zhao
 * 
 */
public class BasePager {

	public Activity mActivity;
	public View mRootView; // 布局对象

	public TextView tvTitle; // 标题对象

	public FrameLayout subContent; // 内容

	public ImageButton btnMenu; // 菜单按钮

	public BasePager(Activity activity) {
		mActivity = activity;
		initViews();
	}

	/**
	 * 初始化布局
	 */
	public void initViews() {
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);

		tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
		subContent = (FrameLayout) mRootView.findViewById(R.id.sub_content);
		btnMenu = (ImageButton) mRootView.findViewById(R.id.btn_menu);

	}

	/**
	 * 初始化数据
	 */
	public void initData() {

	}

	/**
	 * 是否能划出侧边栏
	 */
	public void enableSlidingMenu(boolean enable) {

		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

	}
}
