package administrator.example.com.mykuangjia.aty;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kongzue.kongzueupdatesdk.UpdateInfo;
import com.kongzue.kongzueupdatesdk.UpdateUtil;

import administrator.example.com.framing.BuildConfig;
import administrator.example.com.framing.HttpRequest;
import administrator.example.com.framing.interfaces.DarkNavigationBarTheme;
import administrator.example.com.framing.interfaces.DarkStatusBarTheme;
import administrator.example.com.framing.interfaces.Layout;
import administrator.example.com.framing.interfaces.NavigationBarBackgroundColor;
import administrator.example.com.framing.listener.ResponseListener;
import administrator.example.com.framing.util.JumpParameter;
import administrator.example.com.framing.util.Parameter;
import administrator.example.com.mykuangjia.R;
import administrator.example.com.mykuangjia.base.BaseAty;
import administrator.example.com.mykuangjia.util.HttpUtils;

/**
 * @author dell-pc
 */
@Layout(R.layout.activity_main) //布局
@DarkStatusBarTheme(true) //开启顶部状态栏图标、文字暗色模式
@NavigationBarBackgroundColor(a = 0, r = 0, g = 0, b = 0)
//透明颜色   设置底部导航栏背景颜色（a = 255,r = 255,g = 255,b = 255 黑色的）
@DarkNavigationBarTheme(true) //开启底部导航栏按钮暗色模式

public class MainActivity extends BaseAty {

    private TextView textView;
    private TextView textView_content;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private UpdateInfo updateInfo;

    @Override
    public void initViews() {
        textView = findViewById(R.id.textView);
        textView_content = findViewById(R.id.textView_content);
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_4 = findViewById(R.id.button_4);


        updateInfo = new UpdateInfo()
                .setInfo("1.上线了极力要求以至于无法再拒绝的收入功能\n" +
                        "2.出行的二级分类加入了地铁、地铁、地铁\n" +
                        "3.「关于」新增应用商店评分入口，你们知道怎么做\n" +
                        "4.「关于」还加入了GitHub地址，情怀+1s\n" +
                        "5.全新的底层适配框架，优化更多机型")
                .setVer("v2.5")
                .setDownloadUrl("http://paywhere.kongzue.com/downloads/paywhere.apk");
    }

    @Override
    public void initDatas(JumpParameter paramer) {

    }

    @Override
    public void setEvents() {
        //普通跳转
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(Main2Activity.class);
            }
        });
        //App更新
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUtil updateUtil = new UpdateUtil(me, BuildConfig.APPLICATION_ID)
                        .setOnDownloadListener(new UpdateUtil.OnDownloadListener() {
                            @Override
                            public void onStart(long downloadId) {
                                Log.i("MainActivity", "onStart: 下载开始");
                            }

                            @Override
                            public void onDownloading(long downloadId, int progress) {
                                Log.i("MainActivity", "onStart: 下载中：" + progress);
                            }

                            @Override
                            public void onSuccess(long downloadId) {
                                Log.i("MainActivity", "onStart: 下载完成");
                            }
                        })
                        .showNormalUpdateDialog(updateInfo,
                                "检查到更新（" + updateInfo.getVer() + "）",
                                "从商店下载",
                                "直接下载",
                                "取消");
            }
        });
        //数据请求
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpUtils();
                textView_content.setText("开始请求");
                HttpRequest.POST(me, "/Shop/getGoodsList", new Parameter().add("keywords", "衣服")
                        .add("p", "1")
                        .add("cid", "1")
                        .add("order", "1"), new ResponseListener() {
                    @Override
                    public void onResponse(String response, Exception error) {
                        if (error == null) {
                            textView_content.setText(response);
                            toast(response);
                            log("onResponse: " + response);
                        } else {
                            textView.setText("");
                            toast("请求失败");
                        }
                    }
                });
            }
        });
        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    /**
     * 再次返回键退出程序
     */
    private long lastBack = 0;

    /**
     * 再次返回键退出程序
     */
    @Override
    public void onBackPressed() {
        if (lastBack == 0 || System.currentTimeMillis() - lastBack > 2000) {
            toast("再按一次返回退出程序");
            lastBack = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }
}
