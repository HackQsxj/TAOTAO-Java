package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 显示页面
 * @author Lenovo
 *
 */
@Controller
public class PageController{

	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}
//	显示商品的查询页面
//	url:item-list
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
}
