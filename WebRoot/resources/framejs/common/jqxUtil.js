/**
 * 配合JQwidgets的工具类
 */

// 日期格式的正则表达式 [YYYYMMDD]或[YYYY-MM-DD]或[YYYY/MM/DD] 或 月/日 前不加0 
var dateReg = /^((19|20)\d\d)(-|\/|)((0|)[1-9]|1[012])\3((0|)[1-9]|[12]\d|3[01])$/;

// 设置resultGrid 序号列 的信息
var rowNumCellsrenderer = function(row, column, value) {
    return "<div style='margin:4px;'>" + (value + 1) + "</div>";
};

// 设置resultGrid 序号列 的合计栏显示 "合计:"
var rowNumAggregatesrenderer = function(aggregates) {
    return '<div style="position: relative; margin: 4px; overflow: hidden;">合计: </div>';
};

/**
 * grid查询
 * @param jqxgrid 查询结果grid的id字符串
 * @param source 数据源
 * @param params 查询条件map，key为参数名，value为测试值
 */
var query = function(jqxgrid, source, params) {
    source.data = params; //给数据源添加查询条件
    //拼接过滤条件
    var filterJson = getFilterinformation(jqxgrid);
    source.data.filterJson = JSON.stringify(filterJson);
    var dataAdpater = $("#" + jqxgrid).jqxGrid("source");
    $("#" + jqxgrid).jqxGrid({source: dataAdpater});
};

/**
 * 获取grid所有的过滤条件
 * @param jqxgrid 查询结果grid的id字符串
 * @return 返回所有的过滤信息
 */
var getFilterinformation = function(jqxgrid) {
    var filterJson = [];
    var filterArry = $("#" + jqxgrid).jqxGrid('getfilterinformation');
    for (var i = 0; i < filterArry.length; i++) {
        var colCond = new Array();
        var filterGroup = filterArry[i];
        var filters = filterGroup.filter.getfilters();
        //获取同一个字段过滤组的连接符
        var relate = filterGroup.filter.getoperatorat(0);
        switch(relate) {
            case 0 :
              relate = "AND";
              break;
            case 1 :
              relate = "OR";
              break;
            default:
              relate = "AND";
              break;
        }
        for (var j = 0; j < filters.length; j++) {
            var filterItem = {"colName":filterGroup.datafield, "colOperator": "", "colVal":"", "colType":"CHAR", "colRelate":relate};
            switch (filters[j].type) { //'stringfilter', 'numericfilter', 'booleanfilter' or 'datefilter'
                case "datefilter" :
                    filterItem.colType = "DATE";
                    break;
                default :
                    filterItem.colType = "CHAR";
                    break;
            }
            switch (filters[j].condition) {
                case "CONTAINS" :
                    filterItem.colOperator = "LIKE";
                    filterItem.colVal = "%" + filters[j].value + "%";
                    break;
                case "DOES_NOT_CONTAIN" :
                    filterItem.colOperator = "NOT LIKE";
                    filterItem.colVal = "%" + filters[j].value + "%";
                    break;
                case "STARTS_WITH" :
                    filterItem.colOperator = "LIKE";
                    filterItem.colVal = filters[j].value + "%";
                    break;
                case "ENDS_WITH" :
                    filterItem.colOperator = "LIKE";
                    filterItem.colVal = "%" + filters[j].value;
                    break;
                case "EQUAL" :
                    filterItem.colOperator = "=";
                    filterItem.colVal = filters[j].value;
                    break;
                case "NOT_EQUAL" :
                    filterItem.colOperator = "<>";
                    filterItem.colVal = filters[j].value;
                    break;
                case "LESS_THAN" :
                    filterItem.colOperator = "<";
                    filterItem.colVal = filters[j].value;
                    break;
                case "LESS_THAN_OR_EQUAL" :
                    filterItem.colOperator = "<=";
                    filterItem.colVal = filters[j].value;
                    break;
                case "GREATER_THAN" :
                    filterItem.colOperator = ">";
                    filterItem.colVal = filters[j].value;
                    break;
                case "GREATER_THAN_OR_EQUAL" :
                    filterItem.colOperator = ">=";
                    filterItem.colVal = filters[j].value;
                    break;
                case "NULL" :
                    filterItem.colOperator = "IS NULL";
                    break;
                case "NOT_NULL" :
                    filterItem.colOperator = "IS NOT NULL";
                    break;
                default :
                    filterItem.colOperator = "=";
                    filterItem.colVal = filters[j].value;
                    break;
            }
            colCond.push(filterItem);
        }
        filterJson.push(colCond);
    }
    return filterJson;
}

