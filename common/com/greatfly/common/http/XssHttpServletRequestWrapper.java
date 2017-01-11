package com.greatfly.common.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.greatfly.common.util.StringUtil;


/**
 * Xss http请求包装类，对http请求做Xss过滤
 * @author wuwq
 * 2014-2-14 下午03:55:31
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	/** 原始请求 */
	private final HttpServletRequest originRequest;
	
	/**
	 * 构造函数
	 * @param request http请求
	 */
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		this.originRequest = request;
	}

	/** 
	 * 重写该方法，将参数名和参数值都做xss过滤
	 * @param name 参数名
	 * @return 参数值
	 */
	@Override
	public String getParameter(String name) {
		String value = super.getParameter(xssEncode(name));
		if (StringUtil.isNotBlank(value)) {
			value = xssEncode(value);
		}
		return value;
	}
	
	/** 
	 * 重写该方法，将参数名和参数值都做xss过滤
	 * @param name header名
	 * @return header值
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(xssEncode(name));
		if (StringUtil.isNotBlank(value)) {
			value = xssEncode(value);
		}
		return value;
	}
	
	// private methods

	/**
	 * 将输入参数做xss过滤
	 * 不过滤双引号，因为前后台交互使用json进行传输</br>
	 * 不过滤单引号，因为帆软报表传参数有用到
	 * @param input 输入参数
	 * @return xss过滤后的参数
	 */
	private String xssEncode(String input) {
		if (StringUtil.isBlank(input)) {
			return input;
		}
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			switch (c) {
				case '>':
					output.append('＞'); //全角大于号
					break;
				case '<':
					output.append('＜'); //全角小于号
					break;
				case '&':
					output.append('＆'); //全角
					break;
				case '\\':
					output.append('＼'); //全角斜线
					break;
				case '#':
					output.append('＃'); //全角井号
					break;
				case '(':
					output.append('（'); //圆括号
					break;
				case ')':
					output.append('）'); //圆括号
					break;	
				case '+':
					output.append('＋'); //全角加号
					break;	
				case '$':
					output.append('＄'); //全角
					break;
				default:
					output.append(c);
					break;
			}
		}
		return output.toString();
	}
	
	// getter && setter

	public HttpServletRequest getOriginRequest() {
		return originRequest;
	}
	
	
}
