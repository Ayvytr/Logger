package com.ayvytr.logger;

/**
 * Log打印接口
 *
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 1.0.0
 * @see android.util.Log
 */
public interface IPrinter {
    void v(Object... objects);

    void d(Object... objects);

    void i(Object... objects);

    void w(Object... objects);

    void e(Object... objects);

    void wtf(Object... objects);
}