/**
 * 刷新grid
 * @param jqxgrid 查询结果grid的id字符串
 */
var refreshgrid = function(jqxgrid) {
    var dataAdpater = $("#" + jqxgrid).jqxGrid("source");
    $("#" + jqxgrid).jqxGrid({source: dataAdpater});
};

/**
 * 创建工具栏容器，用于添加按钮等
 * @param toolbar 工具栏
 * @return 工具栏容器
 */
var createToolBarContainer = function(toolbar,containerName) {
    var container = $("<div id='" + containerName + "' style='overflow: hidden; position: relative; margin: 5px;'></div>");
    toolbar.append(container);
    return container;
};

/**
 * 获取工具栏容器，用于添加按钮等
 * @param toolbar 工具栏
 * @return 工具栏容器
 */
var getToolBarContainer = function(containerName) {
    var container = $("#" + containerName);
    return container;
};

/**
 * 创建工具栏的查询按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createQueryButton = function(container, id, theme) {
    return createToolBarButton(container, "查询", "zoom", "left", 60, 20, id, theme);
};

/**
 * 创建工具栏的新增按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createAddButton = function(container, id, theme) {
    return createToolBarButton(container, "新增", "add", "left", 60, 20, id, theme);
};

/**
 * 创建工具栏的编辑按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createEditButton = function(container, id, theme) {
    return createToolBarButton(container, "编辑", "application_form_edit", "left", 60, 20, id, theme);
};

/**
 * 创建工具栏的保按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createSaveButton = function(container, id, theme) {
    return createToolBarButton(container, "保存", "disk", "left", 60, 20, id, theme);
};

/**
 * 创建工具栏的删除按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createDelButton = function(container, id, theme) {
    return createToolBarButton(container, "删除", "delete", "left", 60, 20, id, theme);
};

/**
 * 创建工具栏的重置按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createResetButton = function(container, id, theme) {
    return createToolBarButton(container, "重置", "arrow_rotate_clockwise", "left", 60, 20, id, theme);
};

/**
 * 创建导入按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createImportButton = function(container, id, theme) {
    return createToolBarButton(container, "导入", "database_refresh", "left", 60, 20, id, theme);
};

/**
 * 获取帮助按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createHelpButton = function(container, id, theme) {
    return createToolBarButton(container, "帮助", "help", "right", 60, 20, id, theme);
};

/**
 * 创建上传按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @return 获取工具栏的按钮
 */
var createUploadButton = function(container, id, theme) {
    return createToolBarButton(container, "上传", "page_go", "right", 60, 20, id, theme);
};

/**
 * 创建下载按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @return 获取工具栏的按钮
 */
var createDownloadButton = function(container, id, theme) {
    return createToolBarButton(container, "下载", "page_excel", "right", 60, 20, id, theme);
};

/**
 * 创建自定义按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param title 按钮文字
 * @param imgName 图标
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createToolBarCustomButton = function(container, title, imgName, id, theme) {
    var controlLength  = 60;
    if (title.length > 2) {
        controlLength = title.length * 20 ;
    } else {
        controlLength = 60;
    }
    return createToolBarButton(container, title, imgName, "left", controlLength, 20, id, theme);
};

/**
 * 创建自定义（右侧）按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param title 按钮文字
 * @param imgName 图标
 * @param id 按钮ID
 * @return 获取工具栏的按钮
 */
