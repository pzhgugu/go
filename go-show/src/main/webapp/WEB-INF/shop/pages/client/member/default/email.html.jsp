<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<fis:html mapDir="/map">
  <div class="right-content">

  <div class="path">
    <div><a href="${S_URL}/shop/member/index">我的商城</a><span>»</span>
      <a href="${S_URL}/shop/member/set">个人资料</a><span>»</span>修改电子邮件</div>
  </div>

    <div class="main">


      <div class="wrap">
        <fis:block url="shop:pages/client/member/${P_STYLE}/tabmenu.html.jsp" />
        <div class="ncu-form-style">
          <form action="shop/member/email_save?for=xml" id="email_form" method="post">
            <input type="hidden" value="ok" name="form_submit">
            <dl>
              <dt class="required"><em class="pngFix"></em>您的密码：</dt>
              <dd>
                <input type="password" id="orig_password" name="orig_password" maxlength="40" class="password">
                <label class="error" generated="true" for="orig_password"></label>
              </dd>
            </dl>
            <dl>
              <dt class="required"><em class="pngFix"></em>电子邮件：</dt>
              <dd>
                <input type="text" id="email" name="email" maxlength="40" class="text">
                <label class="error" generated="true" for="email"></label>
              </dd>
            </dl>
            <dl class="bottom">
              <dt>&nbsp;</dt>
              <dd>
                <input type="submit" value="确认提交" class="submit">
              </dd>
            </dl>
          </form>
        </div>
      </div>
      <script type="text/javascript">
        $(function(){
          $('#email_form').validate({
            submitHandler:function(form){
              ajaxpost('email_form', '', '', 'onerror')
            },
            rules : {
              orig_password : {
                required : true
              },
              email : {
                required   : true
              }
            },
            messages : {
              orig_password : {
                required : '密码不能为空'
              },
              email : {
                required : '电子邮件不能为空',
                email    : '电子邮件格式不正确',
                remote	 : '该电子邮箱已经存在'
              }
            }
          });
        });
      </script>
    </div>

  </div>
</fis:html>
