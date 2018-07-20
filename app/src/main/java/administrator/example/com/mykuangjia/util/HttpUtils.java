package administrator.example.com.mykuangjia.util;

import administrator.example.com.framing.HttpRequest;
import administrator.example.com.framing.listener.ResponseInterceptListener;
import okhttp3.Response;

/**
 * Created by dell-pc on 2018/7/19.
 */

public class HttpUtils {
    //开始数据请求
    private String url = "http://taoback.txunda.com/index.php/Api";

    public HttpUtils() {
        HttpRequest.serviceUrl = url;
        HttpRequest.setResponseInterceptListener(new ResponseInterceptListener() {
            @Override
            public boolean onResponse(String url, String response, Exception error) {
                if (error != null){
                    return  true;
                }else {
                    return true;
                }
            }
        });
    }
}