var createToolBarRightButton = function(container, title, imgName, id, theme) {
    var controlLength  = 60;
    if (title.length > 2) {
        controlLength = title.length * 20 ;
    } else {
        controlLength = 60;
    }
    return createToolBarButton(container, title, imgName, "right", controlLength, 20, id, theme);
};

/**
 * 获取工具栏的通用按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param value 按钮显示值
 * @param icon 可选，按钮图标
 * @param float 可选，对齐方式
 * @return 按钮
 */
var createToolBarButton = function(container, value, icon, float, width, height, Id, theme) {
    if (theme == undefined) {
        theme = sysTheme;
    }
    var html = "";
    var floatDirect = "left";
    if (float != undefined) {
        floatDirect = float;
    }
    if (floatDirect == "left") {
        html = "<div style='margin-left: 5px;";   // display:none;
    } else {
        html = "<div style='margin-right: 20px;";   // display:none;
    }
    html = html + "float:" + floatDirect + ";";
    html = html + "'";
    if (Id != undefined) {
        html = html + " id='" + Id + "' ";
    }
    html = html + " class='appbtns'>";
    if (icon != undefined) {
        html = html + "<img  style='position: relative; margin-top: 2px;' src='resources/images/icons/" + icon + ".png'/>";
    }
    html = html + "<span style='margin-left: 4px; position: relative; top: -3px;'>";
    html = html + value + "</span></div>";
    var button = $(html);
    container.append(button);
    button.jqxButton({theme: theme,width: width, height: height});
    return button;
};

/**
 * 创建编辑窗口用户控件
 * @param containerId 容器ID
 * @param item 控件项
 * @param validator 验证rule
 * @param floatDirect 浮动方向
 * @param id 控件ID
 * @param theme 主题
 * @return 编辑控件
 */
var createEditWindowCtrl = function(containerId, item, validator, floatDirect, Id, theme) {
    var container = $("#" + containerId);
    if (item == [] || item == undefined) {
        return;
    }
    if (floatDirect == undefined) {
        floatDirect = "left";
    }
    if (Id == undefined) {
        Id = containerId + item.datafield;
    }
    if (theme == undefined) {
        theme = sysTheme;
    }
    var showText = item.text;
    if (item.notnull) {
        //添加非空验证
        if (item.columntype == "textbox" || item.columntype == "datetimeinput") {
            validator.push({ input: "#" + Id, message: item.text + "不允许为空!", 
                        action: "keyup, blur", rule: "required"});
        }
        if (item.columntype == "dropdownlist") {
            validator.push({ input: "#" + Id, message: item.text + "不允许为空!", 
                        action: "keyup, blur", rule: function() {
                            var value = $('#' + Id).val();
                            return value.length > 0;
                        }});
        }
        showText = item.text + "<font color='red'>*</font>";
    }
    var html = "<div style='margin: 5px 10px;float:" + floatDirect + ";width:250px;height:30px;background:rgba(200, 200, 200, 0.2);'>";
    html = html + "<div style='float:left;text-align:justify;min-width:80px;max-width:80px;'>" + showText + ":</div>";
    if (item.columntype == "textbox") {
        html = html + "<input type='text' id='" + Id + "' style='float:left;' />";
    } else { 
        html = html + "<div id='" + Id + "' style='float:left;'></div>";
    }
    html = html + "</div>";
    var userCtrl = $(html);
    container.append(userCtrl);
    var editCtrl = $("#" + Id);
    if (item.columntype == "textbox") {
        editCtrl.jqxInput({theme: sysTheme,  height: "20px", width: '160px' , maxLength: item.dataLength });
    } else if (item.columntype == "numberinput"){
        var decimaldigits = item.cellsformat.substring(1);
        editCtrl.jqxNumberInput({theme: sysTheme,  height: "20px", width: '160px',decimalDigits : decimaldigits, spinButtons : true, inputMode : 'simple'});
    } else if (item.columntype == "datetimeinput"){ 
        editCtrl.jqxDateTimeInput({ theme: sysTheme,formatString: item.cellsformat, width: "160px", height: "20px", culture: "zh-CN", enableBrowserBoundsDetection : true });
        if (item.cellsformat.indexOf("H") > 0) {
            editCtrl.jqxDateTimeInput({showTimeButton: true});
        }
    } else if (item.columntype == "dropdownlist") {
        item.comboxList.push({'TEXT': '','VALUE': ''});
        editCtrl.jqxDropDownList({theme: sysTheme, width: '160px', height: "20px", placeHolder:'',
            source: item.comboxList, displayMember: "TEXT", valueMember: "VALUE",autoDropDownHeight: true, enableBrowserBoundsDetection : true 
        });
        if (item.comboxList.length >= 6) {
            editCtrl.jqxDropDownList({autoDropDownHeight: false, dropDownHeight: "160px"})
        }
    } else {
        editCtrl.jqxInput({theme: sysTheme,  height: "20px", width: "160px" , maxLength: "100" });
    }
    //将pkid、createUser 、createTime 、modifyUser 、modifyTime 设为不可编辑
    var specialField = item.datafield.toLocaleUpperCase();
    if (specialField == "PKID" || specialField == "CREATEUSER" || specialField == "CREATETIME" ||specialField == "MODIFYUSER" ||specialField == "MODIFYTIME") {
        setJqxDisabled(Id, true);
    }
};

