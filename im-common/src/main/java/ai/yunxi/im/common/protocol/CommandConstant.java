package ai.yunxi.im.common.protocol;
/**
 * 
 * @author Five
 * @createTime 2018年3月27日 下午8:23:31
 * 
 */
public class CommandConstant {

	//客户端向服务端发送登录指令
	public static final String LOGIN="LOGIN";
	//客户端向服务端发送登出指令
	public static final String LOGOUT="LOGOUT";
	//服务端向客户端发送系统指令
	public static final String SYSTEM="SYSTEM";
	//聊天指令
	public static final String CHAT="CHAT";
	
	public static boolean isSystemCommond(String msg){
		return msg.matches("^(SYSTEM|LOGIN|LOGOUT)");
	}
}
