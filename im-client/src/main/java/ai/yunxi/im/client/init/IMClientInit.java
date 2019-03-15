package ai.yunxi.im.client.init;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ai.yunxi.im.client.config.InitConfiguration;
import ai.yunxi.im.client.handle.IMClientHandle;
import ai.yunxi.im.common.constant.BasicConstant;
import ai.yunxi.im.common.constant.MessageConstant;
import ai.yunxi.im.common.pojo.ChatInfo;
import ai.yunxi.im.common.pojo.ServerInfo;
import ai.yunxi.im.common.protocol.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.concurrent.DefaultThreadFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午9:34:17
 * 客户端启动初始化：
 * 1、与服务端建立连接
 * 2、处理客户端输入
 */
@Component
public class IMClientInit {

	private final static Logger LOGGER = LoggerFactory.getLogger(IMClientInit.class);
	private ServerInfo server;
	public Channel channel;
	private EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("im-client-work"));
	
	@Autowired
	private InitConfiguration conf;
    @Autowired
    private OkHttpClient okHttpClient;
    
	@PostConstruct
	public void start() throws Exception{
		if(server != null){
			LOGGER.info("---客户端当前已登录");
			return;
		}
		//1.获取服务端ip+port
		getServerInfo();
		//2.启动客户端
		startClient();
		//3.登录到服务端
		registerToServer();
		
	}
	
	/**
	 * 与服务端通信
	 */
	private void registerToServer() {
		MessageProto.MessageProtocol login = MessageProto.MessageProtocol.newBuilder()
                .setUserId(conf.getUserId())
                .setContent(conf.getUserName())
                .setCommand(MessageConstant.LOGIN)
                .setTime(System.currentTimeMillis())
                .build();
        channel.writeAndFlush(login);
	}

	/**
	 * 向路由服务器获取服务端IP与端口
	 */
	private void getServerInfo() {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId",conf.getUserId());
			jsonObject.put("userName",conf.getUserName());
			RequestBody requestBody = RequestBody.create(BasicConstant.MEDIA_TYPE,jsonObject.toString());
			
			Request request = new Request.Builder()
			        .url(conf.getRouteLoginUrl())
			        .post(requestBody)
			        .build();

			Response response = okHttpClient.newCall(request).execute() ;
			if (!response.isSuccessful()){
			    throw new IOException("Unexpected code " + response);
			}
			ResponseBody body = response.body();
	        try {
	            String json = body.string();
	            server = JSON.parseObject(json, ServerInfo.class);

	        }finally {
	            body.close();
	        }
		} catch (IOException e) {
			LOGGER.error("连接失败！");
		}
	}

	/**
	 * 启动客户端，建立连接
	 */
	public void startClient(){
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
			        .channel(NioSocketChannel.class)
			        .handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							// google Protobuf 编解码
							pipeline.addLast(new ProtobufVarint32FrameDecoder());
						    pipeline.addLast(new ProtobufDecoder(MessageProto.MessageProtocol.getDefaultInstance()));
						    pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
						    pipeline.addLast(new ProtobufEncoder());
						    
							pipeline.addLast(new IMClientHandle());
						}
					});

			ChannelFuture future = bootstrap.connect(server.getIp(), server.getNettyPort()).sync();
			if (future.isSuccess()) {
			    LOGGER.info("---客户端启动成功[nettyport:"+8090+"]");
			}
			channel = future.channel();
		} catch (InterruptedException e) {
			LOGGER.error("---连接失败", e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			group.shutdownGracefully();
		}
		
	}
	
	/**
	 * 客户端发送消息
	 **/
	public void sendMessage(ChatInfo chat){
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command",chat.getCommand());
			jsonObject.put("time",chat.getTime());
			jsonObject.put("userId",chat.getUserId());
			jsonObject.put("content",chat.getContent());
			RequestBody requestBody = RequestBody.create(BasicConstant.MEDIA_TYPE,jsonObject.toString());
			
			Request request = new Request.Builder()
			        .url(conf.getRouteChatUrl())
			        .post(requestBody)
			        .build();
			Response response = okHttpClient.newCall(request).execute() ;
			if (!response.isSuccessful()){
			    throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 清理客户端登录
	 **/
	public void clear(){
		logoutRoute();
		logoutServer();
		server = null;
	}
	
	/**
	 * 客户端登出命令-路由端处理
	 **/
	private void logoutRoute(){
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId",conf.getUserId());
			RequestBody requestBody = RequestBody.create(BasicConstant.MEDIA_TYPE,jsonObject.toString());
			
			Request request = new Request.Builder()
			        .url(conf.getRouteLogoutUrl())
			        .post(requestBody)
			        .build();

			Response response = okHttpClient.newCall(request).execute() ;
			if (!response.isSuccessful()){
			    throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 客户端登出命令-路由端处理
	 **/
	private void logoutServer() {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId",conf.getUserId());
			RequestBody requestBody = RequestBody.create(BasicConstant.MEDIA_TYPE,jsonObject.toString());
			
			Request request = new Request.Builder()
			        .url("http://"+server.getIp()+":"+server.getHttpPort()+"/clientLogout")
			        .post(requestBody)
			        .build();

			Response response = okHttpClient.newCall(request).execute() ;
			if (!response.isSuccessful()){
			    throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void restart() throws Exception {
		//1.清理客户端信息（路由）
		logoutRoute();
		server = null;
		//2.start
		start();
	}
}
