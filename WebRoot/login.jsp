<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <title>客户管理系统</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="客户管理系统">
    <link rel="icon" href="resources/images/icons/finance_rams.ico" type="image/x-icon">
    <script src="resources/framejs/jquery/jquery-1.11.1.min.js"></script>
    <script src="resources/framejs/layer-2.0/layer.js"></script>
    <style type="text/css">
      html{
          width: 100%;
          height: 100%;
          overflow: hidden;
          font-style: sans-serif;
      }
      body{
          width: 100%;
          height: 100%;
          font-family: 'Open Sans',sans-serif;
          margin: 0;
          background-size: 100%; 
          background-image:url(resources/images/Login.jpg);
      }
      #login{
          position: absolute;
          top: 50%;
          left:80%;
          margin: -150px 0 0 -150px;
          width: 320px;
          height: 200px;
          background-color: #fff;
          opacity: 0.8;
          border-radius: 8px;
          text-align: center;
      }
      #login h1{
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
        String.prototype.trim = function () {
          var str = this ;
          str = str.replace(/^\s /, '');
          for (var i = str.length - 1; i >= 0; i--) {
              if (/\S/.test(str.charAt(i))) {
                  str = str.substring(0, i + 1);
                  break ;
              }
          }
          return str;
        };

        var loginBtnClick = function() {
            var userName = $("#userName").val();
            var password = $("#password").val();
            if (userName.trim() == "") {
              layer.msg("请输入用户名！", {icon:8});
              $("#userName").focus();
              return;
            }
            if (password.trim() == "") {
              layer.msg("请输入密码！", {icon:8});
              $("#password").focus();
              return;
            }
            $.ajax({
                url : "basic/login!login.do",
                dataType : 'json',
                async : false,  //true为异步,false为同步
                type : 'POST',
                data : {
                  "user" : userName,
                  "pwd" : password
                },
                success : function(responseText) {
                  if (responseText.success) {
                    window.location.href="index.jsp"; 
                  } else {
                    layer.alert(responseText.msg, {icon:8});
                  }
                },
                error : function(errormessage) {
                    layer.alert("请求出现异常,请重试!", {icon:2});
                }
            });
        };

        //登陆按钮响应回车事件
        $(document).keydown(function(event){
            var e = event || window.event;//浏览器兼容
            if (e.keyCode == 13) {
                $("#loginBtn").click();
            }
        });
    </script>
</head>
<body>
    <div id="login">
        <h1>客户管理系统</h1>
        <form action='basic/login!login.do' method='POST' name='loginForm'>
          <table>
            <tr>
              <td colspan=2><input type="text" required="required" class="inputField" placeholder="用户名" value="10000" id="userName" maxlength='20' name="user"></input></td>
            </tr>
            <tr>
              <td colspan=2><input type="password" required="required" class="inputField" placeholder="密码" value="123456" id="password" maxlength='20' name="pwd"></input></td>
            </tr>
            <tr>
              <td><input type="button" class="btn" value="登录" onclick="loginBtnClick()" id="loginBtn"></input></td>
              <td><button type="reset" class="btn" value="重置">重置</button></td>
            </tr>
          </table>
        </form>
    </div>
</body>
</html>
