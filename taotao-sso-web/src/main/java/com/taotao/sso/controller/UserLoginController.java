package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.sso.service.UserLoginService;

@Controller
public class UserLoginController {
	
	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;
	@Autowired
	private UserLoginService service;
	/**
	 * 用户登录
	 */
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(HttpServletRequest request, HttpServletResponse response, String username, String password){
//		1. 引入服务
//		2. 注入服务
//		3. 调用服务
		TaotaoResult result = service.login(username, password);
//		4. 设置token到cookie中 此时的cookie需要跨域
		if(result.getStatus() == 200){
			CookieUtils.setCookie(request, response, TT_TOKEN_KEY, result.getData().toString());
		}
		return result;
	}
	/**
	 * url:/user/token/{token}
	 * 参数:token
	 * 返回值:json
	 * 请求方法:get
	 */
	@RequestMapping(value="/user/token/{token}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token, String callback){
//		判断是否为Jsonp请求, callback
		TaotaoResult result = service.getUserByToken(token);
		if(StringUtils.isNotBlank(callback)){
//		如果是jsonp 需要拼接,类似于fun({id:1})
			String jsonpstr = callback+"("+JsonUtils.objectToJson(result)+");";
			return jsonpstr;
		}
//		如果不是jsonp
		return JsonUtils.objectToJson(result);
	}
	/**
	 * 安全退出
	 * url:/user/logout/{token}
	 * 参数:token
	 * 请求方法:get
	 */
	@RequestMapping("/user/logout/{token}")
	@ResponseBody
	public TaotaoResult logout(@PathVariable String token){		
		
		return service.logout(token);
	}
	
}
