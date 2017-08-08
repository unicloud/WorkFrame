
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
 * @Title initPagingGrid 初始化分页结果窗
 * @param gridId gridID
 * @param gridVars grid参数
 * @param hasEditWindow 是否需要初始化编辑窗
 * @param canSave 是否需要保存按钮
 */
var initPagingGrid = function(gridId, gridVars, hasEditWindow, canSave) {
    initResultGrid(gridId, gridVars, hasEditWindow, canSave, "PAGING");
};

/**
 * @Title initNoPagingGrid 初始化不分页结果窗
 * @param gridId gridID
 * @param gridVars grid参数
 * @param hasEditWindow 是否需要初始化编辑窗
 * @param canSave 是否需要保存按钮
 */
var initNoPagingGrid = function(gridId, gridVars, hasEditWindow, canSave) {
    initResultGrid(gridId, gridVars, hasEditWindow, canSave, "NO_PAGING");
};

/**
 * @Title initResultGrid 初始化结果窗
 * @param gridId gridID
 * @param gridVars grid参数
 * @param hasEditWindow 是否需要初始化编辑窗
 * @param canSave 是否需要保存按钮
 * @param gridType PAGING -- 分页，NO_PAGING --不分页
 */
var initResultGrid = function(gridId, gridVars, hasEditWindow, canSave, gridType) {
    if (gridVars.resulDwInfos.length == 0) {
        queryDwInfo(gridVars);
    }
    //依然为空，则不往下执行
    if (gridVars.resulDwInfos.length == 0) {
        return;
    }
    var columns = clone(gridVars.resulDwInfos.disColumns);
    for (var i= 0; i< columns.length; i++) {
        var item =  columns[i];
        //如果是非空字段
        if (item.notnull) {
            item.text = item.text + "*";
            item.classname = "requirdField";
        }
        var filedItem = {"name" : item.datafield};
        if (item.columntype == "numberinput") {
            filedItem.type = "number";
            item.aggregates = ['sum'];
        } else if (item.columntype == "datetimeinput") {
            filedItem.type = "date";
            filedItem.format = item.cellsformat;
        } else if (item.columntype == "dropdownlist") {
            item.editable = true;
            var txtFiledItem = {"name": item.datafield + "TXT","type" :"string",
            "value": item.datafield, "values": {"name":"TEXT", "value":"VALUE", "source":item.comboxList}};
            gridVars.resultDatafields.push(txtFiledItem);
            item.displayfield = item.datafield + "TXT";
            eval("var dropdownlisteditor" + gridId + item.datafield + "List = clone(item.comboxList)");
            eval("dropdownlisteditor" + gridId + item.datafield + "List.push({'TEXT': '','VALUE': ''})");
            item.createeditor = function(row, value, editor) {
                var autoHeight = true;
                eval("var testList= " + editor[0].id + "List");
                if (testList.length >= 6) {
                    autoHeight = false;
                }
                editor.jqxDropDownList({source: testList,displayMember: "TEXT", valueMember: "VALUE",autoDropDownHeight: autoHeight});
            };
            filedItem.type = "string";
        } else {
            filedItem.type = "string";
        }
        gridVars.resultColumns.push(item);
        gridVars.resultDatafields.push(filedItem);
        if (item.columntype == "dropdownlist") {

            var txtColumnItem = {};
            txtColumnItem.displayfield = item.datafield;
            txtColumnItem.columntype = "string";
            txtColumnItem.hidden = true;
            txtColumnItem.text = item.text;
            gridVars.resultColumns.push(txtColumnItem);
        }
    }
    //分页与不分页的url不同,默认不分页的
    var srcUrl = "basic/datawindow-factor!queryDataSet.do";
    var isPaging = false;
    if (gridType == "PAGING") {
        srcUrl = "basic/datawindow-factor!queryPagingDataSet.do";
        isPaging = true;
    }

    // 设置结果集Grid查询的数据
    var gridDataSource = {
        type: "POST",
        datatype: "json",
        pagesize: commonpageInitSize,
        datafields: gridVars.resultDatafields,
        url: srcUrl,
        root: 'rows',
        cache: false,
        data : {
            "whereJson" : "-1",
            "dwName" : gridVars.resultDwName
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
            $("#jqxLoader").jqxLoader('close');
            $("#"+gridId).jqxGrid("autoresizecolumns");//调整列以适应文本
        },
        loadError: function (xhr, status, error) {
            $("#jqxLoader").jqxLoader('close');
            layer.alert("数据加载异常！",{icon:8});
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
        showaggregates: true,
        sortable : true,
        editable: true,
        pageable: isPaging,
        virtualmode: isPaging,
        pagesizeoptions: commonpagesizeoptions,
        columnsresize: true,    // 列是否可以拉长
        columnsreorder: true,   // 列是否可以拖动   
        selectionmode : 'multiplerowsextended',
        editmode: 'click',
        columns : gridVars.resultColumns,
        enablemousewheel: true,
        showtoolbar: true,
        filterable: true,
        rendertoolbar: function (toolbar) {
        },
        rendergridrows: function (obj) {
            return obj.data;
        },
        updatefilterconditions: function (type, defaultconditions) {
            var stringcomparisonoperators = [ 'CONTAINS', 'DOES_NOT_CONTAIN','STARTS_WITH','ENDS_WITH','EQUAL', 'NULL', 'NOT_NULL'];
            var numericcomparisonoperators = ['EQUAL', 'NOT_EQUAL', 'LESS_THAN', 'LESS_THAN_OR_EQUAL', 'GREATER_THAN', 'GREATER_THAN_OR_EQUAL', 'NULL', 'NOT_NULL'];
            var datecomparisonoperators = ['EQUAL', 'NOT_EQUAL', 'LESS_THAN', 'LESS_THAN_OR_EQUAL', 'GREATER_THAN', 'GREATER_THAN_OR_EQUAL', 'NULL', 'NOT_NULL'];
            var booleancomparisonoperators = ['EQUAL', 'NOT_EQUAL'];
            switch (type) {
                case 'stringfilter':
                    return stringcomparisonoperators;
                case 'numericfilter':
                    return numericcomparisonoperators;
                case 'datefilter':
                    return datecomparisonoperators;
                case 'booleanfilter':
                    return booleancomparisonoperators;
            }
        },
    });
    

    //去除默认的右键菜单
    $("#" + gridId).on('contextmenu',function() {
        return false;
    });

    //排序事件
    $("#" + gridId).on("sort", function(event) {
        var rows = $("#" + gridId).jqxGrid('getrows');
        if (rows.length == 0) {
            $("#" + gridId).jqxGrid('removesort');
            return;
        }
        $("#" + gridId).jqxGrid("updatebounddata", "data");
    });

    //过滤事件
    $("#" + gridId).on("filter", function(event) {
        var source = $("#"+ gridId).jqxGrid('source')._source
        var whereJson = source.data.whereJson;
        var customCond = "";
        if (whereJson != undefined && whereJson != '-1') {
            customCond = JSON.parse(whereJson).customCond;
        }
        //需要有用户输入的查询条件情况下才做过滤
        if (customCond == undefined || customCond.length == 0) {
            $("#" + gridId).jqxGrid('clearfilters', false);
            return;
        }
        var filterJson = getFilterinformation(gridId);
        source.data.filterJson = JSON.stringify(filterJson);
        $("#" + gridId).jqxGrid("updatebounddata", "data");
    });

    if (hasEditWindow) {
        initEditWindow(gridId + "EditWindow", gridVars);
    }
};

