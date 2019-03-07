package ai.yunxi.im.server.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.yunxi.im.common.protocol.CommandConstant;
import ai.yunxi.im.common.protocol.MessageProto;
import ai.yunxi.im.server.config.SpringBeanFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月27日 下午2:02:42
 * 
 */
public class IMServerHandle extends ChannelInboundHandlerAdapter {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(IMServerHandle.class);
	
	private ChannelMap channelMap;
    
    private AttributeKey<Integer> userId = AttributeKey.valueOf("userId"); 
    
	/**
	 * @param channelMap
	 */
	public IMServerHandle() {
		super();
		this.channelMap = SpringBeanFactory.getBean(ChannelMap.class);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//客户端消息处理
		handleMessages(ctx, msg);
	}

	private void handleMessages(ChannelHandlerContext ctx, Object msg) {
		MessageProto.MessageProtocol message = (MessageProto.MessageProtocol) msg;
		
		//处理消息
		if(CommandConstant.LOGIN.equals(message.getCommand())){
			//客户端登录，保存客户端channel
			ctx.channel().attr(userId).set(message.getUserId());
			channelMap.putClient(message.getUserId(), ctx.channel());
			LOGGER.info("----客户端登录成功。userId:"+message.getUserId());
			
		}
	}
}
