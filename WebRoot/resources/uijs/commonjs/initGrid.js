//初始化grid  区分分页与不分页,区分是查询还是结果集 ,区分是否需要合并项, 区分是否有工具栏
//结果集自动带导出数据、配置数据窗、添加计算列、刷新Grid的右键菜单
//查询窗自动带清理查询条件和配置数据窗的右键菜单
//配置数据窗能修改中文文本
//结果窗带复制数据的功能，能选中数据做复制
//Grid新增数据时，能跳到当前编辑行
//删除数据时，需要取消选中。
//查询数据出来之后，能自适应宽度
//列名能区分是否是必填字段
//查询窗的列名出体现出是否有索引


var oneResultGridFunc = function(oneResultGrid, tabVars, isShowWindow, isShowWindowBtn, paramArray) {
    
    // 获取 oneResultGrid 的信息
    var JQueryAjaxTimeout = $.ajax({
        url : resultGridAjaxURL,
        dataType : 'json',
        async : false,  //true为异步,false为同步
        timeout : AJAXTimeout, //超时时间设置，单位毫秒
        data : {
          "dwName" : tabVars.oneResultGridDwName
        }, 
        type : 'POST',
        success : function(responseText) {
            console.info("结果集GRID的内容");
            console.info(responseText);
            if (responseText.success) {
                tabVars.oneResultGridColumns = responseText.result.columns;
                tabVars.oneResultGridColumns[0].cellsrenderer = cellsrenderer;
                tabVars.oneResultGridColumns[0].aggregatesrenderer = aggregatesrenderer;
                tabVars.oneResultGridDataFields = responseText.result.displayQueryField;
                tabVars.oneResultGridPrmColumn = responseText.result.primaryKeyField;
                tabVars.oneResultGridShowWindowData = responseText.result.showWindowData;
            } else {
                if (typeof(responseText.msg) != "undefined") {
                    layer.alert("请求出现异常,请重试或者联系系统管理员,异常信息为: " 
                            +  responseText.msg, {icon : 3});
                }
            }
        },
        error : function(errormessage) {
            layer.alert("请求出现异常,请重试!",{icon : 3});
        },
        complete : function(XMLHttpRequest,status) { //请求完成后最终执行参数
        　　　　  if(status=='timeout'){//超时,status还有success,error等值的情况
         　　　　　 JQueryAjaxTimeout.abort();
        　　　　　  alert("超时");
        　　　　 }
    　　  }
    });

    
    var columnNum = tabVars.oneResultGridColumns.length;
    // 计算列数组 -- 数组中包含数组
    var calcolumnsArray = tabVars.calcolumns;
    // 计算列操作符
    var calcOperatorsArray = tabVars.calcOperators;
    // 虚拟计算列名称
    var calcNameArray = tabVars.calcName;

    // 计算列的位置 0 前面 ,1 后面  -- 考虑多个计算列出现的情况
    var calcPostionArray = tabVars.calcPostion;
    
    // 计算列的小数位
    var calcDecimalPlaceArray = tabVars.calcDecimalPlace;

    /**
     * 记录每一个计算列中包含的字段的名称与GRID中的索引值
     */
    var calcolumnsObjectArray = new Array();

    var calBool = false;
    // 统计有几个计算列 -- 数组的个数
    var calcolumnsArrLenth;
    /**
     * 统计总共有多少个列参与了计算
     */
    var calcolumnsCount = 0;
    // 统计当前找到了几个列
    var calTempLenth = 0;
    if (calcolumnsArray != undefined && calcOperatorsArray != undefined) {
        calcolumnsArrLenth = calcolumnsArray.length;
        calBool = true;
        // 计算 总共有多少个列参与了计算
        for (var i = 0; i < calcolumnsArrLenth; i++) {
            calcolumnsCount = calcolumnsCount + calcolumnsArray[i].length;
            calcolumnsObjectArray.push(new Array());
        };
    }

    // 开始遍历 oneResultGridColumns
    for (var i = 0; i < columnNum; i++) {
        var columnObject = tabVars.oneResultGridColumns[i];
        // 如果遇到 aggregates: "['sum']" 这些列 则改为 aggregates: ['sum']
        if (typeof(columnObject.aggregates) != "undefined") {
            tabVars.oneResultGridColumns[i].aggregates = ['sum'];
        }

        // 多层循环,将每一层的结果都保存起来.
        if (calBool && calTempLenth < calcolumnsCount) {
            // 获取列的字段名
            var columnDatafield = columnObject.datafield;
            // 循环计算列数组
            for (var j = 0; j < calcolumnsArrLenth; j++) {
                // 计算列的数组对象,为计算列中的每一个单列赋信息
                // 获取每一个计算列中包含的子列
                var calcolumnArrayObject = calcolumnsArray[j];

                for (var k = 0; k < calcolumnArrayObject.length; k++) {
                    // 获取单个列名
                    if (columnDatafield == calcolumnArrayObject[k]) {
                        // 记录 计算列的列名以及列的索引位置
                        var calObject = new Object();
                        calObject.columnName = columnDatafield;
                        calObject.position = i;
                        calcolumnsObjectArray[j].push(calObject);
                        calTempLenth++;
                        break;
                    }
                }
            }
        }
    }
 
    // 往jqxGrid中插入计算列   
    if (calBool) {
        for (var j = 0; j < calcolumnsArrLenth; j++) {
            /*
             * 1. 获取列的别名
             * 2. 获取单个计算列的集合
             * 3. 获取操作符
             */

            var tempColumnAliasArr = calcNameArray[j].split(":");          // taxAmountRate:税费合计列
            var tempCalcColumnArr = calcolumnsArray[j];      // taxAmount,taxRate
            var tempCalcOperators = calcOperatorsArray[j];   // +
            var tempCalcPostionArr = calcPostionArray[j].split(":");       // 1:1

            var tempColumnPostion = tempCalcPostionArr[1];
            
            var tempParam = new Array();

            tempParam.push("var tempCalcColumnArr" + j + " = calcolumnsArray[" + j + "];");
            tempParam.push("var tempColumnName = tempCalcColumnArr" + j + "[" + tempCalcPostionArr[0] + "];");
            tempParam.push("var tempCalcColumnObject" + j + " = new Object();");
            tempParam.push("tempCalcColumnObject" + j + ".text = '" + tempColumnAliasArr[1] + "';");
            tempParam.push("tempCalcColumnObject" + j + ".datafield = '" + tempColumnAliasArr[0] + "';");
            tempParam.push("tempCalcColumnObject" + j + ".editable = false;");
            tempParam.push("tempCalcColumnObject" + j + ".columntype = 'number';");
            tempParam.push("tempCalcColumnObject" + j + ".aggregates = ['sum'];");
            // setCalcColumnResultf()方法写在 commonUtil.js 中
            tempParam.push("tempCalcColumnObject" + j + ".cellsrenderer = function (index, datafield, value, defaultvalue, column, rowdata) { var total" + j + " = setCalcColumnResult(rowdata, tempCalcColumnArr" + j + ", '" + tempCalcOperators + "'); return \"<div style='margin: 4px;' class='jqx-right-align'>\" +  $('#" + oneResultGrid + "').jqxGrid('source').formatNumber(total" + j + ", 'f" + calcDecimalPlaceArray[j] + "') + \"</div>\";};");
            
            eval(tempParam.join(""));

            // 判断列要插入的位置,默认为GRID的最后面
            var tempSpliceIndex = columnNum;

            // 判断列应该加在哪个位置
            // calcolumnsObjectArray 列的索引位置与名称
            var tempObjectArray = calcolumnsObjectArray[j];
            for (var m = 0; m < tempObjectArray.length; m++) {
                var tObject = tempObjectArray[m];
                if (tempColumnName == tObject.columnName) {
                    var tIndex = tObject.position;
                    if (tempColumnPostion == 0) {
                        tempSpliceIndex = tIndex;
                    } else {
                        tempSpliceIndex = tIndex + 1 + j;
                    }
                    break;
                }

            };
            tabVars.oneResultGridColumns.splice(tempSpliceIndex, 0, eval("tempCalcColumnObject" + j));
        }
    }

    // 开始在此处对 下拉格式的单元格的数据源进行分离
           
     var td = 0;
    
     // 将控件类型为 HIDDEN的分离出来,表示为隐藏控件
     var rowNum = tabVars.oneResultGridShowWindowData.length;
     for (var i = rowNum - 1; i >= 0; i--) {
         if (tabVars.oneResultGridShowWindowData[i].controlType == "HIDDEN") {
             tabVars.oneResultGridHiddenShowWindowData.push(tabVars.oneResultGridShowWindowData[i]);
             tabVars.oneResultGridShowWindowData.splice(i, 1);
         }
     };
     
     // 将数据源的值分离出来 -- oneResultGridCDDListData,
     // 给 grid 的下拉单元格及 弹出框的下拉控件使用
     rowNum = tabVars.oneResultGridShowWindowData.length;
     for (var i = 0 ; i < rowNum; i++) {
         var controlInfo = tabVars.oneResultGridShowWindowData[i];
         if (controlInfo.controlType == "DROPDOWNLIST") {
             var listObject = new resultGridListObject(controlInfo.controlID, controlInfo.controlData);
             tabVars.oneResultGridCDDListData.push(listObject);
         }
     };
    
     // 循环遍历 tabVars.oneResultGridDataFields ,如果发现有包含 values 属性,则要进行赋值
     rowNum = tabVars.oneResultGridDataFields.length;
     for (var i = 0;i < rowNum; i++) {
         if (typeof(tabVars.oneResultGridDataFields[i].values) != "undefined") {
             tabVars.oneResultGridDataFields[i].values.source = tabVars.oneResultGridCDDListData[td].columnData;
             td++;
         }
     }
    
    // 设置 某些列单元格的底色
    var cellStyle = tabVars.cellStyle;
    if (cellStyle != undefined) {
        var cellStyleLength = cellStyle.length;
        for (var i = 0; i < cellStyleLength; i++) {
            var jsonObject = cellStyle[i];
            for (var j = 0;j < columnNum; j++) {
                if (tabVars.oneResultGridColumns[j].datafield == jsonObject.columnAlias) {
                    tabVars.oneResultGridColumns[j].cellclassname = jsonObject.columnStyle;
                    break;
                }
            }
        };
    }

    
    // 循环遍历 tabVars.oneResultGridColumns ,如果发现有包含 values 属性,则要进行赋值
     rowNum = tabVars.oneResultGridColumns.length;
     td = 0;
     var autoHeight = true;
     for (var j = 0; j < rowNum; j++) {
         if (typeof(tabVars.oneResultGridColumns[j].createeditor) != "undefined") {
             autoHeight = true;
             var tcolumnData = tabVars.oneResultGridCDDListData[td].columnData;
             if (tcolumnData.length > 10) {
                 autoHeight = false;
             }
    
             eval( "var cdata_" + td + "= tcolumnData,hasAutoHeight_" + td + " = autoHeight ");
    
             if (td == 0) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_0,
                         autoDropDownHeight: hasAutoHeight_0,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 1) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_1,
                         autoDropDownHeight: hasAutoHeight_1,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 2) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_2,
                         autoDropDownHeight: hasAutoHeight_2,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 3) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_3,
                         autoDropDownHeight: hasAutoHeight_3,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 4) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_4,
                         autoDropDownHeight: hasAutoHeight_4,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 5) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_5,
                         autoDropDownHeight: hasAutoHeight_5,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 6) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_6,
                         autoDropDownHeight: hasAutoHeight_6,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 7) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_7,
                         autoDropDownHeight: hasAutoHeight_7,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 8) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_8,
                         autoDropDownHeight: hasAutoHeight_8,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 9) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_9,
                         autoDropDownHeight: hasAutoHeight_9,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 10) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_10,
                         autoDropDownHeight: hasAutoHeight_10,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 11) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_11,
                         autoDropDownHeight: hasAutoHeight_11,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 12) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_12,
                         autoDropDownHeight: hasAutoHeight_12,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 13) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_13,
                         autoDropDownHeight: hasAutoHeight_13,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 14) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_14,
                         autoDropDownHeight: hasAutoHeight_14,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 15) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_15,
                         autoDropDownHeight: hasAutoHeight_15,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 16) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_16,
                         autoDropDownHeight: hasAutoHeight_16,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 17) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_17,
                         autoDropDownHeight: hasAutoHeight_17,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 18) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_18,
                         autoDropDownHeight: hasAutoHeight_18,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 19) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_19,
                         autoDropDownHeight: hasAutoHeight_19,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             } else if (td == 20) {
                 tabVars.oneResultGridColumns[j].createeditor = function (row, value, editor) {
                     editor.jqxDropDownList({
                         source: cdata_20,
                         autoDropDownHeight: hasAutoHeight_20,
                         displayMember: "TEXT", valueMember: "VALUE"
                     });
                 };
             }
             td++;
         }
     }
     
     // -----------------------------
    
    // 设置结果集Grid查询的数据
    var oneResultGridDataSource =
    {
        type: 'POST',
        datatype: "json",
        pagesize: commonpageInitSize,
        datafields: tabVars.oneResultGridDataFields,
        url: pagingTableAjaxURL,
        root: 'rows',
        cache: false,
        data : {
            whereJson : "-1",
            "dwName" : tabVars.oneResultGridDwName
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
        },
        loadError: function (xhr, status, error) {
        }
    });
    
    $("#" + oneResultGrid).jqxGrid(
    {
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
   

    // 记录jqxGrid的单元格修改
    $("#" + oneResultGrid).on('cellendedit', function (event) 
    {
        addModifyDataResultGrid(oneResultGrid,event,tabVars.oneResultGridModifyData);
    });

    $("#" + oneResultGrid).on("sort", function(event) {
        $("#"+oneResultGrid).jqxGrid("updatebounddata");
    });

    $("#" + oneResultGrid).on("filter", function(event) {
        var resultArray = new Array();
        var result = getGridFilterInfo(oneResultGrid, resultArray);
        
        if (result == "") {
            return;
        }
        
        var tabVars;
        eval("tabVars = " + currentTabPrefix + "Vars;");
        $("#jqxLoader").jqxLoader('open');
        
        var flexGridCondition = btnClickQueryData(currentTabPrefix, tabVars);
        if (flexGridCondition == "") return;
        flexGridCondition.filterJson = resultArray;
        
        if (hasCustomWhereCondition ==  "ticketAuditkepiao" || hasCustomWhereCondition ==  "ticketAuditzafeikan") {
            
            var receiveSn = "";
            var carrier = "";
            var issCo = "";
            var selectedItem = $('#flightInfoTree').jqxTree('selectedItem');
            if (selectedItem != null && selectedItem.parentElement != null) {
                var arr = (selectedItem.label).split("-");
                carrier = arr[0];
                issCo = arr[1];
                var parentItem = $('#flightInfoTree').jqxTree('getItem', selectedItem.parentElement);
                receiveSn = parentItem.label;
                selectYm = receiveSn.substring(0,6);
            }
            
            if (hasCustomWhereCondition =  "ticketAuditkepiao") {
                var tktCheckCond = $("#tktCheckCond").jqxDropDownList('getCheckedItems'); 
                var tktChk = $("#tktCheckCond").val();
                whereJson = {"dwName" : tabVars.oneResultGridDwName,"whereJson": JSON.stringify(flexGridCondition), 
                        "issCo": issCo, "carrier" : carrier, "receiveSn" : receiveSn,"chkType" : "TKT:" + tktChk};
                
            } else if (hasCustomWhereCondition =  "ticketAuditzafeikan") {
                var emdCheckCond = $("#emdCheckCond").jqxDropDownList('getCheckedItems'); 
                var emdChk = $("#emdCheckCond").val();
                whereJson = {"dwName" : tabVars.oneResultGridDwName,"whereJson": JSON.stringify(flexGridCondition),
                        "issCo": issCo, "carrier" : carrier, "receiveSn" : receiveSn,"chkType" : "EMD:" + emdChk};
                
            }
            
        } else {
            whereJson = {"dwName" : tabVars.oneResultGridDwName, "whereJson" : JSON.stringify(flexGridCondition)};
        }
        
        query(oneResultGrid, $("#"+ oneResultGrid).jqxGrid('source')._source, whereJson);
        
    });

    $("#" + oneResultGrid).on('contextmenu',function() {
        return false;
    });
    
    /**
     * 右键点击事件,在整个GRID上进行右键点击
     */
    if ($("#" + oneResultGrid).html() != undefined) {
        $("#" + oneResultGrid).mousedown(function(event) {
            if (event.button == 2) {
                var scrollTop = $(window).scrollTop();
                var scrollLeft = $(window).scrollLeft();
                switchContextMenu(oneResultGrid,event,scrollLeft,scrollLeft);
                return false;
            }
        });
    }    

    // 判断是否 添加弹出框
    if (isShowWindow) {
        oneResultGridShowWindowFunc(oneResultGrid, tabVars, isShowWindowBtn);
    }

//    setTimeout("executeBtnPermissions();",1000);

};