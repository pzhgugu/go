<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="quick-login">
    <form id="login_form" class="bg" onsubmit="ajaxpost('login_form', 'ajaxLogin?for=xml&inajax=1', '', 'onerror');return false;" method="post" action="j_spring_security_check?for=xml&inajax=1" target="">
        <dl>
            <dt>用户名</dt>
            <dd>
                <input type="text" id="j_username" name="j_username" autocomplete="off" class="text">
            </dd>
        </dl>
        <dl>
            <dt>密&nbsp;&nbsp;&nbsp;码</dt>
            <dd>
                <input type="password" id="j_password" autocomplete="off" name="j_password" class="text">
            </dd>
        </dl>
        <dl>
            <dt>验证码</dt>
            <dd>
                <input type="text" size="10" maxlength="4" id="captcha" class="text fl w60" name="captcha">
                <img border="0"
                     onclick="this.src='${S_URL}/validationCodeServlet.png?t=' + Math.random()"
                     id="codeimage" name="codeimage"
                     src="${S_URL}/validationCodeServlet.png"><span>点击图片换一张。</span></dd>
        </dl>
        <ul>
            <li>›&nbsp;如果您没有登录帐号，请先<a class="register"
                                      href="#">注册会员</a>然后登录
            </li>
            <li>›&nbsp;如果您<a class="forget"
                             href="#">忘记密码</a>？，申请找回密码
            </li>
        </ul>
        <div class="enter">
            <input type="submit" name="Submit" value="&nbsp;" class="submit">
        </div>
        <input type="hidden" name="ref_url"
               value="<%=request.getHeader("Referer")%>">
    </form>
</div>