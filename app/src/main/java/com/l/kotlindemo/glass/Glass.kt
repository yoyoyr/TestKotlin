package com.l.kotlindemo.glass

import android.view.View
import android.widget.Button
import com.l.kotlindemo.IFace

//这里可以@inject
//open 才可以继承
open class Glass public constructor(var a: String) {

    //初始化按声明顺序调用
    init {
        print("aaa")
    }

    //private 有set和get
    var d: Int
        set(value) {
            d = value
        }
        get() {
            return d++
        }

    //private final 有get
    val e: String
        get() {
            return e + "234"
        }

    lateinit var b: String

    //可继承属性
    open var c: String = ""

    //次构造函数必须调用其他次构造函数或者主构造函数
    //初始化先调用主构造函数，init，然后才是自己
    //变量不能为var/val
    constructor(a: String, b: String) : this(a) {
        this.b = b
    }

    //可继承
    open fun a() {

    }

    fun b() {
        var d: Button = Button(null)
        //匿名内部类/对象表达式
        d.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    //内部类
    inner class C {
        fun innerA() {
            b()
            c
        }
    }

    //实现静态方法和属性
    companion object {
        //添加后java层实现一般静态属性调用，否则需要中间加INSTANCE
        @JvmField
        var d: String = ""

        //原理如上
        @JvmStatic
        fun f() {
            F.getInstance("f")
        }
    }


}

//数据类
data class A(var a: String, val b: Int)

//密封类 类似枚举，只能在类里面继承对象
sealed class B {
    class B1 : B()

    class B2 : B()
}

//接口类
interface C {
    fun a()
}

//抽象类
abstract class D {}

//单例/对象声明
object E {

}


//懒汉 双检查+volatile单例
class F private constructor(a: String) {

    companion object {

        @Volatile
        lateinit var f: F

        fun getInstance(a: String): F {
            if (f == null) {
                synchronized(F.javaClass) {
                    if (f == null) {
                        f = F(a)
                    }
                }
            }
            return f
        }
    }
}

//动态代理
class Delegate(delegate: IFace) : IFace by delegate {}