package com.greatfly.rams.basic.service;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.greatfly.common.CommonConstant;
import com.greatfly.common.dao.impl.JdbcBaseDao;
import com.greatfly.common.util.GlobalUtil;
import com.greatfly.common.util.cryptography.MD5Util;
import com.greatfly.common.util.exception.AppException;
import com.greatfly.common.util.msgconverter.CommonMsgOutput;
import com.greatfly.common.vo.UserVo;

/**
 * 登录ucc
 * @author wuwq
 * 2016-8-10 上午10:37:56
 */
@Service
public class LoginService {
    
	@Resource
	private JdbcBaseDao jdbcBaseDao;

    /**
     * 用户登录校验
     * @param userName 用户名，即工号
     * @param password 明文密码
     * @param need2Vaild 是否校验密码
     * @return 当前登录用户
     * @throws Exception 拷贝属性异常
     */
    @SuppressWarnings("rawtypes")
	public boolean login(String userName, String password,HttpSession session, boolean need2Vaild) {
    	try {
    		 String encodeStr = MD5Util.getMD5(password); //使用MD5加密，不可逆加密，防止密码被解密
             String sqlString = "SELECT USER_NAME,UNIT_NAME,DEPT_NAME,OFFICE_NAME,DUTY_NAME,PCODE,FULL_NAME,USER_PASSWORD FROM　ST_USER WHERE (PCODE = ? or USER_NAME = ?)";
             Object[] objArr = new Object[2];
             objArr[0] = userName.trim();
             objArr[1] = userName.trim();
             Map data = jdbcBaseDao.queryForMap(sqlString, objArr);
             String pwd = (String) data.get("USER_PASSWORD");
    		 if (need2Vaild) {
                 if (pwd.equals(encodeStr)) {
                	 setUserSession(session, data);
                	return true;
                 } else {
                	 clearSession(session);
                	return false;
                 }
             } else {
            	 GlobalUtil.initSysmUser();
            	 return true;
             }
    	} catch (Exception e){
    		throw new AppException(e.getMessage());
    	}
        
    }
    
    /**
     * 用户密码修改
     * @param originalPwd 用户名，即工号
     * @param newPwd 明文密码
     * @param need2Vaild 是否校验密码
     * @return 当前登录用户
     * @throws Exception 拷贝属性异常
     */
    @SuppressWarnings("rawtypes")
    public String resetPassword(String pcode, String originalPwd, String newPwd) {
        try {
             String encodeStr = MD5Util.getMD5(originalPwd); //使用MD5加密，不可逆加密，防止密码被解密
             String sqlString = "SELECT USER_PASSWORD FROM ST_USER WHERE PCODE = ?";
             String updString = "UPDATE ST_USER SET USER_PASSWORD = ? WHERE PCODE = ? ";
             Object[] objArr = new Object[1];
             objArr[0] = pcode;
             Map data = jdbcBaseDao.queryForMap(sqlString, objArr);
             String pwd = (String) data.get("USER_PASSWORD");
             if (pwd.equals(encodeStr)) {
                Object[] objArr2 = new Object[2];
                String newPwdEncodeStr = MD5Util.getMD5(newPwd.trim());
                objArr2[0] = newPwdEncodeStr;
                objArr2[1] = pcode;
                jdbcBaseDao.update(updString,objArr2);
                return CommonMsgOutput.getResponseJson(true, 0, "0", "密码修改成功！", "update");
             } else {
                return CommonMsgOutput.getResponseJson(false, 0, "0", "原密码错误！", "update");
             }
        } catch (Exception e){
            throw new AppException(e.getMessage());
        }
    }
    
    /**
     * 设置当前用户session
     * @param session
     */
    @SuppressWarnings("rawtypes")
	public void setUserSession(HttpSession session, Map data) {
    	UserVo curUserVo = new UserVo();
    	curUserVo.setUserName((String) data.get("USER_NAME"));
    	curUserVo.setUnitName((String) data.get("UNIT_NAME"));
    	curUserVo.setDeptName((String) data.get("DEPT_NAME"));
    	curUserVo.setDutyName((String) data.get("DUTY_NAME"));
    	curUserVo.setPcode((String) data.get("PCODE"));
    	curUserVo.setFullName((String) data.get("FULL_NAME"));
    	curUserVo.setPcType("P");
    	curUserVo.setIdCode("731");
    	session.setAttribute(CommonConstant.CUR_USER, curUserVo);
    }

    /**
     * 清除当前用户session
     * @param session
     */
    public void clearSession(HttpSession session) {
    	session.setAttribute(CommonConstant.CUR_USER, new UserVo());
    }
}
