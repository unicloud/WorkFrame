/**
 * 配合JQwidgets的工具类
 */

// 日期格式的正则表达式 [YYYYMMDD]或[YYYY-MM-DD]或[YYYY/MM/DD] 或 月/日 前不加0 
var dateReg = /^((19|20)\d\d)(-|\/|)((0|)[1-9]|1[012])\3((0|)[1-9]|[12]\d|3[01])$/;

/**
 * grid查询
 * @param jqxgrid 查询结果grid的id字符串
 * @param source 数据源
 * @param params 查询条件map，key为参数名，value为测试值
 */
var query = function(jqxgrid, source, params) {
    source.data = params; //给数据源添加查询条件
    var dataAdpater = $("#" + jqxgrid).jqxGrid("source");
    $("#" + jqxgrid).jqxGrid({source: dataAdpater});
};

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
var createGridToolBarDelButton = function(container, id, theme) {
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
 * 获取工具栏的通用按钮
 * @param theme 主题
 * @param container 工具栏容器
 * @param value 按钮显示值
 * @param icon 可选，按钮图标
 * @param float 可选，对齐方式
 * @return 按钮
 */
var createToolBarButton = function(container, value, icon, float,width,height, Id, theme) {
    if (theme == undefined) {
        theme = sysTheme;
    }
    var html = "<div style='margin-left: 5px;";   // display:none;
    if (float != undefined) {
        html = html + "float:" + float + ";";
    }
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

//权限控制思路。判断如果是发布版，则进入各个页面之前获取页面的权限，根据权限初始化页面，否则不加载页面
//根据权限加载页面时区分tab页权限，按钮权限，右键菜单权限
//考虑对于嵌套JSP的情况和iframe加载页面的情况，权限的控制处理