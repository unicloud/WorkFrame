<%@ page language="java" import="java.util.*"
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/WEB-INF/content/commonJsp/base.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据窗维护</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="数据窗维护">

<script type="text/javascript">
    var oneResultGridVars = {
        queryDwName  : "DW03_DWMAINTAIN_QUERY",
        resultDwName : "DW03_DWMAINTAIN_RESULT",
        //查询窗口
        queryDwInfos : [],
        queryDatafields : [],
        queryColumns : [],
        //数据窗口
        resulDwInfos : [],
        resultDatafields : [],
        resultColumns : []
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
    <div style="overflow: hidden;">
        <div id="tab1_oneSplitter" class="contentSplitter">
            <div>
                <div style="border:none;" id="tab1_normQueryGrid"></div>
            </div>
            <div>
                <div style="border:none;" id="tab1_oneResultGrid"></div>
            </div>
        </div>
    </div>
    <div id="tab1_oneResultGridEditWindow">
      <div id='tab1_oneResultGridEditWindowHeader'>
        <span>信息编辑窗口</span>
      </div>
      <div id = 'tab1_oneResultGridEditWindowContent'></div>
    </div>
    <script type="text/javascript">
        $(".contentSplitter").jqxSplitter({ theme : sysTheme,width: "100%", height: "100%",
        orientation: 'horizontal',
        panels: [{ size: "20%"}, {size: "80%"}] 
        });
    </script>
    <script type="text/javascript" src="resources/uijs/commonjs/initGrid.js"></script>
    <script type="text/javascript" src="resources/uijs/commonjs/initCustomBtns.js"></script>
    <!--  id="jqxwidgets" -->
    <script type="text/javascript">
        initNormQueryWindow("tab1_normQueryGrid", oneResultGridVars);
        initPagingGrid("tab1_oneResultGrid", oneResultGridVars, true);
        initCurdBtns("toolbartab1_oneResultGrid", "tab1_oneResultGrid", oneResultGridVars, "tab1");
    </script>

</body>
</html>