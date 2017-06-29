<%@ page language="java" import="java.util.*"
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>协同开发管理系统</title>
      <meta http-equiv="pragma" content="no-cache">
      <meta http-equiv="cache-control" content="no-cache">
      <meta http-equiv="expires" content="0">
      <meta http-equiv="keywords" content="REVENUE,ACCOUNTING">
      <meta http-equiv="description" content="Revenue Accounting manage system">
      <jsp:include page="/WEB-INF/content/commonJsp/base.jsp" />
      <jsp:include page="/WEB-INF/content/helpInfo.jsp" />
      <style type="text/css">
          .jqx-tabs-content,#tabDiv{
            background-size: 100%; 
            background:url(resources/images/Login2.jpg);
          }
          .jqx-widget-content {
            width: 100%;
            height: 100%;
          }
          #paneltree_0 {
            background-size: 100%; 
            background:url(resources/images/Login2.jpg);
          }
      </style>
      <!-- 自定义JS,模拟菜单 -->
      <script type="text/javascript" src="resources/devtemp/menuTree.js"></script>
      <script type="text/javascript" charset="utf-8">
        var treeID = "0";
        $(document).ready(function(){
            $("#wrapper_full").jqxSplitter({
              theme: sysTheme, width: '100%', height: '100%',
              orientation: "horizontal", panels: [{size: '5%',collapsed :false}, { size: '95%'}]
            });
       
            $("#wrapper_main").jqxSplitter({ 
              theme: sysTheme, width: '100%', height: '100%',  
              orientation: 'vertical' , panels: [{ size: '10%',collapsed :false},{ size: '90%'}] 
            });
            
            $("#tabDiv").jqxTabs({
              theme: sysTheme, width: "100%", height: "100%",
              reorder: true,  showCloseButtons: true
            });
            $('#tabDiv').jqxTabs('removeFirst');

            //初始化菜单
            initMenu(treedata);

            $("#menuListContainer").jqxNavigationBar({theme: sysTheme, height: "100%", width: "100%", expandMode: "singleFitHeight"
            });
            $("#sysName").html(sysname);
            $("#unitName").html(curUser.unitName);
            $("#userName").html(curUser.userName+curUser.pcode);
            $("#dutyName").html(curUser.dutyName);

            $("#home").jqxButton({theme: sysTheme});
            
            $("#home").click(function() {
              createTab("首页", "0", "homePage");
            });
            
            $("#logout").jqxButton({theme: sysTheme});
            
            $("#logout").click(function() {
              $.ajax({
                  url : "basic/login!loginOut.do",
                  dataType : 'json',
                  async : false,  //true为异步,false为同步
                  type : 'POST',
                  success : function(responseText) {
                      alert("注销成功!");
                      window.location.href="login.jsp";
                  },
                  error : function(errormessage) {
                      layer.alert("请求出现异常,请重试!", {icon:2});
                  }
              });
            });

            $("#uqdatePwd").jqxButton({theme: sysTheme});
            
            $("#uqdatePwd").click(function() {
              createTab("个人信息", "0", "userInfo");
            });

            $("#help").jqxButton({theme: sysTheme});
            
            $("#help").click(function() {
              var length = $('#tabDiv').jqxTabs('length');
              if (length == 0) {
                layer.msg("打开当前页面的帮助!",{icon:1});
                return;
              }
              var index = $('#tabDiv').jqxTabs('selectedItem'); 
              var title = $("#tabDiv").jqxTabs("getTitleAt",index);
              var pageId = $("#tabDiv").jqxTabs("getContentAt", index)[0].lastChild.id;
              $("#pageUrl").val(pageId);
              $("#pageName").val(title);
              queryHelpInfo(pageId);
            });
        });
        
        /**
         * 初始化菜单
         * @param menuArr 菜单json
         */
        function initMenu(menuArr) {
          //先创建一级菜单
          for (var i = 0; i < menuArr.length; i++) {
            var menuHtml = createMenuNavigationBar(menuArr[i], i);
            $("#menuListContainer").append(menuHtml);
            //创建二级菜单树形
            $("#tree_" + i).jqxTree({
              theme: sysTheme, toggleMode: "click", source: decodeMenuJson(menuArr[i].children)
            });
            $("#tree_" + i).on("click",function (event) {
                var args = event.currentTarget.id;
                var item = $("#" + event.currentTarget.id).jqxTree('getItem', event.target.parentElement);
                if (item != null && item.value != null) { //判断叶子节点
                  createTab(item.label, item.id, item.value);
                }
            });

            // $("#tree_" + i).on('select',function (event) {
            //     var args = event.args;
            //     var item = $("#" + event.target.id).jqxTree('getItem', args.element);
            //     if (item.value != null) { //判断叶子节点
            //       createTab(item.label, item.id, item.value);
            //     }
            // });
          }
         }
        
        /**
         * 创建一级菜单
         * @param menu 菜单json
         * @param index 一级菜单序号
         * @return 一级菜单（含二级菜单div）html
         */
        function createMenuNavigationBar(menu, index) {
          var html = "<div id='bar_" + index + "'><div style='margin-top:2px;float:left;'><img alt='" + menu.text + "' src='resources/images/icons/application_view_gallery.png'></div><div style='margin-left:10px;float:left;'><strong>" + menu.text + "</strong></div></div>";
          html = html + "<div id='barContent_" + index + "'>";
          //创建二级菜单树div
          html = html + "<div id='tree_" + index + "' class='menuTree' style='border: none;height:100%;weight:100%;'></div>";
          html = html + "</div>";
          return html;
        }
        
        /**
         * 将统一授权平台生成的菜单树json，转成JQwidgets的数据格式
         * @param data 统一授权平台生成的菜单树json
         * @return JQwidgets的数据格式
         */
        function decodeMenuJson(data) {
          var resource = [];
          for (var i = 0; i < data.length; i++) {
            var item = new Object();
            item.id = data[i].id;
            item.label = data[i].text;
            item.icon = "resources/images/icons/application_cascade.png";
            if (data[i].leaf) {
              item.icon = "resources/images/icons/page.png";
              item.value = data[i].url;
            } else {
              item.label = "<strong>" + data[i].text + "</strong>";
              item.items = decodeMenuJson(data[i].children);
            }
            resource.push(item);
          }
          return resource;
        }
        
        /**
         * 新建/切换菜单Tab
         * @param label 菜单名
         * @param url 菜单url
         */
        function createTab(label, id, url) {
          var length = $("#tabDiv").jqxTabs("length");
          for (var i = 0; i < length; i++) {
              var curTitle = $("#tabDiv").jqxTabs("getTitleAt", i);
              var curId = $("#tabDiv").jqxTabs("getContentAt", i)[0].lastChild.id;
              if (curId != undefined && url == curId) { //切换
                  $("#tabDiv").jqxTabs("select", i);
                  return;
              }
          }
          if (label == "首页" || label == "个人信息") {
            //新建
            $("#tabDiv").jqxTabs("addFirst", label, "<iframe id='" + url + "' width='100%' height='100%' frameborder='0' src='" + url + "' />");
            $("#tabDiv").jqxTabs("ensureVisible", 0);
          } else {
            //新建
            $("#tabDiv").jqxTabs("addLast", label, "<iframe id='" + url + "' width='100%' height='100%' frameborder='0' src='" + url + "' />");
            $("#tabDiv").jqxTabs("ensureVisible", -1);
          }
        }
      </script>
  </head>

  <body>
    <div id='wrapper'>
      <div id="wrapper_full" >
        <!-- 头部信息 -->
        <div id="topDiv" style="max-height:35px;float:left;">
          <div class="jqx-widget jqx-widget-energyblue jqx-fill-state-normal 
            jqx-fill-state-normal-energyblue jqx-rc-all jqx-rc-all-energyblue jqx-toolbar jqx-toolbar-energyblue">
            <div style="float:left;">
              <div id="home" style="display:inline;margin-left:20px"><img src="resources\images\icons\house.png"/>首页</div>
              &nbsp;&nbsp;|<span id="unitName">**公司</span>&nbsp;&nbsp;|
              <span id="sysName"> **管理系统 </span>
            </div>
            <div style="float:right;">
              <span> 欢迎您! </span>
              <span id="userName">测试人员</span>
              <span id="dutyName">系统管理员</span>
              |
              <div id="logout" style="display:inline;"><img src="resources\images\icons\user_delete.png"/>&nbsp;注销</div>
              |
              <div id="uqdatePwd" style="display:inline;"><img src="resources\images\icons\key.png"/>&nbsp;密码修改</div>
              |
              <div id="help" style="display:inline;"><img src="resources\images\icons\help.png"/>&nbsp;帮助</div>&nbsp;
            </div>
          </div>
        </div>
        <div>
            <!-- 主体区域 -->
            <div id="wrapper_main">
              <!-- 菜单 -->
              <div id="menuDiv"> 
                  <div class="jqx-hideborder" id="menuListContainer">
                  </div>
              </div>
              <div id="mainContent">
                  <div id="tabDiv" class="jqx-hideborder jqx-hidescrollbars">
                    <ul>
                      <li>首页</li>
                    </ul>
                    <div>
                      <iframe id='homePage' width='100%' height='100%' frameborder='0' src='homePage' />
                    </div>
                  </div>
              </div>
            </div>
        </div>
      </div>
    </div>
  </body>
</html>