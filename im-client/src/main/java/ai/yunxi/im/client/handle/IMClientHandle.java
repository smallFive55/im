package ai.yunxi.im.client.handle;

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

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		MessageProto.MessageProtocol message = (MessageProto.MessageProtocol) msg;
		System.out.println("客户端接收到消息："+message.getContent());
	}
}
