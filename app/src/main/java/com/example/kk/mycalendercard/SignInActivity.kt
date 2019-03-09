package com.example.kk.mycalendercard

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SignInActivity : AppCompatActivity() {
    internal var gvCalendar: GridView? = null
    internal var calendarAdapter: Adapter? = null
    internal var calendarBeanList: MutableList<CalendarBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gvCalendar = findViewById(R.id.gv_calendar)

        val Year = DateUtil.year
        val Month = DateUtil.month
        val Day = DateUtil.day

        val MonthDays = DateUtil.getMonthDays(Year, Month)//该Month的天数
        val FirstDay = DateUtil.getDayOfWeek(Year, 21, Month, 1)//一号周几
        val EmptyDays = FirstDay - 1//表格状，如果一号周二，则空了周日，周一
        val AllDays = ((EmptyDays + MonthDays) / 7 + 1) * 7//所有天数，把表格填满，7的倍数

        for (i in 1..MonthDays) {
            val calendarBean = CalendarBean()
            calendarBean.day = i
            calendarBean.type = "false"
            calendarBeanList.add(calendarBean)
            //给Adapter的本意是指：calendarBeanList是从后台读取的数据，记录了日期和该日期是否签到true or false
            //所以该例子只是前端代码，点击签到后，关闭程序再进入不会保留上次签到记录
            //所以在有后台支持下，可以根据获取的calendarBeanList判断签到与否
            //当然，如果不用后台，也可以保存在本地数据库
        }

        calendarAdapter = Adapter(this, EmptyDays, MonthDays, Day, AllDays, calendarBeanList)
        gvCalendar!!.adapter = calendarAdapter
    }
}
