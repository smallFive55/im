package ai.yunxi.im.server.handle;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import ai.yunxi.im.common.constant.BasicConstant;
import ai.yunxi.im.server.config.InitConfiguration;
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

    @Autowired
    private OkHttpClient okHttpClient;
    @Autowired
    private InitConfiguration conf;
    
	public void down(Integer userId){
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId",userId);
			RequestBody requestBody = RequestBody.create(BasicConstant.MEDIA_TYPE,jsonObject.toString());
			
			Request request = new Request.Builder()
			        .url(conf.getRouteLogoutUrl())
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
