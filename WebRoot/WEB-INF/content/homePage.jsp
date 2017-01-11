<%@ page language="java" import="java.util.*"
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/WEB-INF/content/commonJsp/base.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据窗信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="数据窗信息">

<script type="text/javascript">
    /**   定义公用变量   */
    // 存储查询的JSON对象.
    var whereJson = "";
    
    // 定义一个变量,标识当前界面点到了哪个tab上
    var currentTabPrefix = "tab1";
   
    /**      第1个Tab页     **/
    var tab1Vars = {
            oneResultGridDwName: "DW03_DWMAINTAIN_RESULT",
            oneResultGridColumns: [],
            oneResultGridDataFields: [],
            flexibleGridQueryColumnData : [],
            allFieldsGridDwName : "DW03_DWMAINTAIN_QUERY",
            allFieldsGridColumns: [],
            allFieldsGridDataFields: [],
            allFieldsGridModifyData: new Array(),
            oneResultGridModifyData: new Array(),
            oneResultGridPrmColumn: "",

            // 获取弹出窗口的信息 -- 要将 类型为 Hidden 的分离出来
            oneResultGridShowWindowData: new Array(),
            // 存储 控件类型为 Hidden 的控件信息
            oneResultGridHiddenShowWindowData: new Array(),
            // 获取grid列中下拉列表的信息
            oneResultGridCDDListData: new Array()
    };

</script>
</head>

<body>
    <div id="jqxwidgets">
    <!-- 此处为 界面的Tab 信息 -->
        <div id="tabsMain">
            <ul>
                <li style="margin-left: 10px;" id="tab1">数据窗</li>
            </ul>
            <!--Tab1-->
            <div style="overflow: hidden;">
                <div id="tab1_oneSplitter" class="contentSplitter">
                    <div>
                        <div id="tab1_widget" class="tabswidget">
                            <ul>
                                <li>全字段查询</li>
                                <li>灵活查询</li>
                            </ul>
                            <div style="overflow: hidden;">
                                <div style="border:none;" id="tab1_allFieldsGrid"></div>
                            </div>
                            <div class="overflow: hidden;">
                                <div style="border:none;" id="tab1_flexibleGrid"></div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div style="border:none;" id="tab1_oneResultGrid"></div>
                    </div>
                </div>
            </div>
        </div>  <!-- tabsMain --> 
    </div>
<script type="text/javascript">

    var initTabWidgets = function (tab) {
        switch (tab) {
            case 0:
                // common_Func("tab1_allFieldsGrid", "tab1_flexibleGrid", tab1Vars);
                
                // oneResultGridFunc("tab1_oneResultGrid", tab1Vars, false, false);
                // $("#tab1_oneResultGrid").jqxGrid({editable: false});
                // initQueryBtns("toolbartab1_oneResultGrid", "tab1ToolbarContainer",
                //     "tab1_oneResultGrid", tab1Vars, "tab1", hasConditionQuery);
                // initTab1Btns("tab1ToolbarContainer");
                // executeBtnPermissions();
                
                break;
        }
    };
    
    $("#tabsMain").jqxTabs({ initTabContent: initTabWidgets  });


</script>
</body>
</html>