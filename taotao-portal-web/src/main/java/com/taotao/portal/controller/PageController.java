package com.taotao.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.util.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.Ad1Node;

/**
 * 展示首页
 * @author aa
 *
 */
@Controller
public class PageController {

	@Autowired
	private ContentService contentService;
	
	@Value("${AD1_CATEGORY_ID}")
	private Long categoryId;
	@Value("${AD1_HEIGT}")
	private String height;
	@Value("${AD1_HEIGT_B}")
	private String height_b;
	
	@Value("${AD1_WIDTH}")
	private String width;
	@Value("${AD1_WIDTH_B}")
	private String width_b;
	
	@RequestMapping("/index")
	public String showIndex(Model model){
//		引入服务
//		注入服务
//		添加业务逻辑,根据内容分类的id 查询内容列表
		List<TbContent> contentList = contentService.getContentListByCatId(categoryId);
//		转成自定义的POJO的列表
		List<Ad1Node> nodes = new ArrayList<>();
		for(TbContent tbContent : contentList){
			Ad1Node node = new Ad1Node();
			node.setAlt(tbContent.getSubTitle());
			node.setHeight(height);
			node.setHeightB(height_b);
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setWidth(width);
			node.setWidthB(width_b);
			nodes.add(node);
		}
//		传递数据给JSP
		model.addAttribute("ad1", JsonUtils.objectToJson(nodes));
		return "index";
	}
}
