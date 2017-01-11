//当客户编码变化时
$("#customCode").on('select', function (event) {
    if (event.args) {
        var item = event.args.item;
        if (item != undefined) {
            var val = item.label;
            for (var i = 0; i < customerList.length ; i++) {
                if (customerList[i].customCode == val) {
                    $("#customName").jqxInput("val", customerList[i].customName);
                    refreshCutomerInfo(item.value);
                    break;
                }
            }
        }
    }
});

//查询数据集
var queryData = function(dwName,whereJson) {
    var curDataList = [];
    //发送ajax请求查询数据
    $.ajax({
        url : "basic/system/datawindow-factor!selectNoPagingTablejqx.do",
        dataType : 'json',
        async : false, //true为异步,false为同步
        data : {
            "dwName" : dwName,
            "whereJson" : JSON.stringify(whereJson)
        },
        type : 'POST',
        success : function(responseText) {
            if (responseText.success) {
                curDataList = responseText.rows;
            } else {
                curDataList = [];
            }
        },
        error : function(errormessage) {
            layer.alert("请求出现异常: " + errormessage.responseText,{icon : 2});
        }
    });  // ajax
    return curDataList;
};
//TODO:查询客户扩展信息
var queryExtensionList = function(pkid) {
    var dataList = [];
    $.ajax({
        url : "basic/system/score-factor!getCustExtInfo.do",
        dataType : 'json',
        async : false, //true为异步,false为同步
        data : {
           "pkid" : pkid
        },
        type : 'POST',
        success : function(responseText) {
            if (responseText.success) {
              dataList = responseText.rows;
            } else {
                layer.alert(responseText.msg);
            }
        },
        error : function(errormessage) {
            layer.alert("请求出现异常: " + errormessage.responseText,{icon : 2});
            return;
        }
    });  // ajax 
    return dataList;
};
//刷新客户信息
var refreshCutomerInfo = function(pkid) {
    var whereJson = {"customJson":[[{"columnAlias":"pkid","columnValue": pkid,
    "columnOperator":"=","columnType":"CHAR"}]]};
    curCustomer = queryData(customerDwName,whereJson);
    if (curCustomer.length > 0) {
        curCustomer = curCustomer[0];
        $("#totalScore").jqxInput("val",curCustomer["totalScore"]);
        $("#creditLevel").jqxInput("val",curCustomer["creditLevel"]);
        $("#levelCategory").jqxInput("val",curCustomer["levelCategory"]);
    }
    whereJson = {"customJson":[[{"columnAlias":"DCustomerInfoPkid","columnValue": pkid,
    "columnOperator":"=","columnType":"CHAR"}]]};
    custExtensionList = queryExtensionList(pkid);
    //动态加载评分项
    initextensionInfoArea(custExtensionList);
};
//初始化扩展信息列表
var initextensionInfoArea = function(dataList) {
    //移除item_list
    $("div#item_list").remove();
    //重新添加item_list
    $('#extensionInfoArea').append($("<div id='item_list'></div>"));
    $('#item_list').jqxPanel({ width: "100%", height: "100%"});
    var container = $("#item_list");
    for (var i = 0 ;i < dataList.length ;i++) {
        var item = dataList[i];
        var info1 = $("<table style='float:left;margin-left:10px;margin-top:5px;'><tr><td style='width:80px;'><span style='color:red;width:190px;'>主键:</span>" +item.PKID + "</td><td style='width:200px;'><span style='color:red;'>评分项:</span>"+item.SCOREITEM+"</td><td style='width:200px;'><span style='color:red;'>因素:</span>"+ item.SCOREFACTOR + "</td><td><div id='item_" + item.PKID +"' class='-energyblue' style='width: 150px; height: 28px; text-align: justify; font-size: 15px; float: left;'></div></td><td style='width:80px;'><span style='color:red;width:120px;'>单项得分:</span>" +item.SCORE + "</td></table>");
        $('#item_list').jqxPanel('append', info1);
        if (item.VALTYPE == "T" && item.VALSRC != "" && item.VALSRC != null) {
            $("#item_"+item.PKID).jqxDropDownList({
                theme: sysTheme,
                source : item.VALSRC.split("#"),
                width : "350px",
                height : "25px"
            });
        } else if (item.VALTYPE == "N") {
            $("#item_"+item.PKID).jqxNumberInput({theme: sysTheme,  height: "20px", width: '100px',  decimalDigits : 2, spinButtons : false, groupSeparator : '', inputMode : 'simple'});
        } else {
            $("#item_"+item.PKID).jqxInput({theme: sysTheme,  height: "20px", width: '300px'});
        }
        $("#item_"+item.PKID).val(item.ACTUALVAL);
        $('#item_list').jqxPanel('append',$("<div style='clear:both;''></div>"));
        $("#item_" + item.PKID).on("change", function(event) {
            var curItem = event.args.item;
            var pkid = event.currentTarget.id.substring(5);
            var val = $("#item_" + pkid).val();
            //更新数据
            $.ajax({
                url : "basic/system/score-factor!updateExtInfo.do",
                dataType : 'json',
                async : false, //true为异步,false为同步
                data : {
                   "pkid" : pkid,
                   "val" : val
                },
                type : 'POST',
                success : function(responseText) {
                    if (responseText.success) {
                    } else {
                        layer.alert(responseText.msg);
                    }
                },
                error : function(errormessage) {
                    layer.alert("请求出现异常: " + errormessage.responseText,{icon : 2});
                    return;
                }
            });  // ajax
        });
    }
};

