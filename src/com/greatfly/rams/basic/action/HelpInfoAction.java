package com.greatfly.rams.basic.action;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.greatfly.common.action.BaseAction;
import com.greatfly.common.annotation.Description;
import com.greatfly.common.util.msgconverter.CommonMsgOutput;
import com.greatfly.rams.basic.service.StHelpInfoService;

/**
 * 帮助信息action
 * @author huangqb
 * 2017-07-21 09:05:57
 */
@Scope("prototype")
@Controller
@Description("帮助信息Action")
public class HelpInfoAction extends BaseAction {
    private static Logger logger = Logger.getLogger(HelpInfoAction.class);
    @Resource
    private StHelpInfoService stHelpInfoService;

    /**
     * 获取页面对应的帮助信息
     * @throws SQLException 
     */
    @Description("获取页面对应的帮助信息")
    public void getPageHelpInfo() throws SQLException {
        String pageUrl = request.getParameter("pageUrl");
        String jsonString = null;
        try {
            jsonString = stHelpInfoService.getPageHelpInfo(pageUrl);
        } catch (Exception e) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, "", 
                    e.getMessage().replace("\"", "'"), "exception");
        }
        output(jsonString, logger);
    }

    /**
     * 保存页面帮助信息
     * @throws SQLException 
     */
    @Description("保存用户定制报表")
    public void saveRptGroupCustom() throws SQLException {
        String pageUrl = request.getParameter("pageUrl");
        String pageName = request.getParameter("pageName");
        String helpInfo = request.getParameter("helpInfo");
        String jsonString = null;
        try {
            jsonString = stHelpInfoService.savePageHelpInfo(pageUrl, pageName, helpInfo);
        } catch (Exception e) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, "", 
                    e.getMessage().replace("\"", "'"), "exception");
        }
        output(jsonString, logger);
    }
}