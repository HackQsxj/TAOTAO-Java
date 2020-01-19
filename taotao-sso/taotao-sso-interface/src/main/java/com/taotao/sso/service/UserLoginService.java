package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * 用户登录的接口
 * @author aa
 *
 */
public interface UserLoginService {

	/**
	 * 根据用户名和密码登录
	 * @param username
	 * @param password
	 * @return
	 */
	public TaotaoResult login(String username, String password);
	
	/**
	 * 根据token获取用户的信息
	 * @param token
	 * @return 包含用户的信息
	 */
	public TaotaoResult getUserByToken(String token);
	
	/**
	 * 用户退出登录
	 * @param token
	 * @return
	 */
	public TaotaoResult logout(String token);
}
