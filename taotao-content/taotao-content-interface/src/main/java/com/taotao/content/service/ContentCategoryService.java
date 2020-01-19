package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	/**
	 * 根据结点id查询该节点的子节点列表
	 * @param parentId
	 * @return
	 */
	public List<EasyUITreeNode> getContentCategoryList(Long parentId);

	/**
	 * 添加内容分类
	 *
	 */
	public TaotaoResult createContentCategory(Long parentId, String name);
	

}
