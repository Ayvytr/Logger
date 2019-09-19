package com.ayvytr.logger;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Array;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Log打印实现类
 *
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 1.0.0
 */
public class Printer implements IPrinter {
    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;

    /**
     * The minimum stack trace index, starts at this class after two native calls.
     */
    private static final int MIN_STACK_OFFSET = 3;

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char TOP_LEFT_CONNECT_CORNER = '╠';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char VERTICAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String TOP_CONNECT_BORDER = TOP_LEFT_CONNECT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private static final int CONNECT_BORDER_INTERVAL = 1000;

    private long lastTimeMillis;

    /**
     * It is used to determine log settings such as method count, thread info visibility
     */
    private Settings settings;

    public Printer(Settings settings) {
        this.settings = settings;
    }

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
            if(!name.equals(Printer.class.getName()) && !name.equals(L.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    private String buildMessage(Object... args) {
        //防止直接传入null
        if(args == null) {
            return "null";
        }

        if(args.length == 0) {
            return "[Empty Log]";
        }

        StringBuffer msgBuffer = new StringBuffer();
        for(Object arg : args) {
            if(arg == null) {
                //防止数组等对象为null
                msgBuffer.append("null");
            } else if(arg.getClass().getName().startsWith("[")) {
                msgBuffer.append("[");
                try {
                    for(int i = 0; ; i++) {
                        msgBuffer.append(Array.get(arg, i));
                        msgBuffer.append(",");
                    }
                } catch(IndexOutOfBoundsException e) {
                    if(msgBuffer.length() > 0) {
                        msgBuffer.deleteCharAt(msgBuffer.length() - 1);
                    }
                }
                msgBuffer.append("]");
            } else {
                msgBuffer.append(arg);
            }
            msgBuffer.append(" ");
        }

        return msgBuffer.toString();
    }

    private void disposeLog(int priority, Object... objects) {
        if(!settings.isShowLog()) {
            return;
        }

        String message = buildMessage(objects);
        if(settings.isJustShowMessage()) {
            log(priority, message);
        } else {
            logTopBorder(priority);
            log(priority, getThreadInfo() + message);
            logMethodCountInfo(priority, settings.getMethodCount());

            if(settings.isShowBottomLogBorder()) {
                logBottomBorder(priority);
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

    private void log(int priority, String message) {
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        if(message.length() < CHUNK_SIZE) {
            logChunk(priority, VERTICAL_DOUBLE_LINE + " " + message);
        } else {
            for(int i = 0; i < length; i += CHUNK_SIZE) {
                int count = Math.min(length - i, CHUNK_SIZE);
                logChunk(priority, VERTICAL_DOUBLE_LINE + " " + new String(bytes, i, count));
            }
        }
    }

    private synchronized void logChunk(int priority, String chunk) {
        String tag = getTag();
        switch(priority) {
            case Log.ERROR:
                settings.getLogAdapter().e(tag, chunk);
                break;
            case Log.INFO:
                settings.getLogAdapter().i(tag, chunk);
                break;
            case Log.WARN:
                settings.getLogAdapter().w(tag, chunk);
                break;
            case Log.ASSERT:
                settings.getLogAdapter().wtf(tag, chunk);
                break;
            case Log.DEBUG:
                settings.getLogAdapter().d(tag, chunk);
                break;
            //默认为VERBOSE
            default:
                settings.getLogAdapter().v(tag, chunk);
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

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    @Override
    public void xml(String xml) {
        if(TextUtils.isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch(TransformerException e) {
            e("Invalid xml");
        }

    }
}
