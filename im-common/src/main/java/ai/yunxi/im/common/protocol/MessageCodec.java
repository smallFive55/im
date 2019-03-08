package ai.yunxi.im.common.protocol;

import ai.yunxi.im.common.protocol.MessageProto.MessageProtocol.Builder;

/**
 * 
 * @author Five老师
 * @createTime 2018年3月25日 下午10:23:46
 * 消息编解码
 */
public class MessageCodec {
	
	private MessageProto.MessageProtocol builderMessage(String content, String command, Long time, Integer userId){
		Builder builder = MessageProto.MessageProtocol.newBuilder();
		builder.setContent(content);
		builder.setCommand(command);
		builder.setTime(time);
		builder.setUserId(userId); //消息发送人userId
		return builder.build();
	}
	
	//将字符串指令解码为MessageProto.MessageProtocol对象
	public MessageProto.MessageProtocol decoder(String message, Integer userId){
		if(message ==null || "".equals(message.trim())){return null;}
		
		MessageProto.MessageProtocol msgObj = null;
		//系统指令
		if(CommandConstant.isSystemCommond(message)){
			msgObj = builderMessage("System Message", message, System.currentTimeMillis(), userId);
			return msgObj;
		}
		int idx = message.indexOf(":-+-:");
		if(idx>0){
			//message = command-time-userId:-+-:content
			String sysstr = message.substring(0, idx);
			String content = message.substring(idx+5);
			String[] split = sysstr.split("-");
			//封装Message对象
			msgObj = builderMessage(content, split[0], Long.parseLong(split[1]), Integer.parseInt(split[2]));
		}else{
			//message = content
			//默认聊天
			msgObj = builderMessage(message, CommandConstant.CHAT, System.currentTimeMillis(), userId);
		}
		return msgObj;
	}
	
	
	//将MessageProto对象编码为字符串指令
	public String encoder(MessageProto.MessageProtocol msg){
		if(msg == null){return null;}
		String message = msg.getCommand()+"-"+msg.getTime()+"-"+msg.getUserId()+":-+-:"+msg.getContent();
		return message;
	}
}
