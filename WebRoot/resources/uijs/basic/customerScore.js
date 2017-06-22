
/**
 * tab1_oneResultGrid的工具栏上的按钮
 * 
 * @param containerName
 *            toolBar中Div 的名称,自定义名称
 */
var initTab1Btns = function() {
    var container = getToolBarContainer("toolbartab1_oneResultGrid");
    var addBtn = createToolBarCustomButton(container, "新增", "add", "tab1_add");
    var editBtn = createToolBarCustomButton(container, "编辑", "application_form_edit", "tab1_edit");
    var impBtn = createImportButton(container, "tab1_impBtn");
    // add new row.
    addBtn.click(function (event) {
      var curEditData = {"pkid":1};
        // 清除文本框中的值
        $("#tab1_oneResultGrid").jqxGrid("addrow",null,curEditData);
        $("#tab1_oneResultGrid").jqxGrid("refreshdata");
    });

    editBtn.click(function (event) {
        $("#tab1_oneResultGridEditWindow").jqxWindow("open");
    });

    impBtn.click(function (event) {
        $('#windowFileUpload').jqxWindow({title: "导入数据"});
        $('#window_jqxFileUpload').jqxFileUpload({uploadUrl: "basic/file-deal-record!fileImportInPrc.do?fileType=BASE_AMOUNT"});
        $('#windowFileUpload').jqxWindow('open');
    });
};