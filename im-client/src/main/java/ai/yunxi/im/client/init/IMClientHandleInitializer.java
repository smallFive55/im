package ai.yunxi.im.client.init;

import ai.yunxi.im.client.handle.IMClientHandle;
import ai.yunxi.im.common.protocol.MessageProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class IMClientHandleInitializer extends ChannelInitializer<Channel> {

    private final IMClientHandle cimClientHandle = new IMClientHandle();

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
//                //10 秒没发送消息 将IdleStateHandler 添加到 ChannelPipeline 中
//                .addLast(new IdleStateHandler(0, 10, 0))
//                //心跳解码
//                //.addLast(new HeartbeatEncode())
//
                // google Protobuf 编解码
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(MessageProto.MessageProtocol.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(cimClientHandle);
    }
    
}
