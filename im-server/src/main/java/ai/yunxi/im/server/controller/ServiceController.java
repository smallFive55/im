package ai.yunxi.im.server.controller;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ai.yunxi.im.common.pojo.ChatInfo;
import ai.yunxi.im.common.protocol.CommandConstant;
import ai.yunxi.im.common.protocol.MessageProto;
import ai.yunxi.im.server.handle.ChannelMap;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午3:10:41
 * 服务端处理器
 */
@RestController
@RequestMapping("/")
public class ServiceController {

    private static Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);
    
    @Autowired
	private ChannelMap channelMap;

    private AttributeKey<Integer> userId = AttributeKey.valueOf("userId"); 
    
	/**
	 * 服务端向客户端推送消息
	 **/
	@RequestMapping(value="/pushMessage", method=RequestMethod.POST)
	public void pushMessage(@RequestBody ChatInfo chatinfo){
		//1.接收客户端封装好的消息对象
		MessageProto.MessageProtocol message = MessageProto.MessageProtocol.newBuilder()
				.setCommand(chatinfo.getCommand())
				.setTime(chatinfo.getTime())
				.setUserId(chatinfo.getUserId())
				.setContent(chatinfo.getContent()).build();

		//2.根据消息发送给指定客户端（群发）
		//   根据userID，从本地Map集合中得到对应的客户端Channel，发送消息
		if(CommandConstant.CHAT.equals(message.getCommand())){
			for (Entry<Integer, Channel> entry : channelMap.getCHANNEL_MAP().entrySet()) {
				//过滤客户端本身
				if(entry.getKey() != message.getUserId()){
					LOGGER.info("----服务端向"+entry.getValue().attr(userId).get()+"发送了消息，来自userId="+message.getUserId()+", content="+message.getContent());
					entry.getValue().writeAndFlush(message);
				}
			}
		}
	}
	
	/**
	 * 服务端处理客户端下线事件
	 **/
	@RequestMapping(value="/clientLogout", method=RequestMethod.POST)
	public void clientLogout(@RequestBody ChatInfo chatinfo){

		channelMap.getCHANNEL_MAP().remove(chatinfo.getUserId());
		LOGGER.info("----客户端下线["+chatinfo.getUserId()+"]");
	}
	
}
