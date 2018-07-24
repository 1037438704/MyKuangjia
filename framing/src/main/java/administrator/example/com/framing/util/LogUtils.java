package administrator.example.com.framing.util;

import android.text.TextUtils;
import android.util.Log;

/**
 *
 * @author dell-pc
 * @date 2018/7/23
 */

public class LogUtils {
    /**
     * 是否开启debug日志
     */
    public static boolean isDebug = true;
    private static String assist="------   ";

    /**
     * @param content
     * @author Taurus
     * @Description debug日志
     * @created at 2017/2/24 14:57
     * @E-mail TaurusAndroid@163.com
     */
    public static void d(String content) {
        if (!isDebug) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null) {
            customLogger.e(tag, content);
        } else {
            Log.d(tag + assist, content);
        }
    }

    /**
     * @param content
     * @author Taurus
     * @Description 错误日志
     * @created at 2017/2/24 14:57
     * @E-mail TaurusAndroid@163.com
     */
    public static void e(String content) {
        if (!isDebug) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null) {
            customLogger.e(tag, content);
        } else {
            Log.e(tag + assist, content);
        }
    }

    /**
     * @param content
     * @param tr
     * @author Taurus
     * @Description 错误日志和异常
     * @created at 2017/2/24 14:57
     * @E-mail TaurusAndroid@163.com
     */
    public static void e(String content, Throwable tr) {
        if (!isDebug) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.e(tag, content, tr);
        } else {
            Log.e(tag + assist, content, tr);
        }
    }

    /**
     * @param content
     * @author Taurus
     * @Description 信息日志
     * @created at 2017/2/24 14:57
     * @E-mail TaurusAndroid@163.com
     */
    public static void i(String content) {
        if (!isDebug) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null) {
            customLogger.e(tag, content);
        } else {
            Log.i(tag + assist, content);
        }
    }

    /**
     * @param content
     * @author Taurus
     * @Description 详细日志
     * @created at 2017/2/24 14:57
     * @E-mail TaurusAndroid@163.com
     */
    public static void v(String content) {
        if (!isDebug) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null) {
            customLogger.e(tag, content);
        } else {
            Log.v(tag + assist, content);
        }
    }

    /**
     * @param content
     * @author Taurus
     * @Description 警告日志
     * @created at 2017/2/24 14:57
     * @E-mail TaurusAndroid@163.com
     */
    public static void w(String content) {
        if (!isDebug) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        if (customLogger != null) {
            customLogger.e(tag, content);
        } else {
            Log.w(tag + assist, content);
        }
    }

    /**
     * @param content
     * @author Taurus
     * @Description wtf日志
     * @created at 2017/2/24 14:57
     * @E-mail TaurusAndroid@163.com
     */

    public static void wtf(String content) {
        if (!isDebug) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);

        if (customLogger != null) {
            customLogger.wtf(tag, content);
        } else {
            Log.wtf(tag + assist, content);
        }
    }

    public static String customTagPrefix = "";

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static CustomLogger customLogger;

    public interface CustomLogger {
        void d(String tag, String content);

        void d(String tag, String content, Throwable tr);

        void e(String tag, String content);

        void e(String tag, String content, Throwable tr);

        void i(String tag, String content);

        void i(String tag, String content, Throwable tr);

        void v(String tag, String content);

        void v(String tag, String content, Throwable tr);

        void w(String tag, String content);

        void w(String tag, String content, Throwable tr);

        void w(String tag, Throwable tr);

        void wtf(String tag, String content);

        void wtf(String tag, String content, Throwable tr);

        void wtf(String tag, Throwable tr);
    }
}
