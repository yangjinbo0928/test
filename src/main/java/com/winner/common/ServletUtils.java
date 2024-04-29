package com.winner.common;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ServletUtils {
	/**
	 * 获取request
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}

	// *获取response

	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}
	// 获取session

	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	// request和response在ServletRequestAttributes类当中
	//
	// @return

	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}
	
	// 将字符串渲染到客户端(前后端分离很少会用到)
	// @param response 渲染对象
	// @param string 待渲染的字符串
	// @return null
	public static String renderString(HttpServletResponse response, String string) {
		try {
			response.setStatus(200);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 获取完整的请求路径，包括：域名，端口，上下文访问路径
	// 上传图片的时候需要：服务器路径+上下文访问路径（所以封装了该方法）
	// @return 服务地址
	public static String getUrl() {
		HttpServletRequest request = ServletUtils.getRequest();
		return getDomain(request);
	}

	public static String getDomain(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		String contextPath = request.getServletContext().getContextPath();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
	}
}