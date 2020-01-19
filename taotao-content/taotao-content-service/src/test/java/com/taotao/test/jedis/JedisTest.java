package com.taotao.test.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	// 测试单机版
	public void testJedis(){
//		1. 创建一个Jedis对象,需要指定连接的地址和端口
		Jedis jedis = new Jedis("192.168.10.132", 6379);
//		2. 直接操作redis
		jedis.set("key123", "abc");
		System.out.println(jedis.get("key123"));
//		3. 关闭Jedis
		jedis.close();
	}
	@Test
	public void testJedis02(){
//		1. 创建Jedispool 对象需要指定端口和地址
		JedisPool jedisPool = new JedisPool("192.168.10.132", 6379);
//		2.获取Jedis对象
		Jedis jedis = jedisPool.getResource();
//		3. 直接操作
		jedis.set("keypool", "value");
		System.out.println(jedis.get("keypool"));
//		4. 关闭redis(释放资源到连接池)
		jedis.close();
//		5. 关闭连接池(应用系统关闭时,才关闭)
		jedisPool.close();
	}
//	测试集群版
	@Test
	public void testJedisCluster(){
//		1. 创建JedisCluster对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.10.132", 7001));
		nodes.add(new HostAndPort("192.168.10.132", 7002));
		nodes.add(new HostAndPort("192.168.10.132", 7003));
		nodes.add(new HostAndPort("192.168.10.132", 7004));
		nodes.add(new HostAndPort("192.168.10.132", 7005));
		nodes.add(new HostAndPort("192.168.10.132", 7006));
		JedisCluster cluster = new JedisCluster(nodes);
//		2. 直接根据JedisCluster对象操作redis集群
		cluster.set("keycluster", "value123");
		System.out.println(cluster.get("keycluster"));
//		3. 关闭对象(在应用系统关闭的时候,关闭集群;因为其中封装了连接池)
		cluster.close();
	}
}
