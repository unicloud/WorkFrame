/*
    系统通用变量设置
 */

// 系统名称
var sysname = "RAMS";

// 系统皮肤主题
var sysTheme = 'energyblue';

// 配置 Jquery AJAX 请求的超时时间
var AJAXTimeout = 1800000; // 超时时间设置,默认为30分钟,单位毫秒

//分页页面初始页大小
var commonpageInitSize = 100;

var allFieldsGridRowNum = 3;

var commonpagesizeoptions = [ "100", "500", "2000", "10000" ];

// 设置默认语言
var defaultCulture = "zh-CN";

// 设置resultGrid 序号列 的信息
var cellsrenderer = function(row, column, value) {
    return "<div style='margin:4px;'>" + (value + 1) + "</div>";
};

// 设置resultGrid 序号列 的合计栏显示 "合计:"
var aggregatesrenderer = function(aggregates) {
    return '<div style="position: relative; margin: 4px; overflow: hidden;">合计: </div>';
};

// 创建用户信息对象来存储Session中的用户信息
var curUser = {
    userName : "",
    unitName : "",
    deptName : "",
    dutyName : "",
    pcode : "",
    fullName : ""
};

//默认的查询条件
var defaultQueryCond = "";

var queryNoPaingDataUrl = "basic/datawindow-factor!queryDataSet.do";

var queryPaingDataUrl = "basic/datawindow-factor!queryPagingDataSet.do";

var insertDatasUrl = "basic/datawindow-factor!insertDatas.do";

var updateDatasUrl = "basic/datawindow-factor!updateDatas.do";

var deleteDatasUrl = "basic/datawindow-factor!deleteDatas.do";