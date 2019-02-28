package ai.yunxi.im.common.constant;
/**
 * 
 * @author Five老师
 * @createTime 2018年3月27日 下午8:23:31
 * 
 */
public class MessageStatus {

	public static final String LOGIN="LOGIN";
	public static final String LOGOUT="LOGOUT";
	public static final String CHAT="CHAT";
	public static final String SYSTEM="SYSTEM";
	
	public static boolean isSFP(String msg){
		return msg.matches("^\\[(SYSTEM|LOGIN|LOGOUT|CHAT)\\]");
	}
}
