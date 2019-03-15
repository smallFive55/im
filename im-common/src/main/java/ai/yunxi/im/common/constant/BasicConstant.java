package ai.yunxi.im.common.constant;

import okhttp3.MediaType;

/**
 * @author 小五老师-云析学院
 * @createTime 2019年3月12日 下午10:02:08
 * 
 */
public final class BasicConstant {

	/**
	 * redis中客户端服务端映射前缀
	 **/
	public static final String ROUTE_PREFIX = "im-route:";
	
	/**
	 * 响应格式
	 **/
	public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
}
