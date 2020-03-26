package com.l.kotlindemo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.l.kotlindemo.coroutines.Coroutines
import com.l.kotlindemo.coroutines.CoroutinesScopeTest

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MainActivity : AppCompatActivity() {

    val coroutinesScopeTest: CoroutinesScopeTest = CoroutinesScopeTest(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            coroutinesScopeTest.destory()
        }

        LoggerUtils.LOGV("in")
        Coroutines.produceTest()
//        Coroutines.scop()
//        coroutinesScopeTest.doWork()
        LoggerUtils.LOGV("out")
    }


    fun set(data: String) {
        findViewById<TextView>(R.id.tv).setText(data)
    }
}
