package com.greatfly.rams.basic.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.greatfly.common.CommonConstant;
import com.greatfly.common.action.BaseAction;
import com.greatfly.common.annotation.Description;
import com.greatfly.common.util.PageSupport;
import com.greatfly.common.util.msgconverter.CommonMsgOutput;
import com.greatfly.rams.basic.domain.MdDatawindowFactor;
import com.greatfly.rams.basic.ucc.DatawindowFactorService;
import com.greatfly.rams.basic.vo.DatawindowFactorVo;

/**
 * 数据窗配置action
 * @author huangqb
 * 2017-01-12 15:47:18
 */
@Scope("prototype")
@Controller
@Description("数据窗配置Action")
public class DatawindowFactorAction extends BaseAction {
    private static Logger log = Logger.getLogger(DatawindowFactorAction.class);
    @Resource
    private DatawindowFactorService datawindowFactorService;
    //分页数据集
    private PageSupport<DatawindowFactorVo> ps;
    //查询条件
    private DatawindowFactorVo datawindowFactorVo;

    // getter && setter
    
    public PageSupport<DatawindowFactorVo> getPs() {
        return ps;
    }

    public void setPs(PageSupport<DatawindowFactorVo> ps) {
        this.ps = ps;
    }
    
    public DatawindowFactorVo getDatawindowFactorVo() {
        return datawindowFactorVo;
    }

    public void setDatawindowFactorVo(DatawindowFactorVo datawindowFactorVo) {
        this.datawindowFactorVo = datawindowFactorVo;
    }

    @Description("获取数据窗基本信息")
    public void getDwInfo() {
        //记日志（系统日志）
        String jsonString = "";
        try {
            String dwName = request.getParameter("dwName");
            //获取到对应的数据窗实体
            MdDatawindowFactor curDwfactor = datawindowFactorService.getCurDatawindowFactor(dwName);
            if (curDwfactor == null) {
                jsonString = CommonMsgOutput.getResponseJson(false, 0, null, "未配置该窗口！请联系管理员！", "QUERY");
            } else  {
                JSONObject resultJsonObject = datawindowFactorService.resolveDwfactor(curDwfactor);
                jsonString = CommonMsgOutput.getResponseJson(true, 0, resultJsonObject, "获取成功", "QUERY");
            }
        } catch (Exception e) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, null, e.getMessage(), "QUERY");
        } finally {
            output(jsonString,log);
        }
    }

    @Description("查询分页数据")
    public void queryPagingDataSet() {
        //记日志（操作日志）
        String jsonString = "";
        try {
            String dwName = request.getParameter("dwName");
            String whereJson = request.getParameter("whereJson");
            //1、拼接select语句
            //2、拼接Where条件
        } catch (Exception e) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, null, e.getMessage(), "QUERY");
        } finally {
            output(jsonString,log);
        }
    }

    @Description("查询不分页数据")
    public void queryDataSet() {
        //记日志（系统日志）
        String jsonString = "";
        try {
            String dwName = request.getParameter("dwName");
            String whereJson = request.getParameter("whereJson");
        } catch (Exception e) {
            jsonString = CommonMsgOutput.getResponseJson(false, 0, null, e.getMessage(), "QUERY");
        } finally {
            output(jsonString,log);
        }
    }

    @Description("新增数据")
    public void insertDatas() {
        //记日志（数据更新日志、操作日志）
        //考虑明细的增删改
    }

    @Description("更新数据")
    public void updateDatas() {
        //记日志（数据更新日志、操作日志）
        //考虑明细的增删改
        //需要用数据窗来控制那些字段可更新,并且判断哪些数据有改动到，拼接对应的UPDATE语句
    }

    @Description("删除数据")
    public void deleteDatas() {
        //记日志（数据更新日志、操作日志）
        //考虑明细同时删除  只需要明细的数据窗和外键字段即可
    }
}