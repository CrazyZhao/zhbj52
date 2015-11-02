package com.itheima.zhbj52.fragment;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.zhbj52.R;
import com.itheima.zhbj52.activity.MainActivity;
import com.itheima.zhbj52.base.BaseMenuDetailPager;
import com.itheima.zhbj52.base.imp.NewsCenterPager;
import com.itheima.zhbj52.base.menudetail.InteractMenuDetailPager;
import com.itheima.zhbj52.base.menudetail.NewsMenuDetailPager;
import com.itheima.zhbj52.base.menudetail.PhotoMenuDetailPager;
import com.itheima.zhbj52.base.menudetail.TopicMenuDetailPager;
import com.itheima.zhbj52.domain.NewsData;
import com.itheima.zhbj52.domain.NewsData.NewsMenuData;
import com.itheima.zhbj52.utils.PrefUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LeftMenuFragment extends BaseFragment {
	@ViewInject(R.id.lv_list)
	private ListView lvList;
	private ArrayList<NewsMenuData> mMenuList;
	private int mCurrentPos;// 当前被点击的菜单项
	private MenuAdapter menuAdapter;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentPos = position;
				menuAdapter.notifyDataSetChanged(); // 适配器的内容改变时会强制调用getView来刷新每个Item的内容

				setCurrentMenuDetailPager(position);
				
				toggleSlidingMenu();
			}
		});
	}

	/**
	 * 切换SlidingMenu的状态
	 */
	protected void toggleSlidingMenu() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();// Toggle the SlidingMenu. If it is open, it will
								// be closed, and vice versa.
	}

	/**
	 * 设置当前菜单详情页
	 * 
	 * @param position
	 */
	protected void setCurrentMenuDetailPager(int position) {
		MainActivity mainActivity = (MainActivity) mActivity;
		ContentFragment fragment = mainActivity.getContentFragment();
		NewsCenterPager newsCenterPager = fragment.getNewsCenterPager();
		newsCenterPager.setCurrentMenuDetailPager(position);
	}

	/**
	 * 设置侧边栏菜单数据
	 * 
	 * @param data
	 */
	public void setMenuData(NewsData data) {
		System.out.println("侧边栏获取到数据啦：" + data);
		mMenuList = data.data;
		menuAdapter = new MenuAdapter();
		lvList.setAdapter(menuAdapter);
	}

	/*
	 * 侧边栏菜单数据适配器
	 */
	class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mMenuList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.list_menu_item, null);
			TextView title = (TextView) view.findViewById(R.id.tv_title);
			NewsMenuData newsMenuData = mMenuList.get(position);
			title.setText(newsMenuData.title);
			if (mCurrentPos == position) { // 判断当前绘制的菜单项view是否被选中,选中的设置为红色，非选中的设置为白色
				title.setEnabled(true);
			} else {
				title.setEnabled(false);
			}
			return view;
		}

	}

}
