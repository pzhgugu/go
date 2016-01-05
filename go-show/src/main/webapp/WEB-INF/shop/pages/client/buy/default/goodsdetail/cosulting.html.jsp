<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<!DOCTYPE html>
<fis:html mapDir="/map">
  <head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta charset="utf-8">
  <title>咨询</title>
  <fis:require id="shop:styles/base.css"/>
  <fis:require id="shop:styles/home_login.css"/>
  <fis:styles/>
  <fis:require id="common:widget/jquery/jquery.js"/>
  <fis:require id="common:widget/jquery/jquery.validation.js"/>
  </head>

  <body>

  <div class="ncs-guestbook">
    <div class="ncs-loading" id="cosulting_demo">
      <c:if test="${empty P_CONSULT_PAGE.content}">
        <div class="ncs-norecord">还没有咨询内容</div>
      </c:if>
      <c:forEach items="${P_CONSULT_PAGE.content}" var="consult">
        <div class="ncs-cosult-list">
          <dl class="asker">
            <dt>咨询网友：</dt>
            <dd>
                ${consult.memberName}
              <time class="ml20" pubdate="pubdate" datetime="<fmt:formatDate value="${consult.created}" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${consult.created}" pattern="yyyy-MM-dd HH:mm:ss"/></time>
            </dd></dl>
          <dl class="ask-con"><dt>咨询内容：</dt>
            <dd><p>${consult.consultContent}</p></dd>
          </dl>
        </div>
      </c:forEach>
      <div class="tr pr5 pb5">
      <fis:block url="shop:widget/tpl/pagination.html.jsp" >
        <fis:param name="page" value="P_CONSULT_PAGE"/>
        <fis:param name="paginationSize" value="9"/>
        <fis:param name="reqName" value="curpage"/>
        <fis:param name="url" value="${S_URL}/cl/goodsconsult/cosulting"/>
      </fis:block>
        </div>
      <form action="${S_URL}/cl/goodsconsult/submit" id="message" method="post">
        <input type="hidden" value="${P_GOODS.id}" name="goodsId">
        <input type="hidden" value="${P_GOODS.name}" name="goodsName" >
        <input type="hidden" value="${P_STORE_id}" name="storeId">
        <input type="hidden" value="0" name="isAnonymous" id="isAnonymous">
        <div class="ncs-consult-form">
          <div class="asker-info">
            <label for="gbTextfield"><strong>电子邮箱：</strong>
              <input type="text" value="" placeholder="非会员可输入邮件进行咨询，以便客服人员给您回执。"
                     class="text w300" id="gbTextfield" name="email"><span></span>
            </label>
            <label for="captcha"><strong>验证码：</strong>
              <input type="text" maxlength="4" autocomplete="off" size="4" id="captcha"
                     class="text w60" name="captcha"><span></span>
              <img border="0"
                   onclick="this.src='${S_URL}/validationCodeServlet.png?t=' + Math.random()"
                   id="codeimage" name="codeimage"
                   src="${S_URL}/validationCodeServlet.png"><span>点击图片换一张。</span></label>
          </div>
          <div class="ask-content"><strong>咨询内容：</strong>
            <textarea class="textarea w700 h60" id="textfield3" name="consultContent"></textarea><span></span>
          </div>
          <div class="bottom"><a class="ncs-btn ncs-btn-red" title="发布咨询" nc_type="consult_submit"
                                 href="JavaScript:void(0);">发布咨询</a><span
                  id="consultcharcount"><span class="counter">还可以输入<em
                  class="">120</em>字</span></span></div>
        </div>
      </form>

    </div>
  </div>
  </body>

  <fis:script>
    $(function () {
    $('a[nc_type="consult_submit"]').click(function () {
    if ($("#message").valid()) {
    $.getJSON('${S_URL}/cl/goodsconsult/submit', {
    'goodsId': '${P_GOODS.id}'
    ,'email': $("#gbTextfield").val()
    ,'goodsName':'${P_GOODS.name}'
    ,'storeId':'${P_STORE_id}'
    ,'isAnonymous':$("#isAnonymous").val()
    ,'consultContent': $("#textfield3").val()
    }, function (data) {
    if (data.done == true) {
    $("#cosulting_demo").load('${S_URL}/cl/goodsconsult/cosulting?style=${P_STYLE}&goodsId=${P_GOODS.id}&storeId=${P_STORE_id}');
    } else {
    document.getElementById('codeimage').src = '${S_URL}/validationCodeServlet.png?t=' + Math.random();
    alert(data.message);
    }
    });
    } else {
    document.getElementById('codeimage').src = '${S_URL}/validationCodeServlet.png?t=' + Math.random();
    }
    });
    $('#message').validate({
    errorPlacement: function (error, element) {
    var error_td = element.next('span');
    error_td.next('.field_notice').hide();
    error_td.append(error);
    },
    rules: {
    consultContent: {
    required: true,
    maxlength: 120
    },
    email: {
    email: true
    }
    , captcha: {
    required: true,
    minlength: 4,
    remote: {
    url: '${S_URL}/cl/goodsconsult/validation',
    type: 'get',
    data: {
    captcha: function () {
    return $('#captcha').val();
    }
    }
    }
    }
    },
    messages: {
    consultContent: {
    required: '咨询内容不能为空',
    maxlength: '咨询内容不能超过120个字符'
    },
    email: {
    email: '邮箱地址不正确'
    }
    , captcha: {
    required: '请填写验证码',
    minlength: '验证码错误',
    remote: '验证码错误'
    }
    }
    });

    // textarea 字符个数动态计算
    $("#textfield3").charCount({
    allowed: 120,
    warning: 10,
    counterContainerID: 'consultcharcount',
    firstCounterText: '还可以输入',
    endCounterText: '字',
    errorCounterText: '已经超出'
    });
    });
  </fis:script>
  <fis:scripts/>
</fis:html>
