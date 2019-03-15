package ai.yunxi.im.common.pojo;

import java.io.Serializable;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年3月7日 下午5:27:21
 * 
 */
public class ChatInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1360647504610967672L;
	private String command;
	private Long time;
	private Integer userId;
	private String content;
	
	public ChatInfo(String command, Long time, Integer userId, String content) {
		this.command = command;
		this.time = time;
		this.userId = userId;
		this.content = content;
	}
	public ChatInfo() {
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
