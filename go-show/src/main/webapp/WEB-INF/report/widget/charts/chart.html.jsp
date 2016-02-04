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
<fis:require id="common:widget/charts/echarts.min.js" />
<fis:styles/>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
</body>

<fis:scripts/>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));


    var xAxisData = new Array();
    var seriesData = new Array();
    <c:forEach items="${NameData}" var="entry">
        xAxisData.push('${entry.key}');
        seriesData.push('${entry.value}');
    </c:forEach>

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '${Chart.title}'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: xAxisData
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: seriesData
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</fis:html>