package ai.yunxi.im.server.zk;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ai.yunxi.im.server.config.AppConfiguration;

@Component
public class ZKUtil {

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private AppConfiguration appConfiguration ;

    /**
     * 创建父级节点
     */
    public void createRootNode(){
        boolean exists = zkClient.exists(appConfiguration.getZkRoot());
        if (exists){
            return;
        }

        //创建 root
        zkClient.createPersistent(appConfiguration.getZkRoot()) ;
    }

    /**
     * 写入指定节点 临时目录
     *
     * @param path
     */
    public void createNode(String path) {
        zkClient.createEphemeral(path);
    }

}
