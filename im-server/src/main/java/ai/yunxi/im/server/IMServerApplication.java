package ai.yunxi.im.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ai.yunxi.im.server.zk.RegisterToZK;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午2:59:36
 * 
 */
@SpringBootApplication
public class IMServerApplication implements CommandLineRunner {

	private final static Logger LOGGER = LoggerFactory.getLogger(IMServerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(IMServerApplication.class, args);
		LOGGER.info("启动 Service 服务成功");
	}

	/**
	 * 启动后，将节点注册在Zookeeper
	 **/
	@Override
	public void run(String... args) throws Exception {
		try {
			Thread thread = new Thread(new RegisterToZK());
			thread.setName("im-server-register-thread");
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
