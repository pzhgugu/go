<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>
 <div class="left">
    <div class="nch-module nch-module-style01">
      <div class="title">
        <h3>${P_PARENT_NEWSCATEGORY.alias}</h3>
      </div>
      <div class="content">
        <ul class="nch-sidebar-article-class">
        <c:forEach items="${P_CHILD_NEWSCATEGORY}" var="category" >
        	<li><a href="${S_URL}/cl/newshow/list?type=${category.name}">${category.alias}</a></li>
        </c:forEach>
        </ul>
      </div>
    </div>
    <div class="nch-module nch-module-style03">
      <div class="title">
        <h3>最新文章</h3>
      </div>
      <div class="content">
        <ul class="nch-sidebar-article-list">
                              <li><i></i><a href="#" target="_blank">火爆销售中</a></li>
               </ul>
      </div>
    </div>
  </div>