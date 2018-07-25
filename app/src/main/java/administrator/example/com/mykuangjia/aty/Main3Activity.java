package administrator.example.com.mykuangjia.aty;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.gson.Gson;
import com.kongzue.dialog.v2.WaitDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.List;

import administrator.example.com.framing.HttpRequest;
import administrator.example.com.framing.interfaces.DarkNavigationBarTheme;
import administrator.example.com.framing.interfaces.DarkStatusBarTheme;
import administrator.example.com.framing.interfaces.Layout;
import administrator.example.com.framing.interfaces.NavigationBarBackgroundColor;
import administrator.example.com.framing.listener.ResponseListener;
import administrator.example.com.framing.util.JumpParameter;
import administrator.example.com.framing.util.Parameter;
import administrator.example.com.mykuangjia.R;
import administrator.example.com.mykuangjia.adapter.IndexAdapter;
import administrator.example.com.mykuangjia.base.BaseAty;
import administrator.example.com.mykuangjia.entity.IndexBean;

/**
 * 这个界面上拉刷新
 * @author dell-pc
 */
@Layout(R.layout.activity_main3)
@DarkStatusBarTheme(false) //开启顶部状态栏图标、文字暗色模式
@NavigationBarBackgroundColor(a = 0, r = 0, g = 0, b = 0)//透明颜色   设置底部导航栏背景颜色（a = 255,r = 255,g = 255,b = 255 黑色的)
@DarkNavigationBarTheme(true) //开启底部导航栏按钮暗色模式

public class Main3Activity extends BaseAty {
    private RecyclerView recyclerView;
    private IndexAdapter indexAdapter;
    private int count;
    private RefreshLayout refreshLayout;
    private List<IndexBean.DataBean.GoodsListBean> goods_list;
    private boolean upload;

    @Override
    public void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout = findViewById(R.id.refreshLayout);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(me);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // /刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                goods_list.clear();
                count = 1;
                shujuqingqiu();
                indexAdapter.notifyDataSetChanged();
                //传入false表示刷新失败
                refreshLayout.finishRefresh(upload);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(upload);
            }
        });
    }

    @Override
    public void initDatas(JumpParameter paramer) {
        WaitDialog.show(me, "数据加载中");
        shujuqingqiu();
    }

    private void shujuqingqiu() {
        HttpRequest.POST(me, "http://taoback.txunda.com/index.php/Api/Index/index", new Parameter().add("keywords", "衣服")
                        .add("p", count + "")
                , new ResponseListener() {
                    @Override
                    public void onResponse(String response, Exception error) {
                        if (error == null) {
                            upload = true;
                            WaitDialog.dismiss();
                            Gson gson = new Gson();
                            IndexBean indexBean = gson.fromJson(response, IndexBean.class);
                            IndexBean.DataBean data = indexBean.getData();
                            goods_list = data.getGoods_list();
                            indexAdapter = new IndexAdapter(R.layout.item_view_shop, goods_list);
                            //给RecyclerView设置适配器
                            recyclerView.setAdapter(indexAdapter);
                            log("onResponse: " + response);
                        } else {
                            upload = false;
                            toast("请求失败");
                        }
                    }
                });
    }

    @Override
    public void setEvents() {

    }
}
