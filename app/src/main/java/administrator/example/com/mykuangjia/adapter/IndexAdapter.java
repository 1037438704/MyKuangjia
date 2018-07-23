package administrator.example.com.mykuangjia.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import administrator.example.com.mykuangjia.R;
import administrator.example.com.mykuangjia.entity.IndexBean;

/**
 *
 * @author dell-pc
 * @date 2018/7/21
 */

public class IndexAdapter extends BaseQuickAdapter<IndexBean.DataBean.GoodsListBean,BaseViewHolder> {
    public IndexAdapter(int layoutResId, @Nullable List<IndexBean.DataBean.GoodsListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexBean.DataBean.GoodsListBean item) {
        helper.setText(R.id.item_view_shop_tv,item.getTitle());
        ImageView imageView = helper.itemView.findViewById(R.id.item_view_shop_iv);
        Glide.with(mContext).load(item.getGoods_logo()).apply(new RequestOptions().centerCrop()).into(imageView);
    }
}
