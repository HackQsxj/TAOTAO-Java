package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * 用户注册的接口
 * @author aa
 *
 */
public interface UserRegisterService {

	/**
	 * 根据参数和类型来校验数据
	 * @param param 需要校验的数据
	 * @param type 1 2 3 代表username phone email
	 * @return
	 */
	public TaotaoResult checkDate(String param, Integer type);
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	public TaotaoResult register(TbUser user);
}
