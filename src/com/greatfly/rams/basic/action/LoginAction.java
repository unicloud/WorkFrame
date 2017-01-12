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
import com.greatfly.common.util.JsonUtil;
import com.greatfly.common.util.msgconverter.CommonMsgOutput;
import com.greatfly.common.vo.RespVo;
import com.greatfly.rams.basic.service.LoginService;

/**
 * 登陆Action
 * @author wuwq
 * 2010-9-7   上午10:08:09
 */
@Scope("prototype")
@Controller
@Description("登录Action")
public class LoginAction extends BaseAction {
    private final Logger log = Logger.getLogger(LoginAction.class);
    @Resource
    private LoginService loginService;
    
    /** 异常信息 */
    public String msg;
    /** 是否打开登录验证,true表示验证,false表示不验证 */
    private boolean need2Vaild = true;
    // getter && setter

    public boolean isNeed2Vaild() {
        return need2Vaild;
    }

    public void setNeed2Vaild(boolean need2Vaild) {
        this.need2Vaild = need2Vaild;
    }

    /**
     * 登录
     * @return 成功返回主页面，失败返回登录页面
     */
    @Description("用户登录")
    public void login() {
    	String jsonString = "";
        try {
            String username = request.getParameter("user");
            String password = request.getParameter("pwd");
            boolean isLogin = loginService.login(username, password,getSession(), need2Vaild);
            if (isLogin) {
            	jsonString = CommonMsgOutput.getResponseJson(true, 0, "0", "登录成功！", "login");
            } else {
            	jsonString = CommonMsgOutput.getResponseJson(false, 0, "0", "登录失败！密码不正确！", "login");  	
            }
        } catch (Exception e) {
        	if (e.getMessage().startsWith("Incorrect result size: expected 1, actual 0")) {
        		jsonString = CommonMsgOutput.getResponseJson(false, 0, "0", "登录失败！用户名不存在！", "login");
        	} else {
        		jsonString = CommonMsgOutput.getResponseJson(false, 0, "0", "登录失败！" + e.getMessage(), "login");
        	}
        } finally {
        	output(jsonString, log);
        }
    }
    
    /**
     * 注销登陆
     */
    @Description("用户注销")
    public void loginOut() {
        loginService.clearSession(getSession());
        RespVo resp = new RespVo(true, "注销成功");
        output(JsonUtil.toJson(resp), log);
    }

    /**
     * 获取登录用户信息
     */
    @Description("获取登录用户信息")
    public void getCurUserInfo() {
    	HttpSession session = request.getSession();
    	//从session中获取用户信息
    	JSONObject userObject = new JSONObject();
    	userObject.put("userName", session.getAttribute(CommonConstant.USER_NAME));
    	userObject.put("unitName", session.getAttribute(CommonConstant.UNIT_NAME));
    	userObject.put("deptName", session.getAttribute(CommonConstant.DEPT_NAME));
    	userObject.put("dutyName", session.getAttribute(CommonConstant.DUTY_NAME));
    	userObject.put("pcode", session.getAttribute(CommonConstant.PCODE));
    	userObject.put("fullName", session.getAttribute(CommonConstant.FULL_NAME));
    	output(userObject.toJSONString(), log);
    }
}
