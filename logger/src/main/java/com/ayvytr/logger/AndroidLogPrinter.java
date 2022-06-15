package com.ayvytr.logger;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

/**
 * Log打印实现类
 *
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 1.0.0
 */
public class AndroidLogPrinter implements IPrinter {

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;

    /**
     * The minimum stack trace index, starts at this class after two native calls.
     */
    private static final int MIN_STACK_OFFSET = 3;

    private static final String TOP_LEFT_CORNER = "╔";
    private static final String TOP_LEFT_CONNECT_CORNER = "╠";
    private static final String BOTTOM_LEFT_CORNER = "╚";
    private static final String MIDDLE_CORNER = "╟";
    private static final String VERTICAL_DOUBLE_LINE = "║";
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String TOP_CONNECT_BORDER = TOP_LEFT_CONNECT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private static final int CONNECT_BORDER_INTERVAL = 1000;
    private static final String NULL_VALUE = "[NULL]";

    private long lastTimeMillis;

    private Settings settings = L.settings();

    @Override
    public void v(Object... objects) {
        disposeLog(Log.VERBOSE, objects);
    }

    @Override
    public void d(Object... objects) {
        disposeLog(Log.DEBUG, objects);
    }

    @Override
    public void i(Object... objects) {
        disposeLog(Log.INFO, objects);
    }

    @Override
    public void w(Object... objects) {
        disposeLog(Log.WARN, objects);
    }

    @Override
    public void e(Object... objects) {
        disposeLog(Log.ERROR, objects);
    }

    @Override
    public void wtf(Object... objects) {
        disposeLog(Log.ASSERT, objects);
    }

    /**
     * @return the appropriate tag based on local or global
     */
    private String getTag() {
        return settings.getTag();
    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(StackTraceElement[] trace) {
        for(int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if(!name.equals(AndroidLogPrinter.class.getName()) && !name.equals(L.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private void disposeLog(int priority, Object... objects) {
        if(!settings.isShowLog()) {
            return;
        }

        if(!settings.isJustShowMessage()) {
            logTopBorder(priority);
        }

        logFormat(priority, objects);

        if(!settings.isJustShowMessage()) {
            logMethodCountInfo(priority, settings.getMethodCount());
        }

        if(settings.isShowBottomLogBorder()) {
            logBottomBorder(priority);
        }
    }

    private void logFormat(int priority, Object... args) {
        if(args == null || args.length == 0) {
            logChunk(priority, VERTICAL_DOUBLE_LINE + getThreadInfo() + NULL_VALUE);
            return;
        }

        if(args.length == 1) {
            logChunk(priority, VERTICAL_DOUBLE_LINE + getThreadInfo()
                    + objectToString(args[0]));
            return;
        }

        StringBuilder sb = new StringBuilder(VERTICAL_DOUBLE_LINE).append(getThreadInfo());
        sb.append(objectToString(args[0]));
        sb.append(" ");

        int i = 1;
        while(i < args.length) {
            Object arg = args[i];

            int length = arg == null ? 6 : arg.toString().length();
            if(sb.length() + length <= settings.maxLineLength) {
                sb.append(objectToString(arg));
                sb.append(" ");
            } else {
                logChunk(priority, sb.toString());

                sb = new StringBuilder(VERTICAL_DOUBLE_LINE).append(getThreadInfo());
                sb.append(objectToString(arg));
                sb.append(" ");
            }

            i++;
        }

        logChunk(priority, sb.toString());

    }

    private String objectToString(Object arg) {
        if(arg == null) {
            return NULL_VALUE;
        } else {
            if(arg.getClass().getSimpleName().endsWith("[]")) {
                int length = Array.getLength(arg);
                StringBuilder b = new StringBuilder();
                b.append('[');
                for(int i = 0; i < length; i++) {
                    b.append(Array.get(arg, i));
                    b.append(", ");
                }
                if(length > 0) {
                    b.deleteCharAt(b.length() - 1);
                    b.deleteCharAt(b.length() - 1);
                }
                return b.append(']').toString();
            } else {
                return arg.toString();
            }
        }
    }

    private String getThreadInfo() {
        if(settings.isShowThreadInfo()) {
            return "[\"" + Thread.currentThread().getName() + "\" Thread]: ";
        } else {
            return "";
        }
    }

    private void logDivider(int logType) {
        logChunk(logType, MIDDLE_BORDER);
    }

    private void logMethodCountInfo(int priority, int methodCount) {
        if(methodCount <= 0) {
            return;
        }

        logDivider(priority);

        String spaces = "";
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(trace) + settings.getMethodOffset();

        StringBuilder builder = new StringBuilder();

        for(int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if(stackIndex >= trace.length) {
                continue;
            }

            builder.append("║ ")
                    .append(spaces)
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            logChunk(priority, builder.toString());
            builder.delete(0, builder.length());
            spaces += "   ";
        }
    }

    private void logTopBorder(int logType) {
        logChunk(logType, settings.isShowBottomLogBorder() ? TOP_BORDER :
                needConnectBorder() ? TOP_CONNECT_BORDER : TOP_BORDER);
    }

    private boolean needConnectBorder() {
        long timeMillis = System.currentTimeMillis();
        boolean need = timeMillis - lastTimeMillis < CONNECT_BORDER_INTERVAL;
        lastTimeMillis = timeMillis;
        return need;
    }

    private void logBottomBorder(int logType) {
        logChunk(logType, BOTTOM_BORDER);
    }

    private synchronized void logChunk(int priority, String chunk) {
        String tag = getTag();
        switch(priority) {
            case Log.ERROR:
                Log.e(tag, chunk);
                break;
            case Log.INFO:
                Log.i(tag, chunk);
                break;
            case Log.WARN:
                Log.w(tag, chunk);
                break;
            case Log.ASSERT:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                    Log.wtf(tag, chunk);
                }
                break;
            case Log.DEBUG:
                Log.d(tag, chunk);
                break;
            //默认为VERBOSE
            default:
                Log.v(tag, chunk);
                break;
        }
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    @Override
    public void json(String json) {
        if(TextUtils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if(json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if(json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(message);
                return;
            }
            e("Invalid Json");
        } catch(JSONException e) {
            e("Invalid Json");
        }
    }

}
