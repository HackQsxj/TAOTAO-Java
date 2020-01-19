package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;

@Controller
public class ImportAllItems {

	@Autowired
	private SearchService searchservice;
	
	@RequestMapping("/index/importAll")
	@ResponseBody
	public TaotaoResult importAllItems() throws Exception{
//		1. 引入服务
//		2. 注入服务
		return searchservice.importAllSearchItem();
	}
}
