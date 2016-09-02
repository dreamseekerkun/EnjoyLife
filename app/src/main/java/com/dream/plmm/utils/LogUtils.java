package com.dream.plmm.utils;


/**
 * log日志输出类
 * Created by likun on 15/10/13.
 */
public class LogUtils {

    public static boolean DebugMode = true;
    private static final String AgeGroup = "meizi";

    public static void i(String paramString1, String paramString2) {
        if (DebugMode)
            android.util.Log.i(AgeGroup, paramString1 + paramString2);
    }

    public static void i(String paramString1, String paramString2, Exception paramException) {
        if (DebugMode)
            android.util.Log.i(AgeGroup, paramString1 + paramException.toString() + ":  [" + paramString2 + "]");
    }

    public static void e(String paramString1, String paramString2) {
        if (DebugMode)
            android.util.Log.e(AgeGroup, paramString1 + paramString2);
    }

    public static void e(String paramString1, String paramString2, Exception paramException) {
        if (DebugMode) {
            android.util.Log.e(AgeGroup, paramString1 + paramException.toString() + ":  [" + paramString2 + "]");
            StackTraceElement[] arrayOfStackTraceElement1 = paramException.getStackTrace();
            for (StackTraceElement localStackTraceElement : arrayOfStackTraceElement1)
                android.util.Log.e(AgeGroup, paramString1 + "        at\t " + localStackTraceElement.toString());
        }
    }

    public static void d(String paramString1, String paramString2) {
        if (DebugMode)
            android.util.Log.d(AgeGroup, paramString1 + paramString2);
    }

    public static void d(String paramString1, String paramString2, Exception paramException) {
        if (DebugMode)
            android.util.Log.d(AgeGroup, paramString1 + paramException.toString() + ":  [" + paramString2 + "]");
    }

    public static void v(String paramString1, String paramString2) {
        if (DebugMode)
            android.util.Log.v(AgeGroup, paramString1 + paramString2);
    }

    public static void v(String paramString1, String paramString2, Exception paramException) {
        if (DebugMode)
            android.util.Log.v(AgeGroup, paramString1 + paramException.toString() + ":  [" + paramString2 + "]");
    }

    public static void w(String paramString1, String paramString2) {
        if (DebugMode)
            android.util.Log.w(AgeGroup, paramString1 + paramString2);
    }

    public static void w(String paramString1, String paramString2, Exception paramException) {
        if (DebugMode) {
            android.util.Log.w(AgeGroup, paramString1 + paramException.toString() + ":  [" + paramString2 + "]");
            StackTraceElement[] arrayOfStackTraceElement1 = paramException.getStackTrace();
            for (StackTraceElement localStackTraceElement : arrayOfStackTraceElement1)
                android.util.Log.w(AgeGroup, paramString1 + "        at\t " + localStackTraceElement.toString());
        }
    }
}
