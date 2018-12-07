package administrator.example.com.mykuangjia.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import administrator.example.com.mykuangjia.R;

/**
 * Created by dell-pc on 2018/12/7.
 */

public class RVAdp1 extends BaseQuickAdapter<String,BaseViewHolder> {

    public RVAdp1(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public RVAdp1(int item_view_shop) {
        super(item_view_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RecyclerView itemRecyclerView = helper.itemView.findViewById(R.id.itemRecyclerView);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        List<String> list = new ArrayList<>();
        list.add(""+1);
        list.add(""+1);
        list.add(""+1);
        RVAdp2 rvAdp2 = new RVAdp2(R.layout.item_imageview,list);
        itemRecyclerView.setAdapter(rvAdp2);
    }
}
