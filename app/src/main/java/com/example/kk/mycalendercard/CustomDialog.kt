package com.example.kk.mycalendercard

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.homepage_calendar_pay_dialog.view.*

object CustomDialog {

    fun getCalendarDialog(context: Context, score: Int, iCalendar: ICalendar) {
        val dialog = Dialog(context)
        val layout = LayoutInflater.from(context).inflate(R.layout.homepage_calendar_pay_dialog, null) as LinearLayout
        addDialog(dialog, layout)

        layout.tv_dialog_content.text = String.format(context.resources.getString(R.string.spend), score)
        layout.btn_true.setOnClickListener {
            if (score >= 10) {
                iCalendar.getCalendar()
            } else {
                iCalendar.defeatCalendar()
            }
            dialog.dismiss()
        }

        layout.btn_false.setOnClickListener { dialog.dismiss() }
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
