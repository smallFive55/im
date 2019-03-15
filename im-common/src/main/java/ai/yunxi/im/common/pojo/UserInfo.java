package ai.yunxi.im.common.pojo;
/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月27日 下午3:29:38
 * 
 */

import java.io.Serializable;

public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4227244327808705724L;
	private Integer userId;
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
