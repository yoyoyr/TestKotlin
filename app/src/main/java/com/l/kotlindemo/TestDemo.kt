package com.l.kotlindemo

import kotlin.properties.Delegates

const val name = "yoyo"
class TestDemo {

    lateinit var b:String
    val a:String by lazy {
        ""
    }

    var name: String by Delegates.observable("one") { property, oldValue, newValue ->
        println("旧值 $oldValue 新值 $newValue")
    }


}