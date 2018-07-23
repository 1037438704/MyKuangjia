package administrator.example.com.framing.exceptions;

/**
 * @author dell-pc
 */
public class NetworkErrorException extends Exception {
    public NetworkErrorException(){
        super("网络异常");
    }
}