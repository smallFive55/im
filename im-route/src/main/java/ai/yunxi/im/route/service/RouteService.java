package ai.yunxi.im.route.service;

import java.io.IOException;

import ai.yunxi.im.common.pojo.ChatInfo;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年3月7日 下午8:38:44
 * 
 */
public interface RouteService {

	public void sendMessage(String url, ChatInfo chat) throws IOException;
	
}
