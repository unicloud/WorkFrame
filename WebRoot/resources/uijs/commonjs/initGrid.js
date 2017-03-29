var initGridFun = function(gridId) {
    //获取Grid相关的信息

    // 设置结果集Grid查询的数据
    var gridDataSource =
    {
        type: "POST",
        datatype: "json",
        datafields: tabVars.oneResultGridDataFields,
        url: pagingTableAjaxURL,
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
    
    var oneResultGridDataAdapter = new $.jqx.dataAdapter(oneResultGridDataSource, {
        autoBind: false,
        downloadComplete: function (data, status, xhr) { },
        loadComplete: function (data) {
            switchTabs(oneResultGrid);
            $("#jqxLoader").jqxLoader('close');
            $("#"+oneResultGrid).jqxGrid("autoresizecolumns");//调整列以适应文本
            endLoadingFunc(oneResultGrid);
        },
        loadError: function (xhr, status, error) {
        }
    });
    
    $("#" + oneResultGrid).jqxGrid({
        theme : sysTheme,
        width: '100%',
        height: '100%',
        source: oneResultGridDataAdapter,
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
        columns: tabVars.oneResultGridColumns,
        enablemousewheel: true,
        rendergridrows: function (obj) {
            return obj.data;
        },
        showtoolbar: true,
        rendertoolbar: function (toolbar) {
            // getGridToolBarContainer(toolbar,oneResultGrid);
            // var gridQueryBtn = getGridToolBarQueryButton(sysTheme,$("div#tab1_oneResultGridToolBar"));
        },
         filterable: true
    });
};