/**
 * 创建普通查询窗口用户控件
 * @param containerId 容器ID
 * @param item 控件项
 * @param floatDirect 浮动方向
 * @param id 控件ID
 * @param theme 主题
 * @return 编辑控件
 */
var createNormQueryContent = function(containerId, item, floatDirect, Id, theme) {
    var container = $("#" + containerId);
    if (item == [] || item == undefined) {
        return;
    }
    if (floatDirect == undefined) {
        floatDirect = "left";
    }
    if (Id == undefined) {
        Id = containerId + item.datafield;
    }
    if (theme == undefined) {
        theme = sysTheme;
    }
    var itemText = item.text;
    if (item.isIndex) {
        itemText = "<font color = 'blue'>" + item.text + "</font>";
    }
    var html = "<div style='margin: 5px 10px;float:" + floatDirect + ";width:250px;height:30px;background:rgba(200, 200, 200, 0.2);'>";
    html = html + "<div style='float:left;text-align:justify;min-width:80px;max-width:80px;'>" + itemText + ":</div>";
    if (item.columntype == "dropdownlist") {
        html = html + "<div id='" + Id + "' style='float:left;'></div>";
    } else if (item.columntype == "datetimeinput") {
        html = html + "<input type='text' id='" + Id + "' style='float:left;' class='datetimeinput' />";
    } else {
        html = html + "<input type='text' id='" + Id + "' style='float:left;' />";
    }
    html = html + "</div>";
    var userCtrl = $(html);
    $("#" + containerId).jqxPanel('append', userCtrl);
    var editCtrl = $("#" + Id);
    if (item.columntype == "dropdownlist") {
        editCtrl.jqxDropDownList({theme: sysTheme, checkboxes: true, width: '160px', height: "20px", placeHolder:'',
            source: item.comboxList, displayMember: "TEXT", valueMember: "VALUE",autoDropDownHeight: true, enableBrowserBoundsDetection : true 
        });
        if (item.comboxList.length >= 6) {
            editCtrl.jqxDropDownList({autoDropDownHeight: false, dropDownHeight: "160px"})
        }
    } else {
        editCtrl.jqxInput({theme: sysTheme,  height: "20px", width: "160px" });
    }
};

/**
 * 生成灵活查询Grid的数据行 -- 由全局变量设置默认生成多少行 -- 同时用户可自行添加行]
 * @param flexGridDataFields 全字段GRID的datafields 属性值
 * @param rowNum 要生成的行数
 */
