package com.itheima.zhbj52.base.menudetail;


import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.zhbj52.R;
import com.itheima.zhbj52.activity.MainActivity;
import com.itheima.zhbj52.base.BaseMenuDetailPager;
import com.itheima.zhbj52.base.TabDetailPager;
import com.itheima.zhbj52.domain.NewsData.NewsMenuData.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

/**
 * 菜单详情页-新闻
 * 
 * @author baoliang.zhao
 * 
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements OnPageChangeListener {

	private ViewPager mViewPager;

	private ArrayList<NewsTabData> mNewsTabData; // 页签网络数据

	private ArrayList<TabDetailPager> mPagerList;

	private TabPageIndicator mIndicator;

	public NewsMenuDetailPager(Activity mActivity,
			ArrayList<NewsTabData> children) {
		super(mActivity);
		mNewsTabData = children;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		//用注解时需要注入当前对象
		ViewUtils.inject(this, view);

		mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
		// 初始化自定义控件：TabPageIndicator
		mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		
		mIndicator.setOnPageChangeListener(this);

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
		mIndicator.setViewPager(mViewPager); // 必须在ViewPager setAdapter之后才能调用
	}

	// 跳转到下一个页面
	@OnClick(R.id.btn_next)
	public void nextPage(View view) {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	class MenuDetailAdapter extends PagerAdapter {

		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsTabData.get(position).title;
		}

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

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		
	}

	@Override
	public void onPageSelected(int position) {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		if(position == 0){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		
	}
}
