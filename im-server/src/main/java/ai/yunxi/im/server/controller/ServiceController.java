package ai.yunxi.im.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午3:10:41
 * 服务端处理器
 */
@RestController
@RequestMapping("/")
public class ServiceController {

	/**
	 * 服务端向客户端推送消息
	 **/
	@RequestMapping(value="/pushMessage")
	public String pushMessage(){
		//1.接收客户端封装好的消息对象
		//2.根据消息发送给指定客户端（单发/群发）
		//   根据userID，从本地Map集合中得到对应的客户端NioSocketChannel，发送消息
		//      如果客户端不存在，记录日志
		
		return "";
	}
}
