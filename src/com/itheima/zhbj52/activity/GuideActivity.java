package com.itheima.zhbj52.activity;

import java.util.ArrayList;

import com.itheima.zhbj52.R;
import com.itheima.zhbj52.R.drawable;
import com.itheima.zhbj52.R.id;
import com.itheima.zhbj52.R.layout;
import com.itheima.zhbj52.utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GuideActivity extends Activity {

	private ViewPager vpGuide;
	private static final int[] mImageIds = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };
	private ArrayList<ImageView> mImageViewList;
	private LinearLayout llPointGroup;
	private int mPointWidth; // 圆点左边缘之间的距离
	private View viewRedPoint;
	private Button btnStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point_group);
		viewRedPoint = findViewById(R.id.view_red_point);
		btnStart = (Button) findViewById(R.id.btn_start);
		initViews();
		vpGuide.setAdapter(new GuideAdapter());
		vpGuide.addOnPageChangeListener(new GuidePageListener());

		btnStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 更新sp，表示已经展示了新手引导页
				PrefUtils.setBoolean(GuideActivity.this,
						"is_user_guide_showed", true);
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});
	}

	/**
	 * 初始化界面
	 */
	private void initViews() {
		mImageViewList = new ArrayList<ImageView>();
		// 初始化引导页的三个页面
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView image = new ImageView(this);
			image.setBackgroundResource(mImageIds[i]);
			mImageViewList.add(image);
		}
		// 初始化引导页的三个小圆点
		for (int i = 0; i < mImageIds.length; i++) {
			View point = new View(this);
			point.setBackgroundResource(R.drawable.shap_point_gray);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					10, 10);
			if (i > 0) {
				params.leftMargin = 10; // 设置圆点间隔
			}
			point.setLayoutParams(params);
			llPointGroup.addView(point);
		}

		// 顺序：measure layout draw
		// 获取视图树，对layout结束事件进行监听
		llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					// 当layout执行结束后回调此方法
					@Override
					public void onGlobalLayout() {
						llPointGroup.getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
						mPointWidth = llPointGroup.getChildAt(1).getLeft()
								- llPointGroup.getChildAt(0).getLeft();

					}
				});
	}

	/**
	 * ViewPager数据适配器
	 * 
	 * @author baoliang.zhao
	 * 
	 */
	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mImageViewList.get(position));
			return mImageViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	/**
	 * ViewPager滑动监听
	 */
	class GuidePageListener implements OnPageChangeListener {
		// 滑动事件
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			int len = (int) (mPointWidth * positionOffset + position
					* mPointWidth);
			RelativeLayout.LayoutParams params = (LayoutParams) viewRedPoint
					.getLayoutParams(); // 获取当前小红点的布局参数
			params.leftMargin = len;
			viewRedPoint.setLayoutParams(params);

		}

		@Override
		public void onPageSelected(int position) {
			if (position == mImageIds.length - 1) {
				btnStart.setVisibility(View.VISIBLE);
			} else {
				btnStart.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}

	}
}
