package ai.yunxi.im.client.init;

import ai.yunxi.im.client.handle.IMClientHandle;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 23/02/2018 22:47
 * @since JDK 1.8
 */
public class IMClientHandleInitializer extends ChannelInitializer<Channel> {

    private final IMClientHandle cimClientHandle = new IMClientHandle();

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
//                //10 秒没发送消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(0, 10, 0))
//
//                //心跳解码
//                //.addLast(new HeartbeatEncode())
//
//                // google Protobuf 编解码
//                //拆包解码
//                .addLast(new ProtobufVarint32FrameDecoder())
//                .addLast(new ProtobufDecoder(CIMResponseProto.CIMResProtocol.getDefaultInstance()))
//                //
//                //拆包编码
//                .addLast(new ProtobufVarint32LengthFieldPrepender())
//                .addLast(new ProtobufEncoder())
                .addLast(cimClientHandle);
    }
    
}
