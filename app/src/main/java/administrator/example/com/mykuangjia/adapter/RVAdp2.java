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

public class RVAdp2 extends BaseQuickAdapter<String,BaseViewHolder> {

    public RVAdp2(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    public RVAdp2(int item_view_shop) {
        super(item_view_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {


    }
}
