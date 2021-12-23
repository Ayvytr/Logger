package com.ayvytr.prettylogger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ayvytr.logger.L
import java.util.*

class TestKotlinLogActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logTest()
    }

    private fun logTest() {
        log()
        log1()
        log2()
        log3();
        //        log4();
        //        log5();
        //        log6();
        //        log7();
        //        log8();
        //        logWtf();
        log11()
    }

    private fun log11()
    {
        L.settings().reset()
        L.wtf()
        L.wtf("AA")
    }

    private fun logWtf()
    {
        L.settings().justShowMessage(true).tag("logger")
        L.wtf()
    }

    private fun log8()
    {
        L.settings().showLog(true)
        L.e()
        L.e("ia")
        L.e(1)
        L.e("i1", 2)
    }

    private fun log7()
    {
        L.settings().justShowMessage(true)
        L.i()
        L.w()
        L.wtf()
        L.v()
    }

    private fun log6()
    {
        L.settings().justShowMessage(true)
        L.w("aa")
        L.w("aa", 1)
    }

    private fun log5()
    {
        L.settings().showBottomBorder(true)
        L.e("Msg")
        L.w("Msg")
    }

    private fun log4()
    {
        L.settings().methodCount(0).showBottomBorder(true)
        L.w(1, "aA")
    }

    private fun log3()
    {
        L.settings().methodCount(10).showBottomBorder(true).methodOffset(5)
        L.e()
    }

    private fun log2()
    {
        //        L.t("MyTag").e(1);
        //        L.settings().tag("MyTag");
        L.settings().hideThreadInfo()
        L.e(1, 2)
    }

    private fun log()
    {
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

        L.e("message", "arg1", 2, "arg3")
        L.w("message", "arg1", 2, "arg3")
        L.i("message", "arg1", 2, "arg3")
    }

    private fun log1()
    {
        val list = ArrayList<String>()
        list.add("AAA")
        list.add("BBB")

        val map = HashMap<String, Int>()
        map["KA"] = 1
        map["KB"] = 2
        map["KC"] = 3

        val set = TreeSet<String>()
        set.add("AAA")
        set.add("BBB")
        set.add("CCC")

        //        L.i("Array", 1, 2, "aa", "bb");
        //        L.i("List", list);
        //        L.v("Map", map);
        //        L.e("Set", set);
        //        L.w("Collection", list, map, set);
        L.i(1, 2, "aa", "bb")
        L.i(list)
        L.v(map)
        L.e(set)
        L.w(list, map, set)

    }
}
