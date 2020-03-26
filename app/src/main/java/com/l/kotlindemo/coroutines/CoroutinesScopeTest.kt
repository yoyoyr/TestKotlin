package com.l.kotlindemo.coroutines

import com.l.kotlindemo.LoggerUtils
import com.l.kotlindemo.MainActivity
import kotlinx.coroutines.*

//整个类都是协程环境。全范围可开启launch等。并且可以管理类中所有协程取消
class CoroutinesScopeTest constructor(val activity: MainActivity) :
    CoroutineScope by CoroutineScope(Dispatchers.Main) {


    fun destory() {
        cancel()
    }

    fun doWork() {
        launch {

            LoggerUtils.LOGV(Thread.currentThread().name+ " before job")
            var job1 = async(Dispatchers.IO) {
                for (i in 0..5) {
                    LoggerUtils.LOGV("--" + Thread.currentThread().name + "--" + i)
                    delay(500)
                }
                return@async 1
            }
            var job2 = async(Dispatchers.IO) {
                for (i in 0..5) {
                    LoggerUtils.LOGV("--" + Thread.currentThread().name + "--" + i)
                    delay(500)
                }
                return@async 2
            }
            LoggerUtils.LOGV(Thread.currentThread().name + " finish = ${job1.await() + job2.await()}")

        }
    }

}