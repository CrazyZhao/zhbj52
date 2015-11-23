package com.itheima.zhbj52.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalViewPager extends ViewPager {

	public HorizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizontalViewPager(Context context) {
		super(context);
	}
	/**
	 * 事件分发，请求父控件及祖宗控件是否拦截事件
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(getCurrentItem()!=0){
			getParent().requestDisallowInterceptTouchEvent(true); //禁止拦截
		}else {
			getParent().requestDisallowInterceptTouchEvent(false);
		}		
		return super.dispatchTouchEvent(ev);
	}
}
