package com.example.kk.mycalendercard

import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

// 提示信息工具类
object ScreenUtils {

    //获取屏幕宽度
    fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    //获取屏幕高度
    private fun getScreenHeight(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    //获取状态栏的高度
    fun getStatusHeight(context: Context): Int {
        var statusHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(`object`).toString())
            statusHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusHeight
    }


    //设置控件宽度
    fun accordWidth(view: View, width: Int, molecular: Int, denominator: Int) {
        val ps = view.layoutParams
        ps.width = width / denominator * molecular
        view.layoutParams = ps
    }

    // 是否是横屏
    fun isLandscape(context: Context) =
            context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    //设置控件高度
    fun accordHeight(view: View, height: Int, molecular: Int, denominator: Int) {
        val ps = view.layoutParams
        ps.height = height / denominator * molecular
        view.layoutParams = ps
    }

}
