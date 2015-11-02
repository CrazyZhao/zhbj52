package com.itheima.zhbj52.activity;

import com.itheima.zhbj52.R;
import com.itheima.zhbj52.fragment.ContentFragment;
import com.itheima.zhbj52.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

public class MainActivity extends SlidingFragmentActivity {

	private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
	private static final String FRAGMENT_CONTENT = "fragment_content";
	private FragmentManager fragmentManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		setBehindContentView(R.layout.left_menu); // 设置侧边栏布局
		SlidingMenu slidingMenu = getSlidingMenu();// 获取侧边栏对象
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// slidingMenu.setBehindOffset(200);// 设置预留屏幕的宽度
		// slidingMenu.setBehindWidth(400);//设置SlidingMenu菜单的宽度
		slidingMenu.setBehindOffset(getWindowManager().getDefaultDisplay()
				.getWidth() * 2 / 3);// 设置屏幕除去菜单剩余的宽度

		initFragment();

	}

	/**
	 * 初始化Fragment，将fragment数据填充给布局文件
	 */
	private void initFragment() {
		fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),
				FRAGMENT_LEFT_MENU);
		transaction.replace(R.id.fl_content, new ContentFragment(),
				FRAGMENT_CONTENT);
		transaction.commit();
	}

	/*
	 * 获取侧边栏
	 */
	public LeftMenuFragment getLeftMenuFragment() {
		LeftMenuFragment fragment = (LeftMenuFragment) fragmentManager
				.findFragmentByTag(FRAGMENT_LEFT_MENU);
		return fragment;
	}

	/**
	 * 获取内容fragment
	 */
	public ContentFragment getContentFragment() {
		ContentFragment fragment = (ContentFragment) fragmentManager
				.findFragmentByTag(FRAGMENT_CONTENT);
		return fragment;
	}
}
