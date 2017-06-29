<%@ page language="java" pageEncoding="utf-8"%>
<style type="text/css">
    .inputLabel{
      float:left;
      margin:4px 10px;
    }
    .text_label{
      float:left;
      height:28px;
      font-size:15px;
      text-align:center;
      margin-top:4px;
    }
</style>
<div id="help_window">
    <div id="windowHeader">
        <span>
            帮助信息
        </span>
    </div>
    <div id="windowContent">
        <div id="help_oneSplitter" class="contentSplitter">
            <div>
                <div class="inputLabel">
                    <label class="text_label">页面URL:</label>
                    <input type="text" id= "pageUrl"/>
                </div>
                <div class="inputLabel">
                    <label class="text_label">页面名称:</label>
                    <input type="text" id= "pageName"/>
                    <input type="button" value="保存" id="helpInfoSaveBtn" />
                </div>
            </div>
            <div>
                <div id="help_detailPanel"></div>
            </div>
        </div>
    </div>
</div>
<script src="resources/framejs/jqwidgets/jqxlistbox.js"></script>
<script src="resources/framejs/jqwidgets/jqxdropdownlist.js"></script>
<script src="resources/framejs/jqwidgets/jqxdropdownbutton.js"></script>
<script src="resources/framejs/jqwidgets/jqxeditor.js"></script>
<!--  id="jqxwidgets" -->
<script type="text/javascript">
    var sysTheme = "energyblue";
    $("#help_window").jqxWindow({ minWidth: "70%", minHeight:"60%", width : "70%", height : "65%", theme : sysTheme, autoOpen : false, resizable: false, isModal: false
    });
    $("#help_oneSplitter").jqxSplitter({ theme : sysTheme,width: "100%", height: "100%",
            orientation: 'horizontal',
            panels: [{ size: "10%"}, {size: "90%"}]
    });
    $("#pageUrl").jqxInput({width:150, height:25, theme : sysTheme, disabled: true});
    $("#pageName").jqxInput({width:150, height:25, theme : sysTheme, disabled: true});
    $('#help_detailPanel').jqxEditor({theme: sysTheme, width: "98%", height: "98%" ,tools: 'bold italic underline | format font size | color background | left center right'});
    $('#helpInfoSaveBtn').jqxButton({theme: sysTheme });

    $("#helpInfoSaveBtn").on("click",function() {
        var pageUrl = $("#pageUrl").val();
        var pageName = $("#pageName").val();
        var helpInfo = $("#help_detailPanel").val();
        $.ajax({
           url : "basic/help-info!saveRptGroupCustom.do",
           dataType : 'json',
           async : true, //true为异步,false为同步
           data : {
               "pageUrl" : pageUrl,
               "pageName" : pageName,
               "helpInfo" : helpInfo
           },
           type : 'POST',
           success : function(responseText) {
               if (!responseText.success) {
                   layer.msg("保存失败！", {icon: 8});
               } else {
                   layer.msg("保存成功！", {icon: 1})
               }
           },
           error : function(errormessage) {
               layer.msg("请求出现异常: " + errormessage.responseText,{icon : 2});
               return;
           }
       });
    });

    var queryHelpInfo = function(pageUrl) {
        $.ajax({
           url : "basic/help-info!getPageHelpInfo.do",
           dataType : 'json',
           async : true, //true为异步,false为同步
           data : {
               "pageUrl" : pageUrl
           },
           type : 'POST',
           success : function(responseText) {
               if (!responseText.success) {
                   layer.msg(responseText.msg, {icon: 8});
               } else {
                    var text = responseText.msg;
                    text = text.replace(/\＜/g,'<');
                    text = text.replace(/\＞/g,'>');
                    text = text.replace(/\）/g,')');
                    text = text.replace(/\（/g,'(');
                    text = text.replace(/\＆nbsp;/g,' ');
                    $('#help_detailPanel').jqxEditor('val', text);
                    $("#help_window").jqxWindow("open");
               }
           },
           error : function(errormessage) {
                var src = $('#help_detailPanel').val();
                var resultStr = "查询帮助信息出错！";
                if (errormessage.responseText != null) {
                    var text = errormessage.responseText;
                    text = text.substring(text.indexOf("{\"msg\":\"") + 8,text.indexOf("\",\"operationType\":"));
                    text = text.replace(/\＜/g,'<');
                    text = text.replace(/\＞/g,'>');
                    text = text.replace(/\）/g,')');
                    text = text.replace(/\（/g,'(');
                    text = text.replace(/\＆nbsp;/g,' ');
                    $('#help_detailPanel').jqxEditor("val", text);
                } else {
                    $('#help_detailPanel').jqxEditor("val", resultStr);
                }
                $("#help_window").jqxWindow("open");
                return;
           }
       });
    };
</script>