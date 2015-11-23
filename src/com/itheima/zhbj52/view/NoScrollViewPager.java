package com.itheima.zhbj52.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义禁止滑动的ViewPager
 * 
 * @author baoliang.zhao
 * com.itheima.zhbj52.view.NoScrollViewPager
 */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollViewPager(Context context) {
		super(context);
	}
	
	//表示事件是否拦截，返回false表示不拦截，可以让嵌套在内部的viewpager响应左右滑的事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}
	//重写该方法，返回false表示什么都不用做
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}

}
