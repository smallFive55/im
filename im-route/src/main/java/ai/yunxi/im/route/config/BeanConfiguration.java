package ai.yunxi.im.route.config;

import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import okhttp3.OkHttpClient;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午10:08:36
 * 
 */
@Configuration
public class BeanConfiguration {
	
	@Autowired
    private AppConfiguration appConfiguration ;
	
	/**
     * Redis bean
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
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
    
    @Bean
    public ZkClient buildZKClient(){
        return new ZkClient(appConfiguration.getZkAddr(), 5000);
    }
}