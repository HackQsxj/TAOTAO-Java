package com.taotao.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.cart.service.CartService;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserLoginService;

/**
 * 订单处理的controller
 * 
 * @author aa
 *
 */
@Controller
public class OrderController {

	@Value("${TT_TOKEN_KEY}")
	private String TT_TOKEN_KEY;

	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private CartService cartService;

	/**
	 * url:/order/order-cart.html
	 * 参数:没有参数,但需要用户id;方法:从cookie中获取token,调用SSO服务获取用户id 返回值:逻辑视图(订单的确认页面)
	 */
	@RequestMapping("/order/order-cart.html")
	public String showOrder(HttpServletRequest request) {
		
		System.out.println(request.getAttribute("USER_INFO"));
		/*// 1. 从cookie中获取用户的token
		String token = CookieUtils.getCookieValue(request, TT_TOKEN_KEY);
		// 2. 调用SSO的服务获取用户的信息
		if (StringUtils.isNoneBlank(token)) {
			TaotaoResult result = userLoginService.getUserByToken(token);
			// 3. 要求用户登录才展示
			if (result.getStatus() == 200) {
				// 4. 展示用户的送货地址 根据用户的id查询用户的配送地址.静态数据
				// 5. 展示支付方式 从数据库中获取支付的方式
				// 6. 调用购物车服务从redis中获取购物车的商品列表
				TbUser tbUser = (TbUser) result.getData();
				List<TbItem> cartList = cartService.getCartList(tbUser.getId());
				// 7. 将列表展示到页面中(传递到页面,model)
				request.setAttribute("cartList", cartList);
			}
		}*/
		TbUser tbUser = (TbUser)request.getAttribute("USER_INFO");
		List<TbItem> cartList = cartService.getCartList(tbUser.getId());
		// 7. 将列表展示到页面中(传递到页面,model)
		request.setAttribute("cartList", cartList);
		return "order-cart";
	}
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 创建订单
	 * 参数:表单使用orderinfo来接受
	 * 返回值:逻辑视图
	 * @param info
	 * @return
	 */
	@RequestMapping(value="/order/create", method=RequestMethod.POST)
	public String createOrder(HttpServletRequest request, OrderInfo info){
//		1. 注入服务
//		2. 引入服务
//		3. 调用服务
//		查询用户信息设置到info中(通过httprequest)
		TbUser user  = (TbUser) request.getAttribute("USER_INFO");
		info.setUserId(user.getId());
		info.setBuyerNick(user.getUsername());
		TaotaoResult result = orderService.createOrder(info);
		request.setAttribute("orderId", result.getData());
		request.setAttribute("payment", info.getPayment());
//		使用时间操作组件
		DateTime dateTime = new DateTime();
		DateTime plusDays = dateTime.plusDays(3);//加3天
		request.setAttribute("date", plusDays.toString("yyyy-MM-dd"));
		return "success";
	}
}
