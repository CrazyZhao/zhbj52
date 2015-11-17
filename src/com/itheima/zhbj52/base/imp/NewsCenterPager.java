package com.itheima.zhbj52.base.imp;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.zhbj52.activity.MainActivity;
import com.itheima.zhbj52.base.BaseMenuDetailPager;
import com.itheima.zhbj52.base.BasePager;
import com.itheima.zhbj52.base.menudetail.InteractMenuDetailPager;
import com.itheima.zhbj52.base.menudetail.NewsMenuDetailPager;
import com.itheima.zhbj52.base.menudetail.PhotoMenuDetailPager;
import com.itheima.zhbj52.base.menudetail.TopicMenuDetailPager;
import com.itheima.zhbj52.domain.NewsData;
import com.itheima.zhbj52.fragment.LeftMenuFragment;
import com.itheima.zhbj52.global.GlobalConstants;
import com.itheima.zhbj52.utils.PrefUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 首页实现
 * 
 * @author baoliang.zhao
 * 
 */
public class NewsCenterPager extends BasePager {

	public ArrayList<BaseMenuDetailPager> mPagers; // 四个菜单详情页
	private NewsData mNewsData;

	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		// tvTitle.setText("新闻");
		/*
		 * TextView text = new TextView(mActivity); text.setText("新闻中心");
		 * text.setTextColor(Color.RED); text.setTextSize(25);
		 * text.setGravity(Gravity.CENTER);
		 */
		enableSlidingMenu(true);
		// 向FrameLayout中动态添加布局
		// subContent.addView(text);

		getServerData();

	}

	/**
	 * 获取服务器数据
	 */
	private void getServerData() {

		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalConstants.CATEGORIES_URL,
				new RequestCallBack<String>() {
					//访问成功,在主线程中运行
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						System.out.println("返回结果：" + result);

						parseData(result);

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
						error.printStackTrace();
					}
				});
	}

	/**
	 * 解析网络数据
	 * 
	 * @param result
	 */
	protected void parseData(String result) {

		Gson gson = new Gson();
		mNewsData = gson.fromJson(result, NewsData.class);
		System.out.println("解析结果:" + mNewsData);
		// 设置侧边栏数据
		LeftMenuFragment fragment = ((MainActivity) mActivity)
				.getLeftMenuFragment();
		fragment.setMenuData(mNewsData);

		// 准备4个菜单详情页
		mPagers = new ArrayList<BaseMenuDetailPager>();
		mPagers.add(new NewsMenuDetailPager(mActivity,
				mNewsData.data.get(0).children));
		mPagers.add(new TopicMenuDetailPager(mActivity));
		mPagers.add(new PhotoMenuDetailPager(mActivity));
		mPagers.add(new InteractMenuDetailPager(mActivity));

		setCurrentMenuDetailPager(0); // 设置菜单详情页-新闻为默认当前页

	}

	/**
	 * 设置当前菜单详情页
	 */
	public void setCurrentMenuDetailPager(int position) {
		BaseMenuDetailPager pager = mPagers.get(position);// 获取当前要显示的菜单详情页
		subContent.removeAllViews(); // 清除之前的布局
		subContent.addView(pager.mRootView);// 将菜单详情页设置给内容帧布局

		tvTitle.setText(mNewsData.data.get(position).title);

		pager.initData(); // 初始化当前页面的数据
	}
}