function createFlexGridRowData(flexGridDataFields, rowNum) {
    var customdata = new Array();
    var trowData ;
    for (var i = 0; i < rowNum; i++) {
        trowData = "{";
        $.each(flexGridDataFields, function(i) {
            var columnCotent =  flexGridDataFields[i];
            var tcolumnName = columnCotent.name;
            trowData = trowData + "\"" + tcolumnName + "\" : \"\"," ;
        });
        trowData = trowData.substring(0,trowData.lastIndexOf(','));
        trowData = trowData + "}";
        customdata.push(JSON.parse(trowData));
    }
    return customdata;
};

/**
 * 设置Jqx控件是否可编辑
 * @param itemId 控件ID
 * @param disable true -- 不可编辑，false --可以编辑
 */
function setJqxDisabled(itemId, disable) {
    var item = $("#" + itemId)[0];
    var className = item.className;
    if (className.indexOf("jqx-datetimeinput") >= 0) {
         $("#"+itemId).jqxDateTimeInput({disabled: true});
         $("#input" + itemId).unbind("mousewheel");
    } else if (className.indexOf("jqx-dropdownlist") >= 0) {
         $("#"+itemId).jqxDropDownList({disabled: true});
    } else if (className.indexOf("jqx-numberinput") >= 0) {
         $("#"+itemId).jqxNumberInput({disabled: true});
    } else if (className.indexOf("jqx-input") >=0) {
         $("#"+itemId).jqxInput({disabled: true});
    }
};

/**
 * 拼接灵活查询条件
 * @param gridId 灵活查询gridID
 * @param gridVars gridVars
 */
function generateFlexQueryCond(gridId, gridVars) {
    var dataRows = $("#" + gridId).jqxGrid('getrows');
    var dataRowsSize = dataRows.length;
    var columns = clone(gridVars.queryDwInfos.disColumns);
    var flexQueryCond = [];
    for (var j = 0; j < dataRowsSize; j++) {
        var rowCond = new Array();
        var rowdata = dataRows[j];
        for (var i = 0; i < columns.length; i++) {
            var col = columns[i];
            var itemVal = rowdata[col.datafield];
            if (itemVal == undefined || itemVal == "") {
                continue;
            }
            var arr = getQueryOperatorAndValue(itemVal);
            var colCond = {"colName":col.datafield, "colOperator":arr[1], "colVal":arr[2], "colType":"CHAR", "colRelate":arr[0]};
            if (col.columntype == "datetimeinput") {
                colCond.colType = "DATE";
            }
            rowCond.push(colCond);
        }
        if (rowCond.length > 0) {
            flexQueryCond.push(rowCond);
        }
    };
    return flexQueryCond;
};

/**
 * 拼接普通查询条件
 * @param contentId 普通查询容器ID
 */
function generateNormQueryCond(contentId) {
    var inputs = $("#" + contentId + " .jqx-widget");
    var normQueryCond = [];
    var rowCond = new Array();
    for (var i = 0 ; i < inputs.length; i++) {
        var item = inputs[i];
        var itemId = item.id;
        var colName = itemId.substring(contentId.length);
        var itemVal = $("#"+itemId).val();
        if (itemVal == undefined || itemVal == "") {
            continue;
        }
        var className = inputs[i].className;
        var colCond = "";
        if (className.indexOf("jqx-dropdownlist") >= 0) {
            colCond = {"colName":colName, "colOperator":"IN", "colVal": itemVal, "colType":"CHAR", "colRelate":"AND"};
            rowCond.push(colCond);
        } else if (className.indexOf("jqx-input") >= 0) {
            //拼接这个条件的值
            var arr = getQueryOperatorAndValue(itemVal);
            colCond = {"colName":colName, "colOperator":arr[1], "colVal":arr[2], "colType":"CHAR", "colRelate":arr[0]};
            if (item.className.indexOf("datetimeinput") >= 0) {
                colCond.colType = "DATE";
            }
            rowCond.push(colCond);
        }
    }
    if (rowCond.length > 0) {
        normQueryCond.push(rowCond);
    }
    return normQueryCond;
};

/**
 * 从输入的条件中分离出连接符、操作符和值
 * @param srcvalue
 * @return 返回连接符、操作符和值（如{"colOperator":"LIKE","colVal":"AA","colRelate": "AND",}）
 */
