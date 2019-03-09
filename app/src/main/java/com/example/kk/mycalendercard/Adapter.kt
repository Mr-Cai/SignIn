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
class Adapter(private var context: Context, emptyDays: Int, private var monthDay: Int, private var toDay: Int, private var allDays: Int, private var calendarBeanList: MutableList<CalendarBean>?) : BaseAdapter(), SignInData.ISignIn {
    private var holder: Holder? = null
    private var emptyDays: Int = 0
    private var signInData: SignInData
    private var width: Int = 0

    init {
        this.emptyDays = emptyDays + 7
        width = ScreenUtils.getScreenWidth(context)
        signInData = SignInData(this)

    }

    override fun getCount(): Int {
        return allDays + 7
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    fun getType(position: Int): String {
        var type = "item"
        if (position < 7) {
            type = "title"
        } else {
            type = "item"
        }
        return type
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View? {
        var view = view
        holder = Holder()

        when (getType(position)) {
            "title" -> {
                //标题，周日---周六
                if (view == null) {
                    view = View.inflate(context, R.layout.item_title, null)
                    holder!!.tv_title = view!!.findViewById(R.id.tv_title)
                    view.tag = holder
                } else {
                    holder = view.tag as Holder
                }
                holder!!.tv_title!!.text = DateUtil.weekName[position]
            }
            "item" -> {
                if (view == null) {
                    view = View.inflate(context, R.layout.item, null)
                    holder!!.tv_item = view!!.findViewById(R.id.tv_item)
                    view.tag = holder
                } else {
                    holder = view.tag as Holder
                }

                ScreenUtils.accordHeight(holder!!.tv_item!!, width, 1, 7)//根据屏幕宽度适配

                if (position >= emptyDays && position < emptyDays + monthDay) {
                    holder!!.tv_item!!.text = (position - emptyDays + 1).toString() + ""
                    //点击事件
                    holder!!.tv_item!!.setOnClickListener(clickDay(holder!!.tv_item!!, position, position - emptyDays + 1, toDay, calendarBeanList!![position - emptyDays].type))
                } else {
                    holder!!.tv_item!!.text = ""
                }

                if (position >= emptyDays && position - emptyDays < toDay) {
                    if (calendarBeanList != null && calendarBeanList!!.size > 0) {
                        if ("true" == calendarBeanList!![position - emptyDays].type) {
                            holder!!.tv_item!!.setBackgroundResource(R.color.colorAccent)
                        } else {
                            holder!!.tv_item!!.setBackgroundResource(R.color.white)
                        }
                    }
                } else {
                    holder!!.tv_item!!.setBackgroundResource(R.color.white)
                }
            }
        }
        return view
    }

    private inner class clickDay(private var tv: TextView, private var position: Int, private var clickDay: Int, private var toDay: Int, private var type: String) : View.OnClickListener {

        override fun onClick(view: View) {
            if (0 < clickDay && clickDay < toDay) {
                if ("true" != type) {
                    signInData.getScoreData(position, tv)
                } else {
                    Toast.makeText(context, "都签过了还补签啥啊你！", Toast.LENGTH_SHORT).show()
                }
            } else if (clickDay == toDay) {
                if ("true" != type) {
                    signInData.getSignData(position)
                } else {
                    Toast.makeText(context, "坑货，你今天签过到了、想骗积分？", Toast.LENGTH_SHORT).show()
                }
            } else if (clickDay > toDay) {
                Toast.makeText(context, "瞎点啥，还没到这一天呢", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun getSore(data: Int, position: Int, tv: TextView) {
        Dialog.getCalendarDialog(context, data, object : Dialog.ICalendar {
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

    override fun defeatSore() {

    }

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
        var tv_title: TextView? = null
        var tv_item: TextView? = null
    }


}
