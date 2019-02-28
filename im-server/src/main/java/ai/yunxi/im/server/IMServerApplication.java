package ai.yunxi.im.server;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ai.yunxi.im.server.config.AppConfiguration;
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
	
	@Autowired
	private AppConfiguration appConfiguration ;

	@Value("${server.port}")
	private int httpPort ;
	
	public static void main(String[] args) {
		SpringApplication.run(IMServerApplication.class, args);
		LOGGER.info("启动 Service 服务成功");
	}

	@Override
	public void run(String... args) throws Exception {
		String addr = InetAddress.getLocalHost().getHostAddress();
		Thread thread = new Thread(new RegisterToZK(addr, appConfiguration.getImServerPort(), httpPort));
		thread.setName("server-registerToZK-thread");
		thread.start();
	}
	
	
}
