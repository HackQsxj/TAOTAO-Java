package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserLoginService;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserLoginService service;
	/**
	 * 在Handler执行之前处理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("被拦截器拦截到了");
//		1. 判断用户是否登录
//		从cookie中取token
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
//		根据token得到用户信息
		TaotaoResult result = service.getUserByToken(token);
		if(result.getStatus() != 200){
//		如果得不到用户信息,拦截跳转到登录页面
//		把用户请求的url作为参数传递给登录页面
			response.sendRedirect("http://localhost:8088/page/login");
			return false;
		}
		
//		返回值决定Handler是否执行
		return true;
	}

	/**
	 * 执行Handler之后,返回Model之前
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 返回Model之后
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
