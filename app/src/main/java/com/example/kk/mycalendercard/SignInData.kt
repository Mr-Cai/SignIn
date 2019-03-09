package com.example.kk.mycalendercard

import android.widget.TextView

/**
 * Created by kanghuicong on 2017/3/2.
 * QQ邮箱:515849594@qq.com
 */
class SignInData(internal var iSignIn: ISignIn) {

    fun getScoreData(position: Int, tv: TextView) {
        iSignIn.getSore(100, position, tv)
    }

    fun getSignData(position: Int) {
        iSignIn.getSign(position)
    }

    interface ISignIn {
        fun getSore(data: Int, position: Int, tv: TextView)

        fun defeatSore()

        fun getSign(position: Int)

        fun defeatSign()
    }
}
