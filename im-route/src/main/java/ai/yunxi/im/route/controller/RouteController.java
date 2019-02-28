package ai.yunxi.im.route.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.yunxi.im.common.constant.Constant;
import ai.yunxi.im.common.pojo.ServiceInfo;
import ai.yunxi.im.common.pojo.UserInfo;
import ai.yunxi.im.route.zk.ZKUtil;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月27日 下午3:11:53
 * 
 */
@RestController
@RequestMapping("/")
public class RouteController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private ZKUtil zk;
	
	private AtomicLong index = new AtomicLong();
	
	/**
	 * 客户端获得服务端信息
	 **/
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ServiceInfo login(@RequestBody UserInfo userinfo){
		
		String server="";
		try {
			List<String> all = zk.getAllNode();
			Long position = index.incrementAndGet() % all.size();
			if (position < 0) {
			    position = 0L;
			}
			
			server = all.get(position.intValue());
			redisTemplate.opsForValue().set(Constant.ROUTE_PREFIX+userinfo.getId(), server);
			
			LOGGER.info("get server info :"+server);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] serv = server.substring(server.indexOf("-")+1).split(":");
		ServiceInfo serviceInfo = new ServiceInfo(serv[0], Integer.parseInt(serv[1]), Integer.parseInt(serv[2]));
		return serviceInfo;
	}
	
	/**
	 * 客户端下线
	 **/
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public void logout(@RequestBody UserInfo userinfo){
		try {
			redisTemplate.opsForValue().getOperations().delete(Constant.ROUTE_PREFIX+userinfo.getId());
			LOGGER.info("路由端处理了用户下线逻辑："+userinfo.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
