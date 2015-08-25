<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>
<%@ page import="org.springframework.data.domain.Page"%>
<%@ page import="com.ansteel.core.utils.RequestUtils"%>

<%
String pageName=request.getParameter("page");
Integer paginationSize=Integer.valueOf(request.getParameter("paginationSize"));
Page dataPage = (Page)request.getAttribute(pageName);
String reqName=request.getParameter("reqName");
String url=request.getParameter("url");

url=RequestUtils.getPageUrlParame(request,reqName,url);

int current =  dataPage.getNumber() + 1;  
int begin = Math.max(1, current - paginationSize/2);  
int end = Math.min(begin + (paginationSize - 1), dataPage.getTotalPages());  
long totalCount = dataPage.getTotalElements();  
request.setAttribute("totalCount", totalCount);  
request.setAttribute("current", current);  
request.setAttribute("begin", begin);  
request.setAttribute("end", end);  
request.setAttribute("total", dataPage.getTotalPages());  
request.setAttribute("url", url); 
%>

<div class="pagination">
  <ul>
  
  
  <% if(dataPage.hasPreviousPage()) {%>  
            <li><a href="${url}1" class="demo"> <span>首页</span></a></li>  
            <li><a href="${url}${current-1}" class="demo"><span>上一页</span></a></li>  
  <%} else { %>  
            <li><span>首页</span></li>  
            <li><span>上一页</span></li>  
  <%} %>  
  
    <c:forEach var="i" begin="${begin}" end="${end}">  
            <c:choose>  
                <c:when test="${i == current}">  
                    <li><span class="currentpage">${i}</span></li>  
                </c:when>  
                <c:otherwise>  
                    <li><a href="${url}${i}" class="demo"><span>${i}</span></a></li>  
                </c:otherwise>  
            </c:choose>  
        </c:forEach>  
        
         <% if (dataPage.hasNextPage()){%>  
                <li><a href="${url}${current+1}"  class="demo"><span>下一页</span></a></li>  
                <li><a href="${url}${total}" class="demo"><span>尾页</span></a></li>  
         <%} else {%>  
                <li><span>下一页</span></li>  
                <li><span>尾页</span></li>  
         <%} %>  
  </ul>
</div>

