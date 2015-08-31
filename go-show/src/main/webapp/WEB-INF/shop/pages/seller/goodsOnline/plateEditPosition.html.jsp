<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="eject_con">
  <div id="warning" class="alert alert-error"></div>
  <form method="post" action="se/goodsonline/save/position?for=ajax" id="plate_form">
    <input type="hidden" name="form_submit" value="ok" />
    <input type="hidden" name="commonid" value="${P_COMMONID}" />
    <dl>
      <dt>关联版式：</dt>
      <dd>
        <p>
          <label>顶部版式</label>
          <select name="plate_top">
            <option value="">请选择</option>
            <c:forEach items="${P_STOREPLATE_LIST}" var="storePlate">
              <c:if test="${storePlate.platePosition==1}">
                <option value="${storePlate.id}">${storePlate.plateName}</option>
              </c:if>
            </c:forEach>
          </select>
        </p>
        <p>
          <label>底部版式</label>
          <select name="plate_bottom">
            <option value="">请选择</option>
            <c:forEach items="${P_STOREPLATE_LIST}" var="storePlate">
              <c:if test="${storePlate.platePosition!=1}">
                <option value="${storePlate.id}">${storePlate.plateName}</option>
              </c:if>
            </c:forEach>
          </select>
        </p>
        <p class="hint">如不填，所有已选版式将制空，请谨慎操作</p>
      </dd>
    </dl>
    <div class="bottom">
      <label class="submit-border"><input type="submit" class="submit" value="提交"/></label>
    </div>
  </form>
</div>
<script>
  $(function(){
    $('#plate_form').submit(function(){
      ajaxpost('plate_form', '', '', 'onerror');
      return false;
    });
  });
</script>