/**
 * @Title initEditWindow 初始化数据编辑弹出窗
 * @param containerId 弹出窗容器的ID
 * @param gridVars grid参数
 * @param canSave 是否提供保存按钮
 */
var initEditWindow = function(containerId, gridVars, canSave) {
    //弹出窗上的控件ID需要带一个随机数，以确保同一个页面上的控件ID可以区别开
    $("#" + containerId).jqxWindow({
        theme : sysTheme,
        autoOpen: false,
        isModal: true,
        height: "95%",
        width: "95%",
        resizable: true
    });
    //切换是否限制大写
    var fontSwitchbtn = createToolBarRightButton($("#" + containerId + "Header"), "取消大写限制", "font");
    fontSwitchbtn.click(function (event) {
        $("#" + containerId + "Content .jqx-input").unbind("blur");
        if (this.innerHTML.indexOf("取消") > 0) {
            $("#" + containerId + "Content .jqx-input").bind("blur",function(event) {
            });
            this.innerHTML = this.innerHTML.replace("取消","设置");
        } else {
            //首先将所有的jqx-input的值设置为大写
            var inputs = $("#" + containerId + "Content .jqx-input:not(.jqx-input-disabled)");
            for (var i = 0 ; i < inputs.length; i++) {
                var value = $.trim($("#" +  inputs[i].id).val());
                value = value.toLocaleUpperCase();
                $("#" +  inputs[i].id).val(value);
            }
            //其次添加blur事件
            $("#" + containerId + "Content .jqx-input").bind("blur",function(event) {
                var input = event.target;
                var ctrlId = input.id;
                var value = $.trim($("#" +  ctrlId).val());
                value = value.toLocaleUpperCase();
                $("#" +  ctrlId).val(value);
            });
            this.innerHTML = this.innerHTML.replace("设置", "取消");
        }
    });
    if (gridVars.resulDwInfos.length == 0) {
        queryDwInfo(gridVars);
    }
    //依然为空，则不往下执行
    if (gridVars.resulDwInfos.length == 0) {
        return;
    }
    var columns = clone(gridVars.resulDwInfos.disColumns);
    var validator = new Array();
    for (var i= 0; i< columns.length; i++) {
        var item =  columns[i];
        createEditWindowCtrl(containerId + "Content", item, validator);
    }
    //添加控件验证
    $("#" + containerId + "Content").jqxValidator({
        rules: validator
    });
    //默认将所有文本编辑框变成大写限制
    $("#" + containerId + "Content .jqx-input").blur(function(event) {
        var input = event.target;
        var ctrlId = input.id;
        var value = $.trim($("#" +  ctrlId).val());
        value = value.toLocaleUpperCase();
        $("#" +  ctrlId).val(value);
    });
    //将enter键模拟成Tab键
    $("#" + containerId + "Content .jqx-widget").keydown(function(e) {
        if (e.keyCode == 13) {
            var inputs = $("#" + containerId + "Content .jqx-widget[disabled!='disabled'][aria-disabled!='true']:not(.jqx-input-disabled):not(.jqx-dropdownlist-state-disabled)");
            var idx = inputs.index(this);
            if (idx == inputs.length - 1) {
                var className = inputs[0].className;
                if (className.indexOf("jqx-datetimeinput") >= 0) {
                     $("#"+inputs[0].id).jqxDateTimeInput('focus');
                } else if (className.indexOf("jqx-dropdownlist") >= 0) {
                     $("#"+inputs[0].id).jqxDropDownList('focus');
                } else if (className.indexOf("jqx-numberinput") >= 0) {
                     $("#"+inputs[0].id).jqxNumberInput('selectAll');
                } else if (className.indexOf("jqx-input") >=0) {
                     $("#"+inputs[0].id).jqxInput('selectAll');
                } else {
                    inputs[0].focus();
                }
            } else {
                var className = inputs[idx + 1].className;
                if (className.indexOf("jqx-datetimeinput") >= 0) {
                     $("#"+inputs[idx + 1].id).jqxDateTimeInput('focus');
                } else if (className.indexOf("jqx-dropdownlist") >= 0) {
                     $("#"+inputs[idx + 1].id).jqxDropDownList('focus');
                } else if (className.indexOf("jqx-numberinput") >= 0) {
                     $("#"+inputs[idx + 1].id).jqxNumberInput('selectAll');
                } else if (className.indexOf("jqx-input") >=0) {
                     $("#"+inputs[idx + 1].id).jqxInput('selectAll');
                } else {
                    inputs[idx + 1].focus();
                }
            }
        }
    });
    //添加保存、取消按钮
    $("#" + containerId + "Content").append($("<div style='clear:both;'></div>"));
    $("#" + containerId + "Content").append($("<div style='margin-right: 20px;float:right; width: 60px; height: 20px;'></div>"));
    var canxbtn = createToolBarRightButton($("#" + containerId + "Content"), "取消", "delete", containerId + "_canxBtn");
    var savebtn = createToolBarRightButton($("#" + containerId + "Content"), "保存", "disk", containerId + "_saveBtn");
    // 关闭弹出窗口
    canxbtn.click(function() {
        $("#" + containerId + "Content").jqxValidator('hide');
        $("#" + containerId).jqxWindow("close");
    });
    // 保存弹出窗口
    savebtn.click(function() {
        var validateResult = $("#" + containerId + "Content").jqxValidator('validate');
        if (!validateResult) {
            return;
        }
    });
};

