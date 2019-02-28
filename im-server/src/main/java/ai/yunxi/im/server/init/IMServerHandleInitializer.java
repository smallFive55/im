package ai.yunxi.im.server.init;

import ai.yunxi.im.server.handle.IMServerHandle;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月27日 下午1:40:52
 * 
 */
public class IMServerHandleInitializer extends ChannelInitializer<Channel> {

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ch.pipeline().addLast(new IMServerHandle());
	}

}
