<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<div class="ncs-message-bar">
  <div class="default">
    <h5>${P_STORE.name}</h5>
    <span member_id="1"></span>
    <c:if test="${!empty P_STORE.storeWW}">
      <a href="http://amos.im.alisoft.com/msg.aw?v=2&uid=${P_STORE.storeWW}&site=cntaobao&s=1&charset=UTF-8" target="_blank">
        <img border="0" style="vertical-align: middle; margin-bottom: 8px;" alt="点击这里给我发消息" src="http://amos.im.alisoft.com/online.aw?v=2&uid=${P_STORE.storeWW}&site=cntaobao&s=2&charset=UTF-8"></a>
    </c:if>
    <c:if test="${!empty P_STORE.storeQQ}">
      <img  style="CURSOR: pointer" onclick="javascript:window.open('http://b.qq.com/webc.htm?new=0&sid=${P_STORE.storeQQ}&o=${S_URL}&q=7', '_blank', 'height=502, width=644,toolbar=no,scrollbars=no,menubar=no,status=no');"  border="0" SRC=http://wpa.qq.com/pa?p=1:${P_STORE.storeQQ}:10 alt="联系客服">
    </c:if>
  </div>
</div>
