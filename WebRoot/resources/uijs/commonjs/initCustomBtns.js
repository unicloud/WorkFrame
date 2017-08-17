//为Grid初始化增删改查按钮
//只有查询
//查询，增加，删除，保存
//初始化导出按钮
//注意在查询方法中能适应多种条件拼接方式（默认查询条件、全字段查询、通用查询）
//注意，删除前能预留校验功能，删除后能刷新页面。
//保存方法前需要对数据进行校验，保存成功预留处理其他操作的方法
//设置按钮的快捷键
//保存按钮可以多条同时保存

/**
 * @Title initNormCurdBtns 初始化普通查询增删改btn
 * @param containerId 容器ID
 * @param gridId grid
 * @param gridVars grid参数
 * @param tabName tabName
 * @param initVal 新增初始值
 */
var initNormCurdBtns = function(containerId, gridId, gridVars, tabName, initVal) {
    //需要权限判断是否需要初始化
    initNormQueryBtn(containerId, gridId, gridVars, tabName);
    initAddBtn(containerId, gridId, gridVars, tabName, initVal);
    initDelBtn(containerId, gridId, gridVars, tabName);
};

/**
 * @Title initFlexCurdBtns 初始化灵活查询增删改btn
 * @param containerId 容器ID
 * @param gridId grid
 * @param gridVars grid参数
 * @param tabName tabName
 * @param initVal 新增初始值
 */
var initFlexCurdBtns = function(containerId, gridId, gridVars, tabName, initVal) {
    //需要权限判断是否需要初始化
    initFlexQueryBtn(containerId, gridId, gridVars, tabName);
    initAddBtn(containerId, gridId, gridVars, tabName, initVal);
    initDelBtn(containerId, gridId, gridVars, tabName);
};

/**
 * @Title initMixCurdBtns 初始化混合查询增删改btn
 * @param containerId 容器ID
 * @param gridId grid
 * @param gridVars grid参数
 * @param tabName tabName
 * @param initVal 新增初始值
 */
var initMixCurdBtns = function(containerId, gridId, gridVars, tabName, initVal) {
    //需要权限判断是否需要初始化
    initMixQueryBtn(containerId, gridId, gridVars, tabName);
    initAddBtn(containerId, gridId, gridVars, tabName, initVal);
    initDelBtn(containerId, gridId, gridVars, tabName);
};

/**
 * @Title initNormQueryBtn 初始化普通查询btn
 * @param containerId 容器ID
 * @param gridId grid
 * @param gridVars grid参数
 * @param tabName tabName
 */
var initNormQueryBtn = function(containerId, gridId, gridVars, tabName) {
    var container = getToolBarContainer(containerId);
    var queryBtn = createQueryButton(container, tabName + "_queryBtn");
    queryBtn.click(function (event) {
        //首先需要判断是什么查询，然后拼接查询条件
        var queryCond = generateNormQueryCond(tabName + "_normQueryGrid");
        if (queryCond.length == 0) {
            layer.alert("请输入查询条件！", {icon :8});
            return;
        }
        var whereCond = new Object();
        whereCond.customCond = queryCond;
        whereCond.defaultCond = defaultQueryCond;
        var whereJson = {"dwName" : gridVars.resultDwName,"whereJson" : JSON.stringify(whereCond)};
        $("#jqxLoader").jqxLoader('open');
        query(gridId,$("#"+ gridId).jqxGrid('source')._source, whereJson);
    });
};

/**
 * @Title initFlexQueryBtn 初始化灵活查询btn
 * @param containerId 容器ID
 * @param gridId grid
 * @param gridVars grid参数
 * @param tabName tabName
 */
var initFlexQueryBtn = function(containerId, gridId, gridVars, tabName) {
    var container = getToolBarContainer(containerId);
    var queryBtn = createQueryButton(container, tabName + "_queryBtn");
    queryBtn.click(function (event) {
        //首先需要判断是什么查询，然后拼接查询条件
        var queryCond = generateFlexQueryCond(tabName + "_flexQueryGrid", gridVars);
        if (queryCond.length == 0) {
            layer.alert("请输入查询条件！", {icon :8});
            return;
        }
        var whereCond = new Object();
        whereCond.customCond = queryCond;
        whereCond.defaultCond = defaultQueryCond;
        var whereJson = {"dwName" : gridVars.resultDwName,"whereJson" : JSON.stringify(whereCond)};
        $("#jqxLoader").jqxLoader('open');
        query(gridId,$("#"+ gridId).jqxGrid('source')._source, whereJson);
    });
};

