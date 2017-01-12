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
    /**   定义公用变量   */
    var customerDwName = "DW_D_CUSTOMER_INFO_RESULT";
    var custExtensionDwName = "DW_D_CUSTOMER_EXTENSION_INFO_RESULT";

    //客户基本信息集合
    var customerList = [];
    //客户扩展信息集合
    var custExtensionList = [];
    var curCustomer = {};
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
    <div id="jqxwidgets">
        <div style="overflow: hidden;">
            <div id="tab1_oneSplitter" class="contentSplitter">
                <div>
                    <div class="inputLabel">
                        <label class="col_label">客户编码:</label>
                        <div id= "customCode"></div>
                    </div>
                    <div class="inputLabel">
                        <label class="col_label">客户名称:</label>
                        <input type="text" id= "customName"/>
                    </div>
                    <div style="clear:both;"></div>
                    <div class="inputLabel">
                        <label class="col_label">得分:</label>
                        <input type="text" id= "totalScore"/>
                    </div>
                    <div class="inputLabel">
                        <label class="col_label">资信风险评级:</label>
                        <input type="text" id= "creditLevel"/>
                    </div>
                    <div class="inputLabel">
                        <label class="col_label">客户分类:</label>
                        <input type="text" id= "levelCategory"/>
                    </div>
                    <div id="tab1Toolbar" style="float:left;"></div>
                    <div style="clear:both;"></div>
                </div>
                <div>
                    <div id='extensionInfoArea'>
                        <div id="item_list"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="resources/uijs/basic/customerScore.js"></script>
    <!--  id="jqxwidgets" -->
    <script type="text/javascript">
        $("#tab1_oneSplitter").jqxSplitter({ theme : sysTheme,width: "100%", height: "100%",
            orientation: 'horizontal',
            panels: [{ size: "15%"}, {size: "85%"}] 
        });

        $("#customName").jqxInput({width:400, height:25, theme : sysTheme, disabled: true});
        $("#totalScore").jqxInput({width:60, height:25, theme : sysTheme, disabled: true});
        $("#creditLevel").jqxInput({width:120, height:25, theme : sysTheme, disabled: true});
        $("#levelCategory").jqxInput({width:120, height:25, theme : sysTheme, disabled: true});
        var whereJson = {"customJson":[]};
        // customerList = queryData(customerDwName,whereJson);
        $("#customCode").jqxComboBox({theme: sysTheme ,source : customerList,  displayMember : "customCode", valueMember : "pkid",height: "28px", width: '120px',
                                    dropDownWidth: 200});
        // $("#extensionInfoArea").jqxPanel({ width: "100%", height: "100%"});
        initTab1Btns();

    </script>

</body>
</html>