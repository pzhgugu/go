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
        <title>商家入驻</title>
        <fis:require id="shop:styles/base.css"/>
        <fis:require id="shop:scripts/dialog/dialog.css"/>
        <fis:require id="shop:styles/store_joinin.css"/>

        <fis:styles/>
    </head>


    <body>
    <fis:block url="shop:pages/client/store/header.html.jsp"/>

    <div class="container wrapper">
        <fis:block url="shop:pages/client/store/sidebar.html.jsp"/>

        <div class="main">
            <c:if test="${P_STOREJOININ.joininState=='10'}">
                <div class="explain"><i></i>入驻申请已经提交，请等待管理员审核</div>
                <div class="bottom"></div>
            </c:if>
            <c:if test="${P_STOREJOININ.joininState=='11'}">
                <div class="explain"><i></i>已经提交，请等待管理员核对后为您开通店铺</div>
                <div class="bottom"></div>
            </c:if>
            <c:if test="${P_STOREJOININ.joininState=='30'}">
                <div class="explain"><i></i>审核失败:${P_STOREJOININ.joininMessage}</div>
                <div class="bottom">
                    <a class="btn" href="${S_URL}/storejoinin/step1">下一步</a>
                </div>
            </c:if>

            <c:if test="${P_STOREJOININ.joininState=='20'}">
                <div class="explain"><i></i>审核成功，请完成付款，付款后点击下一步提交付款凭证</div>
                <div class="bottom">
                    <a class="btn" href="${S_URL}/storejoinin/pay">下一步</a>
                </div>
            </c:if>


            <c:if test="${P_STOREJOININ.joininState=='31'}">
                <div class="explain"><i></i>付款审核失败:${P_STOREJOININ.joininMessage}</div>
                <div class="bottom">
                    <a class="btn" href="${S_URL}/storejoinin/pay">下一步</a>
                </div>
            </c:if>

        </div>
    </div>

    <fis:block url="cms:widget/tpl/button.html.jsp"/>
    <fis:block url="cms:widget/tpl/footer.html.jsp"/>


    <fis:require id="common:widget/jquery/jquery.js"/>
    <fis:scripts/>
    </body>
</fis:html>