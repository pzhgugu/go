package com.ansteel.cms.news.core;

/**
 * 
 * 创 建 人：gugu 创建日期：2015-04-19 修 改 人： 修改日 期： 描 述：信息类型。
 */
public enum NewsType {
	/**
	 * 发布箱
	 */
	publish {
		@Override
		public int getValue() {
			return 1;
		}
	},
	/**
	 * 待审箱
	 */
	pendingtrial {
		@Override
		public int getValue() {
			return 2;
		}
	},
	/**
	 * 垃圾箱
	 */
	rubbish {
		@Override
		public int getValue() {
			return 3;
		}
	};
	public abstract int getValue();
}
