package com.ayvytr.logger;

import android.text.TextUtils;

/**
 * Log设置类
 *
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 1.0.0
 */
public final class Settings {
    static final String DEFAULT_TAG = "PRETTYLOGGER";

    private int methodCount = 1;
    private boolean showThreadInfo = false;
    private int methodOffset = 0;
    //是否显示底部Border，默认不显示
    private boolean showBottomLogBorder;

    /**
     * show log or not
     */
    private boolean showLog = true;

    private String tag = DEFAULT_TAG;

    private boolean justShowMessage;

    /**
     * 单行字符串最大长度
     */
    int maxLineLength = 160;

    public Settings hideThreadInfo() {
        showThreadInfo = false;
        return this;
    }

    public Settings showThreadInfo(boolean isShow) {
        this.showThreadInfo = isShow;
        return this;
    }

    public Settings methodCount(int methodCount) {
        if(methodCount < 0) {
            methodCount = 0;
        }
        this.methodCount = methodCount;
        return this;
    }

    public Settings methodOffset(int offset) {
        this.methodOffset = offset;
        return this;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public boolean isShowLog() {
        return showLog;
    }

    public int getMethodOffset() {
        return methodOffset;
    }

    public void reset() {
        methodCount = 1;
        methodOffset = 0;
        showThreadInfo = false;
        showLog = true;
        showBottomLogBorder = false;
    }

    public boolean isShowBottomLogBorder() {
        return showBottomLogBorder;
    }

    public Settings showBottomBorder(boolean showBottomBorder) {
        this.showBottomLogBorder = showBottomBorder;
        return this;
    }

    public Settings tag(String tag) {
        if(!TextUtils.isEmpty(tag)) {
            this.tag = tag;
        }
        return this;
    }

    public String getTag() {
        return tag;
    }

    public Settings justShowMessage(boolean justShowMessage) {
        this.justShowMessage = justShowMessage;
        return this;
    }

    public boolean isJustShowMessage() {
        return justShowMessage;
    }

    public void showLog(boolean showLog) {
        this.showLog = showLog;
    }

    public Settings maxLineLength(int maxLineLength) {
        if(maxLineLength > 0) {
            this.maxLineLength = maxLineLength;
        }
        return this;
    }
}
