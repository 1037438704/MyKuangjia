package administrator.example.com.framing;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import administrator.example.com.framing.exceptions.NetworkErrorException;
import administrator.example.com.framing.listener.ResponseInterceptListener;
import administrator.example.com.framing.listener.ResponseListener;
import administrator.example.com.framing.util.Parameter;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * BaseOkHttp
 *
 * @author myzcx
 * @date 2017/12/27
 * ver:2.0
 */

public class HttpRequest {
    
    //是否开启调试模式
    public static boolean DEBUGMODE = false;
    
    //默认服务器地址
    public static String serviceUrl = "";
    
    private static int GET_REQUEST = 1;
    private static int POST_REQUEST = 0;
    
    //Https请求需要传入Assets目录下的证书文件名称
    private static String SSLInAssetsFileName;
    
    //Https请求是否需要Hostname验证，请保证serviceUrl中即Hostname地址
    private static boolean httpsVerifyServiceUrl = false;
    
    //全局拦截器
    private static ResponseInterceptListener responseInterceptListener;
    
    private Parameter headers;
    
    private static OkHttpClient okHttpClient;
    private static Activity activity;
    private static Context context;
    
    //单例
    private static HttpRequest httpRequest;
    
    private HttpRequest() {
    }
    
    //默认请求创建方法（不再推荐使用）
    @Deprecated
    public static HttpRequest getInstance(Activity a) {
        synchronized (HttpRequest.class) {
            if (httpRequest == null) {
                httpRequest = new HttpRequest();
            }
            activity = a;
            context = null;
        }
        return httpRequest;
    }
    
    //快速请求创建方法
    public static HttpRequest POST(Activity a, String partUrl, Parameter parameter, ResponseListener listener) {
        synchronized (HttpRequest.class) {
            if (httpRequest == null) {
                httpRequest = new HttpRequest();
            }
            activity = a;
            context = null;
            httpRequest.postRequest(partUrl, parameter, listener);
        }
        return httpRequest;
    }
    
    public static HttpRequest POST(Context c, String partUrl, Parameter parameter, ResponseListener listener) {
        synchronized (HttpRequest.class) {
            if (httpRequest == null) {
                httpRequest = new HttpRequest();
            }
            activity = null;
            context = c;
            httpRequest.postRequest(partUrl, parameter, listener);
        }
        return httpRequest;
    }
    
    //快速请求创建方法
    public static HttpRequest GET(Activity a, String partUrl, Parameter parameter, ResponseListener listener) {
        synchronized (HttpRequest.class) {
            if (httpRequest == null) {
                httpRequest = new HttpRequest();
            }
            activity = a;
            context = null;
            httpRequest.getRequest(partUrl, parameter, listener);
        }
        return httpRequest;
    }
    
    public Parameter getHeaders() {
        return headers;
    }
    
    public HttpRequest setHeaders(Parameter headers) {
        this.headers = headers;
        return this;
    }
    
    public void postRequest(String partUrl, final Parameter parameter,
                            final ResponseListener listener) {
        doRequest(partUrl, parameter, listener, POST_REQUEST);
    }
    
    public void getRequest(String partUrl, final Parameter parameter,
                           final ResponseListener listener) {
        doRequest(partUrl, parameter, listener, GET_REQUEST);
    }
    
    private String postUrl;
    
    private void doRequest(final String url, final Parameter parameter, final ResponseListener listener, int requestType) {
        try {
            OkHttpClient okHttpClient;
            
            if (SSLInAssetsFileName == null || SSLInAssetsFileName.isEmpty()) {
                okHttpClient = new OkHttpClient();
            } else {
                if (activity == null) {
                    okHttpClient = getOkHttpClient(context, context.getAssets().open(SSLInAssetsFileName));
                } else {
                    okHttpClient = getOkHttpClient(activity, activity.getAssets().open(SSLInAssetsFileName));
                }
            }
            
            postUrl = url;
            
            if (DEBUGMODE) {
                Log.i("<<<", "创建请求:" + postUrl + "\nparameter:" + parameter.toParameterString());
            }
            
            if (!postUrl.startsWith("http")) {
                postUrl = serviceUrl + postUrl;
            }
            
            RequestBody requestBody = parameter.toOkHttpParameter();
            
            //创建请求
            Request request;
            Request.Builder builder = new Request.Builder();
            //请求类型处理
            if (requestType == GET_REQUEST) {
                builder.url(postUrl + "?" + parameter.toParameterString());
            } else {
                builder.url(postUrl);
                builder.post(requestBody);
            }
            //请求头处理
            if (parameter != null) {
                if (headers != null && !headers.entrySet().isEmpty()) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        builder.addHeader(entry.getKey(), entry.getValue());
                    }
                }
            }
            request = builder.build();
            
