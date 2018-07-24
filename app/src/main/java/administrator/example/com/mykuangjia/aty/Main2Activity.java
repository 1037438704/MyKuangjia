package administrator.example.com.mykuangjia.aty;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import administrator.example.com.framing.HttpRequest;
import administrator.example.com.framing.interfaces.DarkNavigationBarTheme;
import administrator.example.com.framing.interfaces.DarkStatusBarTheme;
import administrator.example.com.framing.interfaces.Layout;
import administrator.example.com.framing.interfaces.NavigationBarBackgroundColor;
import administrator.example.com.framing.listener.ResponseListener;
import administrator.example.com.framing.util.JumpParameter;
import administrator.example.com.framing.util.LogUtils;
import administrator.example.com.framing.util.Parameter;
import administrator.example.com.mykuangjia.R;
import administrator.example.com.mykuangjia.adapter.ShopAdapter;
import administrator.example.com.mykuangjia.base.BaseAty;
import administrator.example.com.mykuangjia.entity.ShopBean;
import administrator.example.com.mykuangjia.view.LocalImageHolderView;

/**
 * @author dell-pc
 */
@Layout(R.layout.activity_main2)
@DarkStatusBarTheme(false) //开启顶部状态栏图标、文字暗色模式
@NavigationBarBackgroundColor(a = 0, r = 0, g = 0, b = 0)
//透明颜色   设置底部导航栏背景颜色（a = 255,r = 255,g = 255,b = 255 黑色的)
@DarkNavigationBarTheme(true) //开启底部导航栏按钮暗色模式

public class Main2Activity extends BaseAty implements OnItemClickListener {
    private ConvenientBanner convenientBanner;
    private List<Integer> list;
    private LinearLayout btn_back;
    private RecyclerView recyclerView;
    private ShopAdapter shopAdapter;
    private SmartRefreshLayout swipeLayout;

    private int count = 1;

    @Override
    public void initViews() {
        convenientBanner = findViewById(R.id.convenientBanner);
        list = new ArrayList<>();
        btn_back = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.recyclerView);
        swipeLayout = findViewById(R.id.swipeLayout);

        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(me);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void initDatas(JumpParameter paramer) {
        list.add(R.mipmap.ali);
        list.add(R.mipmap.ali);
        list.add(R.mipmap.ali);
        list.add(R.mipmap.ali);
        btn_back.setY(getStatusBarHeight());
        shujuqingqiu();
        lunBoTu();
    }


    @Override
    public void setEvents() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void shujuqingqiu() {
        //数据请求
//        new HttpUtils();
        HttpRequest.POST(me, "http://taoback.txunda.com/index.php/Api/Shop/getGoodsList", new Parameter().add("keywords", "衣服")
                .add("p", "1")
                .add("cid", "1")
                .add("order", "1"), new ResponseListener() {
            @Override
            public void onResponse(String response, Exception error) {
                if (error == null) {
                    LogUtils.d(response);
                    Gson gson = new Gson();
                    ShopBean shopBean = gson.fromJson(response, ShopBean.class);
                    List<ShopBean.DataBean> data = shopBean.getData();
                    shopAdapter = new ShopAdapter(R.layout.item_view_shop, data);
                    //给RecyclerView设置适配器
                    recyclerView.setAdapter(shopAdapter);
                    log("onResponse: " + response);
                } else {
                    toast("请求失败");
                }
            }
        });
    }

    /**
     * 轮播图的点击事件
     */
    @Override
    public void onItemClick(int position) {
        toast("你点击了" + position + "张图片");
        jump(Main3Activity.class);
    }

    /**
     * 轮播图
     */
    private void lunBoTu() {
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        }, list)
                //设置指示器是否可见
                .setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(2000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                //设置点击监听事件
                .setOnItemClickListener(this)
                //设置手动影响（设置了该项无法手动切换）
                .setManualPageable(true);
    }
}
