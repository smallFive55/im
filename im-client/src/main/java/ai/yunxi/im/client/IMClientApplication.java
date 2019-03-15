package ai.yunxi.im.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ai.yunxi.im.client.scanner.Scan;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午2:59:36
 * 
 */
@SpringBootApplication
public class IMClientApplication implements CommandLineRunner {

	private final static Logger LOGGER = LoggerFactory.getLogger(IMClientApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(IMClientApplication.class, args);
		LOGGER.info("启动 Client 服务成功");
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Thread th = new Thread(new Scan());
			th.setName("client-scanner-thread");
			th.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
