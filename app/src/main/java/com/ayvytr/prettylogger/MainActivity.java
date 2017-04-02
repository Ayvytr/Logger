package com.ayvytr.prettylogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ayvytr.logger.L;
import com.ayvytr.logger.LogLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logTest();
    }

    private void logTest()
    {
//        log();
//        log1();
//        log2();
//        log3();
//        log4();
//        log5();
//        log6();
//        log7();
//        log8();
//        logWtf();
        logShowCalledInfo();
    }

    private void logShowCalledInfo()
    {
        L.getSettings().showCalledInfo(true);
        L.wtf();
        L.wtf("AA");
    }

    private void logWtf()
    {
        L.getSettings().justShowMessage(true).tag("logger");
        L.wtf();
    }

    private void log8()
    {
        L.getSettings().logLevel(LogLevel.NONE);
        L.e();
        L.e("ia");
        L.e(1);
        L.e("i1", 2);
    }

    private void log7()
    {
        L.getSettings().justShowMessage(true);
        L.i();
        L.w();
        L.wtf();
        L.v();
    }

    private void log6()
    {
        L.getSettings().justShowMessage(true);
        L.w("aa");
        L.w("aa", 1);
    }

    private void log5()
    {
        L.getSettings().showBottomBorder(true);
        L.e("Msg");
        L.w("Msg");
    }

    private void log4()
    {
        L.getSettings().methodCount(0).showBottomBorder(true);
        L.w(1, "aA");
    }

    private void log3()
    {
        L.getSettings().methodCount(10).showBottomBorder(true).methodOffset(5);
        L.e();
    }

    private void log2()
    {
//        L.t("MyTag").e(1);
//        L.getSettings().tag("MyTag");
        L.getSettings().hideThreadInfo();
        L.e(1, 2);
    }

    private void log()
    {
        //        L.getSettings().showBottomBorder(true);
//        L.getSettings().tag("mytag");
//        L.getSettings().tag("mytag").justShowMessage(true);
//        L.getSettings().tag("mytag").methodCount(55).showBottomBorder(true);
//        L.e("MyLog");
//        L.w("Message");
//
////        L.t("taggg").e("aaa");
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                SystemClock.sleep(2000);
//                L.w("Thread");
//
//                L.i();
//                L.w();
//                L.wtf();
//                L.v();
//            }
//        }).start();

        L.e("message", "arg1", 2, "arg3");
        L.w("message", "arg1", 2, "arg3");
        L.i("message", "arg1", 2, "arg3");
    }

    private void log1()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("AAA");
        list.add("BBB");

        HashMap<String, Integer> map = new HashMap<>();
        map.put("KA", 1);
        map.put("KB", 2);
        map.put("KC", 3);

        TreeSet<String> set = new TreeSet<>();
        set.add("AAA");
        set.add("BBB");
        set.add("CCC");

//        L.i("Array", 1, 2, "aa", "bb");
//        L.i("List", list);
//        L.v("Map", map);
//        L.e("Set", set);
//        L.w("Collection", list, map, set);
        L.i(1, 2, "aa", "bb");
        L.i(list);
        L.v(map);
        L.e(set);
        L.w(list, map, set);

    }
}
