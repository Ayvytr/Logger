package com.ayvytr.logger;


/**
 * Log打印类，所有打印从这里开始.
 * L is a wrapper of {@link android.util.Log}
 * But more pretty, simple and powerful.
 *
 * @author Ayvytr ['s GitHub](https://github.com/Ayvytr)
 * @since 1.0.0
 */
public final class L
{
    private static Settings settings = new Settings();
    private static Printer printer = new Printer(settings);

    private L()
    {
        throw new UnsupportedOperationException();
    }

    public static void v(Object... objects)
    {
        printer.v(objects);
    }

    public static void d(Object... objects)
    {
        printer.d(objects);
    }

    public static void i(Object... objects)
    {
        printer.i(objects);
    }

    public static void w(Object... objects)
    {
        printer.w(objects);
    }

    public static void e(Object... objects)
    {
        printer.e(objects);
    }

    public static void wtf(Object... objects)
    {
        printer.wtf(objects);
    }

    /**
     * It is used to get the settings object in order to change settings
     *
     * @return the settings object
     */
    public static Settings settings()
    {
        return settings;
    }


    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json)
    {
        printer.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml)
    {
        printer.xml(xml);
    }
}
