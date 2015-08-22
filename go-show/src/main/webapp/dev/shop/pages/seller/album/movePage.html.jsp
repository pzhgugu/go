<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/fis" prefix="fis"%>
<div class="eject_con">
  <div id="warning"></div>
    <form action="se/albumpic/image/move?for=xml" onsubmit="ajaxpost('category_form','','','onerror')" target="_parent" method="post" id="category_form">
    <input type="hidden" value="${P_ID}" name="id">
    <input type="hidden" value="${P_CID}" name="category_id">
    <dl>
      <dt>选择相册：</dt>
      <dd>
        <select class="w100" name="cid">
        <c:forEach items="${P_ALBUM_CLASSLIST}" var="albums">
              <option class="w100" value="${albums.id}">${albums.aclassName}</option>               
            </c:forEach>
                  </select>
      </dd>
    </dl>
    <div class="bottom">
      <label class="submit-border">
        <input type="submit" value="开始转移" class="submit">
      </label>
    </div>
  </form>
  </div>