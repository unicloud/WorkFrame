<%@ page language="java" import="java.util.*"
    contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);

    String path = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath() + "/";
    request.setAttribute("path", path);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<base href="${path }">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人主页信息</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="个人主页信息">
<script src="resources/framejs/jquery/jquery-1.11.1.min.js"></script>
<script src="resources/framejs/layer-2.0/layer.js"></script>
<script src="resources/framejs/common/globalParam.js"></script>
<script src="resources/framejs/common/commonUtil.js"></script>
<style type="text/css">
   #userImg {
        position: absolute;
        top: 5%;
        left:5%;
        margin: 10px 0 0 10px;
        width: 100px;
        height: 100px;
        background-color: #fff;
        opacity: 0.8;
        border-radius: 8px;
        text-align: center;
        background-image:url(resources/images/user.jpg);
    }
    #userInfo {
        position: absolute;
        top: 5%;
        left:5%;
        margin: 10px 0 0 150px;
        width: 200px;
        height: 250px;
        background-color: #fff;
        opacity: 0.8;
        border-radius: 8px;
        text-align: left;
    }
    #userInfo div {
        margin: 10px;
    }

    #pwdReset{
        position: absolute;
        top: 5%;
        left:5%;
        margin: 10px 0 0 360px;
        width: 350px;
        height: 250px;
        background-color: #fff;
        opacity: 0.8;
        border-radius: 8px;
        text-align: left;
        display: none;
    }
    #pwdReset h1{
      color: #4A374A;
      text-shadow:0 0 10px;
      letter-spacing: 1px;
      text-align: center;
    }
    h1{
      font-size: 2em;
      margin: 0.67em 0;
    }
    .inputField{
      width: 300px;
      height: 20px;
      margin-bottom: 10px;
      outline: none;
      font-size: 13px;
      color: #fff;
      text-shadow:1px 1px 1px;
      border-top: 1px solid #312E3D;
      border-left: 1px solid #312E3D;
      border-right: 1px solid #312E3D;
      border-bottom: 1px solid #56536A;
      border-radius: 4px;
      background-color: #2D2D3F;
    }
    .btn{
      width: 150px;
      min-height: 30px;
      display: block;
      background-color: #4a77d4;
      border: 1px solid #3762bc;
      color: #fff;
      font-size: 15px;
      line-height: normal;
      border-radius: 5px;
      margin: 0;
    }
</style>
<script type="text/javascript">
    //修改按钮点击事件
    var resetPwdClick = function() {
        var originalPwd = $("#originalPwd").val();
        var newPwd = $("#newPwd").val();
        var newPwd2 = $("#newPwd2").val();
        if (originalPwd.trim() == "") {
          layer.msg("请输入原密码！", {icon:8});
          $("#originalPwd").focus();
          return;
        }
        if (newPwd.trim() == "") {
          layer.msg("请输入新密码！", {icon:8});
          $("#newPwd").focus();
          return;
        }
        if (newPwd2.trim() == "") {
          layer.msg("请再次输入新密码！", {icon:8});
          $("#newPwd2").focus();
          return;
        }
        if (newPwd.trim() != newPwd2.trim()) {
            layer.msg("两次输入新密码不一致！", {icon:8});
            $("#newPwd").focus();
            return;
        }
        $.ajax({
            url : "basic/login!resetPwd.do",
            dataType : 'json',
            async : false,  //true为异步,false为同步
            type : 'POST',
            data : {
              "originalPwd" : originalPwd,
              "newPwd" : newPwd,
              "newPwd2" : newPwd2
            },
            success : function(responseText) {
              if (responseText.success) {
                layer.msg(responseText.msg,{icon:1});
              } else {
                layer.alert(responseText.msg, {icon: 8});
              }
            },
            error : function(errormessage) {
                layer.alert("请求出现异常,请重试!", {icon: 2});
            }
        });
    };
    //显示修改密码的框
    var showClick = function() {
      $("#pwdReset").show();
    };
    //登陆按钮响应回车事件
    $(document).keydown(function(event){
        var e = event || window.event;//浏览器兼容
        if (e.keyCode == 13) {
            $("#resetPwdBtn").click();
        }
    });
</script>
</head>

<body>
    <div>
        <div id="userImg"></div>
        <div id="userInfo">
            <div><strong>姓名:     </strong><span id="userName"></span></div>
            <div><strong>工号:     </strong><span id="pcode"></span></div>
            <div><strong>公司:     </strong><span id="unitName"></span></div>
            <div><strong>部门:     </strong><span id="deptName"></span></div>
            <div><strong>岗位:     </strong><span id="dutyName"></span></div>
            <input type="button" value="?" onclick="showClick()" >修改密码</input>
        </div>
        <div id="pwdReset">
            <h1>密码修改</h1>
            <form action='basic/login!resetPwd.do' method='POST' name='pwdResetForm'>
              <table>
                <tr>
                  <td colspan=2><input type="password" required="required" class="inputField" placeholder="输入原密码" id="originalPwd" maxlength='20' name="originalPwd"></input></td>
                </tr>
                <tr>
                  <td colspan=2><input type="password" required="required" class="inputField" placeholder="输入新密码" id="newPwd" maxlength='20' name="newPwd"></input></td>
                </tr>
                <tr>
                  <td colspan=2><input type="password" required="required" class="inputField" placeholder="再次输入新密码" id="newPwd2" maxlength='20' name="newPwd2"></input></td>
                </tr>
                <tr>
                  <td><input type="button" class="btn" value="修改" onclick="resetPwdClick()" id="resetPwdBtn"></input></td>
                  <td><button type="reset" class="btn" value="清空">清空</button></td>
                </tr>
              </table>
            </form>
        </div>
    </div>
    <script type="text/javascript">
        var curUser = {};
        //获取当前用户信息
        ajaxExecute("basic/login!getCurUserInfo.do",null,function(responseText) {
            curUser = responseText;
        },false);
        $("#userName").html(curUser.userName);
        $("#pcode").html(curUser.pcode);
        $("#unitName").html(curUser.unitName);
        $("#deptName").html(curUser.deptName);
        $("#dutyName").html(curUser.dutyName);
    </script>
</body>
</html>