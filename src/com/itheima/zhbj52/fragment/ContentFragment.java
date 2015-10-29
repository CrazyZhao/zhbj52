package com.itheima.zhbj52.fragment;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.itheima.zhbj52.R;
import com.itheima.zhbj52.base.BasePager;
import com.itheima.zhbj52.base.imp.GovAffairsPager;
import com.itheima.zhbj52.base.imp.HomePager;
import com.itheima.zhbj52.base.imp.NewsCenterPager;
import com.itheima.zhbj52.base.imp.SettingPager;
import com.itheima.zhbj52.base.imp.SmartServicePager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ContentFragment extends BaseFragment {

	@ViewInject(R.id.rg_group)
	private RadioGroup rgGroup;
	@ViewInject(R.id.vp_content)
	private ViewPager mViewPager;
	private ArrayList<BasePager> mPagerList;

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

		mPagerList = new ArrayList<BasePager>();
		/*
		 * for (int i = 0; i < 5; i++) { BasePager basePager = new
		 * BasePager(mActivity); mPagerList.add(basePager); }
		 */
		mPagerList.add(new HomePager(mActivity));
		mPagerList.add(new NewsCenterPager(mActivity));
		mPagerList.add(new SmartServicePager(mActivity));
		mPagerList.add(new GovAffairsPager(mActivity));
		mPagerList.add(new SettingPager(mActivity));

		mViewPager.setAdapter(new ContentAdapter());

		// 监听RadioGroup的选择事件
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					mViewPager.setCurrentItem(0, false);
					break;
				case R.id.rb_news:
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_smart:
					mViewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_gov:
					mViewPager.setCurrentItem(3, false);
					break;
				case R.id.rb_setting:
					mViewPager.setCurrentItem(4, false);
					break;

				default:
					break;
				}
			}
		});

		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				mPagerList.get(position).initData();// 获取当前被选中的页面, 初始化该页面数据
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		mPagerList.get(0).initData(); // 默认初始化首页数据
	}

	class ContentAdapter extends PagerAdapter {

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
			BasePager pager = mPagerList.get(position);
			container.addView(pager.mRootView);// 注意：初始化数据必须在加载各个页面的时候执行
			return pager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

}
