package com.itheima.zhbj52.base.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.zhbj52.R;
import com.itheima.zhbj52.base.BaseMenuDetailPager;
import com.itheima.zhbj52.base.TabDetailPager;
import com.itheima.zhbj52.domain.NewsData;
import com.itheima.zhbj52.domain.NewsData.NewsMenuData.NewsTabData;

/**
 * 菜单详情页-新闻
 * 
 * @author baoliang.zhao
 * 
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager {

	private ViewPager mViewPager;

	private ArrayList<NewsTabData> mNewsTabData; // 页签网络数据

	private ArrayList<TabDetailPager> mPagerList;

	public NewsMenuDetailPager(Activity mActivity,
			ArrayList<NewsTabData> children) {
		super(mActivity);
		mNewsTabData = children;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);

		return view;
	}

	@Override
	public void initData() {
		mPagerList = new ArrayList<TabDetailPager>();
		// 初始化页签数据
		for (int i = 0; i < mNewsTabData.size(); i++) {
			mPagerList.add(new TabDetailPager(mActivity, mNewsTabData.get(i)));
		}

		mViewPager.setAdapter(new MenuDetailAdapter());
	}

	class MenuDetailAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager tabDetailPager = mPagerList.get(position);
			container.addView(tabDetailPager.mRootView);
			tabDetailPager.initData();
			return tabDetailPager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
}
