package com.itheima.zhbj52.domain;

import java.util.ArrayList;

/**
 * 网络分类信息的封装
 * 
 * 字段名字必须和服务器返回的名字一样
 * 
 * @author baoliang.zhao
 * 
 */
public class NewsData {

	public int retCode;

	public ArrayList<NewsMenuData> data;

	/**
	 * 侧边栏数据对象
	 * 
	 * @author baoliang.zhao
	 * 
	 */
	public class NewsMenuData {
		public String id;
		public String title;
		public String type;
		public String url;
		public ArrayList<NewsTabData> children;
		
		
		@Override
		public String toString() {
			return "NewsMenuData [title=" + title + ", children=" + children
					+ "]";
		}


		/**
		 * 新闻页面下11个子页签的数据对象
		 * 
		 * @author baoliang.zhao
		 * 
		 */
		public class NewsTabData {
			public String id;
			public String title;
			public String type;
			public String url;
			@Override
			public String toString() {
				return "NewsTabData [title=" + title + "]";
			}
			
			
		}
	}

	@Override
	public String toString() {
		return "NewsData [data=" + data + "]";
	}
	
	
}
