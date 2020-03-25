package com.l.kotlindemo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

//        block()
//        testCoroutine()
        testLaunch()
//        testrunBlocking()
        testAsync()
    }

    fun block() = runBlocking {
        println("--- main start ---")
        //创建任务list，若默认CommonPool线程数很多，可加大任务数量模拟，p.s. List(50)
        val deferredList = List(10) {
            serviceAsync(it)
        }
        //并行启动任务，模拟大量请求下的并发情况
        deferredList.forEach {
            runBlocking {
                println("start  " + Thread.currentThread().name)
                println("${it.await()} end")
            }
        }
        //死锁发生、永远不会执行到这里
        println("--- main end ---")
    }

    /**
     * 异步并行任务
     */
    fun serviceAsync(order: Int) = GlobalScope.async(EmptyCoroutineContext, CoroutineStart.LAZY) {
        blokingIoWork()
        order
    }

    /**
     * 模拟耗时的io操作
     */
    fun blokingIoWork() = runBlocking {
        delay(2000)
    }

    fun testCoroutine() {

        GlobalScope.launch(Dispatchers.IO) {
            LoggerUtils.LOGV("协程测试 开始执行，线程：${Thread.currentThread().name}")

            withContext(Dispatchers.Unconfined) {
                LoggerUtils.LOGV("主线程协程后面代码执行，线程：${Thread.currentThread().name}")
            }

            var response = GlobalScope.async(Dispatchers.IO) {
                return@async getResponse("123")
            }.await()

            setText(response)
        }
    }

    fun testrunBlocking() = runBlocking {
        testDelay("runBlocking")
    }

    fun testLaunch() =GlobalScope.launch{
            LoggerUtils.LOGV("launch in "+ Thread.currentThread().name)
            testDelay("launch ")
    }

    fun testAsync() = runBlocking {
        LoggerUtils.LOGV("async in "+ Thread.currentThread().name)
        var job = GlobalScope.async {
            LoggerUtils.LOGV("async in "+ Thread.currentThread().name)
            testDelay("async ")
            return@async "hello"
        }

        LoggerUtils.LOGV("job is ${job.await()}")
        LoggerUtils.LOGV("absdfsd")
    }

    suspend fun testDelay(func: String) {
        var time = System.currentTimeMillis()
        for (i in 0..10) {
            delay(100)
            LoggerUtils.LOGV("${func} delay =  ${System.currentTimeMillis() - time}")
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getToken(): String {
        LoggerUtils.LOGV("主线程协程后面代码执行，线程：${Thread.currentThread().name}")
        return "ask"
    }

    suspend fun getResponse(token: String): String {
        delay(200)
        LoggerUtils.LOGV("主线程协程后面代码执行，线程：${Thread.currentThread().name}")
        return "response"
    }

    fun setText(response: String) {
        LoggerUtils.LOGV("主线程协程后面代码执行，线程：${Thread.currentThread().name}")
    }

}
