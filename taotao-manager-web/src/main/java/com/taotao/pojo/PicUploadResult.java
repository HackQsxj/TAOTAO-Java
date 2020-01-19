package com.taotao.pojo;

import java.io.Serializable;

/**
 * 图片上传的结果
 * @author aa
 *
 */
public class PicUploadResult implements Serializable{

	private Integer error;
	
	private String width;
	private String height;
	private String url;
	@Override
	public String toString() {
		return "PicUploadResult [error=" + error + ", width=" + width + ", height=" + height + ", url=" + url + "]";
	}
	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
