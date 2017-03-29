<%@ page language="java" import="java.util.*"
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/WEB-INF/content/commonJsp/base.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户汇总信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="客户汇总信息">

<script type="text/javascript">
    var oneResultGridVars = {
        dwName : "DW02_PS_BANK_ET_DATA_RESULT",
        dwInfos : [],
        gridDatafields : [],
        gridColumns : []
    };
</script>
<style type="text/css">
.col_label{
  float:left;
  height:28px;
  font-size:15px;
  text-align:center;
  margin-top:4px;
}
.inputLabel{
  float:left;
  margin:4px 10px;
}
</style>
</head>

<body>
    <div id="tab1_oneResultGrid" class="contentSplitter" />
    <script type="text/javascript" src="resources/uijs/commonjs/initGrid.js"></script>
    <!--  id="jqxwidgets" -->
    <script type="text/javascript">
        initPagingGridFun("tab1_oneResultGrid", oneResultGridVars);
    </script>

</body>
</html>