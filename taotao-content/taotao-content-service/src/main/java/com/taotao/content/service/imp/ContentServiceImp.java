package com.taotao.content.service.imp;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;

@Service
public class ContentServiceImp implements ContentService {

//		1. 注入mapper
	@Autowired
	private TbContentMapper mapper;	  	
	@Override
	public TaotaoResult saveContent(TbContent content) {

//		2. 补全其他的属性
		content.setCreated(new Date());
		content.setUpdated(content.getUpdated());
//		3. 插入内容表中
		mapper.insertSelective(content);
		
//		当添加内容时,需要清空该分类所属的在redis中的内容,实现缓存同步
		try {
			client.hdel(CONTENT_KEY, content.getCategoryId()+"");
			System.out.println("插入内容时, 清空缓存");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}
	@Autowired
	private JedisClient client;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	
	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {
		
//		判断redis中是否有数据,如果有,直接从redis中获取数据并返回
		try {
			String jsonstr = client.hget(CONTENT_KEY, categoryId+"");//从redis库中获取内容分类下的所有内容
			
			if(StringUtils.isNotBlank(jsonstr)){
				System.out.println("redis中有数据");
				return JsonUtils.jsonToList(jsonstr, TbContent.class);
			}
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
//		无缓存则执行以下逻辑
//		2. 创建example
		TbContentExample example = new TbContentExample();
//		3. 设置查询条件
		example.createCriteria().andCategoryIdEqualTo(categoryId);//select * from tb_content where category_id = ?
//		4. 执行查询
		List<TbContent> list = mapper.selectByExample(example);
		
//		将数据写入到redis数据库中
//		1. 注入JedisClient
//		2. 调用方法写入redis
		try {
			System.out.println("redis中无该数据");
			client.hset(CONTENT_KEY, categoryId+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
//		返回
		return list;
	}

}
