<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis"%>


<div class="ncsc-layout wrapper">
  <fis:block url="shop:widget/tpl/seller/layoutLeftGoods.html.jsp" />
  <div class="ncsc-layout-right" id="layoutRight">
    <fis:block url="shop:widget/tpl/seller/nav.html.jsp" />
      <div id="mainContent" class="main-content">
        <div class="tabmenu">
          <ul class="tab pngFix">
            <li class="active"><a href="${S_URL}/se/warning/edit">库存警报</a></li></ul>
        </div>
        <div class="ncsc-form-default">
          <form id="my_store_form" action="se/warning/save?for=xml" method="post">
            <input type="hidden" value="ok" name="form_submit">
            <dl>
              <dt>库存警报：</dt>
              <dd>
                <input type="text" value="${P_STOREWARNING.warningValue}" id="store_storage_alarm" maxlength="10" name="store_storage_alarm" class="text w60">
                <p class="hint">请填写0~255之间的数字。</p>
                <p class="hint">为0表示不警报。在出售中的商品列表页，如商品库存不足库存会显示为红字。</p>
              </dd>
            </dl>
            <div class="bottom">
              <label class="submit-border"><input type="submit" value="保存" class="submit"></label>
            </div>
          </form>
        </div>
        <script type="text/javascript">
          var SITEURL = "${S_URL}";
          $(function(){
            $('#my_store_form').validate({
              submitHandler:function(form){
                ajaxpost('my_store_form', '', '', 'onerror')
              },
              rules : {
                store_storage_alarm: {
                  required : true,
                  digits : true,
                  max : 255
                }
              },
              messages : {
                store_storage_alarm: {
                  required : '请填写数字',
                  digits : '请正确数字',
                  max : '不能超过255'
                }
              }
            });

          });
        </script>
      </div>
  </div>
</div>


<fis:require id="shop:scripts/common_select.js"/>
<fis:require id="common:widget/jquery/perfect-scrollbar.js"/>
<fis:require id="common:widget/jquery/jquery.mousewheel.js"/>
