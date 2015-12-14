package com.itheima.zhbj52.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.itheima.zhbj52.R;

/**
 * 新闻详情
 * 
 * @author baoliang.zhao
 * 
 */
public class NewsDetailActivity extends Activity implements OnClickListener {

	private ImageButton btnBack;
	private ImageButton btnSize;
	private ImageButton btnShare;
	private ProgressBar pbProgress;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_detail);

		btnBack = (ImageButton) findViewById(R.id.btn_back);
		btnSize = (ImageButton) findViewById(R.id.btn_size);
		btnShare = (ImageButton) findViewById(R.id.btn_share);
		mWebView = (WebView) findViewById(R.id.wv_web);
		pbProgress = (ProgressBar) findViewById(R.id.pb_progress);

		String url = getIntent().getStringExtra("url");

		btnBack.setOnClickListener(this);
		btnSize.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		WebSettings settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setBuiltInZoomControls(true);
		settings.setUseWideViewPort(true);

		mWebView.setWebViewClient(new WebViewClient() {

			// 网页开始加载
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				pbProgress.setVisibility(View.VISIBLE);
			}

			// 网页结束加载
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				pbProgress.setVisibility(View.GONE);
			}

			// 所有跳转的链接都会在此方法中回调
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			// 进度发生变化
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}

			// 获取网页标题
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}
		});

		mWebView.loadUrl(url); // 加载网页

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_size:
			showChooseDialog();
			break;
		case R.id.btn_share:
			showShare();
			break;

		default:
			break;
		}
	}

	private int mCurrentChooseItem; // 记录当前选中的Item,点击确定前
	private int mCurrentItem = 2; // 记录当前选中的Item,点击确定后

	private void showChooseDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
				"超小号字体" };
		builder.setTitle("字体设置");
		builder.setSingleChoiceItems(items, mCurrentItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mCurrentChooseItem = which;
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				WebSettings settings = mWebView.getSettings();
				switch (mCurrentChooseItem) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);
					break;
				case 4:
					settings.setTextSize(TextSize.SMALLEST);
					break;

				default:
					break;
				}
				mCurrentItem = mCurrentChooseItem;

			}
		});

		builder.setNegativeButton("取消", null);
		builder.show();
	}

	private void showShare() {
		
	}

}