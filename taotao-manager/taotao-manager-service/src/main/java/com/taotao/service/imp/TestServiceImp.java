package com.taotao.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TestMapper;
import com.taotao.service.TestService;

@Service
public class TestServiceImp implements TestService {

//		1. 注入mapper
	@Autowired
	private TestMapper mapper;
	@Override
	public String queryNow() {
//		2. 调用Mapper的方法,返回
		return mapper.queryNow();
	}

}
