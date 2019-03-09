package com.example.kk.mycalendercard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by kanghuicong on 2017/3/2.
 * QQ邮箱:515849594@qq.com
 */
object Dialog {

    fun getCalendarDialog(context: Context, score: Int, iCalendar: ICalendar) {
        val dialog = android.app.Dialog(context)
        val layout = LayoutInflater.from(context).inflate(R.layout.homepage_calendar_pay_dialog, null) as LinearLayout
        val tv_content = layout.findViewById<TextView>(R.id.tv_dialog_content)
        val bt_true = layout.findViewById<Button>(R.id.btn_true)
        val bt_false = layout.findViewById<Button>(R.id.btn_false)
        addDialog(dialog, layout)

        tv_content.text = "是否花费10积分补签？(剩余" + score + "积分)"
        bt_true.setOnClickListener {
            if (score >= 10) {
                iCalendar.getCalendar()
            } else {
                iCalendar.defeatCalendar()
            }
            dialog.dismiss()
        }

        bt_false.setOnClickListener { dialog.dismiss() }
    }

    private fun addDialog(dialog: android.app.Dialog, layout: LinearLayout) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.show()
        dialog.window!!.setContentView(layout)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
    }

    interface ICalendar {
        fun getCalendar()

        fun defeatCalendar()
    }

}
