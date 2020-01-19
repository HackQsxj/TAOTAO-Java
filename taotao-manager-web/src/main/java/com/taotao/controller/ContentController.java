package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;

/**
 * 处理内容表相关的
 * 
 * @author aa
 *
 */
@Controller
public class ContentController {

	// 2. 注入服务
	@Autowired
	private ContentService service;

	/**
	 * 返回值是JSON $.post("/content/save",$("#contentAddForm").serialize(),
	 * function(data){
	 * 
	 * @return
	 */
	@RequestMapping(value = "/content/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult saveContent(TbContent tb) {
		// 1. 引入服务

		return service.saveContent(tb);
	}
	
	/**
	 * 显示选中分类下的内容列表
	 */
	@RequestMapping(value="/content/query/list", method=RequestMethod.GET)
	@ResponseBody
	public List<TbContent> getContentList(Long categoryId){
		
		return service.getContentListByCatId(categoryId);
	}

}
