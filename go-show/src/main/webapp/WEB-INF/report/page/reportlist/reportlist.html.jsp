<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<!DOCTYPE html>
<fis:html mapDir="/map"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<fis:out id="common:scripts/global.js" />
<fis:require id="report:scripts/reportlist.js" />  
<fis:styles/>
</head>
<body>
<div id="layoutObj"></div>
<script type="text/javascript">

GLOBAL.S.URL = "${S_URL}";
GLOBAL.S.URL_R = "${S_URL_R}";
GLOBAL.P.MODULES="${P_TPL_MODULES}";
GLOBAL.P.CLASSNAME="${P_CLASS_NAME}";
GLOBAL.P.REPORT_TYPE="${P_REPORT_TYPE}";
GLOBAL.P.REPORT_NAME="${P_REPORT_NAME}";
GLOBAL.P.REPORT="${P_REPORT}";
GLOBAL.P.DefaultSecurity="${DefaultSecurity}";
GLOBAL.P.DefaultPermission="${DefaultPermission}";


GLOBAL.P.P_GRID_HEADER="${P_GRID_HEADER}";
GLOBAL.P.P_GRID_COLUMNIDS="${P_GRID_COLUMNIDS}";
GLOBAL.P.P_GRID_WIDTH="${P_GRID_WIDTH}";
GLOBAL.P.P_GRID_COLALIGN="${P_GRID_COLALIGN}";
GLOBAL.P.P_GRID_COLTYPES="${P_GRID_COLTYPES}";
GLOBAL.P.P_GRID_COLSORTING="${P_GRID_COLSORTING}";
GLOBAL.P.P_GRID_VALIDATORS=${P_GRID_VALIDATORS};
GLOBAL.P.P_GRID_FORMGRIDASSEMBLE=${P_GRID_FORMGRIDASSEMBLE};

GLOBAL.P.P_SIMPLE_QUERY_NAMES=${P_SIMPLE_QUERY_NAMES};
GLOBAL.P.P_SIMPLE_QUERY_VALUES=${P_SIMPLE_QUERY_VALUES};
GLOBAL.P.P_SIMPLE_QUERY_OPERATOR=${P_SIMPLE_QUERY_OPERATOR};
GLOBAL.P.P_SIMPLE_QUERY_FORM_TYPE=${P_SIMPLE_QUERY_FORM_TYPE};

</script>

<fis:scripts/>
</body>
</fis:html>