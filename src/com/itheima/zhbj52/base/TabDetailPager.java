package com.itheima.zhbj52.base;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.zhbj52.R;
import com.itheima.zhbj52.domain.NewsData.NewsMenuData.NewsTabData;
import com.itheima.zhbj52.domain.TabData;
import com.itheima.zhbj52.domain.TabData.TabNewsData;
import com.itheima.zhbj52.domain.TabData.TopNewsData;
import com.itheima.zhbj52.global.GlobalConstants;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

public class TabDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {

	private NewsTabData mNewsTabData;
	private String mUrl;
	private TabData mTabData;

	@ViewInject(R.id.vp_news)
	private ViewPager mViewPager;
	@ViewInject(R.id.tv_title)
	private TextView tvTitle; // 头条新闻标题
	@ViewInject(R.id.indicator)
	private CirclePageIndicator mIndicator; // 头条新闻指示器
	@ViewInject(R.id.lv_list)
	private ListView lvList; // 新闻列表

	private ArrayList<TopNewsData> mTopNewsList;// 头条新闻数据集合
	private ArrayList<TabNewsData> mTabNewsList; // 新闻列表数据集合
	private NewsAdapter mNewsAdapter;

	public TabDetailPager(Activity mActivity, NewsTabData newsTabData) {
		super(mActivity);
		mNewsTabData = newsTabData;
		mUrl = GlobalConstants.SERVER_URL + mNewsTabData.url;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void initData() {
		getDataFromServer();
	}

	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println("页签详情页返回结果：" + result);
				parseData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
			}
		});
	}

	protected void parseData(String result) {
		Gson gson = new Gson();
		mTabData = gson.fromJson(result, TabData.class);

		mTopNewsList = mTabData.data.topnews;
		mTabNewsList = mTabData.data.news;
		if (mTopNewsList != null) {
			mViewPager.setAdapter(new TopNewsAdapter());

			mIndicator.setViewPager(mViewPager);
			mIndicator.setSnap(true); // 支持快照显示
			mIndicator.setOnPageChangeListener(this);

			mIndicator.onPageSelected(0);// 让指示器重新定位到第一个点

			tvTitle.setText(mTopNewsList.get(0).title);
		}
		if (mTabNewsList != null) {
			mNewsAdapter = new NewsAdapter();
			lvList.setAdapter(mNewsAdapter); // 填充新闻列表
		}

	}

	/**
	 * 头条新闻适配器
	 */
	class TopNewsAdapter extends PagerAdapter {

		BitmapUtils bitmapUtils;

		public TopNewsAdapter() {
			bitmapUtils = new BitmapUtils(mActivity);
			bitmapUtils
					.configDefaultLoadingImage(R.drawable.topnews_item_default);// 设置默认加载的图片
		}

		@Override
		public int getCount() {
			return mTabData.data.topnews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView mImage = new ImageView(mActivity);
			;
			mImage.setScaleType(ScaleType.FIT_XY); // 基于控件大小填充图片
			TopNewsData topNewsData = mTopNewsList.get(position);
			bitmapUtils.display(mImage, topNewsData.topimage); // 传入imageView对象和图片地址
			container.addView(mImage);
			return mImage;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	class NewsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mTabNewsList.size();
		}

		@Override
		public TabNewsData getItem(int position) {
			return mTabNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				
			}
			return null;
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		tvTitle.setText(mTopNewsList.get(position).title);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
