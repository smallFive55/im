package ai.yunxi.im.server.zk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.yunxi.im.server.config.AppConfiguration;
import ai.yunxi.im.server.config.SpringBeanFactory;

public class RegisterToZK implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(RegisterToZK.class);

    private ZKUtil zk;

    private AppConfiguration appConfiguration ;

    private String ip;
    private int imServerPort;
    private int httpPort;

    public RegisterToZK(String ip, int imServerPort,int httpPort) {
        this.ip = ip;
        this.imServerPort = imServerPort;
        this.httpPort = httpPort ;
        zk = SpringBeanFactory.getBean(ZKUtil.class) ;
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class) ;
    }

    @Override
    public void run() {
    	System.out.println("regist to zookeeper. ip:"+ip+"; imServerPort:"+imServerPort+"; httpPort:"+httpPort);
        //创建父节点
    	zk.createRootNode();
        //是否要将自己注册到 ZK
        if (appConfiguration.isZkSwitch()){
            String path = appConfiguration.getZkRoot() + "/ip-" + ip + ":" + imServerPort + ":" + httpPort;
            zk.createNode(path);
            logger.info("注册 zookeeper 成功，msg=[{}]", path);
        }
    }
}