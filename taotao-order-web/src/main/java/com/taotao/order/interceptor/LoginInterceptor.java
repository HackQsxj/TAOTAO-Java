package com.taotao.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.sso.service.UserLoginService;

/**
 * 用户身份认证的拦截器
 * 
 * @author aa
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;

	@Autowired
	private UserLoginService userLoginService;
	
	@Value("${SSO_URL}")
	private String SSO_URL;

	/**
	 * 在进入目标方法之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 用户的身份认证
		// 1. 取cookie中的token
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		// 2. 判断token是否存在
		if (StringUtils.isEmpty(token)) {
			// 3. 如果未登录,重定向到登录页面
			// request.getRequest().toString() 访问的url
			response.sendRedirect(SSO_URL+"/page/login?redirect=" + request.getRequestURL().toString());
			return false;
		}
		// 4. 如果token存在,调用SSO的服务 查询用户的信息(看用户是否已经过期)
		TaotaoResult result = userLoginService.getUserByToken(token);
		if (result.getStatus() != 200) {
			response.sendRedirect(SSO_URL+"/page/login?redirect=" + request.getRequestURL().toString());
			return false;
		}
		// 5. 设置用户信息到request中,目标方法的request就可以获取用户的信息
		request.setAttribute("USER_INFO", result.getData());
		return true;

	}

	/**
	 * 在进入目标方法之后,在返回modelandview之前执行 共用变量的设置
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在返回modelandview之后执行,渲染到页面之前 异常处理,清理工作
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
