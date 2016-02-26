<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<fis:html mapDir="/map">

  <div class="right-content">

    <div class="path">
      <div><a href="${S_URL}/shop/member/index">我的商城</a><span>»</span>
        <a href="${S_URL}/shop/member/set">个人资料</a><span>»</span>修改密码</div>
    </div>

    <div class="main">


      <div class="wrap">
        <fis:block url="shop:pages/client/member/${P_STYLE}/tabmenu.html.jsp" />
        <div class="ncu-form-style">
          <form action="shop/member/passwd_save?for=xml" name="password_form" id="password_form" method="post">
            <input type="hidden" value="ok" name="form_submit">
            <dl>
              <dt class="required"><em class="pngFix"></em>您的密码：</dt>
              <dd>
                <input type="password" id="orig_password" name="orig_password" class="text" maxlength="40">
                <label class="error" generated="true" for="orig_password"></label>
              </dd>
            </dl>
            <dl>
              <dt class="required"><em class="pngFix"></em>新密码：</dt>
              <dd>
                <input type="password" id="new_password" name="new_password" class="password" maxlength="40">
                <label class="error" generated="true" for="new_password"></label>
              </dd>
            </dl>
            <dl>
              <dt class="required"><em class="pngFix"></em>确认密码：</dt>
              <dd>
                <input type="password" id="confirm_password" name="confirm_password" class="password" maxlength="40">
                <label class="error" generated="true" for="confirm_password"></label>
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
          $('#password_form').validate({
            submitHandler:function(form){
              ajaxpost('password_form', '', '', 'onerror')
            },
            rules : {
              orig_password : {
                required : true
              },
              new_password : {
                required   : true,
                minlength  : 6,
                maxlength  : 20
              },
              confirm_password : {
                required   : true,
                equalTo    : '#new_password'
              }
            },
            messages : {
              orig_password : {
                required : '原始密码不能为空'
              },
              new_password  : {
                required   : '新密码不能为空',
                minlength  : '密码长度大于等于6位小于等于20位'
              },
              confirm_password : {
                required   : '确认密码不能为空',
                equalTo    : '新密码与确认密码不相同'
              }
            }
          });
        });
      </script>
    </div>

  </div>

</fis:html>