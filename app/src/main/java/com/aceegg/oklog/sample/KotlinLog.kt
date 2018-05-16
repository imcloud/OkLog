package com.aceegg.oklog.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.aceegg.oklog.OkLog

class KotlinLog: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * how to init in kotlin
         */

        OkLog.init(with(OkLog.ConfigureBuilder(BuildConfig.DEBUG), {
            showMethod(true)
            showThread(true)
            showTime(true)
            methodCount(2)
            build()
        }))
    }
}