package com.l.kotlindemo.coroutines

import android.os.Handler
import android.os.Looper
import com.l.kotlindemo.LoggerUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

object Coroutines {

    //阻塞当前线程打印日志.主要用于在非协程环境下开启协程环境CoroutinesScope
    fun runBlockingTest() = runBlocking {
        for (i in 0..10) {
            LoggerUtils.LOGV(Thread.currentThread().name + "--" + i)
            delay(500)
        }
    }

    //在io线程执行，当时仍然会阻塞当前线程直到执行完毕
    fun runBlockingThread() = runBlocking(Dispatchers.IO) {
        for (i in 0..10) {
            LoggerUtils.LOGV(Thread.currentThread().name + "--" + i)
            delay(500)
        }
    }

    //launch任务不会阻塞接下去的ui界面
    fun scop() {
        var scop = CoroutineScope(Dispatchers.IO)
        scop.launch {
            delayPrint("scop")
        }

    }


    //launch任务会阻塞接下去的ui界面，也就是launch执行完毕，才会接着绘制ui
    fun runBlockScop() = runBlocking {
        launch(Dispatchers.IO) {
            delayPrint("launch")
        }

    }


    /**
     * GlobalScope  DefaultDispatcher-worker-1--0
    normal  main--0
    normal  main--1
    GlobalScope  DefaultDispatcher-worker-1--1
    normal  main--2
    DefaultDispatcher-worker-4--3
    GlobalScope  DefaultDispatcher-worker-4--4
    GlobalScope  DefaultDispatcher-worker-2--5
    GlobalScope  DefaultDispatcher-worker-7--6
    GlobalScope  DefaultDispatcher-worker-6--7
    GlobalScope  DefaultDispatcher-worker-1--8
    GlobalScope  DefaultDispatcher-worker-1--9
    GlobalScope  DefaultDispatcher-worker-4--10
     */
    fun luanchTest() = runBlocking {
        //launch在协程环境下启动协程
        var job = launch {
            //GlobalScope.launch会启动一个top-level协程，他的生命周期只受整个应用程序的声明周期影响
            //无法被job.cancel()取消,可被job2取消
            var job2 = GlobalScope.launch {
                for (i in 0..10) {
                    LoggerUtils.LOGV("GlobalScope  " + Thread.currentThread().name + "--" + i)
                    delay(100)//非阻塞挂起当前协程，此时cpu可能回去执行其他协程
                }
            }

            //launch的生命周期受外部协程的生命周期周期影响
            //被job.cancel()取消
            launch {
                for (i in 0..10) {
                    LoggerUtils.LOGV("normal  " + Thread.currentThread().name + "--" + i)
                    delay(100)
                }
            }
        }

        delay(300) // 延迟一会，让第二个协程能执行3次左右
        job.cancel() // 将外层任务取消了
//        job.join()    阻塞当前线程直到job执行完毕
        delay(2000)
    }


    fun asyncTest() = runBlocking {
        //async在协程环境下启动协程
        var job1 = async {
            return@async 1
        }
        var job2 = async {
            return@async 2
        }

        //阻塞协程，直到可以获得执行结果
        LoggerUtils.LOGV((job1.await() + job2.await()).toString())
    }

    //限时运行，否则返回null
    fun timeOut() = runBlocking {
        var job = withTimeoutOrNull(500) {
            try {
                LoggerUtils.LOGV("start time")
                delay(600)
                LoggerUtils.LOGV("end time")
            } catch (e: Exception) {

            }
            return@withTimeoutOrNull "hello"
        }
        LoggerUtils.LOGV(job.toString())
    }

    //切换上下文
    fun doWork() {
        var scop = CoroutineScope(Dispatchers.IO)
        scop.launch {
            LoggerUtils.LOGV("--" + Thread.currentThread().name + "--")
            withContext(Dispatchers.Main) {
                LoggerUtils.LOGV("--" + Thread.currentThread().name + "--")

            }
        }
    }

    //生产者 消费者
    fun produceTest() {

        val scop = CoroutineScope(Dispatchers.IO)
        val channel = Channel<Int>()
        scop.launch {
            launch {
                for (i in 0..10) {
                    channel.send(i)
                    delay(1000)
                }
            }

            repeat(10) {
                LoggerUtils.LOGV("receive ${channel.receive()}")
            }
        }
    }

    //suspend修饰的函数才能挂起
    suspend fun delayPrint(name: String) {
        for (i in 0..10) {
            LoggerUtils.LOGV(name + "--" + Thread.currentThread().name + "--" + i)
            delay(500)
        }
    }
}