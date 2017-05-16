
/**
 * @Title queryDwInfo 获取数据窗信息
 * @param gridId grid
 * @param gridVars grid参数
 */
var queryDwInfo = function(gridVars, type) {
    var dwName = gridVars.resultDwName;
    if (type == "query") {
        dwName = gridVars.queryDwName;
    }
    //获取Grid相关的信息
    var JQueryAjaxTimeout = $.ajax({
        url : "basic/datawindow-factor!getDwInfo.do",
        dataType : 'json',
        async : false,  //true为异步,false为同步
        timeout : AJAXTimeout, //超时时间设置，单位毫秒
        data : {
          "dwName" : dwName
        },
        type : 'POST',
        success : function(responseText) {
            if (responseText.success) {
                if (type == "query") {
                    gridVars.queryDwInfos = responseText.rows;
                } else {
                    gridVars.resulDwInfos = responseText.rows;
                }
            } else {
                if (typeof(responseText.msg) != "undefined") {
                    layer.alert(responseText.msg, {icon : 3});
                }
                return;
            }
        },
        complete : function(XMLHttpRequest, status) {
    　　　　if (status=='timeout') { 
     　　　　　 JQueryAjaxTimeout.abort();
    　　　　　  alert("超时");
    　　　　}
    　　},
        error : function(errormessage) {
            layer.alert("请求出现异常,请重试!",{icon : 3});
            return;
        },
    });
};

/**
 * @Title initPagingGridFun 初始化分页结果窗
 * @param gridId grid
 * @param gridVars grid参数
 */
var initPagingGrid = function(gridId, gridVars) {
    if (gridVars.resulDwInfos.length == 0) {
        queryDwInfo(gridVars);
    }
    //依然为空，则不往下执行
    if (gridVars.resulDwInfos.length == 0) {
        return;
    }
    var columns = clone(gridVars.resulDwInfos.disColumns);
    for (var i in columns) {
        var item =  columns[i];
        //如果是非空字段
        if (item.notnull) {
            item.text = item.text + "*";
            item.classname = "requirdField";
        }
        var filedItem = {"name" : item.datafield};
        if (item.columntype == "numberinput") {
            filedItem.type = "number";
        } else if (item.columntype == "datetimeinput") {
            filedItem.type = "date";
        } else {
            filedItem.type = "string";
        }
        gridVars.resultColumns.push(item);
        gridVars.resultDatafields.push(filedItem);
    }

    // 设置结果集Grid查询的数据
    var gridDataSource = {
        type: "POST",
        datatype: "json",
        datafields: gridVars.resultDatafields,
        url: "",
        root: 'rows',
        cache: false,
        data : {
            whereJson : "-1"
        },
        pager: function (pagenum, pagesize, oldpagenum) {

        },
        beforeprocessing: function (data) {
            if (data.success === undefined) {
                return;
            }
            if (data.success) {
                gridDataSource.totalrecords = data.total;
            }
            else {
                if (typeof(data.msg) != "undefined") {
                    if (data.operationType != "invalid") {
                            layer.alert(data.msg, {icon : 3});
                    }
                }
                return;
            }
        },
        addrow: function (rowid, rowdata, position, commit) {
            commit(true);
        },
        deleterow: function (rowid, commit) {
            commit(true);
        },
        updaterow: function (rowid, newdata, commit) {
            commit(true);
        }
    };
    
    var gridDataAdapter = new $.jqx.dataAdapter(gridDataSource, {
        autoBind: false,
        downloadComplete: function (data, status, xhr) { },
        loadComplete: function (data) {
            $("#"+gridId).jqxGrid("autoresizecolumns");//调整列以适应文本
        },
        loadError: function (xhr, status, error) {
        }
    });
    
    $("#" + gridId).jqxGrid({
        theme : sysTheme,
        width: '100%',
        height: '100%',
        source: gridDataAdapter,
        localization: getLocalization(defaultCulture),
        showstatusbar: true,
        statusbarheight: 30,
        showaggregates: true,           // 显示 合计的状态栏
        sortable: true,
        editable: true,
        pageable: true,
        virtualmode: true,
        pagesizeoptions: commonpagesizeoptions,
        columnsresize: true,
        selectionmode: 'multiplerowsextended',     // checkbox
        columnsreorder: true,   // 列是否可以拖动
        editmode: 'click',
        columns: gridVars.resultColumns,
        enablemousewheel: true,
        rendergridrows: function (obj) {
            return obj.data;
        },
        showtoolbar: true,
        rendertoolbar: function (toolbar) {
        },
        filterable: true
    });

    $("#" + gridId).on('contextmenu',function() {
        return false;
    });
};

