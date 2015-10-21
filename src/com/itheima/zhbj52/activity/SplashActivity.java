package com.itheima.zhbj52.activity;

import com.itheima.zhbj52.R;
import com.itheima.zhbj52.R.id;
import com.itheima.zhbj52.R.layout;
import com.itheima.zhbj52.utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

	private RelativeLayout rlRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		rlRoot = (RelativeLayout) findViewById(R.id.rl_root);

		startAnim();
	}

	/**
	 * 启动动画
	 */
	private void startAnim() {
		AnimationSet set = new AnimationSet(false);// 动画集合
		// 旋转动画
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(2000); // 动画时间
		rotate.setFillAfter(true);// 保持动画状态
		// 缩放动画
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(2000);
		scale.setFillAfter(true);
		// 渐变动画
		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setDuration(2000);
		alpha.setFillAfter(true);

		set.addAnimation(rotate);
		set.addAnimation(scale);
		set.addAnimation(alpha);

		rlRoot.startAnimation(set);

		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// 跳转页面
				jumpNextPage();
			}
		});
	}

	protected void jumpNextPage() {
		// 获取sp
		boolean isShowed = PrefUtils.getBoolean(this, "is_user_guide_showed",
				false);
		if (!isShowed) {
			// 跳转新手引导页
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
		} else {
			// 跳转主页面
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
		finish();
	}
}
