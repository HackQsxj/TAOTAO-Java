package com.taotao.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;


@Controller
public class HtmlGen {

	@Autowired
	private FreeMarkerConfigurer config;
	
	@RequestMapping("/genHtml")
	@ResponseBody

	public String gen() throws Exception{
		
//		1. 根据config,获取对象
		Configuration configuration = config.getConfiguration();
//		2. 创建模板文件
		Template template = configuration.getTemplate("template.ftl");
//		3. 创建数据集
		Map model = new HashMap();
		model.put("springtestkey", "hello");
//		4. 创建流
		Writer writer = new FileWriter(new File("F:/Java/taotao-item-web/src/main/webapp/WEB-INF/ftl/template.htm"));
//		5. 调用方法输出
		template.process(model, writer);
//		6. 关闭流
		writer.close();
		return "ok";
	}
}
