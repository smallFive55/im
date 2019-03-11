package ai.yunxi.im.server.handle;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 
 * @author 小五老师-云析学院
 * @createTime 2019年3月7日 下午10:38:47
 * 
 */
@Component
public class ClientProcessor {

	private MediaType mediaType = MediaType.parse("application/json");
    @Autowired
    private OkHttpClient okHttpClient;
    
    @Value("${im.clear.route.request.url}")
    private String clearRouteUrl;
    
	public void down(Integer userId){
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",userId);
			RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
			
			Request request = new Request.Builder()
			        .url(clearRouteUrl)
			        .post(requestBody)
			        .build();

			Response response = okHttpClient.newCall(request).execute() ;
			if (!response.isSuccessful()){
			    throw new IOException("Unexpected code " + response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
