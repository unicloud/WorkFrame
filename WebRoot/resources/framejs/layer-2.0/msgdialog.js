//将layer封装成需要的信息弹出窗
//注意提示信息的转换，都在这个地方统一处理

//提示
var msginfo = function(msgInfo, iconNum) {
    if (iconNum == undefined) {
        iconNum = 1;
    }
    layer.alert(msgInfo,{icon:iconNum});
};

//不需要点确认按钮的提示
var msgtips = function(msgInfo) {
    layer.msg(msgInfo,{icon:1});
};

//警告
var warninfo = function(warnInfo) {
    layer.alert(warnInfo,{icon:8});
};

//不需要点确认按钮的警告
var warntips = function(warnInfo) {
    layer.msg(warnInfo,{icon:8});
};

//错误信息提示
var errorInfo = function(errorInfo) {
    layer.alert(warnInfo,{icon:2});
};

//确认框
var confirm = function() {

};

//警告
var warntips = function() {

};

//页面帮助信息(不同页面,每个Tab页添加一个信息提示按钮，区分系统备注和用户备注)
var helptips = function() {
    //用户能重置提示弹出窗的大小，并输入用户备注信息保存。以供其他用户参考
    //系统备注的权限提供给管理员。用户备注的权限提供给所有用户

}

/* 提示信息转换
* 统一处理提示信息，针对特殊的提示，可以做统一的转化。如常用的保存成功，保存失败。
* 又如常见的数据库ora错误提示
*/
var contentConvert = function() {

}