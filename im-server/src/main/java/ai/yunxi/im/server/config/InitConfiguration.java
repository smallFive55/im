package ai.yunxi.im.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 小五老师-云析学院
 * @createTime 2019年3月12日 下午8:56:48
 * 
 */
@Component
public class InitConfiguration {

	@Value("${server.port}")
	private int httpPort;
	@Value("${im.server.port}")
	private int nettyPort;
	
	@Value("${im.zk.switch}")
	private boolean zkSwitch;
	@Value("${im.zk.root}")
	private String root;
	@Value("${im.zk.addr}")
	private String addr;
	
	@Value("${im.route.logout.url}")
    private String routeLogoutUrl;
	public int getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	public int getNettyPort() {
		return nettyPort;
	}
	public void setNettyPort(int nettyPort) {
		this.nettyPort = nettyPort;
	}
	public boolean isZkSwitch() {
		return zkSwitch;
	}
	public void setZkSwitch(boolean zkSwitch) {
		this.zkSwitch = zkSwitch;
	}
	public String getRoot() {
		return root;
	}
	public void setRoot(String root) {
		this.root = root;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRouteLogoutUrl() {
		return routeLogoutUrl;
	}
	public void setRouteLogoutUrl(String routeLogoutUrl) {
		this.routeLogoutUrl = routeLogoutUrl;
	}
}
