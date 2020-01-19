package com.taotao.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器的类
 * @author aa
 *
 */
public class GlobalExceptionReslover implements HandlerExceptionResolver {

	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
//		1. 将日志写入到日志文件中
		System.out.println(ex.getMessage());
		ex.printStackTrace();
//		2. 及时的通知开发人员 发短信或邮件(第三方接口)
		System.out.println("发短信");
//		3. 给予用户一个友好的提示:您的网络有异常,请重试
		ModelAndView modelAndView = new ModelAndView();
//		设置视图的信息
		modelAndView.setViewName("error/exception");//不需要带后缀,有视图解析器
//		设置模型数据
		modelAndView.addObject("message", "您的网络有异常");
		return modelAndView;
	}

}
