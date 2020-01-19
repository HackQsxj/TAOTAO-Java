package com.taotao.order.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.jedis.JedisClient;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

@Service
public class OrderServiceImp implements OrderService {

	@Autowired
	private JedisClient client;
	
	@Value("${GEN_ORDER_ID_KEY}")
	private String GEN_ORDER_ID_KEY;
	@Value("${GEN_ORDER_ID_INIT}")
	private String GEN_ORDER_ID_INIT;
	
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	
	@Value("${GEN_ORDER_ITEM_ID_KEY}")
	private String GEN_ORDER_ITEM_ID_KEY;
	
	@Override
	public TaotaoResult createOrder(OrderInfo info) {
		
//		1. 插入订单表
//		通过redis的insr生成订单id
//		判断如key不存在,就初始化一个key设置一个初始值
		if(!client.exists(GEN_ORDER_ID_KEY)){
			client.set(GEN_ORDER_ID_KEY, GEN_ORDER_ID_INIT);
		}
		String orderId = client.incr(GEN_ORDER_ID_KEY).toString();
//		补全其他的属性
//		info.setBuyerNick(buyerNick); 在controller设置
		info.setCreateTime(new Date());
		info.setOrderId(orderId);
		info.setPostFee("0");
		info.setStatus(1);
//		info.setUserId(userId); 在controller设置
		info.setUpdateTime(info.getCreateTime());
//		注入mapper
		orderMapper.insert(info);
//		2. 插入订单项表
//		设置订单项的id 通过redis生成订单项的id
		List<TbOrderItem> orderItems = info.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			String orderItemId = client.incr(GEN_ORDER_ITEM_ID_KEY).toString();
			tbOrderItem.setId(orderItemId);
			tbOrderItem.setOrderId(orderId);
//			插入订单项表
			orderItemMapper.insert(tbOrderItem);
		}
//		3. 插入订单物流表
//		设置订单id
		TbOrderShipping orderShipping = info.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(info.getCreateTime());
//		插入表中
		orderShippingMapper.insert(orderShipping);
		
		return TaotaoResult.ok(orderId);
	}

}
