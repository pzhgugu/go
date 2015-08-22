<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>
<fis:require id="core:widget/tpl/DetailQuery/detailquery.js" />    

<div id="layoutObj"></div>

<script type="text/javascript">

GLOBAL.P.MODULES="${P_TPL_MODULES}";
GLOBAL.P.CLASSNAME="${P_CLASS_NAME}";
GLOBAL.P.CLASS="${P_CLASS}";

GLOBAL.P.P_DETAIL_QUERY_TREE=${P_DETAIL_QUERY_TREE};
GLOBAL.P.P_DETAIL_QUERY_OFTEN=${P_DETAIL_QUERY_OFTEN};
GLOBAL.P.P_DETAIL_QUERY_ALL=${P_DETAIL_QUERY_ALL};

</script>