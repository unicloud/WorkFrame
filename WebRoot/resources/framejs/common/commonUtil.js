/**
 * 通用工具类
 * @author by wuwq 
 * 2015-09-04 15:43
 */
// 对Date的扩展，将 Date 转化为指定格式的String 
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")    ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function(fmt) { //author: meizz 
    var o = { 
    "M+" : this.getMonth()+1,                   //月份 
    "d+" : this.getDate(),                      //日 
    "h+" : this.getHours(),                     //小时 
    "H+" : this.getHours(),                     //小时 
    "m+" : this.getMinutes(),                   //分 
    "s+" : this.getSeconds(),                   //秒 
    "q+" : Math.floor((this.getMonth()+3)/3),   //季度 
    "S"  : this.getMilliseconds()               //毫秒 
    };
    if(/(y+)/.test(fmt)) 
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    for(var k in o) 
    if(new RegExp("("+ k +")").test(fmt)) 
    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
    return fmt;
};

/**
    获取距离今天某一个时间的日期(正整数为之后的日期,负整数为之前的日期)
    @param AddDayCount 天数
    document.write("前天："+GetDateStr(-2));
    document.write("<br />昨天："+GetDateStr(-1));
    document.write("<br />今天："+GetDateStr(0));
    document.write("<br />明天："+GetDateStr(1));
    document.write("<br />后天："+GetDateStr(2));
    document.write("<br />大后天："+GetDateStr(3));
*/
function GetCalculateDate(AddDayCount) {
    var dd = new Date();
    return (new Date(dd.setDate(dd.getDate()+AddDayCount)));//获取AddDayCount天后的日期
};

String.prototype.startWith = function(str){
    if(str == null || str == ""|| this.length == 0 || str.length > this.length)
      return false
    if(this.substr(0,str.length) == str)
      return true;
    else
      return false;
};

String.prototype.endWith = function(str){
    if(str == null || str == ""|| this.length == 0 || str.length > this.length)
      return false;
    if(this.substring(this.length - str.length)==str)
      return true;
    else
      return false;
};

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

/**
 * 对 JSON字符串进行 转义符 转义
 * @returns 转义后的JSON字符串
 */
