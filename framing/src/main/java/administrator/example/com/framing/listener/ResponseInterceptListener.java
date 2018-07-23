package administrator.example.com.framing.listener;

/**
 * CreateTime: 2018/7/23 18:24
 * @author dell-pc
 */
public interface ResponseInterceptListener {
    boolean onResponse(String url, String response, Exception error);
}
