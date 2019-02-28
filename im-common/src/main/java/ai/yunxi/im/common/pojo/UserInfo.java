package ai.yunxi.im.common.pojo;
/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月27日 下午3:29:38
 * 
 */

import java.io.Serializable;

public class UserInfo implements Serializable {

	private Integer id;
	private String userName;
	
	/**
	 * 
	 */
	public UserInfo() {
		super();
	}
	/**
	 * @param id
	 * @param userName
	 */
	public UserInfo(Integer id, String userName) {
		super();
		this.id = id;
		this.userName = userName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
