package com.itheima.zhbj52.base;

import java.io.UTFDataFormatException;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.zhbj52.R;
import com.itheima.zhbj52.domain.NewsData.NewsMenuData.NewsTabData;
import com.itheima.zhbj52.domain.TabData;
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

public class TabDetailPager extends BaseMenuDetailPager {

	private NewsTabData mNewsTabData;
	private TextView tvText;
	private String mUrl;
	private TabData mTabData;

	@ViewInject(R.id.vp_news)
	private ViewPager mViewPager;
	
	private ArrayList<TopNewsData> mTopNewsList;// 头条新闻数据集合

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
		mViewPager.setAdapter(new TopNewsAdapter());
	}

	/**
	 * 头条新闻适配器
	 */
	class TopNewsAdapter extends PagerAdapter {
		
		BitmapUtils bitmapUtils;
		public TopNewsAdapter(){
			bitmapUtils = new BitmapUtils(mActivity);
			bitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);//设置默认加载的图片
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
			ImageView mImage = new ImageView(mActivity);;
			mImage.setScaleType(ScaleType.FIT_XY); //基于控件大小填充图片
			TopNewsData topNewsData = mTopNewsList.get(position);
			bitmapUtils.display(mImage, topNewsData.topimage); //传入imageView对象和图片地址
			container.addView(mImage);
			return mImage;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}
}