/**
 * @Title initNormQueryWindow 初始化普通查询容器
 * @param containerId 普通查询容器的ID
 * @param gridVars grid参数
 */
var initNormQueryWindow = function(containerId, gridVars) {
    if (gridVars.queryDwInfos.length == 0) {
        queryDwInfo(gridVars, "query");
    }
    //依然为空，则不往下执行
    if (gridVars.queryDwInfos.length == 0) {
        return;
    }
    $("#" + containerId).jqxPanel({ width: "100%", height: "100%"});
    var columns = clone(gridVars.queryDwInfos.disColumns);
    for (var i= 0; i< columns.length; i++) {
        var item =  columns[i];
        createNormQueryContent(containerId, item);
    }
    //将enter键模拟成Tab键
    $("#" + containerId + " .jqx-widget").keydown(function(e) {
        if (e.keyCode == 13) {
            var inputs = $("#" + containerId + " .jqx-widget[disabled!='disabled'][aria-disabled!='true']:not(.jqx-input-disabled):not(.jqx-dropdownlist-state-disabled)");
            var idx = inputs.index(this);
            if (idx == inputs.length - 1) {
                var className = inputs[0].className;
                if (className.indexOf("jqx-datetimeinput") >= 0) {
                     $("#"+inputs[0].id).jqxDateTimeInput('focus');
                } else if (className.indexOf("jqx-dropdownlist") >= 0) {
                     $("#"+inputs[0].id).jqxDropDownList('focus');
                } else if (className.indexOf("jqx-numberinput") >= 0) {
                     $("#"+inputs[0].id).jqxNumberInput('selectAll');
                } else if (className.indexOf("jqx-input") >=0) {
                     $("#"+inputs[0].id).jqxInput('selectAll');
                } else {
                    inputs[0].focus();
                }
            } else {
                var className = inputs[idx + 1].className;
                if (className.indexOf("jqx-datetimeinput") >= 0) {
                     $("#"+inputs[idx + 1].id).jqxDateTimeInput('focus');
                } else if (className.indexOf("jqx-dropdownlist") >= 0) {
                     $("#"+inputs[idx + 1].id).jqxDropDownList('focus');
                } else if (className.indexOf("jqx-numberinput") >= 0) {
                     $("#"+inputs[idx + 1].id).jqxNumberInput('selectAll');
                } else if (className.indexOf("jqx-input") >=0) {
                     $("#"+inputs[idx + 1].id).jqxInput('selectAll');
                } else {
                    inputs[idx + 1].focus();
                }
            }
        }
    });
    //对于日期字段,需要验证日期格式
    $("#" + containerId + " .datetimeinput").blur(function(event) {
        var input = event.target;
        var ctrlId = input.id;
        var value = $.trim($("#" +  ctrlId).val());
        value = value.toLocaleUpperCase();
        value = getQueryOperatorAndValue(value)[2];
        // 对日期数据进行验证
        if (value != "" && !dateReg.test(value)) {
            layer.alert("日期格式有误,正确格式应为[YYYYMMDD]或[YYYY-MM-DD]或[YYYY/MM/DD],请修改后再进行查询",
                {icon: 2,title: "系统提示"},function (index){
                    $("#" +  ctrlId).val("");
                    layer.close(index);
                }
            );
        }
    });
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
    for (var i= 0; i< columns.length; i++) {
        var item =  columns[i];
        item.classname = item.columntype;
        if (item.columntype == "numberinput" || item.columntype == "datetimeinput") {
            item.columntype = "textbox";
            item.cellsformat = "";
        } else if (item.columntype == "dropdownlist") {
            item.editable = true;
            var txtFiledItem = {"name": item.datafield + "TXT","type" :"string",
            "value": item.datafield, "values": {"name":"TEXT", "value":"VALUE", "source":item.comboxList}};
            gridVars.queryDatafields.push(txtFiledItem);
            item.displayfield = item.datafield + "TXT";
            eval("var dropdownlisteditor" + gridId + item.datafield + "List = item.comboxList");
            item.createeditor = function(row, value, editor) {
                var autoHeight = true;
                eval("var testList= " + editor[0].id + "List");
                if (testList.length >= 6) {
                    autoHeight = false;
                }
                editor.jqxDropDownList({source: testList,displayMember: "TEXT", valueMember: "VALUE",autoDropDownHeight: autoHeight});
            };
        }
        //如果是索引
        if (item.isIndex) {
            item.classname = item.columntype + " indexField";
        }
        var filedItem = {"name" : item.datafield, "type" : "string"};
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

    $("#" + gridId).on('contextmenu',function() {
        return false;
    });

    $("#" + gridId).on('cellendedit', function(event) {
        judgeFlexCond(gridId, event);
    });
};

/**
 * @Title judgeFlexCond 校验灵活查询输入的查询条件
 * @param gridId gridId
 * @param event 查询条件编辑完成事件
 */
var judgeFlexCond = function(gridId, event) {
    // event arguments.
    var args = event.args;
    // column data field.
    var dataField = event.args.datafield;
    // cell value
    var value = args.value;
    var col = $("#" + gridId).jqxGrid('getcolumn',dataField);
    if (col.classname.indexOf("datetimeinput") >= 0) {
        value = getQueryOperatorAndValue(value)[2];
        // 对日期数据进行验证
        if (value != "" && !dateReg.test(value)) {
            layer.alert("日期格式有误,正确格式应为[YYYYMMDD]或[YYYY-MM-DD]或[YYYY/MM/DD],请修改后再进行查询",
                {icon: 2,title: "系统提示"},function (index){
                        $("#" + gridId).jqxGrid('setcellvalue', args.rowindex, dataField, "");
                        layer.close(index);
                    }
            );
            return ;
        }
    }
};