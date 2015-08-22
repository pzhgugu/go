package com.ansteel.core.query;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为各种查询条件的工厂。  
 */
public enum Operator {
	EQ {
		@Override
		public String getAlias() {
			return "等于";
		}

		@Override
		public String getCode() {
			return "=";
		}

	},
	NOTEQ{
		@Override
		public String getAlias() {
			return "不等于";
		}

		@Override
		public String getCode() {
			return "!=";
		}
		
	},
	GT{
		@Override
		public String getAlias() {
			return "大于";
		}

		@Override
		public String getCode() {
			return ">";
		}
		
	},
	GE{
		@Override
		public String getAlias() {
			return "大于等于";
		}

		@Override
		public String getCode() {
			return ">=";
		}
		
	},
	LT{
		@Override
		public String getAlias() {
			return "小于";
		}

		@Override
		public String getCode() {
			return "<";
		}
		
	},
	LE{
		@Override
		public String getAlias() {
			return "小于等于";
		}

		@Override
		public String getCode() {
			return "<=";
		}
		
	},
	LIKE {
		@Override
		public String getAlias() {
			return "包含";
		}

		@Override
		public String getCode() {
			return "like";
		}
	},
	ISEMPTY{
		@Override
		public String getAlias() {
			return "为空";
		}

		@Override
		public String getCode() {
			return "isEmpty";
		}
		
	},
	NOTEMPTY{
		@Override
		public String getAlias() {
			return "不为空";
		}

		@Override
		public String getCode() {
			return "notEmpty";
		}
		
	},
	ISNULL{
		@Override
		public String getAlias() {
			return "isNull";
		}

		@Override
		public String getCode() {
			return "isNull";
		}
		
	},
	NOTNULL{
		@Override
		public String getAlias() {
			return "notNull";
		}

		@Override
		public String getCode() {
			return "notNull";
		}
		
	};
	public abstract String getAlias();
	public abstract String getCode();
}
