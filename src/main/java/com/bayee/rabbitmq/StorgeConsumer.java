package com.bayee.rabbitmq;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bayee.dao.StorgeEventImageListMapper;
import com.bayee.dao.StorgeEventMapper;
import com.bayee.model.StorgeEvent;
import com.bayee.model.StorgeEventImageList;

public class StorgeConsumer implements MessageListener {
	String[] configLocations = new String[]{"spring-mybatis.xml","spring-mvc.xml"};
	ApplicationContext ac = new ClassPathXmlApplicationContext(configLocations);
	StorgeEventMapper storgeEventMapper=(StorgeEventMapper)ac.getBean("storgeEventMapper");
	StorgeEventImageListMapper storgeEventImageListMapper=(StorgeEventImageListMapper)ac.getBean("storgeEventImageListMapper");
	
	@Override
	public void onMessage(Message message) {
		try {
			String bodyStr = new String(message.getBody(), "UTF-8");
			System.out.println("message2>>>"+bodyStr);
			JSONObject jsonObject=JSONObject.parseObject(bodyStr);
			JSONArray imageList=(JSONArray)jsonObject.get("imageList");
			StorgeEvent storgeEvent =JSON.parseObject(bodyStr,StorgeEvent.class);
			List<StorgeEventImageList> storgeEventImageList=JSON.parseArray(JSONObject.toJSONString(imageList), StorgeEventImageList.class);
			String image_url="";
			for(StorgeEventImageList image:storgeEventImageList) {
				image.setPicRecordId(storgeEvent.getRecordId());
				if(0==image.getImgType()) {
					image_url=image.getImgUrl();
				}
				storgeEventImageListMapper.insertSelective(image);
			}
			storgeEvent.setImgUrl(image_url);
			storgeEventMapper.insertSelective(storgeEvent);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
