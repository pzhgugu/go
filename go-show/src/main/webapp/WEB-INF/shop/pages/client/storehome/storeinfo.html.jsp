<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>


<div class="ncs-info">
  <div class="title">
    <h4>${P_STORE.name}</h4>
  </div>
  <div class="content">
    <dl class="all-rate">
      <dt>综合评分：</dt>
      <dd>
        <div class="rating"><span style="width: 100%"></span></div>
        <em><fmt:formatNumber value="${P_STORE_SCORE.multipleScore}"  minFractionDigits="1" /></em>分</dd>
    </dl>
    <div class="detail-rate">
      <h5><strong>店铺动态评分</strong>与行业相比</h5>
      <ul>
        <li> 描述相符<span class="credit"><fmt:formatNumber value="${P_STORE_SCORE.descScore}" minFractionDigits="1" /> 分</span> <span class="equal"><i></i>持平<em>----</em></span> </li>
        <li> 服务态度<span class="credit"><fmt:formatNumber value="${P_STORE_SCORE.serviceScore}" minFractionDigits="1" /> 分</span> <span class="equal"><i></i>持平<em>----</em></span> </li>
        <li> 发货速度<span class="credit"><fmt:formatNumber value="${P_STORE_SCORE.deliveryScore}" minFractionDigits="1" /> 分</span> <span class="equal"><i></i>持平<em>----</em></span> </li>
      </ul>
    </div>
    <dl class="messenger">
      <dt>联系方式：</dt>
      <dd><span member_id="1"></span>
        <c:if test="${!empty P_STORE.storeWW}">
          <a href="http://amos.im.alisoft.com/msg.aw?v=2&uid=${P_STORE.storeWW}&site=cntaobao&s=1&charset=UTF-8" target="_blank">
            <img border="0" style="vertical-align: middle; margin-bottom: 8px;" alt="点击这里给我发消息" src="http://amos.im.alisoft.com/online.aw?v=2&uid=${P_STORE.storeWW}&site=cntaobao&s=2&charset=UTF-8"></a>
        </c:if>
        <c:if test="${!empty P_STORE.storeQQ}">
        <img  style="CURSOR: pointer" onclick="javascript:window.open('http://b.qq.com/webc.htm?new=0&sid=${P_STORE.storeQQ}&o=${S_URL}&q=7', '_blank', 'height=502, width=644,toolbar=no,scrollbars=no,menubar=no,status=no');"  border="0" SRC=http://wpa.qq.com/pa?p=1:${P_STORE.storeQQ}:10 alt="联系客服">
        </c:if>

      </dd>
    </dl>
    <dl class="no-border">
      <dt>公司名称：</dt>
      <dd>${P_STORE.storeCompanyName}</dd>
    </dl>
    <dl>
      <dt>所&nbsp;&nbsp;在&nbsp;&nbsp;地：</dt>
      <dd>${P_STORE.areaId}</dd>
    </dl>
    <div class="goto"><a target="_blank" href="${S_URL}/cl/store/home?store_id=${P_STORE.id}">进入商家店铺</a>
      <a href="javascript:collect_store('1','count','store_collect')">收藏店铺<em nctype="store_collect">0</em></a></div>
  </div>
</div>