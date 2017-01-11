package com.greatfly.common.interceptor;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 日志拦截器
 * @author wuwq
 * 2015-8-5 上午11:37:41
 */
@Scope("prototype")
@Component
public class RequestLogInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -5300256637779203771L;
	private static Logger log = Logger.getLogger(RequestLogInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		try {
			//执行Action之前的动作。。。
			//1、
			//登录的action不拦截，其他action需要拦截
			//拦截之后判断是否登录，没登录得情况下抛出异常  throw new AppException("未登录或登录超时！");
			//登录过后对特定的按钮操作（需要考虑如何从请求中识别），需判断用户权限

			//2、数据验证(尽可能的写成通用的)

			//3、数据更新日志（针对通用的新增、删除、更新方法,可以用独立的拦截器处理）
				//可以记录ACTION执行前后数据的变化

			long begin = System.nanoTime();
			result = invocation.invoke(); //执行调用
			
			//执行Action之后的动作。。。
			long costTime = System.nanoTime() - begin; //计算请求处理时间，纳秒
		} catch (Exception e) {

		}
		
		return result;
	}
}
