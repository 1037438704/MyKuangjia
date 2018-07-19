package administrator.example.com.framing.util;

public interface OnPermissionResponseListener {
    void onSuccess(String[] permissions);
    void onFail();
}
