<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<div class="quick-login">
  <form class="bg" onsubmit="ajaxpost('login_form', 'http://localhost/shopnc/shop/index.php?act=login', '', 'onerror');return false;" method="post" action="http://localhost/shopnc/shop/index.php?act=login" id="login_form">
    <input type="hidden" value="tlnuItjjGdjvmldc5KTfjySgxvad1Ka" name="formhash">    <input type="hidden" value="ok" name="form_submit">
    <input type="hidden" value="dc7d5ddf" name="nchash">
    <dl>
      <dt>用户名</dt>
      <dd>
        <input type="text" id="user_name" name="user_name" autocomplete="off" class="text">
      </dd>
    </dl>
    <dl>
      <dt>密&nbsp;&nbsp;&nbsp;码</dt>
      <dd>
        <input type="password" id="password" autocomplete="off" name="password" class="text">
      </dd>
    </dl>
    <dl>
      <dt>验证码</dt>
      <dd>
        <input type="text" size="10" maxlength="4" id="captcha" class="text fl w60" name="captcha">
        <img border="0" onclick="this.src='http://localhost/shopnc/shop/index.php?act=seccode&amp;op=makecode&amp;nchash=dc7d5ddf&amp;t=' + Math.random()" id="codeimage" name="codeimage" title="看不清，换一张" src="http://localhost/shopnc/shop/index.php?act=seccode&amp;op=makecode&amp;nchash=dc7d5ddf" class="fl ml10"><span></span></dd>
    </dl>
    <ul>
      <li>›&nbsp;如果您没有登录帐号，请先<a class="register" href="http://localhost/shopnc/shop/index.php?act=login&amp;op=register">注册会员</a>然后登录</li>
      <li>›&nbsp;如果您<a class="forget" href="http://localhost/shopnc/shop/index.php?act=login&amp;op=forget_password">忘记密码</a>？，申请找回密码</li>
    </ul>
    <div class="enter">
      <input type="submit" name="Submit" value="&nbsp;" class="submit">
    </div>
    <input type="hidden" name="ref_url" value="http://localhost/shopnc/shop/index.php?act=goods&amp;op=index&amp;goods_id=256">
  </form>
</div>