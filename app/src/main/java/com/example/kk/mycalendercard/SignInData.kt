package com.example.kk.mycalendercard

import android.widget.TextView

class SignInData(private var iSignIn: ISignIn) {

    fun getScoreData(position: Int, tv: TextView) = iSignIn.getSore(100, position, tv)

    fun getSignData(position: Int) = iSignIn.getSign(position)

    interface ISignIn {
        fun getSore(data: Int, position: Int, tv: TextView)
        fun defeatSore()
        fun getSign(position: Int)
        fun defeatSign()
    }
}
