package com.aceegg.oklog

import android.util.Log

/**
 * OkLog
 * <br />
 *
 * Created by imcloud on 2017/3/8.
 */
object OkLog {
    @JvmStatic val LINE_TOP_LEFT = "╔════════════════"
    @JvmStatic val LINE_TOP_RIGHT = "══════════════════════════════════════════════════════════════════════"
    @JvmStatic val LINE_ONE_LINE = "══════════════════════════════════════════════════════════════════════════════════════════════════════"
    @JvmStatic val LINE_BOTTOM_LINE = "╚══════════════════════════════════════════════════════════════════════════════════════════════════════"
    @JvmStatic val LINE_END_WITH_NEW_LINE_START = "\r\n║\t"
    @JvmStatic val LINE_END_WITH_NEW_LINE = "\r\n║"

    var mTag: String = "---[OkLog]---"
    @JvmStatic var isPrint: Boolean = true

    @JvmStatic fun init(isPrint : Boolean) {
        this.isPrint = isPrint
    }

    @JvmStatic fun v(log: Any?) {
        print(log, Log.VERBOSE)
    }

    @JvmStatic fun d(log: Any?) {
        print(log, Log.DEBUG)
    }

    @JvmStatic fun i(log: Any?) {
        print(log, Log.INFO)
    }

    @JvmStatic fun w(log: Any?) {
        print(log, Log.WARN)
    }

    @JvmStatic fun e(log: Any?) {
        print(log, Log.ERROR)
    }

    fun print(log: Any?, level: Int) {
        if (isPrint) {
            Log.println(level, mTag, String.format(createLogBody(), log))
        }
    }

    fun createLogBody(): String {
        val builder = StringBuilder()
        // 线程信息
        builder
            .append(" \r\n")
            .append(LINE_TOP_LEFT)
            .append(" [Thread: " + Thread.currentThread().name + "] ")
            .append(LINE_TOP_RIGHT)
            .append(LINE_END_WITH_NEW_LINE_START)
        // 调用内存栈信息
        val stackTraceElements = Thread.currentThread().stackTrace
        val stackOffset = getStackOffset(stackTraceElements)
        for (i in stackOffset until stackOffset+3) {
            for (j in stackOffset until i) {
                builder.append("\t")
            }
            if (i != stackOffset) {
                builder.append("∟\t")
            }
            builder
                .append(stackTraceElements[i].className)
                .append("." + stackTraceElements[i].methodName + " (")
                .append(stackTraceElements[i].fileName + ":")
                .append(stackTraceElements[i].lineNumber)
                .append(")" + LINE_END_WITH_NEW_LINE)
        }
        // 主体信息
        builder
            .append(LINE_ONE_LINE + "\r\n\r\n\t")
            .append("%s")
            .append("\r\n\r\n")
            .append(LINE_BOTTOM_LINE)
        return builder.toString()
    }

    fun getStackOffset(stackTraceElements: Array<StackTraceElement>): Int {

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
}