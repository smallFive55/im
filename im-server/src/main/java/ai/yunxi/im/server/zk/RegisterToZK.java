package ai.yunxi.im.server.zk;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.yunxi.im.server.config.InitConfiguration;
import ai.yunxi.im.server.config.SpringBeanFactory;

public class RegisterToZK implements Runnable {

    private static Logger LOGGER = LoggerFactory.getLogger(RegisterToZK.class);
    
	private InitConfiguration conf;
	private ZKUtil zk;
	
	public RegisterToZK() {
		conf = SpringBeanFactory.getBean(InitConfiguration.class);
		zk = SpringBeanFactory.getBean(ZKUtil.class);
	}

	@Override
	public void run() {
		try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			int httpPort = conf.getHttpPort();
			int nettyPort = conf.getNettyPort();
			LOGGER.info("---服务端注册到Zookeeper. ip:"+ip+"; httpPort:"+httpPort+"; nettyPort:"+nettyPort);
			
			//创建父节点
			zk.createRootNode();
			//判断是否需要注册到zk
			if(conf.isZkSwitch()){
				String path = conf.getRoot() + "/"+ip+"-"+conf.getNettyPort()+"-"+conf.getHttpPort();
				zk.createNode(path);
				LOGGER.info("---服务端注册到ZK成功，Path="+path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}