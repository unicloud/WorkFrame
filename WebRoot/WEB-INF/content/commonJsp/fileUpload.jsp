<%@ page language="java" pageEncoding="utf-8"%>

<!-- 
文件上传弹出框的公共JSP文件,在有使用 上传文件的地方则调用 此JSP界面.
调用路径: 
jsp:include page="/WEB-INF/content/commonjsp/fileUpLoad.jsp"
要求参数: 
    fileUpLoadURL -- 此参数已定义为界面全局变量
    参数内容:
    默认值:  dwinfo/dwAction_fileUpload.do
    追加值:  ?analysisFileType=值
    说明: 后台暂时只 处理文件解析,未处理其它操作
    例: fileUpLoadURL + "?analysisFileType=" + 值
      
-->

<!-- 文件上传弹出框 -->
    <div id="windowFileUpload">
        <div id="uploadHeader">
         文件上传解析
        </div>
        <div>
            <div id="window_jqxFileUpload"></div>
            <div>
                <div style="margin-bottom: 10px; font-family: Verdana;">
                    上传详情:
                    <input type="button" value="清除详情" id="clearBtn" />
                </div>
                <div id="detailsPanel">
                </div>
            </div>
        </div>
    </div>

<!-- 界面单独引用JS -->
<script src="resources/framejs/jqwidgets/jqxfileupload.js"></script>

<script type="text/javascript">

    $(document).ready(function() {

        $('#windowFileUpload').jqxWindow({theme: sysTheme, width: "50%",height: "50%", autoOpen: false });
        
        $('#window_jqxFileUpload').jqxFileUpload({theme: sysTheme, width: "100%",height: "55%",
            localization: getLocalization(defaultCulture), fileInputName: 'fileToUpload'});
        $('#detailsPanel').jqxPanel({theme: sysTheme, width: "100%", height: "30%" });
        
        $('#clearBtn').jqxButton({theme: sysTheme });

        $('#window_jqxFileUpload').on('select', function (event) {
            // var args = event.args;
            // var fileName = args.file;
            // var fileSize = (args.size/1024).toFixed(2) + "KB";
            // $('#detailsPanel').jqxPanel('append', new Date().toLocaleString()+' <strong>选择文件:</strong> ' + fileName + ';大小: ' + fileSize + '<br />');
        });

        $('#window_jqxFileUpload').on('remove', function (event) {
            // var fileName = event.args.file;
            // $('#detailsPanel').jqxPanel('append', new Date().toLocaleString()+' <strong>移除文件:</strong> ' + fileName + '<br />');
        });

        $('#window_jqxFileUpload').on('uploadStart', function (event) {
            // var fileName = event.args.file;
            // $('#detailsPanel').jqxPanel('append', new Date().toLocaleString()+' <strong>开始上传解析:</strong> ' + fileName + '<br />');
        });

        $('#window_jqxFileUpload').on('uploadEnd', function (event) {
            var args = event.args;
            var fileName = args.file;
            var serverResponce = args.response;
            serverResponce = serverResponce.substring(serverResponce.indexOf("{"),serverResponce.indexOf("}") + 1);
            if(serverResponce=="") {
                $('#detailsPanel').jqxPanel('append',' <font color="red"><strong>解析失败:</strong> [' + fileName + ']<br />文件导入出错！请联系系统开发人员！<br /></font>');
                return;
            }
            try {
                var srvresponse = JSON.parse(serverResponce);
                if (srvresponse.success) {
                    if (srvresponse.msg.indexOf("OK") == 0) {
                      $('#detailsPanel').jqxPanel('append', ' <strong>解析结果:</strong> [' + fileName + ']<br />'+ srvresponse.msg + '<br />');  
                    } else {
                        $('#detailsPanel').jqxPanel('append', ' <font color="red"><strong>解析结果:</strong> [' + fileName + ']<br />'+ srvresponse.msg + '<br /></font>');
                    }
                } else {
                    var message = srvresponse.msg.replace(/&lt;br\/&gt;/g,"<br />");
                    $('#detailsPanel').jqxPanel('append', ' <font color="red"><strong>解析失败:</strong> [' + fileName + ']<br />'+ message + '<br /></font>');
                }
            } catch (e) {
                $('#detailsPanel').jqxPanel('append', ' <font color="red"><strong>解析失败:</strong> [' + fileName + ']<br />'+ serverResponce + '<br /></font>');
            }
        });

        $("#clearBtn").on("click",function() {
            $('#detailsPanel').jqxPanel('clearcontent');
        });
    });

</script>
