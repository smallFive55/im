package ai.yunxi.im.common.pojo;

import java.io.Serializable;

import ai.yunxi.im.common.pojo.ServiceInfo;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午9:16:44
 * 
 */
public class IMServiceResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7896902552584462607L;
	/**
	 * code : 9000
	 * message : success
	 **/
	
	private String code;
	private String message;
	private ServiceInfo dataBody;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ServiceInfo getDataBody() {
		return dataBody;
	}
	public void setDataBody(ServiceInfo dataBody) {
		this.dataBody = dataBody;
	}

	public static class LoginInfo {
	    private Long userId ;
	    private String userName ;

	    public LoginInfo(Long userId, String userName) {
	        this.userId = userId;
	        this.userName = userName;
	    }

	    public Long getUserId() {
	        return userId;
	    }

	    public void setUserId(Long userId) {
	        this.userId = userId;
	    }

	    public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    @Override
	    public String toString() {
	        return "LoginReqVO{" +
	                "userId=" + userId +
	                ", userName='" + userName + '\'' +
	                "} " + super.toString();
	    }
	}
}
