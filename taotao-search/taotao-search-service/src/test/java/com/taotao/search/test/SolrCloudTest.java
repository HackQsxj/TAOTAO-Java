package com.taotao.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {

	@Test
	public void testAdd() throws SolrServerException, IOException{
//		1. 创建solrserver 集群实现类
//		指定zookeeper集群的节点列表字符串
		CloudSolrServer cloudSolrServer = new CloudSolrServer("192.168.10.133:2181,192.168.10.133:2182,192.168.10.133:2183");
//		2. 设置默认的搜索collection 默认的索引库(不是core所对应的,指的是整个collection索引集合)
		cloudSolrServer.setDefaultCollection("collection2");		
//		3. 创建solrinputdocument对象
		SolrInputDocument document = new SolrInputDocument();
//		4. 添加域到文档
		document.addField("id", "mytest");
		document.addField("item_title", "今天天气不错");
//		5. 将文档提交到索引库中
		cloudSolrServer.add(document);
//		6. 提交
		cloudSolrServer.commit();
	}
}
