package ai.yunxi.im.server.config;

import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午5:48:27
 * 
 */
@Configuration
public class BeanConfiguration {
	@Autowired
	private InitConfiguration conf;
	
	@Bean
	public ZkClient createZKClient(){
		return new ZkClient(conf.getAddr());
	}
	
    /**
     * http client
     * @return okHttp
     */
    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }
}
