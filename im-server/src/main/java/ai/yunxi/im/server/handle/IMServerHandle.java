package ai.yunxi.im.server.handle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import okhttp3.MediaType;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月27日 下午2:02:42
 * 
 */
public class IMServerHandle extends SimpleChannelInboundHandler {

    private MediaType mediaType = MediaType.parse("application/json");
    
    private static final Map<Long, Channel> CHANNEL_MAP = new ConcurrentHashMap<Long, Channel>(16);
    
    //设置一些Channel的属性
  	private AttributeKey<Long> userId = AttributeKey.valueOf("userId"); 
    
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(msg.toString());
		//客户端消息处理
		handleMessages(ctx, msg.toString());
	}

	private void handleMessages(ChannelHandlerContext ctx, String msg) {
		
		if("LOGIN".equals(msg)){
			//处理客户端登陆逻辑
//			ctx.channel().attr(userId).set(message.getUserId());
			//保存客户端userId与channel的关系映射
//			CHANNEL_MAP.put(message.getUserId(), ctx.channel());
		} else if("CHAT".equals(msg)){
			//处理客户端消息发送逻辑
			
		}
		
		
		
//		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
//		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
//		//将待发送的消息放到发送缓存数组中
//		ctx.writeAndFlush(resp);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("-------------channelRead0:"+msg);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("------有人退出了服务器"+ctx.channel());
		
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("userId","0");
//		RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
//		
//		Request request = new Request.Builder()
//		        .url(clientLogoutUrl)
//		        .post(requestBody)
//		        .build();
//
//		Response response = okHttpClient.newCall(request).execute() ;
//		if (!response.isSuccessful()){
//		    throw new IOException("Unexpected code " + response);
//		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("服务端退出连接");
//		ctx.channel().close();
	}

}
