package com.taotao.sso.service.imp;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserRegisterService;

@Service
public class UserRegisterServiceImp implements UserRegisterService {

	@Autowired
	private TbUserMapper usermapper;
	@Override
	public TaotaoResult checkDate(String param, Integer type) {
//		1. 注入mapper
//		2. 根据参数和类型动态生成查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(type == 1){
			if(StringUtils.isEmpty(param)){
				return TaotaoResult.ok(false);
			}
			criteria.andUsernameEqualTo(param);
		}else if(type == 2){
			criteria.andPhoneEqualTo(param);
		}else if(type == 3){
			criteria.andEmailEqualTo(param);
		}else{
//			非法参数
			return TaotaoResult.build(400, "非法的参数类型");
		}
//		3. 调用mapper方法获取数据
		List<TbUser> list = usermapper.selectByExample(example);
//		4. 如果查询到数据, 则该数据不可用 false
		if(list != null && list.size()>0){
			return TaotaoResult.ok(false);
		}
//		5. 如果没有查询到数据, 则数据可用
		return TaotaoResult.ok(true);
	}
	@Override
	public TaotaoResult register(TbUser user) {
//		1. 注入mapper
//		2. 校验数据
		if(StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())){
			return TaotaoResult.build(400, "注册失败,用户名和密码不能为空");
		}
		TaotaoResult result = checkDate(user.getUsername(), 1);
		if(!(boolean)result.getData()){
//			数据不可用
			return TaotaoResult.build(400, "注册失败, 用户名已经被注册");
		}
		if(StringUtils.isNotBlank(user.getPhone())){
			TaotaoResult result2 = checkDate(user.getPhone(), 2);
			if(!(boolean)result2.getData()){
//				数据不可用
				return TaotaoResult.build(400, "注册失败, 手机号已经被注册");
			}
		}
		if(StringUtils.isNotBlank(user.getEmail())){
			TaotaoResult result3 = checkDate(user.getEmail(), 3);
			if(!(boolean)result3.getData()){
//				数据不可用
				return TaotaoResult.build(400, "注册失败, 邮箱已经被注册");
			}
		}
//		3. 如果检验成功,补全属性,加密密码
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
//		4. 插入数据
		usermapper.insertSelective(user);
		return TaotaoResult.ok();
	}

}
