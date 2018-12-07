package administrator.example.com.mykuangjia.aty;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import administrator.example.com.framing.BaseActivity;
import administrator.example.com.framing.interfaces.Layout;
import administrator.example.com.framing.util.JumpParameter;
import administrator.example.com.mykuangjia.R;
import administrator.example.com.mykuangjia.adapter.RVAdp1;

/**
 * @author dell-pc
 */
@Layout(R.layout.activity_main4)

public class Main4Activity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<String> list;
    private RVAdp1 rvAdp1;

    @Override
    public void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(me));

        list = new ArrayList<>();
    }

    @Override
    public void initDatas(JumpParameter paramer) {
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        rvAdp1 = new RVAdp1(R.layout.item_rv_1, list);
        recyclerView.setAdapter(rvAdp1);
    }

    @Override
    public void setEvents() {

    }
}
