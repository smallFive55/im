package ai.yunxi.im.client.scanner;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.yunxi.im.client.config.InitConfiguration;
import ai.yunxi.im.client.config.SpringBeanFactory;
import ai.yunxi.im.client.init.IMClientInit;
import ai.yunxi.im.common.constant.MessageConstant;
import ai.yunxi.im.common.pojo.ChatInfo;
import ai.yunxi.im.common.utils.StringUtil;

public class Scan implements Runnable {

	private final static Logger LOGGER = LoggerFactory.getLogger(Scan.class);
	
	private IMClientInit client;
	private InitConfiguration conf;
	
	public Scan() {
		super();
		this.client = SpringBeanFactory.getBean(IMClientInit.class);
		this.conf = SpringBeanFactory.getBean(InitConfiguration.class);
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		try {
			while(true){
				String msg = scanner.nextLine();
				
				if(StringUtil.isEmpty(msg)){
					LOGGER.info("---不允许发送空消息！");
					continue;
				}
				
				//处理系统指令，如：LOGIN LOGOUT 等
				if(MessageConstant.LOGOUT.equals(msg)){
					//移除登录状态数据
					client.clear();
					LOGGER.info("---下线成功，如需加入聊天室，请重新登录");
					continue;
				} else if(MessageConstant.LOGIN.equals(msg)){
					client.start();
					LOGGER.info("---重新登录成功");
					continue;
				}
				
				//调用Route端API进行消息发送
				ChatInfo chat = new ChatInfo(MessageConstant.CHAT, System.currentTimeMillis(), conf.getUserId(), msg);
				client.sendMessage(chat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scanner.close();
		}
	}

}
