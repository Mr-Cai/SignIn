package com.example.kk.mycalendercard

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast

/**
 * Created by kanghuicong on 2017/3/1.
 * QQ邮箱:515849594@qq.com
 */
class Adapter(
        private var context: Context,
        emptyDays: Int,
        private var monthDay: Int,
        private var toDay: Int,
        private var allDays: Int,
        private var calendarBeanList: MutableList<CalendarBean>?
) : BaseAdapter(), SignInData.ISignIn {
    private var holder: Holder? = null
    private var emptyDays: Int = 0
    private var signInData: SignInData
    private var width: Int = 0

    init {
        this.emptyDays = emptyDays + 7
        width = ScreenUtils.getScreenWidth(context)
        signInData = SignInData(this)
    }

    override fun getCount() = allDays + 7

    override fun getItem(i: Int) = Unit

    override fun getItemId(i: Int) = 0L

    private fun getType(position: Int) = when {
        position < 7 -> "title"
        else -> "item"
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View? {
        var convertView = view
        holder = Holder()
        when (getType(position)) {
            "title" -> {
                //标题，周日---周六
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item_title, null)
                    holder!!.titleTxT = convertView!!.findViewById(R.id.tv_title)
                    convertView.tag = holder
                } else {
                    holder = convertView.tag as Holder
                }
                holder!!.titleTxT!!.text = DateUtil.weekName[position]
            }
            "item" -> {
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.item, null)
                    holder!!.itemTxT = convertView!!.findViewById(R.id.tv_item)
                    convertView.tag = holder
                } else {
                    holder = convertView.tag as Holder
                }

                ScreenUtils.accordHeight(holder!!.itemTxT!!, width, 1, 7)//根据屏幕宽度适配

                if (position >= emptyDays && position < emptyDays + monthDay) {
                    holder!!.itemTxT!!.text = "${position - emptyDays + 1}"
                    //点击事件
                    holder!!.itemTxT!!.setOnClickListener(ClickDay(
                            holder!!.itemTxT!!,
                            position,
                            position - emptyDays + 1,
                            toDay,
                            calendarBeanList!![position - emptyDays].type
                    ))
                } else {
                    holder!!.itemTxT!!.text = ""
                }

                when {
                    position >= emptyDays && position - emptyDays < toDay -> when {
                        calendarBeanList != null && calendarBeanList!!.size > 0 -> when {
                            "true" == calendarBeanList!![position - emptyDays].type ->
                                holder!!.itemTxT!!.setBackgroundResource(R.color.colorAccent)
                            else -> holder!!.itemTxT!!.setBackgroundResource(R.color.white)
                        }
                    }
                    else -> holder!!.itemTxT!!.setBackgroundResource(R.color.white)
                }
            }
        }
        return convertView
    }

    inner class ClickDay(
            private var tv: TextView,
            private var position: Int,
            private var clickDay: Int,
            private var toDay: Int,
            private var type: String
    ) : View.OnClickListener {

        override fun onClick(view: View) {
            when {
                clickDay in 1..(toDay - 1) -> when {
                    "true" != type -> signInData.getScoreData(position, tv)
                    else -> Toast.makeText(context, "都签过了还补签啥啊你！", Toast.LENGTH_SHORT).show()
                }
                clickDay == toDay -> when {
                    "true" != type -> signInData.getSignData(position)
                    else -> Toast.makeText(context, "坑货，你今天签过到了、想骗积分？", Toast.LENGTH_SHORT).show()
                }
                clickDay > toDay -> Toast.makeText(context, "瞎点啥，还没到这一天呢", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun getSore(data: Int, position: Int, tv: TextView) {
        CustomDialog.getCalendarDialog(context, data, object : CustomDialog.ICalendar {
            override fun getCalendar() {
                Toast.makeText(context, "-10积分", Toast.LENGTH_SHORT).show()
                tv.setBackgroundResource(R.color.colorAccent)
                val calendarBean = CalendarBean()
                calendarBean.type = "true"
                calendarBean.day = calendarBeanList!![position - emptyDays].day
                calendarBeanList!![position - emptyDays] = calendarBean
                notifyDataSetChanged()
            }

            override fun defeatCalendar() {
                Toast.makeText(context, "没钱你补签个毛线啊！", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun defeatSore() = Unit

    override fun getSign(position: Int) {
        Toast.makeText(context, "签到成功，送你一个积分吧", Toast.LENGTH_SHORT).show()
        val calendarBean = CalendarBean()
        calendarBean.type = "true"
        calendarBean.day = toDay
        calendarBeanList!![position - emptyDays] = calendarBean
        notifyDataSetChanged()
    }

    override fun defeatSign() {
        Toast.makeText(context, "签到失败", Toast.LENGTH_SHORT).show()
    }

    inner class Holder {
        var titleTxT: TextView? = null
        var itemTxT: TextView? = null
    }


}