String.prototype.escapeSpecialChars = function() {
    return this.replace(/[\"]/g, '\"')
    .replace(/[\\]/g, '\\\\')
    .replace(/[\/]/g, '\\/')
    .replace(/[\b]/g, '\\b')
    .replace(/[\f]/g, '\\f')
    .replace(/[\n]/g, '\\n')
    .replace(/[\r]/g, '\\r')
    .replace(/[\t]/g, '\\t');
};


//String.prototype.startWith=function(str){
//  var reg=new RegExp("^"+str);
//  return reg.test(this);
//};
//
//String.prototype.endWith=function(str){
//  var reg=new RegExp(str+"$");
//  return reg.test(this);
//};

// 获取jqx数字框
function getjqxNumberInputVal(tablename, fieldArray) {
    $(tablename).each(function(){
        var id = $(this)[0].id;
        var fieldObject = new Object();
        fieldObject.datafield = id.substring(3);
        fieldObject.value = $("#" + id).jqxNumberInput("val");
        fieldArray.push(fieldObject);
    });
};

/**
 * 获取jqx文本框中值,并构建JSON
 * "datafield" : "控件ID", "value" : "控件值"
 */
function getjqxInputVal(tablename, fieldArray) {
    $(tablename).each(function(){
        var id = $(this)[0].id;
        console.info(id);
        console.info($("#" + id).jqxInput("val"));
        var fieldObject = new Object();
        fieldObject.datafield = id.substring(3);
        fieldObject.value = $("#" + id).jqxInput("val");
        fieldArray.push(fieldObject);
    });
};


/**
 * 批量获取 文本框的值
 */
function getWebInputText(tablename) {
    var whereArray = [];
    $(tablename).each(function(){
        var txtContext = $(this).context;
        if ($.trim(txtContext.value).length > 0) {
            whereArray.push( {"columnAlias":txtContext.id,"columnValue":$.trim(txtContext.value),
                "columnType":$(this).jqxInput('valueMember')});
        }
    });

    return whereArray;
};

/**
 *  验证字符串是否为数字
 */
function isNumber(str) {
    var dateReg = /^\d+$/;
    if (!dateReg.test(str)) {
        return false ;
    }
    return true;
};

/**
 * 设置默认值的格式与内容
 */
function setRowDefaultValue(rowData,columnName,userName) {
    switch(columnName){
        case "numID":
            rowData = rowData + "\"" + columnName + "\" : 0," ;
            break;
        case "pkid":
            rowData = rowData + "\"" + columnName + "\" : 0," ;
            break;
        case "createUser":
            rowData = rowData + "\"" + columnName + "\" : \"" + userName + "\"," ;
            break;
        case "modifyUser":
            rowData = rowData + "\"" + columnName + "\" : \"" + userName + "\"," ;
            break;
        case "modifyTime":
            rowData = rowData + "\"" + columnName + "\" : \"" + (new Date).Format("yyyy-MM-dd HH:mm:ss") + "\"," ;
            break;
        case "createTime":
            rowData = rowData + "\"" + columnName + "\" : \"" + (new Date).Format("yyyy-MM-dd HH:mm:ss") + "\"," ;
            break;
        default:
            rowData = rowData + "\"" + columnName + "\" : \"\"," ;
            break;
    }
    return rowData;
};

// 运行JS代码字符串
function runJS(code) {
    new Function(code)();
}


/**
 *运行JS代码字符串
 */
function runEVAL(code) {
    eval(code);
}

/**
 * 定义一个clone方法来实现
 */
function clone(obj) {
    var o;
    switch (typeof obj) {
    case 'undefined': break;
    case 'string'   : o = obj + '';break;
    case 'number'   : o = obj - 0;break;
    case 'boolean'  : o = obj;break;
    case 'object'   :
        if (obj === null){
            o = null;
        } else {
            if (obj instanceof Array) {
                o = [];
                for(var i = 0, len = obj.length; i < len; i++){
                    o.push(clone(obj[i]));
                }
            } else {
                o = {};
                for (var k in obj) {
                    o[k] = clone(obj[k]);
                }
            }
        }
        break;
    default:
        o = obj;break;
    }
    return o;
};


/**
 *AJAX请求封装(注意考虑异步请求的回调函数)
 *@param ajaxUrl 请求的url
 *@param paramsData 请求的参数对象
 *@param successFun 请求的请求成功的回调函数
 *@param reqAsync true(默认)为异步,false为同步
 *@param sendMethod POST(默认),GET
 *@param ajaxTimeout 请求超时设置(单位毫秒)
 *@param sendDataType json
 */
function ajaxExecute(ajaxUrl,paramsData,successFun,reqAsync,sendMethod,ajaxTimeout,sendDataType) {
    if (ajaxUrl == undefined) {
        layer.msg("请确认请求的url！", {icon: 8});
        return;
    }
    if (successFun == undefined || successFun == null) {
        successFun = function(responseText) {
            layer.msg("请求执行完成！", {icon: 1});
        }
    }
    if (reqAsync == undefined || reqAsync == null) {
        reqAsync = true; //默认发送异步请求
    }
    if (sendMethod == undefined || sendMethod == null) {
        sendMethod = "POST";
    }
    if (ajaxTimeout == undefined || ajaxTimeout == null) {
        ajaxTimeout = AJAXTimeout; // 超时时间设置,默认为30分钟,单位毫秒
    }
    if (sendDataType == undefined || sendDataType == null) {
        sendDataType = "json";
    }
    var JQueryAjaxTimeout = $.ajax({
          url : ajaxUrl,
          dataType : sendDataType,
          async : reqAsync,  //true为异步,false为同步
          timeout : ajaxTimeout, //超时时间设置，单位毫秒
          type : sendMethod,
          success : function(responseText) {
            successFun(responseText);
          },
          error : function(errormessage) {
              layer.alert("请求出现异常,请重试!", {icon: 2});
          },
          complete : function(XMLHttpRequest,status) { //请求完成后最终执行参数
    　　　　 if(status=='timeout'){//超时,status还有success,error等值的情况
     　　　　　 JQueryAjaxTimeout.abort();
    　　　　　  layer.alert("请求超时！",{icon: 2});
    　　　　 }
    　　  }
      });
};
