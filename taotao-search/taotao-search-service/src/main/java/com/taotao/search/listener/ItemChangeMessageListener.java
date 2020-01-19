package com.taotao.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;

/**
 * 接受消息的监听器
 * 
 * @author aa
 *
 */
public class ItemChangeMessageListener implements MessageListener {

	@Autowired
	private SearchService service;

	@Override
	public void onMessage(Message message) {
		// 判断消息的类型是否为TextMessage
		if (message instanceof TextMessage) {
			// 如果是 获取上面的id
			TextMessage message2 = (TextMessage) message;
			String itemidstr;
			try {
				itemidstr = message2.getText();
				Long itemId = Long.parseLong(itemidstr);
				// 通过商品的id查询数据, 需要开发mpper(通过id查询商品的数据)
				// 更新索引库
				TaotaoResult taotaoResult = service.updateSearchItemById(itemId);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

}
