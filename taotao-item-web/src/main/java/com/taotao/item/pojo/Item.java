package com.taotao.item.pojo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.taotao.pojo.TbItem;

public class Item extends TbItem {
	
	public Item(TbItem tbItem){
//		this.setBarcode(tbItem.getBarcode());
		BeanUtils.copyProperties(tbItem, this); // 将原来的数据属性值拷贝到item中
	}

	public String[] getImages(){
		if(StringUtils.isNotBlank(super.getImage())){
			return super.getImage().split(",");
		}
		return null;
	}
}
