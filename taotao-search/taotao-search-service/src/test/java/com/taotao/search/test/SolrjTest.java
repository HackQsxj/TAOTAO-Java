package com.taotao.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjTest {

	/**
	 * 测试添加数据到索引库
	 * 
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@Test
	public void add() throws SolrServerException, IOException {
		// 1. 创建solrserver对象,建立连接,指定url地址

		SolrServer solrServer = new HttpSolrServer("http://192.168.10.132:8080/solr/");
		// 2. 创建solrinputdocument
		SolrInputDocument document = new SolrInputDocument();
		// 3. 向文档中添加域
		document.addField("id", "test001");
		document.addField("item_title", "这是一个测试");
		// 4. 将文档提交到索引库中
		solrServer.add(document);
		// 5. 提交
		solrServer.commit();
	}

	/**
	 * 测试从索引库中查询数据
	 * 
	 * @throws Exception
	 */
	@Test
	public void testquery() throws Exception {
		// 1. 创建solrserver对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.10.132:8080/solr");
		// 2. 创建solrquery对象,设置过滤条件,主查询条件 排序
		SolrQuery solrquery = new SolrQuery();
		// 3. 设置查询条件
		solrquery.setQuery("阿尔卡特");
		solrquery.addFilterQuery("item_price:[0 TO 30000000]");
		solrquery.set("df", "item_title");
		// 4. 执行查询
		QueryResponse response = solrServer.query(solrquery);
		// 5. 获取结果集
		SolrDocumentList results = response.getResults();
		System.out.println("查询的总记录数" + results.getNumFound());
		// 6. 遍历结果集 打印
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
		}
	}

}
