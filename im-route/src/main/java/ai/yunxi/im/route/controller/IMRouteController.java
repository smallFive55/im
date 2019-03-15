package ai.yunxi.im.route.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ai.yunxi.im.common.constant.BasicConstant;
import ai.yunxi.im.common.pojo.ChatInfo;
import ai.yunxi.im.common.pojo.ServerInfo;
import ai.yunxi.im.common.pojo.UserInfo;
import ai.yunxi.im.common.utils.StringUtil;
import ai.yunxi.im.route.service.RouteService;
import ai.yunxi.im.route.zk.ZKUtil;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月27日 下午3:11:53
 * 
 */
@RestController
@RequestMapping("/")
public class IMRouteController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IMRouteController.class);
	private AtomicLong index = new AtomicLong();
	
	@Autowired
	private ZKUtil zk;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private RouteService routeService;
	
	/**
	 * 客户端登录，发现可用服务端:
	 * 1、获取所有zk上的节点；
	 * 2、轮询法得到一个节点 
	 **/
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ServerInfo login(@RequestBody UserInfo userInfo){
		String server="";
		try {
			List<String> all = zk.getAllNode();
			if(all.size()<=0){
				LOGGER.info("no server start...");
				return null;
			}
			Long position = index.incrementAndGet() % all.size();
			if (position < 0) {
			    position = 0L;
			}
			server = all.get(position.intValue());
			redisTemplate.opsForValue().set(BasicConstant.ROUTE_PREFIX+userInfo.getUserId(), server);
			LOGGER.info("get server info :"+server);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] serv = server.split("-");
		ServerInfo serviceInfo = new ServerInfo(serv[0], Integer.parseInt(serv[1]), Integer.parseInt(serv[2]));
		return serviceInfo;
	}
	
	/**
	 * 分发消息
	 **/
	@RequestMapping(value="/chat", method=RequestMethod.POST)
	public void chat(@RequestBody ChatInfo chat){
		//判断userId是否登录——从缓存取数据 ...
		String islogin = redisTemplate.opsForValue().get(BasicConstant.ROUTE_PREFIX + chat.getUserId());
		if(StringUtil.isEmpty(islogin)){
			LOGGER.info("该用户并未登录["+chat.getUserId()+"]");
			return;
		}
		try {
			//从ZK拿到所有节点，分发消息
			List<String> all = zk.getAllNode();
			for (String server : all) {
				String[] serv = server.split("-");
				String ip = serv[0];
				int httpPort = Integer.parseInt(serv[2]);
				String url = "http://"+ip+":"+httpPort+"/pushMessage";
				routeService.sendMessage(url, chat);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 客户端下线，从缓存中删除客户端与服务端映射关系
	 **/
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public void logout(@RequestBody UserInfo userInfo){
		redisTemplate.opsForValue().getOperations().delete(BasicConstant.ROUTE_PREFIX+userInfo.getUserId());
		LOGGER.info("路由端处理了用户下线逻辑："+userInfo.getUserId());
	}
}
