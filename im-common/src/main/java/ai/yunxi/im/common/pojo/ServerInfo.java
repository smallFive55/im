package ai.yunxi.im.common.pojo;

import java.io.Serializable;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午9:14:28
 * 服务端基本信息
 */
public class ServerInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2230742812761280401L;
	private String ip;
	private Integer nettyPort;
	private Integer httpPort;
	
	public ServerInfo() {
	}
	public ServerInfo(String ip, Integer nettyPort, Integer httpPort) {
		super();
		this.ip = ip;
		this.nettyPort = nettyPort;
		this.httpPort = httpPort;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getNettyPort() {
		return nettyPort;
	}
	public void setNettyPort(Integer nettyPort) {
		this.nettyPort = nettyPort;
	}
	public Integer getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(Integer httpPort) {
		this.httpPort = httpPort;
	}
	
}
