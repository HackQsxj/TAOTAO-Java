package com.taotao.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.pojo.PicUploadResult;

@Controller
public class PicUploadController {

	@Value("${TAOTAO_IMAGE_SERVER_URL}")
	private String TAOTAO_IMAGE_SERVER_URL;

	private static String[] TYPE = { ".jpg", ".jpeg", ".png", ".bmp", ".gif" };
	
	// filePostName : "uploadFile", //上传文件名
	// uploadJson : '/rest/pic/upload', //图片上传请求路径
	// dir : "image" //上传文件类型

	/**
	 * 图片上传
	 * @throws Exception 
	 */
	@RequestMapping(value="pic/upload", method=RequestMethod.POST)
	@ResponseBody
	public PicUploadResult upload(MultipartFile uploadFile) throws Exception{
		
//		声明标志位
		boolean flag = false;
//		初始化返回数据,初始值为0,默认上传失败
		PicUploadResult picUploadResult = new PicUploadResult();
		picUploadResult.setError(1);
		
//		校验后缀
		for (String type : TYPE) {
			String oname = uploadFile.getOriginalFilename();
//			如果后缀是要求的格式结尾,标志位设置为true,跳出循环
			if(StringUtils.endsWithIgnoreCase(oname, type)){
				flag = true;
				break;
			}
		}
//		如果校验失败,直接返回
		if(!flag){
			return picUploadResult;
		}
//		重置标志位
		flag = false;
		
//		图片内容校验
		try{
			BufferedImage image = ImageIO.read(uploadFile.getInputStream());
			if(image != null){
				picUploadResult.setHeight(String.valueOf(image.getHeight()));
				picUploadResult.setWidth(String.valueOf(image.getWidth()));
				flag = true;
			}
		}catch(Exception e){
			
		}
//		如果校验成功,则上传图片
		if(flag){
//			1. 加载tracker配置文件
			ClientGlobal.init(System.getProperty("user.dir") + "/src/main/resources/resource/fastdfs.conf");
//			2. 获取上传文件的后缀名
			String ext = StringUtils.substringAfterLast(uploadFile.getOriginalFilename(), ".");
			TrackerClient trackerClient = new TrackerClient();
			TrackerServer trackerServer = trackerClient.getConnection();
			StorageServer storageServer = null;
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			String[] str = storageClient.upload_file(uploadFile.getBytes(), ext, null);
//			3. 进行返回结果的拼接
			String picUrl = this.TAOTAO_IMAGE_SERVER_URL+str[0]+"/"+str[1];
//			4. 设置图片url
			picUploadResult.setUrl(picUrl);
//			5. 设置上传成功
			picUploadResult.setError(0);
			
		}
		return picUploadResult;
	}
}
