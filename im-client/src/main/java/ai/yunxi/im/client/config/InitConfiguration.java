package ai.yunxi.im.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 小五老师-云析学院
 * @createTime 2019年3月12日 下午8:56:48
 * 
 */
@Component
public class InitConfiguration {

	@Value("${im.user.id}")
	private int userId;
	@Value("${im.user.userName}")
	private String userName;
	@Value("${im.route.login.url}")
	private String routeLoginUrl;
	@Value("${im.route.chat.url}")
	private String routeChatUrl;
	@Value("${im.route.logout.url}")
    private String routeLogoutUrl;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRouteLoginUrl() {
		return routeLoginUrl;
	}
	public void setRouteLoginUrl(String routeLoginUrl) {
		this.routeLoginUrl = routeLoginUrl;
	}
	public String getRouteChatUrl() {
		return routeChatUrl;
	}
	public void setRouteChatUrl(String routeChatUrl) {
		this.routeChatUrl = routeChatUrl;
	}
	public String getRouteLogoutUrl() {
		return routeLogoutUrl;
	}
	public void setRouteLogoutUrl(String routeLogoutUrl) {
		this.routeLogoutUrl = routeLogoutUrl;
	}
}