var getQueryOperatorAndValue = function(srcvalue) {
    //先去除前后空格
    srcvalue = $.trim(srcvalue);
    var srcvalueUpper = $.trim(srcvalue).toLocaleUpperCase();
    var cellArray = new Array();
    // 设置默认的 关联符号
    cellArray[0] = "AND";
    //先取 AND OR 关联符号
    if (srcvalueUpper.indexOf("AND ") == 0){
        cellArray[0] = "AND";
        srcvalue = $.trim(srcvalue.substring(3));
    } else if (srcvalueUpper.indexOf("OR ") == 0) {
        cellArray[0] = "OR";
        srcvalue = $.trim(srcvalue.substring(2));
    }
    // 分离出 关联符后,再一次进行 大写转换,分离出 LIKE IN NOT 等
    srcvalueUpper = $.trim(srcvalue).toLocaleUpperCase();
    //设置初始值
    cellArray[1] = "=";
    cellArray[2] = $.trim(srcvalue);
    if (srcvalueUpper.indexOf(">=") == 0) {
        cellArray[1] = ">=";
        cellArray[2] = $.trim(srcvalue.substring(2));
    } else if (srcvalueUpper.indexOf(">") == 0) {
        cellArray[1] = ">";
        cellArray[2] = $.trim(srcvalue.substring(1));
    } else if (srcvalueUpper.indexOf("<=") == 0) {
        cellArray[1] = "<=";
        cellArray[2] = $.trim(srcvalue.substring(2));
    } else if (srcvalueUpper.indexOf("<>") == 0) {
        cellArray[1] = "<>";
        cellArray[2] = $.trim(srcvalue.substring(2));
    } else if (srcvalueUpper.indexOf("<") == 0) {
        cellArray[1] = "<";
        cellArray[2] = $.trim(srcvalue.substring(1));
    } else if (srcvalueUpper.indexOf("=") == 0) {
        cellArray[1] = "=";
        cellArray[2] = $.trim(srcvalue.substring(1));
    } else if (srcvalueUpper.indexOf("IN ") == 0) {
        cellArray[1] = "IN";
        cellArray[2] = $.trim(srcvalue.substring(3));
    } else if (srcvalueUpper.indexOf("LIKE ") == 0) {
        cellArray[1] = "LIKE";
        cellArray[2] = $.trim(srcvalue.substring(4));
    } else if (srcvalueUpper.indexOf("NOT ") == 0) {
        var nextStr = $.trim(srcvalueUpper.substring(4));
        if (nextStr.indexOf("IN ") == 0) {
            cellArray[1] = "NOT IN";
            cellArray[2] = $.trim(nextStr.substring(3));
        } else if (nextStr.indexOf("LIKE ") == 0) {
            cellArray[1] = "NOT LIKE";
            cellArray[2] = $.trim(nextStr.substring(5));
        }
    } else if (srcvalueUpper.indexOf("IS ") == 0) {
        var nextStr = $.trim(srcvalueUpper.substring(3));
        if (nextStr == "NULL" || nextStr.indexOf("NULL ") == 0) {
            cellArray[1] = "IS NULL";
            cellArray[2] = "";
        } else if (nextStr.indexOf("NOT ") == 0) {
            var secondStr = $.trim(nextStr.substring(4));
            if (secondStr == "NULL" || secondStr.indexOf("NULL ") == 0) {
                cellArray[1] = "IS NOT NULL";
                cellArray[2] = "";
            }
        }
    } else if (srcvalueUpper.indexOf("NVL ") == 0) {
        cellArray[1] = "NVL";
        cellArray[2] = $.trim(srcvalue.substring(4));
    }
    return cellArray ;
};
//权限控制思路。判断如果是发布版，则进入各个页面之前获取页面的权限，根据权限初始化页面，否则不加载页面
//根据权限加载页面时区分tab页权限，按钮权限，右键菜单权限
//考虑对于嵌套JSP的情况和iframe加载页面的情况，权限的控制处理