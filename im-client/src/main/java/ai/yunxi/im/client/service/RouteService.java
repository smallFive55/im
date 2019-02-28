package ai.yunxi.im.client.service;

import org.apache.catalina.util.ServerInfo;

import ai.yunxi.im.common.pojo.IMServiceResponse;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午9:24:47
 * 
 */
public interface RouteService {

	/**
	 * 向路由服务器获取服务器IP+netty端口
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	ServerInfo getIMServer(IMServiceResponse.LoginInfo loginInfo) throws Exception;
}
