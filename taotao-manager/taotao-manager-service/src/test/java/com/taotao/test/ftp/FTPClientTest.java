package com.taotao.test.ftp;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class FTPClientTest {

	@Test
	public void testFtp() throws Exception{
//		1. 连接ftp服务器
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("192.168.10.133", 21);
//		2. 登录ftp服务器
		ftpClient.login("ftpuser", "ftpuser");
//		3. 读取本地文件
		FileInputStream is = new FileInputStream(new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Desert.jpg"));
//		4. 上传文件
//		指定上传目录
		ftpClient.changeWorkingDirectory("/home/ftpuser/images");
//		指定文件类型
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//		第一个参数:文件在远程服务器的名称 第二个参数:文件流
		ftpClient.storeFile("Desert.jsp", is);
//		5. 退出登录
		ftpClient.logout();
	}
}
