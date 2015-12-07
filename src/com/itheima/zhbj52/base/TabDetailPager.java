package com.itheima.zhbj52.base;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.zhbj52.R;
import com.itheima.zhbj52.domain.NewsData.NewsMenuData.NewsTabData;
import com.itheima.zhbj52.domain.TabData;
import com.itheima.zhbj52.domain.TabData.TabNewsData;
import com.itheima.zhbj52.domain.TabData.TopNewsData;
import com.itheima.zhbj52.global.GlobalConstants;
import com.itheima.zhbj52.utils.PrefUtils;
import com.itheima.zhbj52.view.RefreshListView;
import com.itheima.zhbj52.view.RefreshListView.OnRefreshListener;
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
	private RefreshListView lvList; // 新闻列表

	private ArrayList<TopNewsData> mTopNewsList;// 头条新闻数据集合
	private ArrayList<TabNewsData> mTabNewsList; // 新闻列表数据集合
	private NewsAdapter mNewsAdapter;
	private String mMoreUrl; // 更多页面的地址

	public TabDetailPager(Activity mActivity, NewsTabData newsTabData) {
		super(mActivity);
		mNewsTabData = newsTabData;
		mUrl = GlobalConstants.SERVER_URL + mNewsTabData.url;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		// 加载头布局
		View headerView = View.inflate(mActivity, R.layout.list_header_topnews,
				null);
		ViewUtils.inject(this, view);
		ViewUtils.inject(this, headerView);
		// 将头条新闻以头布局的形式加给ListView
		lvList.addHeaderView(headerView);

		// 设置下拉刷新监听
		lvList.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				getDataFromServer();
			}

			@Override
			public void onLoadMore() {
				if (mMoreUrl != null) {
					getMoreDataFromServer();
				} else {
					Toast.makeText(mActivity, "到底啦~", Toast.LENGTH_SHORT)
							.show();
					lvList.onRefreshComplete(false);// 收起脚布局
				}
			}
		});

		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("被点击：" + position);

				String ids = PrefUtils.getString(mActivity, "read_ids", "");
				
				ids = ids + mTabNewsList.get(position).id + ",";
				PrefUtils.setString(mActivity, "read_ids", ids);

			}
		});

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
				parseData(result, false);
				lvList.onRefreshComplete(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
				lvList.onRefreshComplete(false);
			}
		});
	}

	/**
	 * 加载下一页数据
	 */
	private void getMoreDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println("页签详情页返回结果：" + result);
				parseData(result, true);
				lvList.onRefreshComplete(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				error.printStackTrace();
				lvList.onRefreshComplete(false);
			}
		});
	}

	protected void parseData(String result, boolean isMore) {
		Gson gson = new Gson();
		mTabData = gson.fromJson(result, TabData.class);
		// 处理下一页的链接
		String more = mTabData.data.more;
		if (!TextUtils.isEmpty(more)) {
			mMoreUrl = GlobalConstants.SERVER_URL + more;
		} else {
			mMoreUrl = null;
		}

		if (!isMore) {
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
		} else { // 如果是加载下一页，需要将数据追加给原来的集合
			ArrayList<TabNewsData> news = mTabData.data.news;
			mTabNewsList.addAll(news);
			mNewsAdapter.notifyDataSetChanged();
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

	/**
	 * 新闻列表适配器
	 * 
	 * @author baoliang.zhao
	 * 
	 */
	class NewsAdapter extends BaseAdapter {
		private BitmapUtils utils;

		public NewsAdapter() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
		}

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
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.list_news_item,
						null);
				viewHolder = new ViewHolder();
				viewHolder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title);
				viewHolder.tvDate = (TextView) convertView
						.findViewById(R.id.tv_date);
				viewHolder.ivPic = (ImageView) convertView
						.findViewById(R.id.iv_pic);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			TabNewsData item = getItem(position);
			viewHolder.tvTitle.setText(item.title);
			viewHolder.tvDate.setText(item.pubdate);
			utils.display(viewHolder.ivPic, item.listimage);
			return convertView;
		}

	}

	static class ViewHolder {
		public TextView tvTitle;
		public TextView tvDate;
		public ImageView ivPic;
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
