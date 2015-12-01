package com.itheima.zhbj52.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 下拉刷新的ListView
 * @author baoliang.zhao
 *
 */
public class RefreshListView extends ListView {

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RefreshListView(Context context) {
		super(context);
	}
	
}
