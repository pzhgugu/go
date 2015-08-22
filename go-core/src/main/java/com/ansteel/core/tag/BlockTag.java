package com.ansteel.core.tag;

import javax.servlet.jsp.JspException;

import org.apache.taglibs.standard.tag.common.core.ImportSupport;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;

import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.utils.FisUtils;

public class BlockTag extends ImportSupport {
	// *********************************************************************
	// 'Private' state (implementation details)

	private String context_; // stores EL-based property
	private String charEncoding_; // stores EL-based property
	private String url_; // stores EL-based property

	// *********************************************************************
	// Constructor

	/**
	 * Constructs a new ImportTag. As with TagSupport, subclasses should not
	 * provide other constructors and are expected to call the superclass
	 * constructor
	 */
	public BlockTag() {
		super();
		init();
	}

	// *********************************************************************
	// Tag logic

	// evaluates expression and chains to parent
	public int doStartTag() throws JspException {

		// evaluate any expressions we were passed, once per invocation
		evaluateExpressions();

		// chain to the parent implementation
		return super.doStartTag();
	}

	// Releases any resources we may have (or inherit)
	public void release() {
		super.release();
		init();
	}

	// *********************************************************************
	// Accessor methods

	// for EL-based attribute
	public void setUrl(String url_) {
		this.url_ = url_;
	}

	public void setContext(String context_) {
		this.context_ = context_;
	}

	public void setCharEncoding(String charEncoding_) {
		this.charEncoding_ = charEncoding_;
	}

	// *********************************************************************
	// Private (utility) methods

	// (re)initializes state (during release() or construction)
	private void init() {
		// null implies "no expression"
		url_ = context_ = charEncoding_ = null;
	}

	/* Evaluates expressions as necessary */
	private void evaluateExpressions() throws JspException {
		/*
		 * Note: we don't check for type mismatches here; we assume the
		 * expression evaluator will return the expected type (by virtue of
		 * knowledge we give it about what that type is). A ClassCastException
		 * here is truly unexpected, so we let it propagate up.
		 */

		url = (String) ExpressionUtil.evalNotNull("import", "url", url_,
				String.class, this, pageContext);
		//IBlockRegister blockRegister = ContextHolder.getSpringBean("blockRegister");
		//blockRegister.run(pageContext.getRequest(),url);
		url=FisUtils.path(url);
		if (url == null || url.equals(""))
			throw new NullAttributeException("import", "url");

		context = (String) ExpressionUtil.evalNotNull("import", "context",
				context_, String.class, this, pageContext);
		charEncoding = (String) ExpressionUtil.evalNotNull("import",
				"charEncoding", charEncoding_, String.class, this, pageContext);
	}
}
