package com.test.fastdfs;

import java.io.IOException;

import org.apache.log4j.net.SyslogAppender;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FastDFSTest {

	@Test
	public void test() throws Exception{
//		1. 加载FastDFS的tracker的配置信息
		ClientGlobal.init(System.getProperty("user.dir")+"/src/test/resources/tracker.conf");
//		2. 创建TrackerClient
		TrackerClient trackerClient = new TrackerClient();
//		3. 使用TrackerClient，获取TrackerServer
		TrackerServer trackerServer = trackerClient.getConnection();
//		4. 声明StorageServer
		StorageServer storageServer = null;
//		5. 创建StorageClient
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
//		6. 使用StorageClient上传图片
		String[] result = storageClient.upload_file("E:/项目/Hydrangeas.jpg", "jpg", null);
//		7. 打印结果
		for (String s : result) {
			System.out.println(s);
		}
		
	}
	public static void main(String[] args) {
		System.out.println("123//\"");
	}
}
