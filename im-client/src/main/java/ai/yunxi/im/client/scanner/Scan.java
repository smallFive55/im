package ai.yunxi.im.client.scanner;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.yunxi.im.client.config.SpringBeanFactory;
import ai.yunxi.im.client.config.UserClientConfiguration;
import ai.yunxi.im.client.init.IMClientInit;
import ai.yunxi.im.common.pojo.ChatInfo;
import ai.yunxi.im.common.protocol.CommandConstant;
import ai.yunxi.im.common.utils.StringUtil;

public class Scan implements Runnable {

    private final static Logger LOGGER = LoggerFactory.getLogger(Scan.class);

    private UserClientConfiguration bean;
    private IMClientInit client;
    /**
	 * @param channel
	 */
	public Scan() {
		bean = SpringBeanFactory.getBean(UserClientConfiguration.class);
		client = SpringBeanFactory.getBean(IMClientInit.class);
	}

	@Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        
        try {
			while (true) {
			    String msg = sc.nextLine();
			    //检查消息
			    if (StringUtil.isEmpty(msg)) {
			        LOGGER.warn("不能发送空消息！");
			        continue;
			    }
			    //对用户发送的消息编码
//            MessageProtocol message = codec.decoder(msg, bean.getUserId());
			    
			    //系统内置命令
			    if (CommandConstant.isSystemCommond(msg)){
			    	//处理登陆指令
			    }
			    
			    ChatInfo chat = new ChatInfo();
			    chat.setCommand(CommandConstant.CHAT);
			    chat.setTime(System.currentTimeMillis());
			    chat.setUserId(bean.getUserId());
			    chat.setContent(msg);

			    //真正的发送消息，调用路由器，获得所有活动服务端，分发消息
			    client.sendMessage(chat);
			    
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
    }

}
