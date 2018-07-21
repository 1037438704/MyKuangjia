package administrator.example.com.mykuangjia.view;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by dell-pc on 2018/7/20.
 */

public class ToaChUtils {
    public static StateListDrawable addDrawable(int RADIUS, int normalFileName,
                                                int pressFileName, int type) {
        StateListDrawable sd = new StateListDrawable();


        if (RADIUS != 0) {
            float[] float1 = null;


            if (type == -1) {
                float1 = new float[]{0, 0, 0, 0, 0, 0, RADIUS, RADIUS};


            } else if (type == 0) {
                float1 = new float[]{0, 0, 0, 0, 0, 0, 0, 0};


            } else if (type == 1) {
                float1 = new float[]{0, 0, 0, 0, RADIUS, RADIUS, 0, 0};


            } else if (type == 2) { // 设置四个角为圆角
                float1 = new float[]{RADIUS, RADIUS, RADIUS, RADIUS, RADIUS,
                        RADIUS, RADIUS, RADIUS};


            } else {
                float1 = new float[]{0, 0, 0, 0, RADIUS, RADIUS, RADIUS,
                        RADIUS};
            }


            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadii(float1);
            gd.setColor(normalFileName);
            GradientDrawable gd2 = new GradientDrawable();
            gd2.setCornerRadii(float1);
            gd2.setColor(pressFileName);


            int pressed = android.R.attr.state_pressed;
            int window_focused = android.R.attr.state_window_focused;
            int focused = android.R.attr.state_focused;
            int selected = android.R.attr.state_selected;
            sd.addState(new int[]{pressed, window_focused}, gd2);
            sd.addState(new int[]{pressed, -focused}, gd2);
            sd.addState(new int[]{selected}, gd2);
            sd.addState(new int[]{focused}, gd2);
            sd.addState(new int[]{}, gd);
            return sd;


        } else {
            GradientDrawable gd = new GradientDrawable();
            gd.setColor(normalFileName);
            GradientDrawable gd2 = new GradientDrawable();
            gd2.setColor(pressFileName);
            int pressed = android.R.attr.state_pressed;
            int window_focused = android.R.attr.state_window_focused;
            int focused = android.R.attr.state_focused;
            int selected = android.R.attr.state_selected;
            sd.addState(new int[]{pressed, window_focused}, gd2);
            sd.addState(new int[]{pressed, -focused}, gd2);
            sd.addState(new int[]{selected}, gd2);
            sd.addState(new int[]{focused}, gd2);
            sd.addState(new int[]{}, gd);
            return sd;
        }
    }
}