/**
 * tab1_oneResultGrid的工具栏上的按钮
 * 
 * @param containerName
 *            toolBar中Div 的名称,自定义名称
 */
var initTab1Btns = function() {

    var container = createToolBarContainer($("#tab1Toolbar"), "tab1ToolbarContainer");
    var addBtn = createToolBarCustomButton(container, "产生评分项目", "add", "tab1_add", sysTheme);
    var calculateBtn = createToolBarCustomButton(container, "计算", "calculator", "tab1_calculateBtn", sysTheme);
    addBtn.click(function(event) {
        var pkid = $("#customCode").val();
        if (pkid != undefined && pkid != "") {
            var args = [{"type":"int","val": pkid},{"type":"String","val": "userCode"},{"type":"outString","val": ""}];
            $.ajax({
              url : prcExcuteURL + "customerExtensionCreate.do",
              dataType : 'json',
              async : true, //true为异步,false为同步
              data : {
                 "args" : JSON.stringify(args)
              },
              type : 'POST',
              success : function(responseText) {
                  if (responseText.success) {
                      if (responseText.msg == "OK") {
                          layer.msg("创建成功!" + responseText.msg , {icon: 1});
                          refreshCutomerInfo(pkid);
                      } else {
                          layer.alert("创建失败!" + responseText.msg);
                      }
                  } else {
                      layer.alert(responseText.msg);
                  }
              },
              error : function(errormessage) {
                  layer.alert("请求出现异常: " + errormessage.responseText,{icon : 2});
                  return;
              }
          });  // ajax 
        }
    });
    calculateBtn.click(function(event) {
        var pkid = $("#customCode").val();
        if (pkid != undefined && pkid != "") {
            var args = [{"type":"int","val": pkid},{"type":"String","val": "userCode"},{"type":"outString","val": ""}];
            $.ajax({
              url : prcExcuteURL + "customerScoreCompute.do",
              dataType : 'json',
              async : true, //true为异步,false为同步
              data : {
                 "args" : JSON.stringify(args)
              },
              type : 'POST',
              success : function(responseText) {
                  if (responseText.success) {
                      if (responseText.msg == "OK") {
                          layer.msg("计算成功!" + responseText.msg , {icon: 1});
                          refreshCutomerInfo(pkid);
                      } else {
                          layer.alert("计算失败!" + responseText.msg);
                      }
                  } else {
                      layer.alert(responseText.msg);
                  }
              },
              error : function(errormessage) {
                  layer.alert("请求出现异常: " + errormessage.responseText,{icon : 2});
                  return;
              }
          });  // ajax 
        }
    });
};