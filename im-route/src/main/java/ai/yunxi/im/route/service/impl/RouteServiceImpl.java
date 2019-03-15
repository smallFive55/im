package ai.yunxi.im.route.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import ai.yunxi.im.common.pojo.ChatInfo;
import ai.yunxi.im.route.service.RouteService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年3月7日 下午8:40:34
 * 
 */
@Service
public class RouteServiceImpl implements RouteService {

	private MediaType mediaType = MediaType.parse("application/json");
    @Autowired
    private OkHttpClient okHttpClient;
    
	@Override
	public void sendMessage(String url, ChatInfo chat) throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("command",chat.getCommand());
		jsonObject.put("time",chat.getTime());
		jsonObject.put("userId",chat.getUserId());
		jsonObject.put("content",chat.getContent());
		
		RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
		
		Request request = new Request.Builder()
		        .url(url)
		        .post(requestBody)
		        .build();

		Response response = okHttpClient.newCall(request).execute() ;
		if (!response.isSuccessful()){
		    throw new IOException("Unexpected code " + response);
		}
	}
}
