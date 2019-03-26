package ai.yunxi.im.route.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import ai.yunxi.im.route.zk.ZKUtil;
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
	private InitConfiguration conf;
	@Autowired
	private ZKUtil zkUtil;
	
	@Bean
	public ZkClient createZKClient(){
		ZkClient zk = new ZkClient(conf.getAddr());
		
		//监听/route节点下子节点的变化，实时更新server list
		zk.subscribeChildChanges(conf.getRoot(), new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				zkUtil.setAllNode(currentChilds);
			}
		});
		return zk;
	}
	
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
}