/**
 * @Title initMixQueryBtn 初始化混合查询btn
 *      注： 混合查询指包含灵活查询和普通查询,需要判断当前查询窗口是哪个
 * @param containerId 容器ID
 * @param gridId grid
 * @param gridVars grid参数
 * @param tabName tabName
 */
var initMixQueryBtn = function(containerId, gridId, gridVars, tabName) {
    var container = getToolBarContainer(containerId);
    var queryBtn = createQueryButton(container, tabName + "_queryBtn");
    queryBtn.click(function (event) {
        //首先需要判断是什么查询，然后拼接查询条件
        var queryCond = "";
        var selectedItem = $("#" + tabName + "_widget").jqxTabs('selectedItem');
        if (selectedItem == 0) {
            queryCond = generateFlexQueryCond(tabName + "_flexQueryGrid", gridVars);
        } else {
            queryCond = generateNormQueryCond(tabName + "_normQueryGrid");
        }
        if (queryCond.length == 0) {
            layer.alert("请输入查询条件！", {icon :8});
            return;
        }
        var whereCond = new Object();
        whereCond.customCond = queryCond;
        whereCond.defaultCond = defaultQueryCond;
        var whereJson = {"dwName" : gridVars.resultDwName,"whereJson" : JSON.stringify(whereCond)};
        $("#jqxLoader").jqxLoader('open');
        query(gridId,$("#"+ gridId).jqxGrid('source')._source, whereJson);
    });
};

/**
 * @Title initAddBtn 初始化新增btn
 * @param containerId 容器ID
 * @param gridId grid
 * @param gridVars grid参数
 * @param tabName tabName
 * @param initVal 新增初始值
 */
var initAddBtn = function(containerId, gridId, gridVars, tabName, initVal) {
    var container = getToolBarContainer(containerId);
    var addBtn = createAddButton(container, tabName + "_addBtn");
    addBtn.click(function (event) {
        var windowId = gridId + "EditWindow";
        //1、遍历所有的控件,清空控件值
        var items = $("#" + windowId + "Content .jqx-widget");
        for (var i = 0 ; i < items.length; i++) {
            $("#" + items[i].id).val("");
        };
        $("#" + windowId + "SWRowIndex").val("");
        //2、设置主键值、创建者、修改者、创建日期、修改日期,需要赋初始值的设置初始值。。
        for (var i = 0 ; i < items.length; i++) {
            var ctrlId = items[i].id;
            var colId = ctrlId.substring(gridId.length + 17);
            if (colId == "pkid") {
                $("#" + ctrlId).val(0);
            } else if (colId == "createTime" || colId == "modifyTime") {
                var date = new Date();
                $("#" + ctrlId).val(date);
            } if (colId == "createUser" || colId == "modifyUser") {
                $("#" + ctrlId).val(curUser.pcode);
            }
            if (initVal != undefined) {
                for (var j = 0; j < initVal.length; j ++) {
                    if (initVal[j]["KEY"] == colId) {
                        $("#" + ctrlId).val(initVal[j]["VALUE"]);
                    }
                }
            }
        };
        //3、打开新增窗口
        $("#" + windowId).jqxWindow("open");
    });
};

/**
 * @Title initAddBtn 初始化删除btn
 * @param containerId 容器ID
 * @param gridId grid
 * @param gridVars grid参数
 * @param tabName tabName
 */
var initDelBtn = function(containerId, gridId, gridVars, tabName) {
    var container = getToolBarContainer(containerId);
    var delBtn = createDelButton(container, tabName + "_delBtn");
    delBtn.click(function (event) {
        
    });
};

/**
 * @Title initEditBtn 初始化编辑btn
 * @param containerId 容器ID
 * @param gridId grid
 */
var initEditBtn = function(containerId, gridId) {

};

/**
 * @Title initSaveBtn 初始化保存btn
 * @param containerId 容器ID
 * @param gridId grid
 */
var initSaveBtn = function(containerId, gridId, gridVars, tabName) {
    var container = getToolBarContainer(containerId);
    var saveBtn = createSaveButton(container, tabName + "_saveBtn");
    saveBtn.click(function (event) {
        
    });
};

/**
 * @Title initImpBtn 初始化导入btn
 * @param containerId 容器ID
 * @param gridId grid
 */
var initImpBtn = function(containerId, gridId) {

};

/**
 * @Title initExpBtn 初始化导出btn
 * @param containerId 容器ID
 * @param gridId grid
 */
var initExpBtn = function(containerId, gridId) {

};

/**
 * @Title getQueryCondition 拼接查询条件
 * @param containerId 容器ID
 * @param gridId grid
 */
var getQueryCondition = function() {

};