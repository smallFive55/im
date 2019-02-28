package ai.yunxi.im.client.scanner;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.SocketChannel;

public class Scan implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Scan.class);

    private SocketChannel channel;
    
//    /**
//     * 系统参数
//     */
//    private AppConfiguration configuration;
//
//    private MsgHandle msgHandle ;
//
//    private MsgLogger msgLogger ;
//
//    public Scan() {
//        this.configuration = SpringBeanFactory.getBean(AppConfiguration.class);
//        this.msgHandle = SpringBeanFactory.getBean(MsgHandle.class) ;
//        this.msgLogger = SpringBeanFactory.getBean(MsgLogger.class) ;
//    }

    /**
	 * @param channel
	 */
	public Scan(SocketChannel channel) {
		super();
		this.channel = channel;
	}

	@Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String msg = sc.nextLine();
//            //检查消息
//            if (msgHandle.checkMsg(msg)) {
//                continue;
//            }
//
//            //系统内置命令
//            if (msgHandle.innerCommand(msg)){
//                continue;
//            }
//
            //真正的发送消息
    		byte[] req = msg.getBytes();
    		ByteBuf message = Unpooled.buffer(req.length);
    		message.writeBytes(req);
    		channel.writeAndFlush(message);
//
//            //写入聊天记录
//            msgLogger.log(msg) ;
//
//            LOGGER.info("{}:【{}】", configuration.getUserName(), msg);
        }
    }

}