/**
 * @Title initEditWindow 初始化数据编辑弹出窗
 * @param containerId 弹出窗容器的ID
 * @param gridVars grid参数
 * @param canSave 是否提供保存按钮
 */
var initEditWindow = function(containerId, gridVars, canSave) {
    //弹出窗上的控件ID需要带一个随机数，以确保同一个页面上的控件ID可以区别开
};

/**
 * @Title initEditWindow 初始化普通查询容易
 * @param containerId 弹出窗容器的ID
 * @param gridVars grid参数
 * @param canSave 是否提供保存按钮
 */
var initNormQueryWindow = function(containerId, gridVars, canSave) {
    //弹出窗上的控件ID需要带一个随机数，以确保同一个页面上的控件ID可以区别开
    //日期类型（区分是否需要到时分秒）、数值类型（保留位数）、文本类型（长文本）、下拉列表（区分单选、多选）
    if (gridVars.queryDwInfos.length == 0) {
        queryDwInfo(gridVars, "query");
    }
    //依然为空，则不往下执行
    if (gridVars.queryDwInfos.length == 0) {
        return;
    }
    var columns = clone(gridVars.queryDwInfos.disColumns);
    for (var i in columns) {
        var item =  columns[i];
        createUserCtrl(containerId, item);
    }
};

/**
 * @Title initFlexQueryGrid 初始化灵活查询窗口
 * @param gridId grid
 * @param gridVars grid参数
 */
var initFlexQueryGrid = function(gridId, gridVars) {
    //首先是初始化成只有三行数据，思考条件拼接方式（默认行优先，可切换成列优先）
    if (gridVars.queryDwInfos.length == 0) {
        queryDwInfo(gridVars, "query");
    }
    //依然为空，则不往下执行
    if (gridVars.queryDwInfos.length == 0) {
        return;
    }

    var columns = clone(gridVars.queryDwInfos.disColumns);
    //datafields需要做处理： 数值、日期类型都得处理成文本类型
    for (var i in columns) {
        var item =  columns[i];
        if (item.columntype == "numberinput" || item.columntype == "datetimeinput") {
            item.columntype = "textbox";
            item.cellsformat = "";
        }
        //如果是索引
        if (item.isIndex) {
            item.classname = "indexField";
        }
        var filedItem = {"name" : item.datafield};
        gridVars.queryColumns.push(item);
        gridVars.queryDatafields.push(filedItem);
    }

    // 默认创建全字段查询的行--可传行数
    var flexGridRowData = createFlexGridRowData(gridVars.queryDatafields, 
        3);

    // 自定义查询Grid 的配置信息
    var flexGridDataSource = {
        datatype: 'json',
        datafields: gridVars.queryDatafields,
        localdata:  flexGridRowData,
        addrow: function (rowid, rowdata, position, commit) {
            commit(true);
        },
        deleterow: function (rowid, commit) {
            commit(true);
        },
        updaterow: function (rowid, newdata, commit) {
            commit(true);
        }
    };

    var flexGridDataAdapter = new $.jqx.dataAdapter(flexGridDataSource, {
        autoBind: true
    });

    $("#" + gridId).jqxGrid({
        theme: sysTheme,
        width: '100%',
        height: '100%',
        source : flexGridDataAdapter,
        localization : getLocalization("zh-CN"),
        pageable : false,
        // filterable : true,
        // sortable : true,
        altrows : true,
        editable : true,        // 是否开启编辑模式
        columnsresize: true,    // 列是否可以拉长
        columnsreorder: true,   // 列是否可以拖动
        selectionmode : 'multiplecellsadvanced',
        enablemousewheel: true,
        editmode: 'click',
        columns : gridVars.queryColumns
    });
};

