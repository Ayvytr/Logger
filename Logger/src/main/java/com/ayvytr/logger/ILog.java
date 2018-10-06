package com.ayvytr.logger;

/**
 * Log接口
 *
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 1.0.0
 */
public interface ILog
{
    /**
     * @param tag     Tag
     * @param message Message
     * @see android.util.Log#d(String, String)
     */
    void d(String tag, String message);

    /**
     * @param tag     Tag
     * @param message Message
     * @see android.util.Log#e(String, String)
     */
    void e(String tag, String message);

    /**
     * @param tag     Tag
     * @param message Message
     * @see android.util.Log#w(String, String)
     */
    void w(String tag, String message);

    /**
     * @param tag     Tag
     * @param message Message
     * @see android.util.Log#i(String, String)
     */
    void i(String tag, String message);

    /**
     * @param tag     Tag
     * @param message Message
     * @see android.util.Log#v(String, String)
     */
    void v(String tag, String message);

    /**
     * @param tag     Tag
     * @param message Message
     * @see android.util.Log#wtf(String, String)
     */
    void wtf(String tag, String message);
}