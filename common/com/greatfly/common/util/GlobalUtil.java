package com.greatfly.common.util;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.greatfly.common.CommonConstant;
import com.greatfly.common.vo.UserVo;

/**
 * 全局util
 * @author wuwq
 * 2011-10-19 下午01:50:29
 */
public final class GlobalUtil {
	private static Logger log = Logger.getLogger(GlobalUtil.class);
	private static ThreadLocal<UserVo> th = new ThreadLocal<UserVo>();
	
	/**
	 * 防止实例化使用
	 */
	private GlobalUtil() {}
	
	/**
	 * 返回當前線程用戶
	 * @return UserVo 用户类
	 */
	public static UserVo getUser() {
		// 每个线程一个当前用户
		return th.get();
	}
	
	/**
	 * 在拦截器中，刚发起请求时设置此值
	 * @param user 当前登录用户
	 */
	public static void setUser(UserVo user) {
		th.set(user);
	}
	
	/**
	 * 获取系统作业的user，用于配合记录系统日志
	 */
	public static void initSysmUser() {
		UserVo user = new UserVo();
		user.setUserName("SYSM");
		user.setUnitName("**公司");
		user.setDeptName("**部门");
		user.setDutyName("**职位");
		user.setPcode("SYSM");
		user.setFullName("SYSM");
		user.setPcType("P");
		user.setIdCode("731");
		try {
			user.setClientIp(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			user.setClientIp("获取本机IP失败");
			log.info("获取本机IP失败", e);
		}
		
		setUser(user);
	}
}
