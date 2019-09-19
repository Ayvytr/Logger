package com.ayvytr.prettylogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ayvytr.logger.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logTest();
    }

    private void logTest() {
//        log();
        log1();
//        log2();
//        log3();
//        log4();
//        log5();
//        log6();
//        log7();
//        log8();
//        logWtf();
        logShowCalledInfo();
        logArray();
    }

    private void logArray() {
        String[] stringArray = new String[10];
        for(int i = 0; i < stringArray.length; i++) {
            stringArray[i] = "item" + i;
        }
        L.e(stringArray);

        int[] intArray = new int[10];
        for(int i = 0; i < intArray.length; i++) {
            intArray[i] = i * i;
        }
        L.e(intArray);
    }

    private void logShowCalledInfo() {
        L.wtf();
        L.wtf("AA");
    }

    private void logWtf() {
        L.settings().justShowMessage(true).tag("logger");
        L.wtf();
    }

    private void log8() {
        L.settings().showLog(true);
        L.e();
        L.e("ia");
        L.e(1);
        L.e("i1", 2);
    }

    private void log7() {
        L.settings().justShowMessage(true);
        L.i();
        L.w();
        L.wtf();
        L.v();
    }

    private void log6() {
        L.settings().justShowMessage(true);
        L.w("aa");
        L.w("aa", 1);
    }

    private void log5() {
        L.settings().showBottomBorder(true);
        L.e("Msg");
        L.w("Msg");
    }

    private void log4() {
        L.settings().methodCount(0).showBottomBorder(true);
        L.w(1, "aA");
    }

    private void log3() {
        L.settings().methodCount(10).showBottomBorder(true).methodOffset(5);
        L.e();
    }

    private void log2() {
//        L.t("MyTag").e(1);
//        L.settings().tag("MyTag");
        L.settings().hideThreadInfo();
        L.e(1, 2);
    }

    private void log() {
        //        L.settings().showBottomBorder(true);
//        L.settings().tag("mytag");
//        L.settings().tag("mytag").justShowMessage(true);
//        L.settings().tag("mytag").methodCount(55).showBottomBorder(true);
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

    private void log1() {
        L.e(null);
        int[] intArray = null;
        L.e(intArray);
//        ArrayList<String> list = new ArrayList<>();
//        list.add("AAA");
//        list.add("BBB");
//
//        HashMap<String, Integer> map = new HashMap<>();
//        map.put("KA", 1);
//        map.put("KB", 2);
//        map.put("KC", 3);
//
//        TreeSet<String> set = new TreeSet<>();
//        set.add("AAA");
//        set.add("BBB");
//        set.add("CCC");
//
////        L.i("Array", 1, 2, "aa", "bb");
////        L.i("List", list);
////        L.v("Map", map);
////        L.e("Set", set);
////        L.w("Collection", list, map, set);
//        L.i(1, 2, "aa", "bb");
//        L.i(list);
//        L.v(map);
//        L.e(set);
//        L.w(list, map, set);

        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add(i * i);
        }
        L.e(list);
    }
}
