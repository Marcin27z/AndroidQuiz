package com.example.quiz

import android.content.Context
import com.example.quiz.entity.MyObjectBox
import io.objectbox.BoxStore


object ObjectBox {
    private var boxStore: BoxStore? = null

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    fun get(): BoxStore? {
        return boxStore
    }
}