    <%@ page language="java" pageEncoding="utf-8"%>
    <!-- 设置默认请求路径 -->
    <%
        String path = request.getScheme() + "://" + request.getServerName()
        + ":" + request.getServerPort() + request.getContextPath()
        + "/";
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        request.setAttribute("path",path);
    %>
    <base href="${path}">
    <link rel="icon" href="resources/images/icons/finance_rams.ico" type="image/x-icon">
    <!-- 引用CSS样式 -->
    <link type="text/css" rel="stylesheet" href="resources/framejs/jqwidgets/styles/jqx.base.css" />
    <link type="text/css" rel="stylesheet" href="resources/framejs/jqwidgets/styles/jqx.energyblue.css" />
    <link type="text/css" rel="stylesheet" href="resources/framejs/jqwidgets/styles/jqx.darkblue.css" />
    <link type="text/css" rel="stylesheet" href="resources/framejs/jqwidgets/styles/jqx.metrodark.css" />
    <!-- 自定义CSS格式,设置界面的风格 -->
    <link type="text/css" rel="stylesheet" href="resources/css/main.css" />

    <script src="resources/framejs/jquery/jquery-1.11.1.min.js"></script>
    
    <script src="resources/framejs/jqwidgets/jqxcore.js"></script>
    
    <!-- 引用通用jqwidgets组件js -->
    <script src="resources/framejs/jqwidgets/jqxsplitter.js"></script>
    <script src="resources/framejs/jqwidgets/jqxtabs.js"></script>
    <script src="resources/framejs/jqwidgets/jqxexpander.js"></script>
    <script src="resources/framejs/jqwidgets/jqxpanel.js"></script>
    <script src="resources/framejs/jqwidgets/jqxgrid.js"></script>
    <script src="resources/framejs/jqwidgets/jqxgrid.selection.js"></script>
    <script src="resources/framejs/jqwidgets/jqxgrid.pager.js"></script> 
    <script src="resources/framejs/jqwidgets/jqxgrid.sort.js"></script> 
    <script src="resources/framejs/jqwidgets/jqxgrid.edit.js"></script> 
    <script src="resources/framejs/jqwidgets/jqxgrid.filter.js"></script> 
    <script src="resources/framejs/jqwidgets/jqxgrid.columnsresize.js"></script> 
    <script src="resources/framejs/jqwidgets/jqxgrid.columnsreorder.js"></script>
    <script src="resources/framejs/jqwidgets/jqxgrid.aggregates.js"></script>
    <script src="resources/framejs/jqwidgets/jqxgrid.grouping.js"></script>
    <script src="resources/framejs/jqwidgets/jqxdata.js"></script> 
    <script src="resources/framejs/jqwidgets/jqxlistbox.js"></script> 
    <script src="resources/framejs/jqwidgets/jqxscrollbar.js"></script>
    <script src="resources/framejs/jqwidgets/jqxbuttons.js"></script>
    <script src="resources/framejs/jqwidgets/jqxmenu.js"></script>
    <script src="resources/framejs/jqwidgets/jqxwindow.js"></script>
    <script src="resources/framejs/jqwidgets/jqxloader.js"></script>
    <script src="resources/framejs/jqwidgets/jqxdate.js"></script>
    <script src="resources/framejs/jqwidgets/jqxdatetimeinput.js"></script>
    <script src="resources/framejs/jqwidgets/jqxcalendar.js"></script>
    <script src="resources/framejs/jqwidgets/jqxcheckbox.js"></script>
    <script src="resources/framejs/jqwidgets/jqxdropdownlist.js"></script>
    <script src="resources/framejs/jqwidgets/jqxnumberinput.js"></script>
    <script src="resources/framejs/jqwidgets/jqxinput.js"></script>
    <script src="resources/framejs/jqwidgets/jqxtooltip.js"></script>
    <script src="resources/framejs/jqwidgets/jqxvalidator.js"></script>
    <script src="resources/framejs/jqwidgets/jqxnavigationbar.js"></script>
    <script src="resources/framejs/jqwidgets/jqxtree.js"></script>
    <script src="resources/framejs/jqwidgets/jqxcombobox.js"></script>
    <!-- 第三方JS -->
    <script src="resources/framejs/layer-2.0/layer.js"></script>
    <script src="resources/framejs/jquery/json2.js"></script>
    
    <!-- 引用自定义JS -->
    <script src="resources/framejs/layer-2.0/msgdialog.js"></script>
    <script src="resources/framejs/common/globalParam.js"></script>
    <script src="resources/framejs/common/commonUtil.js"></script>
    <script src="resources/framejs/common/jqxUtil.js"></script>
    <!--控件国际化/本地化-->
    <script src="resources/framejs/jqwidgets/globalization/globalize.js"></script>
    <script src="resources/framejs/jqwidgets/globalization/globalize.culture.zh-CN.js"></script>
    <script src="resources/framejs/jqwidgets/globalization/localization.js"></script>

<script type="text/javascript" charset="utf-8" > 
    //获取当前用户信息
    ajaxExecute("basic/login!getCurUserInfo.do",null,function(responseText) {
        curUser = responseText;
    },false);

    //加载当前页面权限(含Tab\Btn权限) TODO
</script>
<div id="jqxLoader">
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#jqxLoader").jqxLoader({theme:sysTheme,text: "请等待……",
            width: 120, height: 30, isModal: true});
        $("#jqxLoaderModal")[0].style.zIndex = 19891014;
    });
</script>