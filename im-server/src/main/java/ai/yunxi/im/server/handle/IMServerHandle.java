package ai.yunxi.im.server.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.yunxi.im.common.constant.MessageConstant;
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
	
	private AttributeKey<Integer> userId = AttributeKey.valueOf("userId");
	
	private ChannelMap CHANNEL_MAP = ChannelMap.newInstance();
	
	private ClientProcessor clientProcessor;
	
	
	public IMServerHandle() {
		this.clientProcessor = SpringBeanFactory.getBean(ClientProcessor.class);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		MessageProto.MessageProtocol message = (MessageProto.MessageProtocol)msg;
		
		//处理客户端向服务端推送的消息
		if(MessageConstant.LOGIN.equals(message.getCommand())){
			//登录，保存Channel
			ctx.channel().attr(userId).set(message.getUserId()); 
			CHANNEL_MAP.putClient(message.getUserId(), ctx.channel());
			LOGGER.info("---客户端登录成功。userId:"+message.getUserId());
		}
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Integer uid = ctx.channel().attr(userId).get();
		//从Channel缓存删除客户端
		CHANNEL_MAP.getCHANNEL_MAP().remove(uid);
		clientProcessor.down(uid);
		
		LOGGER.info("----客户端强制下线。userId:"+uid);
	}
}
