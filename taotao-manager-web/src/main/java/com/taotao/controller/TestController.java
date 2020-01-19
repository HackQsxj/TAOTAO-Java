package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.TestService;

@Controller
public class TestController {
	
//		2. 注入服务
	@Autowired
	private TestService testService;

	@RequestMapping("/test/queryNow")
	@ResponseBody
	public String queryNow(){
//		1. 引入服务,在配置文件中配置
//		3. 服务的方法
		return testService.queryNow();
	}
}
