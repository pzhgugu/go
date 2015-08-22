package com.ansteel.core.query;



import java.util.Collection;

import com.ansteel.core.query.criterion.AndCriterion;
import com.ansteel.core.query.criterion.BetweenCriterion;
import com.ansteel.core.query.criterion.BetweenExCriterion;
import com.ansteel.core.query.criterion.ContainsTextCriterion;
import com.ansteel.core.query.criterion.EndWithTextCriterion;
import com.ansteel.core.query.criterion.EqCriterion;
import com.ansteel.core.query.criterion.EqPropCriterion;
import com.ansteel.core.query.criterion.GeCriterion;
import com.ansteel.core.query.criterion.GePropCriterion;
import com.ansteel.core.query.criterion.GtCriterion;
import com.ansteel.core.query.criterion.GtPropCriterion;
import com.ansteel.core.query.criterion.InCriterion;
import com.ansteel.core.query.criterion.IsEmptyCriterion;
import com.ansteel.core.query.criterion.IsNullCriterion;
import com.ansteel.core.query.criterion.LeCriterion;
import com.ansteel.core.query.criterion.LePropCriterion;
import com.ansteel.core.query.criterion.LtCriterion;
import com.ansteel.core.query.criterion.LtPropCriterion;
import com.ansteel.core.query.criterion.NotCriterion;
import com.ansteel.core.query.criterion.NotEmptyCriterion;
import com.ansteel.core.query.criterion.NotEqCriterion;
import com.ansteel.core.query.criterion.NotEqPropCriterion;
import com.ansteel.core.query.criterion.NotInCriterion;
import com.ansteel.core.query.criterion.NotNullCriterion;
import com.ansteel.core.query.criterion.OrCriterion;
import com.ansteel.core.query.criterion.SizeEqCriterion;
import com.ansteel.core.query.criterion.SizeGeCriterion;
import com.ansteel.core.query.criterion.SizeGtCriterion;
import com.ansteel.core.query.criterion.SizeLeCriterion;
import com.ansteel.core.query.criterion.SizeLtCriterion;
import com.ansteel.core.query.criterion.SizeNotEqCriterion;
import com.ansteel.core.query.criterion.StartsWithTextCriterion;


/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为各种查询条件的工厂。  
 */
public class Criterions {

	private Criterions() {
	}

	public static QueryCriterion eq(String propName, Object value) {
		return new EqCriterion(propName, value);
	}
	
	public static QueryCriterion notEq(String propName, Object value) {
		return new NotEqCriterion(propName, value);
	}
	
	public static QueryCriterion ge(String propName, Comparable<?> value) {
		return new GeCriterion(propName, value);
	}
	
	public static QueryCriterion gt(String propName, Comparable<?> value) {
		return new GtCriterion(propName, value);
	}
	
	public static QueryCriterion le(String propName, Comparable<?> value) {
		return new LeCriterion(propName, value);
	}
	
	public static QueryCriterion lt(String propName, Comparable<?> value) {
		return new LtCriterion(propName, value);
	}
	
	public static QueryCriterion eqProp(String propName, String otherProp) {
		return new EqPropCriterion(propName, otherProp);
	}
	
	public static QueryCriterion notEqProp(String propName, String otherProp) {
		return new NotEqPropCriterion(propName, otherProp);
	}
	
	public static QueryCriterion gtProp(String propName, String otherProp) {
		return new GtPropCriterion(propName, otherProp);
	}
	
	public static QueryCriterion geProp(String propName, String otherProp) {
		return new GePropCriterion(propName, otherProp);
	}
	
	public static QueryCriterion ltProp(String propName, String otherProp) {
		return new LtPropCriterion(propName, otherProp);
	}
	
	public static QueryCriterion leProp(String propName, String otherProp) {
		return new LePropCriterion(propName, otherProp);
	}
	
	public static QueryCriterion sizeEq(String propName, int size) {
		return new SizeEqCriterion(propName, size);
	}
	
	public static QueryCriterion sizeNotEq(String propName, int size) {
		return new SizeNotEqCriterion(propName, size);
	}
	
	public static QueryCriterion sizeGt(String propName, int size) {
		return new SizeGtCriterion(propName, size);
	}
	
	public static QueryCriterion sizeGe(String propName, int size) {
		return new SizeGeCriterion(propName, size);
	}
	
	public static QueryCriterion sizeLt(String propName, int size) {
		return new SizeLtCriterion(propName, size);
	}
	
	public static QueryCriterion sizeLe(String propName, int size) {
		return new SizeLeCriterion(propName, size);
	}

	public static QueryCriterion containsText(String propName, String value) {
		return new ContainsTextCriterion(propName, value);
	}

	public static QueryCriterion startsWithText(String propName, String value) {
		return new StartsWithTextCriterion(propName, value);
	}

	public static QueryCriterion endWithText(String propName, String value) {
		return new EndWithTextCriterion(propName, value);
	}
	
	public static QueryCriterion in(String propName, Collection<?> value) {
		return new InCriterion(propName, value);
	}

	public static QueryCriterion in(String propName, Object[] value) {
		return new InCriterion(propName, value);
	}

	public static QueryCriterion notIn(String propName, Collection<?> value) {
		return new NotInCriterion(propName, value);
	}

	public static QueryCriterion notIn(String propName, Object[] value) {
		return new NotInCriterion(propName, value);
	}

	/*public static QueryCriterion between(String propName, Comparable<?> from, Comparable<?> to) {
		return new BetweenCriterion(propName, from, to);
	}*/
	
	public static QueryCriterion between(String propName, Object from, Object to) {
		return new BetweenExCriterion(propName, from, to);
	}
	
	public static QueryCriterion isNull(String propName) {
		return new IsNullCriterion(propName);
	}
	
	public static QueryCriterion notNull(String propName) {
		return new NotNullCriterion(propName);
	}
	
	public static QueryCriterion isEmpty(String propName) {
		return new IsEmptyCriterion(propName);
	}
	
	public static QueryCriterion notEmpty(String propName) {
		return new NotEmptyCriterion(propName);
	}
	
	public static QueryCriterion not(QueryCriterion criterion) {
		return new NotCriterion(criterion);
	}
	
	public static QueryCriterion and(QueryCriterion... criterions) {
		return new AndCriterion(criterions);
	}
	
	public static QueryCriterion or(QueryCriterion... criterions) {
		return new OrCriterion(criterions);
	}
	
}
