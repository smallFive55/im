package ai.yunxi.im.client.init;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ai.yunxi.im.common.constant.MessageStatus;
import ai.yunxi.im.common.pojo.ChatInfo;
import ai.yunxi.im.common.pojo.ServiceInfo;
import ai.yunxi.im.common.protocol.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午9:34:17
 * 
 */
@Component
public class IMClientInit {
	private final static Logger LOGGER = LoggerFactory.getLogger(IMClientInit.class);
	
	private EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("im-client-work"));
	
	private SocketChannel channel;
	
    @Value("${im.user.id}")
    private Integer userId;

    @Value("${im.user.userName}")
    private String userName;
    
    @Value("${im.server.route.request.url}")
    private String serverRouteLoginUrl;
    
    @Value("${im.clear.route.request.url}")
    private String clearRouteUrl;
    
    @Value("${im.chat.route.request.url}")
    private String chatRouteUrl;
    
    
    private MediaType mediaType = MediaType.parse("application/json");
    
    @Autowired
    private OkHttpClient okHttpClient;
    
    @PostConstruct
    public void start() throws Exception {
		LOGGER.info("1.获取服务端IP+端口;2.启动客户端;3.向服务端注册;");
		//1.获取服务端IP+端口;
		ServiceInfo serviceInfo = getServiceInfo();
		//2.启动客户端;
		startClient(serviceInfo);
		//3.向服务端登陆;
		regiestToService();
	}

	/**
	 * 与服务端通信
	 */
	private void regiestToService() {
		MessageProto.MessageProtocol login = MessageProto.MessageProtocol.newBuilder()
                .setUserId(userId)
                .setContent(userName)
                .setCommand(MessageStatus.LOGIN)
                .setTime(System.currentTimeMillis())
                .build();
        channel.writeAndFlush(login);
	}
	
	/**
	 * 启动客户端，建立连接
	 */
	private void startClient(ServiceInfo serviceInfo) {
		Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new IMClientHandleInitializer());

        ChannelFuture future = null;
        try {
            future = bootstrap.connect(serviceInfo.getIp(), serviceInfo.getNettyPort()).sync();
        } catch (InterruptedException e) {
            LOGGER.error("连接失败", e);
        }
        if (future.isSuccess()) {
            LOGGER.info("启动 cim client 成功[nettyport:"+serviceInfo.getNettyPort()+"]");
        }
        channel = (SocketChannel) future.channel();
	}
	
	/**
	 * 向路由服务器获取服务端IP与端口
	 */
	private ServiceInfo getServiceInfo() {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",userId);
			jsonObject.put("userName",userName);
			RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
			
			Request request = new Request.Builder()
			        .url(serverRouteLoginUrl)
			        .post(requestBody)
			        .build();

			Response response = okHttpClient.newCall(request).execute() ;
			if (!response.isSuccessful()){
			    throw new IOException("Unexpected code " + response);
			}
			ServiceInfo serviceinfo;
			ResponseBody body = response.body();
	        try {
	            String json = body.string();
	            serviceinfo = JSON.parseObject(json, ServiceInfo.class);

	        }finally {
	            body.close();
	        }
	        return serviceinfo;
		} catch (IOException e) {
			LOGGER.error("连接失败！");
			
			//---未建立路由
			e.printStackTrace();
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setIp("192.168.110.1");
			serviceInfo.setHttpPort(8083);
			serviceInfo.setNettyPort(8088);
			return serviceInfo;
		}
	}

	/**
	 * 客户端发送消息
	 **/
	public void sendMessage(ChatInfo chat) {
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("command",chat.getCommand());
			jsonObject.put("time",chat.getTime());
			jsonObject.put("userId",chat.getUserId());
			jsonObject.put("content",chat.getContent());
			RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
			
			Request request = new Request.Builder()
			        .url(chatRouteUrl)
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
	 * 客户端处理登出命令
	 **/
	public void logout(){

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",userId);
			RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
			
			Request request = new Request.Builder()
			        .url(clearRouteUrl)
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
}
