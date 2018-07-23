package administrator.example.com.mykuangjia.base;

import administrator.example.com.framing.BaseActivity;

/**
 *
 * @author dell-pc
 * @date 2018/7/19
 */

public abstract class BaseAty extends BaseActivity{
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
