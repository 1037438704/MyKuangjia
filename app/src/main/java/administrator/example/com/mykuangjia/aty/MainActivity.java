package administrator.example.com.mykuangjia.aty;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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


    @Override
    public void initViews() {
        textView = findViewById(R.id.textView);
        textView_content = findViewById(R.id.textView_content);
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
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
        //带值跳转
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump(MainActivity.class,new JumpParameter()
                .put("can1","100000000000000000000000000")
                );
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
    }
}
