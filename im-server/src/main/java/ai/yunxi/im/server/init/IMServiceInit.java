package ai.yunxi.im.server.init;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ai.yunxi.im.common.protocol.MessageProto;
import ai.yunxi.im.server.handle.IMServerHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月27日 下午1:35:52
 * 
 */
@Component
public class IMServiceInit {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(IMServiceInit.class);

	private EventLoopGroup acceptorGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    
    @Value("${im.server.port}")
    private int nettyPort;
    
    @PostConstruct
	public void start() throws InterruptedException{
		//Netty用于启动NIO服务器的辅助启动类
		ServerBootstrap sb = new ServerBootstrap();
		//将两个NIO线程组传入辅助启动类中
		sb.group(acceptorGroup, workerGroup)
			//设置创建的Channel为NioServerSocketChannel类型
			.channel(NioServerSocketChannel.class)
			//保持长连接
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			//设置绑定IO事件的处理类
			.childHandler(new ChannelInitializer<SocketChannel>() {
				//创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
				@Override
				protected void initChannel(SocketChannel arg0) throws Exception {
					ChannelPipeline pipeline = arg0.pipeline();
					// google Protobuf 编解码
					pipeline.addLast(new ProtobufVarint32FrameDecoder());
					pipeline.addLast(new ProtobufDecoder(MessageProto.MessageProtocol.getDefaultInstance()));
					pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
					pipeline.addLast(new ProtobufEncoder());
					pipeline.addLast(new IMServerHandle());
				}
			});
		ChannelFuture future = sb.bind(nettyPort).sync();
        if (future.isSuccess()) {
            LOGGER.info("启动 cim server 成功");
        }
	}
}
