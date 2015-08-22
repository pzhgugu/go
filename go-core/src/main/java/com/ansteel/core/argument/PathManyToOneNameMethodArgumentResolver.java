package com.ansteel.core.argument;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;
import javax.servlet.ServletException;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.servlet.HandlerMapping;

import com.ansteel.common.entityinfo.service.EntityInfoService;
import com.ansteel.common.entityinfo.service.EntityInfoServiceBean;
import com.ansteel.core.annotation.EntityType;
import com.ansteel.core.annotation.PathManyToOneName;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.exception.PageException;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：通过从表在路径名中的class名称，得到从表中的关联主表的名称。 
 */
public class PathManyToOneNameMethodArgumentResolver extends
		AbstractNamedValueMethodArgumentResolver {

	public PathManyToOneNameMethodArgumentResolver() {
		super(null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (!parameter.hasParameterAnnotation(PathManyToOneName.class)) {
			return false;
		}
		if (Map.class.isAssignableFrom(parameter.getParameterType())) {
			String paramName = parameter.getParameterAnnotation(
					PathManyToOneName.class).value();
			return StringUtils.hasText(paramName);
		}
		return true;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		PathManyToOneName annotation = parameter
				.getParameterAnnotation(PathManyToOneName.class);
		return new PathVariableNamedValueInfo(annotation);
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter,
			NativeWebRequest request) throws Exception {
		Map<String, String> uriTemplateVars = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
						RequestAttributes.SCOPE_REQUEST);
		Class declaringClass = parameter.getContainingClass();
		if (uriTemplateVars != null && uriTemplateVars.containsKey(name)) {
			EntityInfoService entityInfoService = new EntityInfoServiceBean();
			Class clazz = entityInfoService.getBaseEntityClass(declaringClass,
					uriTemplateVars.get(name));
			List<EntityInfo> entityInfos = (List<EntityInfo>) entityInfoService
					.getEntityInfoList(declaringClass);
			if (entityInfos != null) {
				for (EntityInfo o : entityInfos) {
					if (o.getClazz() == clazz) {
						PathManyToOneName annotation = parameter
								.getParameterAnnotation(PathManyToOneName.class);

						Class mainTable = null;
						if (annotation.type().equals(EntityType.TABLE)) {
							if (o.getMainSub() == null) {
								throw new PageException("实体信息中没有设置主从表信息！");
							}
							mainTable = o.getMainSub().getPrincipal();
						} else if (annotation.type().equals(EntityType.TREE)) {
							if (o.getTree() == null) {
								throw new PageException("实体信息中没有设置树信息！");
							}
							mainTable = o.getTree().getTree();
						}
						return this.getMianName(mainTable, o.getClazz());
					}
				}
			}
		}
		return null;
	}

	/**
	 * 通过主从表关联得到主表名称
	 * 
	 * @param main
	 *            主表class *
	 * @param table
	 *            从表class
	 * @return
	 */
	public String getMianName(Class main, Class table) {
		String name = "";
		Field[] fields = table.getDeclaredFields();
		for (Field f : fields) {
			if (f.getType() == main) {
				Annotation[] annots = f.getAnnotations();
				for (Annotation o : annots) {
					if (o.annotationType().getClass() == ManyToOne.class
							.getClass()) {
						name = f.getName();
					}
				}
			}
		}
		return name;

	}

	private static class PathVariableNamedValueInfo extends NamedValueInfo {

		private PathVariableNamedValueInfo(PathManyToOneName annotation) {
			super(annotation.value(), true, ValueConstants.DEFAULT_NONE);
		}
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter)
			throws ServletException {
		String paramType = parameter.getParameterType().getName();
		throw new ServletRequestBindingException(
				"Missing URI template variable '" + name
						+ "' for method parameter type [" + paramType + "]");

	}

}
