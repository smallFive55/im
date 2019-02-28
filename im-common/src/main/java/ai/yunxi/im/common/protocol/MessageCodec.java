package ai.yunxi.im.common.protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.yunxi.im.common.constant.MessageStatus;

/**
 * 
 * @author Five老师
 * @createTime 2018年3月25日 下午10:23:46
 * 消息编解码
 */
public class MessageCodec {
	
	//将字符串指令解码为MessageObject对象
	public MessageObject decoder(String message){
		if(message ==null || "".equals(message.trim())){return null;}
		
		Pattern pattern = Pattern.compile("^\\[(.*)\\](\\s-\\s(.*))?");
		Matcher matcher = pattern.matcher(message);
		String headers = ""; //消息头
		String content = ""; //消息体
		if(matcher.find()){
			headers = matcher.group(1);
			content = matcher.group(3);
		}
		String[] split = headers.split("\\]\\[");
		String cmd = split[0];
		long time = Long.parseLong(split[1]);
		long userId = Long.parseLong(split[2]);
		
		//将客户发送的消息封装为MessageObject对象
		if(cmd.equals(MessageStatus.LOGIN) || cmd.equals(MessageStatus.LOGOUT)){
			return new MessageObject(cmd, time, userId);
		}else if(cmd.equals(MessageStatus.CHAT) || cmd.equals(MessageStatus.SYSTEM)){
			return new MessageObject(cmd, time, userId, content);
		}
		return null;
	}
	
	
	//将MessageObject对象编码为字符串指令
	public String encoder(MessageObject msg){
		if(msg == null){return null;}
		String message = "["+msg.getCmd()+"]["+msg.getTime()+"]";
		if(msg.getCmd().equals(MessageStatus.SYSTEM)){
			message += "["+msg.getOnline()+"]";
		}else if(msg.getCmd().equals(MessageStatus.CHAT)
				||msg.getCmd().equals(MessageStatus.LOGIN)
				||msg.getCmd().equals(MessageStatus.LOGOUT)){
			message += "["+msg.getUserId()+"]";
		}
		if(msg.getContent() != null && !msg.getContent().equals("")){
			message += " - "+msg.getContent();
		}
		return message;
	}
}
