package com.ayvytr.logger;


/**
 * 漂亮，简单，强大的Log打印类，支持格式化json: L.json(str)，xml: L.xml(str).
 * <p>
 * 2021-12-23 目前碰到问题：转换为kotlin代码后的vararg objects: Any?，decompile的java代码会加上非空
 * 判断，传空导致空指针异常，有知道怎么解决的老铁请邮件我.
 *
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 3.3.2 限制行默认长度为160；删除json和xml打印方法
 * @since 1.0.0
 */
public final class L {
    private static Settings settings = new Settings();

    private static AndroidLogPrinter printer = new AndroidLogPrinter();

    private L() {
        throw new UnsupportedOperationException();
    }

    public static void v(Object... objects) {
        printer.v(objects);
    }

    public static void d(Object... objects) {
        printer.d(objects);
    }

    public static void i(Object... objects) {
        printer.i(objects);
    }

    public static void w(Object... objects) {
        printer.w(objects);
    }

    public static void e(Object... objects) {
        printer.e(objects);
    }

    public static void wtf(Object... objects) {
        printer.wtf(objects);
    }

    public static Settings settings() {
        return settings;
    }

}
