package ai.yunxi.im.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午9:37:27
 * 
 */
@Component
public class UserClientConfiguration {

    @Value("${im.user.id}")
    private Integer userId;

    @Value("${im.user.userName}")
    private String userName;

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
    
}
