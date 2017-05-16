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
        queryDwName  : "DW02_MD_CABIN_GRADE_RESULT",
        resultDwName : "DW02_MD_CABIN_GRADE_RESULT",
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
    <div id="tabsMain">
      <ul>
          <li id="tab1">测试数据</li>
      </ul>
      <!--Tab1-->
      <div style="overflow: hidden;">
          <div id="tab1_oneSplitter" class="contentSplitter">
              <div>
                  <div id="tab1_widget" class="tabswidget">
                      <ul>
                          <li>灵活查询</li>
                          <li>普通查询</li>
                      </ul>
                      <div style="overflow: hidden;">
                          <div style="border:none;" id="tab1_flexQueryGrid"></div>
                      </div>
                      <div class="overflow: hidden;">
                          <div style="border:none;" id="tab1_normQueryGrid"></div>
                      </div>
                  </div>
              </div>
              <div>
                  <div style="border:none;" id="tab1_oneResultGrid"></div>
              </div>
          </div>
      </div>
    </div>
    <script type="text/javascript">
        $("#tabsMain").jqxTabs({theme : sysTheme,width: "100%", height: "100%" });

        $(".contentSplitter").jqxSplitter({ theme : sysTheme,width: "100%", height: "100%",
        orientation: 'horizontal',
        panels: [{ size: "20%"}, {size: "80%"}] 
        });
        
        $(".tabswidget").jqxTabs({theme : sysTheme,height: "100%",width: "100%" });
    </script>
    <script type="text/javascript" src="resources/uijs/commonjs/initGrid.js"></script>
    <script type="text/javascript" src="resources/uijs/basic/customerScore.js"></script>
    <!--  id="jqxwidgets" -->
    <script type="text/javascript">
        initFlexQueryGrid("tab1_flexQueryGrid", oneResultGridVars);
        initNormQueryWindow("tab1_normQueryGrid", oneResultGridVars);
        initPagingGrid("tab1_oneResultGrid", oneResultGridVars);
        initTab1Btns();
    </script>

</body>
</html>