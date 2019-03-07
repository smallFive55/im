package ai.yunxi.im.client.scanner;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.yunxi.im.common.protocol.CommondConstant;
import ai.yunxi.im.common.protocol.MessageCodec;
import ai.yunxi.im.common.protocol.MessageProto.MessageProtocol;
import ai.yunxi.im.common.utils.StringUtil;
import io.netty.channel.socket.SocketChannel;

public class Scan implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Scan.class);

    private SocketChannel channel;
    private Integer userId;
    private MessageCodec codec = new MessageCodec();
    
    /**
	 * @param channel
	 */
	public Scan(SocketChannel channel, Integer userId) {
		super();
		this.channel = channel;
		this.userId = userId;
	}

	@Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String msg = sc.nextLine();
            //检查消息
            if (StringUtil.isEmpty(msg)) {
                LOGGER.warn("不能发送空消息！");
                continue;
            }
            //对用户发送的消息编码
            MessageProtocol message = codec.decoder(msg, userId);
            
            //系统内置命令
            if (CommondConstant.LOGOUT.equals(message.getCommand())){
            	//处理登陆指令
            }
            
            //真正的发送消息
    		channel.writeAndFlush(message);
        }
    }

}
