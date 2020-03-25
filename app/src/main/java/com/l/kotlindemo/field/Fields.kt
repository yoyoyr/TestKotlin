package com.l.kotlindemo.field

import kotlin.properties.Delegates

//private static final + get
val f: String = "yoyo"
//常量，public static final
const val g: String = "yoyo"

class Field {
    //可空属性
    var a: String? = null
    val b: String? = null

    //延迟初始化
    lateinit var c: String

    //属性委托
    val d: String by lazy {
        ""
    }

    val e: String by Delegates.observable("f")//初始值
    { prop//被赋值的属性
      , old//旧值
      , new//新值
        ->
        // 赋值后执行的操作
    }

    companion object {
        //常量，public static final
        const val i: String = "i"
    }
}

object single {
    //常量，public static final
    const val h: String = "yoyo"
}