package com.itheima.zhbj52.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itheima.zhbj52.R;

/**
 * 下拉刷新的ListView
 * 
 * @author baoliang.zhao
 * 
 */
public class RefreshListView extends ListView {

	private static final int STATE_PULL_REFRESH = 0; // 下拉刷新
	private static final int STATE_RELEASE_REFRESH = 1;// 松开刷新
	private static final int STATE_REFRESHING = 2;// 正在刷新
	private int startY = -1; // 滑动起点的Y坐标
	private int mHeaderHeight;
	private View mHeaderView;

	private int mCurrentState = STATE_PULL_REFRESH; // 当前状态
	private TextView tvTitle;
	private TextView tvTime;
	private ImageView ivArrow;
	private ProgressBar pbProgress;
	private RotateAnimation animUp;
	private RotateAnimation animDown;

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context) {
		super(context);
		initHeaderView();
		initFooterView();
	}

	/**
	 * 初始化头布局
	 */
	private void initHeaderView() {
		mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
		this.addHeaderView(mHeaderView);

		tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
		tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
		ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arr);
		pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);

		mHeaderView.measure(0, 0);
		mHeaderHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);

		initArrowAnim();
	}

	/**
	 * 初始化脚布局
	 */
	private void initFooterView() {

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (startY == -1) {
				startY = (int) ev.getRawY();
			}

			if (mCurrentState == STATE_REFRESHING) {
				break; // 正在刷新时不做处理
			}
			int endY = (int) ev.getRawY();
			int dy = endY - startY; // 移动偏移量
			if (dy > 0 && getFirstVisiblePosition() == 0) {// 只有下拉并且是第一个item
				int padding = -mHeaderHeight + dy;
				mHeaderView.setPadding(0, padding, 0, 0);
				if (padding > 0 && mCurrentState != STATE_RELEASE_REFRESH) {// 将状态改为松开刷新
					mCurrentState = STATE_RELEASE_REFRESH;
					refreshState();
				} else if (padding < 0 && mCurrentState != STATE_PULL_REFRESH) {// 将状态改为下拉刷新
					mCurrentState = STATE_PULL_REFRESH;
					refreshState();
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			startY = -1;// 重置

			if (mCurrentState == STATE_RELEASE_REFRESH) {
				mCurrentState = STATE_REFRESHING;// 正在刷新
				mHeaderView.setPadding(0, 0, 0, 0);
				refreshState();
			} else if (mCurrentState == STATE_PULL_REFRESH) {
				mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 刷新下拉控件的布局
	 */
	private void refreshState() {
		switch (mCurrentState) {
		case STATE_PULL_REFRESH:
			tvTitle.setText("下拉刷新");
			ivArrow.setVisibility(VISIBLE);
			pbProgress.setVisibility(INVISIBLE);

			ivArrow.startAnimation(animDown);
			break;
		case STATE_RELEASE_REFRESH:
			tvTitle.setText("松开刷新");
			ivArrow.setVisibility(VISIBLE);
			pbProgress.setVisibility(INVISIBLE);

			ivArrow.startAnimation(animUp);
			break;
		case STATE_REFRESHING:
			tvTitle.setText("正在刷新...");
			ivArrow.clearAnimation(); // 必须先清除动画，才能隐藏
			ivArrow.setVisibility(INVISIBLE);
			pbProgress.setVisibility(VISIBLE);

			if (mListener != null) {
				mListener.onRefresh();
			}
			break;

		default:
			break;
		}
	}

	private void initArrowAnim() {
		// 箭头向上的动画
		animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animUp.setDuration(200);
		animUp.setFillAfter(true);
		// 箭头向下的动画
		animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animDown.setDuration(200);
		animDown.setFillAfter(true);
	}

	OnRefreshListener mListener;

	public void setOnRefreshListener(OnRefreshListener listener) {
		mListener = listener;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	/**
	 * 收起下拉刷新的控件
	 */
	public void onRefreshComplete(boolean success) {
		mCurrentState = STATE_PULL_REFRESH;
		tvTitle.setText("下拉刷新");
		ivArrow.setVisibility(VISIBLE);
		pbProgress.setVisibility(INVISIBLE);

		mHeaderView.setPadding(0, -mHeaderHeight, 0, 0); // 隐藏
		if (success) {
			tvTime.setText("最后刷新时间：" + getCurrentTime());
		}
	}

	/**
	 * 获取当前时间
	 */
	public String getCurrentTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
}
