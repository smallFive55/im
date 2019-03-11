package ai.yunxi.im.client.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.yunxi.im.client.config.SpringBeanFactory;
import ai.yunxi.im.client.init.IMClientInit;
import ai.yunxi.im.common.protocol.MessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午10:01:02
 * 
 */
public class IMClientHandle extends ChannelInboundHandlerAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(IMClientHandle.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		MessageProto.MessageProtocol message = (MessageProto.MessageProtocol) msg;
		LOGGER.info("客户端接收到消息："+message.getContent());
	}

	/**
	 * 当客户端发现服务端断线后，发起重连
	 **/
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		IMClientInit client = SpringBeanFactory.getBean(IMClientInit.class);
		LOGGER.info("所连接的服务端断开了连接，发起重连请求...");
		try {
			client.restart();
			LOGGER.info("客户端重连成功！");
		} catch (Exception e) {
			LOGGER.info("客户端重连失败！");
			e.printStackTrace();
		}
	}
	
}
