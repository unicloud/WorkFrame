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
 * @Title initCurdBtns 初始化增删改查btn
 * @param gridId grid
 * @param gridVars grid参数
 */
var initCurdBtns = function(containerId, gridId, gridVars, tabName) {
    //需要权限判断是否需要初始化
    initNormQueryBtn(containerId, gridId, gridVars, tabName);
    initAddBtn(containerId, gridId, gridVars, tabName);
    initDelBtn(containerId, gridId, gridVars, tabName);
    initSaveBtn(containerId, gridId, gridVars, tabName);
};

/**
 * @Title initNormQueryBtn 初始化普通查询btn
 * @param containerId 容器ID
 * @param gridId grid
 */
var initNormQueryBtn = function(containerId, gridId, gridVars, tabName) {
    var container = getToolBarContainer(containerId);
    var queryBtn = createQueryButton(container, tabName + "_queryBtn");
    queryBtn.click(function (event) {
        //首先需要判断是什么查询，然后拼接查询条件
        var queryCond1 = generateNormQueryCond(tabName + "_normQueryGrid");
        var queryCond2 = generateFlexQueryCond(tabName + "_flexQueryGrid");
        var whereJson = {"dwName" : gridVars.resultDwName,"whereJson" : ""};
        query(gridId,$("#"+ gridId).jqxGrid('source')._source, whereJson);
    });
};

/**
 * @Title initFlexQueryBtn 初始化灵活查询btn
 * @param containerId 容器ID
 * @param gridId grid
 */
var initFlexQueryBtn = function(containerId, gridId, gridVars, tabName) {
    var container = getToolBarContainer(containerId);
    var queryBtn = createQueryButton(container, tabName + "_queryBtn");
    queryBtn.click(function (event) {
        //首先需要判断是什么查询，然后拼接查询条件
        var queryCond1 = generateNormQueryCond(tabName + "_normQueryGrid");
        var queryCond2 = generateFlexQueryCond(tabName + "_flexQueryGrid");
        var whereJson = {"dwName" : gridVars.resultDwName,"whereJson" : ""};
        query(gridId,$("#"+ gridId).jqxGrid('source')._source, whereJson);
    });
};

/**
 * @Title initMixQueryBtn 初始化混合查询btn
 *      注： 混合查询指包含灵活查询和普通查询,需要判断当前查询窗口是哪个
 * @param containerId 容器ID
 * @param gridId grid
 */
var initMixQueryBtn = function(containerId, gridId, gridVars, tabName) {
    var container = getToolBarContainer(containerId);
    var queryBtn = createQueryButton(container, tabName + "_queryBtn");
    queryBtn.click(function (event) {
        //首先需要判断是什么查询，然后拼接查询条件
        var queryCond1 = generateNormQueryCond(tabName + "_normQueryGrid");
        var queryCond2 = generateFlexQueryCond(tabName + "_flexQueryGrid");
        var whereJson = {"dwName" : gridVars.resultDwName,"whereJson" : ""};
        query(gridId,$("#"+ gridId).jqxGrid('source')._source, whereJson);
    });
};

/**
 * @Title initAddBtn 初始化新增btn
 * @param containerId 容器ID
 * @param gridId grid
 */
var initAddBtn = function(containerId, gridId, gridVars, tabName) {
    var container = getToolBarContainer(containerId);
    var addBtn = createAddButton(container, tabName + "_addBtn");
    addBtn.click(function (event) {

    });
};

/**
 * @Title initAddBtn 初始化删除btn
 * @param containerId 容器ID
 * @param gridId grid
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