package ai.yunxi.im.common.pojo;

import java.io.Serializable;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午9:14:28
 * 服务端基本信息
 */
public class ServiceInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7082896534513406904L;
	private String ip;
	private Integer nettyPort; //netty提供消息推送端口
	private Integer httpPort; //http提供湍口

	/**
	 * 
	 */
	public ServiceInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param ip
	 * @param nettyPort
	 * @param httpPort
	 */
	public ServiceInfo(String ip, Integer nettyPort, Integer httpPort) {
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
