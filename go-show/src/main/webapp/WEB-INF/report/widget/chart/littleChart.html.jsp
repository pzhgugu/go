<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<!DOCTYPE html>
<fis:html mapDir="/map"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>图表显示</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<fis:require id="common:scripts/global.js" />
<fis:require id="report:scripts/jscharts.js" />  
<fis:styles/>
</head>
<body>
	<div id="graph">加载中...</div>
</body>

<fis:scripts/>
<script type="text/javascript">
var myData = new Array();

var myChart = new JSChart('graph', '${Chart.type}');
myChart.setTitle('${Chart.title}');
myChart.setTitleColor('#8E8E8E');
myChart.setAxisNameX('');
myChart.setAxisNameY('');
myChart.setAxisColor('#C4C4C4');
myChart.setAxisNameFontSize(160);
myChart.setAxisNameColor('#999');
myChart.setAxisValuesColor('#777');
myChart.setAxisColor('#B5B5B5');
myChart.setAxisWidth(1);
myChart.setAxisPaddingTop(60);
myChart.setAxisPaddingBottom(40);
myChart.setAxisPaddingLeft(45);
myChart.setTitleFontSize(11);


<c:if test="${Chart.type=='bar'}">
<c:forEach items="${NameData}" var="entry">  
myData.push(['${entry.key}',${entry.value}]);
</c:forEach>
myChart.setDataArray(myData);
myChart.setBarValuesColor('<c:choose><c:when test="${!empty Chart.barValuesColor}">${Chart.barValuesColor}</c:when><c:otherwise>#2F6D99</c:otherwise></c:choose>');
myChart.setBarOpacity(0.5);
myChart.setBarBorderWidth(0);
myChart.setBarSpacingRatio(50);
myChart.setBarOpacity(0.9);
myChart.colorizeBars([${Chart.colorizeBars}]);
</c:if>

<c:if test="${Chart.type=='pie'}">
<c:forEach items="${NameData}" var="entry">  
myData.push(['${entry.key}',${entry.value}]);
</c:forEach>
myChart.setDataArray(myData);
myChart.setPiePosition(288, 158);
myChart.setPieRadius(80);
myChart.setPieUnitsColor('#555');
myChart.colorizePie([${Chart.colorizeBars}]);
</c:if>

<c:if test="${Chart.type=='line'}">
<c:forEach items="${NameData}" var="entry">  
myData.push(['${entry.key}',${entry.value}]);
</c:forEach>
myChart.setDataArray(myData);
myChart.setGraphExtend(true);
myChart.setLineWidth(4);
myChart.setLineColor('<c:choose><c:when test="${!empty Chart.barValuesColor}">${Chart.barValuesColor}</c:when><c:otherwise>#9F0505</c:otherwise></c:choose>');
myChart.setSize(<%=request.getParameter("width")%> , <%=request.getParameter("height")%> );//设置线图大小
</c:if>

myChart.setFlagRadius(6);
<c:forEach items="${NameData}" var="entry">  
myChart.setTooltip(['${entry.key}', '${entry.value}']);
</c:forEach>
myChart.setTooltipPosition('nw');
myChart.setTooltipOffset(3);
myChart.setSize(<%=request.getParameter("width")%> , <%=request.getParameter("height")%>);//设置图表背景大小
myChart.setBackgroundImage('${Chart.backgroundImage}');
myChart.draw();

</script>
</fis:html>