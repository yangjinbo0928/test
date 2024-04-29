package com.winner.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 
 * 
 * @Package: com.*.*.config
 * @Description:拦截器配置
 * @author: dxw
 * @date: 2021年11月5日 11:14:35
 */
@Configuration
public class InterceptConfig implements WebMvcConfigurer {

	@Autowired
	AdminInterceptor adminInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册 InterceptorRegistration 拦截器
		InterceptorRegistration registration = registry.addInterceptor(adminInterceptor);
		// 所有路径都被拦截
		registration.addPathPatterns("/**");
		// 添加不拦截路径
		registration.excludePathPatterns(
				"/pay/*"
				);
	}
}