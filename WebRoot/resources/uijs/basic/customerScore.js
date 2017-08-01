
/**
 * tab1_oneResultGrid的工具栏上的按钮
 * 
 * @param containerName
 *            toolBar中Div 的名称,自定义名称
 */
var initTab1Btns = function() {
    var container = getToolBarContainer("toolbartab1_oneResultGrid");
    var impBtn = createImportButton(container, "tab1_impBtn");
    
    impBtn.click(function (event) {
        $('#windowFileUpload').jqxWindow({title: "导入数据"});
        $('#window_jqxFileUpload').jqxFileUpload({uploadUrl: "basic/file-deal-record!fileImportInPrc.do?fileType=BASE_AMOUNT"});
        $('#windowFileUpload').jqxWindow('open');
    });
};