/**  初始化分页结果窗
 * @param gridId grid
 * @param gridVars grid参数
 */
var initPagingGridFun = function(gridId, gridVars) {

    //获取Grid相关的信息
    var JQueryAjaxTimeout = $.ajax({
        url : "basic/datawindow-factor!getDwInfo.do",
        dataType : 'json',
        async : false,  //true为异步,false为同步
        timeout : AJAXTimeout, //超时时间设置，单位毫秒
        data : {
          "dwName" : gridVars.dwName
        }, 
        type : 'POST',
        success : function(responseText) {
            console.info("结果集GRID的内容");
            console.info(responseText);
            if (responseText.success) {
                gridVars.gridColumns = responseText.rows.columns;
                gridVars.gridColumns[0].cellsrenderer = cellsrenderer;
                gridVars.gridColumns[0].aggregatesrenderer = aggregatesrenderer;
                gridVars.gridDatafields = responseText.rows.datafields;
            } else {
                if (typeof(responseText.msg) != "undefined") {
                    layer.alert(responseText.msg, {icon : 3});
                }
                return;
            }
        },
        error : function(errormessage) {
            layer.alert("请求出现异常,请重试!",{icon : 3});
            return;
        },
        complete : function(XMLHttpRequest,status) { //请求完成后最终执行参数
        　　　　  if(status=='timeout'){//超时,status还有success,error等值的情况
         　　　　　 JQueryAjaxTimeout.abort();
        　　　　　  alert("超时");
        　　　　 }
    　　  }
    });

    //拼接分页结果grid需要的信息
    //拼接灵活查询grid需要的信息
    //拼接弹出窗所需要的信息

    // 设置结果集Grid查询的数据
    var gridDataSource = {
        type: "POST",
        datatype: "json",
        datafields: gridVars.gridDatafields,
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
                oneResultGridDataSource.totalrecords = data.total;
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
        columns: gridVars.gridColumns,
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