package ai.yunxi.im.server.handle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ai.yunxi.im.common.protocol.MessageProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import okhttp3.MediaType;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月27日 下午2:02:42
 * 
 */
public class IMServerHandle extends ChannelInboundHandlerAdapter {

    private MediaType mediaType = MediaType.parse("application/json");
    
    private static final Map<Long, Channel> CHANNEL_MAP = new ConcurrentHashMap<Long, Channel>(16);
    
    //设置一些Channel的属性
  	private AttributeKey<Integer> userId = AttributeKey.valueOf("userId"); 
    
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//客户端消息处理
		handleMessages(ctx, msg);
	}

	private void handleMessages(ChannelHandlerContext ctx, Object msg) {
		System.out.println("服务端接收到消息："+msg+".---------客户端channel属性：userId:"+ctx.channel().attr(userId).get());
		MessageProto.MessageProtocol message = (MessageProto.MessageProtocol) msg;
//		ctx.writeAndFlush(message);
	}
}
