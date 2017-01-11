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
    <script src="resources/framejs/common/commonUtil.js"></script>
    <script src="resources/framejs/common/jqxUtil.js"></script>
    <script src="resources/framejs/common/globalParam.js"></script>
    <!--控件国际化/本地化-->
    <script src="resources/framejs/jqwidgets/globalization/globalize.js"></script>
    <script src="resources/framejs/jqwidgets/globalization/globalize.culture.zh-CN.js"></script>
    <script src="resources/framejs/jqwidgets/globalization/localization.js"></script>

<script type="text/javascript" charset="utf-8" >   
    //获取当前登录人
    $.ajax({
      url : "basic/login!getCurUserInfo.do",
      dataType : 'json',
      async : false,  //true为异步,false为同步
      type : 'POST',
      success : function(responseText) {
        curUser = responseText;
      },
      error : function(errormessage) {
          alert("请求出现异常,请重试!");
      }
  });
    //获取当前页面的权限，再通过权限初始化各个界面的元素。如果没有权限的，直接不初始化。（每个按钮如果有相对应的关联控件，则需要隐藏对应的控件
    //tab页也同样需要在权限加载完之后，就进行可视化控制
</script>