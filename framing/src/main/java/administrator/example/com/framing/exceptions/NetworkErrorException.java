package administrator.example.com.framing.exceptions;

public class NetworkErrorException extends Exception {
    public NetworkErrorException(){
        super("网络异常");
    }
}