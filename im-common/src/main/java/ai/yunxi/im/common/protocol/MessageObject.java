package ai.yunxi.im.common.protocol;

import org.msgpack.annotation.Message;

/**
 * 
 * @author Five老师
 * @createTime 2018年3月25日 下午10:24:29
 * 
 */
@Message
public class MessageObject {

	private String cmd; //指令类型 例如：LOGIN\LOGOUT\CHAT\SYSTEM
	private long time; //消息发送的时间戳
	private long userId; //消息发送人
	private String content; //消息体
	private int online;//在线人数
	
	
	/**
	 * 
	 */
	public MessageObject() {
		super();
	}
	/**
	 * @param cmd
	 * @param time
	 * @param nickName
	 */
	public MessageObject(String cmd, long time, long userId) {
		super();
		this.cmd = cmd;
		this.time = time;
		this.userId = userId;
	}
	/**
	 * @param cmd
	 * @param time
	 * @param nickName
	 * @param content
	 */
	public MessageObject(String cmd, long time, long userId, String content) {
		super();
		this.cmd = cmd;
		this.time = time;
		this.userId = userId;
		this.content = content;
	}
	/**
	 * @param cmd
	 * @param time
	 * @param nickName
	 * @param content
	 * @param online
	 */
	public MessageObject(String cmd, long time, long userId, String content, int online) {
		super();
		this.cmd = cmd;
		this.time = time;
		this.userId = userId;
		this.content = content;
		this.online = online;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
}
