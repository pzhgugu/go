package com.ansteel.core.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.ansteel.core.constant.Public;
import com.ansteel.core.context.DefaultEditors;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.TreeEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.query.Operator;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：查询工具类。
 */
public class CriteriaUtils {

    /**
     * 获取查询Specification
     *
     * @param clazz
     * @param key
     * @param value
     * @param order
     * @param queryList
     * @return
     */
    public static <T extends BaseEntity> Specification<T> getSpecification(final Class clazz, final String key, final String value, final String order,
                                                                           final List<QueryMapping> queryList) {
        Specification<T> spec = new Specification<T>() {
            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = getPredicate(root, query, cb, clazz, key, value, order);
                List<Predicate> pList = new ArrayList<Predicate>();
                if (predicate != null) {
                    pList.add(predicate);
                }
                pList.addAll(getQueryMappingPredicate(root, query, cb, clazz, queryList));
                Predicate[] pArray = pList.toArray(new Predicate[pList.size()]);
                query.where(cb.and(pArray));
                return query.getRestriction();
            }
        };
        return spec;
    }

    /**
     * 查询
     *
     * @param root
     * @param query
     * @param cb
     * @param clazz
     * @param queryList
     * @return
     */
    public static <T extends BaseEntity> List<Predicate> getQueryMappingPredicate(Root<T> root,
                                                                                  CriteriaQuery<?> query, CriteriaBuilder cb, Class clazz, List<QueryMapping> queryList) {
        List<Predicate> pList = new ArrayList<Predicate>();
        for (QueryMapping qm : queryList) {
            if (Public.QUERY_BETWEEN.endsWith(qm.getOperator())) {
                String beginStr = qm.getBegin();
                if (!StringUtils.hasText(beginStr)) {
                    beginStr = "1900-01-01";
                }
                Object begin = getWhereValue(clazz, qm.getName(), beginStr);

                String endStr = qm.getEnd();
                if (!StringUtils.hasText(endStr)) {
                    endStr = "9999-01-01";
                }
                Object end = getWhereValue(clazz, qm.getName(), endStr);
                Assert.isTrue(begin instanceof Comparable, "区间查询，必须实现Comparable接口！");
                Assert.isTrue(end instanceof Comparable, "区间查询，必须实现Comparable接口！");
                pList.add(cb.between(root.<Comparable>get(qm.getName()), (Comparable) begin, (Comparable) end));

            } else if (StringUtils.hasText(qm.getValue())) {
                Object wValue = getWhereValue(clazz, qm.getName(), qm.getValue());
                String operator = qm.getOperator();
                String name = qm.getName();
                Operator operEnum = Operator.EQ;
                for (Operator oe : Operator.values()) {
                    if (oe.getCode().equals(operator)) {
                        operEnum = oe;
                        break;
                    }
                }
                Predicate p = null;
                switch (operEnum) {
                    case EQ:
                        p = cb.equal(root.get(name), wValue);
                        break;
                    case NOTEQ:
                        p = cb.notEqual(root.get(name), wValue);
                        break;
                    case GT:
                        Assert.isTrue(wValue instanceof Number, "大于，必须实现Number接口！");
                        p = cb.gt(root.<Number>get(name), (Number) wValue);
                        break;
                    case GE:
                        Assert.isTrue(wValue instanceof Number, "大于等于，必须实现Number接口！");
                        p = cb.ge(root.<Number>get(name), (Number) wValue);
                        break;
                    case LT:
                        Assert.isTrue(wValue instanceof Number, "小于，必须实现Number接口！");
                        p = cb.lt(root.<Number>get(name), (Number) wValue);
                        break;
                    case LE:
                        Assert.isTrue(wValue instanceof Number, "小于等于，必须实现Number接口！");
                        p = cb.le(root.<Number>get(name), (Number) wValue);
                        break;
                    case LIKE:
                        p = cb.like(root.<String>get(name), "%" + wValue + "%");
                        break;
                    case ISEMPTY:
                        p = cb.isEmpty(root.<Collection>get(name));
                        break;
                    case NOTEMPTY:
                        break;
                    case ISNULL:
                        p = cb.isNull(root.get(name));
                        break;
                    case NOTNULL:
                        p = cb.notEqual(root.get(name), null);
                        break;
                    default:
                        break;
                }
                if (p != null) {
                    pList.add(p);
                }
            }
        }
        return pList;
    }


    public static Object getWhereValue(Class clazz, String name, String value) {
        Field field = null;
        try {
            field = ReflectionUtils.findField(clazz, name);
        } catch (Exception e) {
            throw new PageException("输入类型错误:" + e.getMessage());
        }
        Object wValue = new DefaultEditors().getValue(field.getType(), value);
        if (wValue == null) {
            throw new PageException("输入类型错误！");
        }
        return wValue;
    }

    public static Path<Object> getPathByKey(Root root, String key) {
        String[] keyArray = key.split(Public.SPLIT_POINT);
        Path<Object> path = null;
        for (String k : keyArray) {
            if (path == null) {
                path = root.get(k);
            } else {
                path = path.get(k);
            }
        }
        return path;
    }

    public static <T extends BaseEntity> Predicate getPredicate(Root<T> root,
                                                                CriteriaQuery<?> query, CriteriaBuilder cb, Class clazz, String key, String value, String order) {
       if (StringUtils.hasText(order)) {
            query.orderBy(cb.asc(root.get(order)));
        } else {
            Field field = ReflectionUtils.findField(clazz, Public.DISPLAY_ORDER);
            if (field != null) {
                query.orderBy(cb.asc(root.get(Public.DISPLAY_ORDER)));
            }
        }
        if (StringUtils.hasText(key)) {
            Path<Object> path = getPathByKey(root, key);
            return cb.equal(path, value);
        }
        return null;
    }

    public static <T extends BaseEntity> Specification<T> getSpecification(final Class clazz, final String key, final String value, final String order) {
        Specification<T> spec = new Specification<T>() {
            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = CriteriaUtils.getPredicate(root, query, cb, clazz, key, value, order);
                if (p != null) {
                    query.where(p);
                }
                return query.getRestriction();
            }
        };
        return spec;
    }

    public static <T extends TreeEntity> Specification<T> getTreeSpecification(final Class clazz, final String parentId) {
        Specification<T> spec = new Specification<T>() {
            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                root.alias("o");
                Join<Object, Object> children = root.join("children", JoinType.LEFT);

                query.multiselect(root.get("id").alias("id"),
                        root.get("alias").alias("text"),
                        root.get("parent").get("id").alias("parent"),
                        root.get("displayOrder").alias("displayOrder"),
                        cb.count(children.get("id")).alias("child"));

                Predicate condition;
                if (StringUtils.hasText(parentId)) {
                    condition = cb.equal(root.get("parent").get("id"), parentId);
                } else {
                    condition = cb.isNull(root.get("parent"));
                }
                query.where(condition);
                query.groupBy(root.get("id"), root.get("alias"), root.get("displayOrder"), root.get("parent").get("id"));
                query.orderBy(cb.asc(root.get("displayOrder")));

                return query.getRestriction();
            }
        };
        return spec;
    }

}
