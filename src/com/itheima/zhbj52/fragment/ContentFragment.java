package com.itheima.zhbj52.fragment;

import android.view.View;
import android.widget.RadioGroup;

import com.itheima.zhbj52.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ContentFragment extends BaseFragment {

	@ViewInject(R.id.rg_group)
	private RadioGroup rgGroup;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		// rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		ViewUtils.inject(this, view);// 注入view和事件
		return view;
	}

	@Override
	public void initData() {
		super.initData();
		rgGroup.check(R.id.rb_home);// 默认勾选首页
		
	}
	
}
