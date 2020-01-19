package com.taotao.test.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.content.jedis.JedisClient;

public class TestJedisClient {

	@Test
	public void testJedisClient(){
//		System.out.println("classpath:spring/applicationContext-redis.xml");
//		1. 初始化Spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
//		2. 获取实现类实例
		JedisClient bean = context.getBean(JedisClient.class);
//		3. 调用方法操作
		bean.set("jedisclient", "jedisclient");
		System.out.println(bean.get("jedisclient"));
		
	}
}
