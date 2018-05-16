package com.aceegg.oklog

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

@Suppress("unused")
object OkLog {
    private const val ARROW = "\t<--\t"
    private const val LINE_TOP_LINE = "┏━━━━━━━━━━┓"
    private const val LINE_CENTER_LINE = "┣━━━━━━━━━━┫"
    private const val LINE_BOTTOM_LINE = "┗━━━━━━━━━━┛"
    private const val NEW_LINE_START = "┃\t"
    private const val LINE_END = "\t┃\t"

    private var mTag: String = "--OkLog--"
    @JvmStatic
    private var isPrint: Boolean = true

    private var config: Configure

    init {
        config = Configure(true, true, true, 1, false)
    }

    @JvmStatic
    fun init(configure: Configure) {
        this.config = configure
    }

    @JvmStatic
    fun v(log: Any?) {
        print(log, Log.VERBOSE)
    }

    @JvmStatic
    fun d(log: Any?) {
        print(log, Log.DEBUG)
    }

    @JvmStatic
    fun i(log: Any?) {
        print(log, Log.INFO)
    }

    @JvmStatic
    fun w(log: Any?) {
        print(log, Log.WARN)
    }

    @JvmStatic
    fun e(log: Any?) {
        print(log, Log.ERROR)
    }

    private fun print(log: Any?, level: Int) {
        if (config.enable) {
            Log.println(level, mTag, String.format(createLogBody(), log))
        }
    }

    private fun createLogBody(): String {
        val builder = StringBuilder()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:")
        if (config.showTime) {
            builder.append(Date().toString())
        }
        builder.append("\r\n").append(LINE_TOP_LINE + "\r\n")
        // 线程信息
        if (config.showThread) {
            builder
                    .append(NEW_LINE_START)
                    .append("Thread")
                    .append(LINE_END)
                    .append(Thread.currentThread().name)
                    .append("\r\n" + LINE_CENTER_LINE + "\r\n")
        }
        // 调用内存栈信息
        if (config.showMethod) {
            builder
                    .append(NEW_LINE_START)
                    .append("Method")
                    .append(LINE_END)
            val stackTraceElements = Thread.currentThread().stackTrace
            val stackOffset = getStackOffset(stackTraceElements)
            for (i in stackOffset until stackOffset + config.methodCount) {
                if (i != stackOffset) {
                    builder.append(ARROW)
                }
                builder
                        .append(stackTraceElements[i].className)
                        .append("." + stackTraceElements[i].methodName + " (")
                        .append(stackTraceElements[i].fileName + ":")
                        .append(stackTraceElements[i].lineNumber)
                        .append(")")
            }
            builder.append("\r\n" + LINE_CENTER_LINE + "\r\n")
        }

        // 主体信息
        builder
                .append(NEW_LINE_START)
                .append(" Log  $LINE_END")
                .append("%s")
                .append("\r\n" + LINE_BOTTOM_LINE)
        return builder.toString()
    }

    private fun getStackOffset(stackTraceElements: Array<StackTraceElement>): Int {

        var index = 0
        // 寻找调用此library的位置
        var name: String = stackTraceElements[index].className
        while (javaClass.name != name) {
            index++
            name = stackTraceElements[index].className
        }
        while (javaClass.name == name) {
            index++
            name = stackTraceElements[index].className
        }
        return index
    }

    class ConfigureBuilder(val enable: Boolean) {

        var showThread = true
        var showMethod = true
        var methodCount = 1
        var showTime = false

        fun showThread(show: Boolean) {
            this.showThread = show
        }

        fun showMethod(show: Boolean) {
            this.showMethod = show
        }

        fun showTime(show: Boolean) {
            this.showTime = show
        }

        fun methodCount(count: Int) {
            this.methodCount = Math.max(1, count)
        }

        fun build(): Configure {
            return Configure(enable, showThread, showMethod, methodCount, showTime)
        }
    }

    class Configure internal constructor(val enable: Boolean,
                                         val showThread: Boolean,
                                         val showMethod: Boolean,
                                         val methodCount: Int,
                                         val showTime: Boolean)
}