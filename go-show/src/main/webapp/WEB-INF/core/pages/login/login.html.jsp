<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>
<!DOCTYPE html>
<fis:html mapDir="/map"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<fis:require id="core:scripts/login.js" />
<fis:styles/>

<title>管理登录</title>
</head>
<body style="background: #FFF; color: #000; font: 75% Arial, Helvetica, sans-serif;">

	<div
		style="position: absolute; left: 50%; top: 50%; width: 500px; height: 230px; margin-left: -250px; margin-top: -115px;">
		<div
			style="border: 1px solid #CCC; background: #EEE; padding: 5px; text-align: left;">
			<p class="intro"></p>
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
				<font color="red"> 登录失败，原因: <c:out
						value="${errorMessage}" />。
				</font>	
			</c:if>
			<form method="post" name="login"
				action="<c:url value='j_spring_security_check'/>"
				id="loginSubmit">
				<fieldset
					style="border: none; border-left: 1px solid #EEE; padding-left: 3em;">
					<p style="margin: 0.5em 0;">
						用户名：<input type='text' tabindex="1" name='j_username' id='j_username'
							style="width: 10em; border: 1px solid #CCC; padding: 4px 2px;"
							value='<c:out value="${SPRING_SECURITY_LAST_USERNAME}"/>' />
					</p>
					<p style="margin: 0.5em 0;">
						密&nbsp;&nbsp;&nbsp;码：<input type='password' name='j_password'
							tabindex="2"
							style="width: 10em; border: 1px solid #CCC; padding: 4px 2px;">
					</p>
<c:if test="${IS_LOGIN_CAPTCHA=='1'}">
					<p style="margin: 0.1em 0;">验证码： <input type="text" maxlength="4" autocomplete="off" size="4"  name="captcha" style=" border: 1px solid #CCC; padding: 4px 2px;">
						<img border="0"
							 onclick="this.src='${S_URL}/validationCodeServlet.png?t=' + Math.random()"
							 id="codeimage" name="codeimage"
							 src="${S_URL}/validationCodeServlet.png" style="height: 22px;"></p>
</c:if>

					<p style="margin: 0.5em 0;">
						<input type="submit" class="button" tabindex="4" id="loginbtn" name="dologinbtn"
							value="登录管理平台"
							style="background: #DDD; border-top: 1px solid #EEE; border-right: 1px solid #BBB; border-bottom: 1px solid #BBB; border-left: 1px solid #EEE; padding: 3px; cursor: pointer;" />
		
					</p>
				</fieldset>
				<input type="hidden" name="dologin" value="yes" />
			</form>
		</div>
		<p style="margin: 0.5em 0; text-align: center; font-size: 10px;">
			Powered by <b>GoReport</b> 1.0&copy; 2012-2013 <a href="#"
				target="_blank" style="color: #006">GuGu.</a>
		</p>
	</div>
	<fis:scripts/>
</body>
</fis:html>
