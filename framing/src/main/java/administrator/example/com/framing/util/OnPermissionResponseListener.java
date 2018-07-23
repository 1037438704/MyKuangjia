package administrator.example.com.framing.util;

/**
 * @author dell-pc
 */
public interface OnPermissionResponseListener {
    void onSuccess(String[] permissions);
    void onFail();
}