            final String finalPostUrl = postUrl;
            
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    if (DEBUGMODE) {
                        Log.e(">>>", "failure:" + finalPostUrl + "\nparameter:" + parameter.toParameterString() + "\ninfo:" + e);
                    }
                    //回到主线程处理
                    if (activity == null) {
                        if (responseInterceptListener != null) {
                            if (responseInterceptListener.onResponse(finalPostUrl, null, new NetworkErrorException())) {
                                listener.onResponse(null, new NetworkErrorException());
                            }
                        } else {
                            listener.onResponse(null, new NetworkErrorException());
                        }
                    } else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (responseInterceptListener != null) {
                                    if (responseInterceptListener.onResponse(finalPostUrl, null, new NetworkErrorException())) {
                                        listener.onResponse(null, new NetworkErrorException());
                                    }
                                } else {
                                    listener.onResponse(null, new NetworkErrorException());
                                }
                            }
                        });
                    }
                    
                }
                
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String strResponse = response.body().string();
                    if (DEBUGMODE) {
                        Log.i(">>>", "request:" + finalPostUrl + "\nparameter:" + parameter.toParameterString() + "\nresponse:" + strResponse);
                    }
                    
                    //回到主线程处理
                    if (activity == null) {
                        if (responseInterceptListener != null) {
                            if (responseInterceptListener.onResponse(finalPostUrl, strResponse, null)) {
                                listener.onResponse(strResponse, null);
                            }
                        } else {
                            listener.onResponse(strResponse, null);
                        }
                    }else{
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (responseInterceptListener != null) {
                                    if (responseInterceptListener.onResponse(finalPostUrl, strResponse, null)) {
                                        listener.onResponse(strResponse, null);
                                    }
                                } else {
                                    listener.onResponse(strResponse, null);
                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public static OkHttpClient getOkHttpClient(Context context, InputStream... certificates) {
        if (okHttpClient == null) {
            File sdcache = context.getExternalCacheDir();
            int cacheSize = 10 * 1024 * 1024;
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            if (DEBUGMODE) {
                                Log.i("<<<", "hostnameVerifier: " + hostname);
                            }
                            if (httpsVerifyServiceUrl) {
                                if (serviceUrl.contains(hostname)) {
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return true;
                            }
                        }
                    })
                    .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
            if (certificates != null) {
                builder.sslSocketFactory(getSSLSocketFactory(certificates));
            }
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }
    
    private static SSLSocketFactory getSSLSocketFactory(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                
                try {
                    if (certificate != null) {
                        certificate.close();
                    }
                } catch (IOException e) {
                }
            }
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getSSLInAssetsFileName() {
        return SSLInAssetsFileName;
    }
    
    public static void setSSLInAssetsFileName(String fileName) {
        SSLInAssetsFileName = fileName;
    }
    
    public static boolean isHttpsVerifyServiceUrl() {
        return httpsVerifyServiceUrl;
    }
    
    public static void setHttpsVerifyServiceUrl(boolean httpsVerifyServiceUrl) {
        HttpRequest.httpsVerifyServiceUrl = httpsVerifyServiceUrl;
    }
    
    public static ResponseInterceptListener getResponseInterceptListener() {
        return responseInterceptListener;
    }
    
    public static void setResponseInterceptListener(ResponseInterceptListener responseInterceptListener) {
        HttpRequest.responseInterceptListener = responseInterceptListener;
    }
}
