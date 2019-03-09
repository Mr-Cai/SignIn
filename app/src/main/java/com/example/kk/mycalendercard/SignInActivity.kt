package com.example.kk.mycalendercard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class SignInActivity : AppCompatActivity() {
    private lateinit var calendarAdapter: Adapter
    private var calendarBeanList: MutableList<CalendarBean> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val year = DateUtil.year
        val month = DateUtil.month
        val toDay = DateUtil.day
        val monthDays = DateUtil.getMonthDays(year, month)//该Month的天数
        val firstDays = DateUtil.getDayOfWeek(year, 21, month, 1)//一号周几
        val emptyDays = firstDays - 1//表格状，如果一号周二，则空了周日，周一
        val allDays = ((emptyDays + monthDays) / 7 + 1) * 7//所有天数，把表格填满，7的倍数

        for (i in 1..monthDays) {
            val calendarBean = CalendarBean()
            calendarBean.day = i
            calendarBean.type = "false"
            calendarBeanList.add(calendarBean)
            /*        给Adapter的本意是指：calendarBeanList是从后台读取的数据，记录了日期和该日期是否签到true or false
                    所以该例子只是前端代码，点击签到后，关闭程序再进入不会保留上次签到记录
                    所以在有后台支持下，可以根据获取的calendarBeanList判断签到与否
                    当然，如果不用后台，也可以保存在本地数据库*/
        }

        calendarAdapter = Adapter(this, emptyDays, monthDays, toDay, allDays, calendarBeanList)
        gv_calendar!!.adapter = calendarAdapter
    }
}
