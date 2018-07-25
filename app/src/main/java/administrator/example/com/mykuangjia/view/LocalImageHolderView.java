package administrator.example.com.mykuangjia.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 *
 * @author dell-pc
 * @date 2018/6/27
 */

public class LocalImageHolderView implements Holder<Integer> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        imageView.setImageResource(data);
    }

    /**
     * 这里要注意传的是字符串还是int 型的
     * */
//    @Override
//    public void UpdateUI(Context context, int position, String data) {
////        imageView.setImageResource(data);
//        Glide.with(context).load(data).into(imageView);
//    }
}
