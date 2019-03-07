package ai.yunxi.im.common.pojo;

import java.io.Serializable;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年2月26日 下午9:16:44
 * 接口调用返回对象
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
	
	/**
	 * 
	 */
	public IMServiceResponse() {
		super();
	}
	/**
	 * @param code
	 * @param message
	 */
	public IMServiceResponse(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
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
}
